package com.github.ngoanh2n.wds;

import com.github.ngoanh2n.Commons;
import com.github.ngoanh2n.RuntimeError;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.internal.BaseTestMethod;
import org.testng.internal.ConstructorOrMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class WDSTestNG implements IInvokedMethodListener, WebDriverProvider {
    private static final Logger log = LoggerFactory.getLogger(WDSTestNG.class);
    private static final String BE = "BE";
    private static final String BO = "BO";
    private static final String AF = "AF";
    private static ITestResult iTestResult;
    private WebDriver driver;

    public WDSTestNG() { /* No implementation necessary */ }

    //-------------------------------------------------------------------------------//

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
        getWD(testResult, BE);
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
        getWD(testResult, AF);
    }

    @Override
    public WebDriver provide() {
        if (iTestResult != null) {
            getWD(iTestResult, BO);
        }
        return driver;
    }

    //-------------------------------------------------------------------------------//

    private void getWD(ITestResult testResult, String aspect) {
        iTestResult = testResult;
        Object instance = testResult.getInstance();
        Class<?> clazz = instance.getClass();
        Field[] fields = FieldUtils.getAllFields(clazz);

        for (Field field : fields) {
            Object value = Commons.readField(instance, field.getName());

            if (value instanceof WebDriver) {
                driver = (WebDriver) value;
                break;
            }
        }

        BaseTestMethod cm = Commons.readField(iTestResult, "m_method");
        ConstructorOrMethod com = Commons.readField(cm, "m_method");

        Method method = Commons.readField(com, "m_method");
        String annotation = getSignatureAnnotation(method).getSimpleName();
        log.debug("{} @{} {} -> {}", aspect, annotation, method, driver);
    }

    private Class<?> getSignatureAnnotation(Method method) {
        Class<?>[] signatures = new Class[]{
                BeforeClass.class, BeforeMethod.class,
                Test.class,
                AfterClass.class, AfterMethod.class
        };
        Annotation[] declarations = method.getDeclaredAnnotations();

        for (Class<?> signature : signatures) {
            for (Annotation declaration : declarations) {
                if (signature.getName().equals(declaration.annotationType().getName())) {
                    return signature;
                }
            }
        }

        String msg = String.format("Get signature annotation at %s", method);
        log.error(msg);
        throw new RuntimeError(msg);
    }
}

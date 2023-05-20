package com.github.ngoanh2n.wds;

import com.github.ngoanh2n.Commons;
import com.github.ngoanh2n.RuntimeError;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.InvocationInterceptor;
import org.junit.jupiter.api.extension.ReflectiveInvocationContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Optional;

public class WDSJUnit5 implements InvocationInterceptor, WebDriverProvider {
    private static final Logger log = LoggerFactory.getLogger(WDSJUnit5.class);
    private static final String BE = "BE";
    private static final String BO = "BO";
    private static final String AF = "AF";
    private static ReflectiveInvocationContext<Method> invocationContext;
    private WebDriver driver;

    public WDSJUnit5() { /* No implementation necessary */ }

    //-------------------------------------------------------------------------------//

    @Override
    public void interceptBeforeAllMethod(Invocation<Void> invocation,
                                         ReflectiveInvocationContext<Method> invocationContext,
                                         ExtensionContext extensionContext) throws Throwable {
        getWD(invocationContext, BE);
        invocation.proceed();
        getWD(invocationContext, AF);
    }

    @Override
    public void interceptBeforeEachMethod(Invocation<Void> invocation,
                                          ReflectiveInvocationContext<Method> invocationContext,
                                          ExtensionContext extensionContext) throws Throwable {
        getWD(invocationContext, BE);
        invocation.proceed();
        getWD(invocationContext, AF);
    }

    @Override
    public void interceptTestMethod(Invocation<Void> invocation,
                                    ReflectiveInvocationContext<Method> invocationContext,
                                    ExtensionContext extensionContext) throws Throwable {
        getWD(invocationContext, BE);
        invocation.proceed();
        getWD(invocationContext, AF);
    }

    @Override
    public <T> T interceptTestFactoryMethod(Invocation<T> invocation,
                                            ReflectiveInvocationContext<Method> invocationContext,
                                            ExtensionContext extensionContext) throws Throwable {
        getWD(invocationContext, BE);
        T result = invocation.proceed();
        getWD(invocationContext, AF);
        return result;
    }

    @Override
    public void interceptTestTemplateMethod(Invocation<Void> invocation,
                                            ReflectiveInvocationContext<Method> invocationContext,
                                            ExtensionContext extensionContext) throws Throwable {
        getWD(invocationContext, BE);
        invocation.proceed();
        getWD(invocationContext, AF);
    }

    @Override
    public void interceptAfterEachMethod(Invocation<Void> invocation,
                                         ReflectiveInvocationContext<Method> invocationContext,
                                         ExtensionContext extensionContext) throws Throwable {
        getWD(invocationContext, BE);
        invocation.proceed();
        getWD(invocationContext, AF);
    }

    @Override
    public void interceptAfterAllMethod(Invocation<Void> invocation,
                                        ReflectiveInvocationContext<Method> invocationContext,
                                        ExtensionContext extensionContext) throws Throwable {
        getWD(invocationContext, BE);
        invocation.proceed();
        getWD(invocationContext, AF);
    }

    @Override
    public WebDriver provide() {
        if (invocationContext != null) {
            getWD(invocationContext, BO);
        }
        return driver;
    }

    //-------------------------------------------------------------------------------//

    private void getWD(ReflectiveInvocationContext<Method> context, String aspect) {
        invocationContext = context;
        Optional<Object> optInstance = context.getTarget();
        Object instance;
        Class<?> clazz;

        if (optInstance.isPresent()) {
            instance = optInstance.get();
            clazz = instance.getClass();
        } else {
            instance = context.getTargetClass();
            clazz = context.getTargetClass();
        }

        Field[] fields = FieldUtils.getAllFields(clazz);
        for (Field field : fields) {
            field.setAccessible(true);
            Object value;

            try {
                value = field.get(instance);
            } catch (IllegalAccessException e) {
                String fieldName = field.getName();
                String clazzName = clazz.getName();
                String msg = String.format("Read field %s in class %s", fieldName, clazzName);
                log.error(msg);
                throw new RuntimeError(msg, e);
            }

            if (value instanceof WebDriver) {
                driver = (WebDriver) value;
                break;
            }
        }

        Method method = Commons.readField(context, "method");
        String annotation = getSignatureAnnotation(method).getSimpleName();
        log.debug("{} @{} {} -> {}", aspect, annotation, method, driver);
    }

    private static Class<?> getSignatureAnnotation(Method method) {
        Class<?>[] signatures = new Class[]{
                BeforeAll.class, BeforeEach.class,
                Test.class, ParameterizedTest.class, TestFactory.class, RepeatedTest.class, TestTemplate.class,
                AfterEach.class, AfterAll.class
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

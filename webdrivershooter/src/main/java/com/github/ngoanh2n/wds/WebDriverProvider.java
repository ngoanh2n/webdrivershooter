package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebDriver;

/**
 * Provide a {@link WebDriver} to the {@link WebDriverShooter}.<br>
 * Therefore, you don't need to pass {@link WebDriver} to the argument of static APIs.<br>
 * <ul>
 *     <li>When not using SPI {@link WebDriverProvider}: <pre>{@code WebDriverShooter.page(driver)}</pre>
 *     <li>When applying SPI {@link WebDriverProvider}: <pre>{@code WebDriverShooter.page()}</pre>
 * </ul>
 * <p>
 * How to build the service provider:<br>
 * <ul>
 *      <li>1. Create a class that implements SPI {@link WebDriverProvider}
 *      <pre>{@code
 *      package com.company.project.impl;
 *
 *      import org.openqa.selenium.WebDriver;
 *      import com.github.ngoanh2n.wds.WebDriverProvider;
 *
 *      public class MyWebDriverProvider implements WebDriverProvider {
 *          public WebDriver provide() {
 *              WebDriver driver = MyStaticDriver.getDriver();
 *              return driver;
 *          }
 *      }
 *      }</pre>
 *      <li>2. Create a provider configuration file:
 *      <ul>
 *          <li>Location: {@code resources/META-INF/services}
 *          <li>Name: {@code com.github.ngoanh2n.wds.WebDriverProvider}
 *          <li>Content: {@code com.company.project.impl.MyWebDriverProvider}
 *      </ul>
 * </ul>
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 */
public interface WebDriverProvider {
    /**
     * Provide a {@link WebDriver} to the {@link WebDriverShooter}.
     *
     * @return {@link WebDriver} instance you have set up.
     */
    WebDriver provide();
}

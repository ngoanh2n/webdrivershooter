[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.ngoanh2n/webdrivershooter-junit5/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.ngoanh2n/webdrivershooter-junit5)
[![javadoc](https://javadoc.io/badge2/com.github.ngoanh2n/webdrivershooter-junit5/javadoc.svg)](https://javadoc.io/doc/com.github.ngoanh2n/webdrivershooter-junit5)
[![badge-jdk](https://img.shields.io/badge/jdk-8-blue.svg)](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
[![License: MIT](https://img.shields.io/badge/License-MIT-blueviolet.svg)](https://opensource.org/licenses/MIT)

# WebDriverShooter for JUnit5
Project is using JUnit Jupiter as a testing framework, `webdrivershooter-junit5` should be used.<br>
It automatically gets WebDriver instance from the current running test by using `org.junit.jupiter.api.extension.InvocationInterceptor` extension.<br>
You don't need to pass the WebDriver instance to the argument of shooter methods.

| webdrivershooter   	        | webdrivershooter-junit5 |
|---	                        |---	                  |
| WebDriverShooter.page(driver) | WebDriverShooter.page() |

# Declarations
## Gradle
Add to `build.gradle`
```gradle
implementation("com.github.ngoanh2n:webdrivershooter-junit5:1.0.0")
```

## Maven
Add to `pom.xml`
```xml
<dependency>
    <groupId>com.github.ngoanh2n</groupId>
    <artifactId>webdrivershooter-junit5</artifactId>
    <version>1.0.0</version>
</dependency>
```

# Test Structure
1. Must declare a field of WebDriver type with any modifiers at current class or parent/abstract class.
2. WebDriverShooter can detect WebDriver instance after the field is assigned a value.

```java
public class MyTest {
    private WebDriver driver;

    @BeforeAll
    public static void beforeAll() {
        driver = new ChromeDriver();
        // WebDriverShooter can find WebDriver instance from here.
        // WebDriverShooter.page(driver) <=> WebDriverShooter.page()
    }
}
```

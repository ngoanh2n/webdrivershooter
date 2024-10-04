[![Java](https://img.shields.io/badge/Java-17-orange)](https://adoptium.net)
[![Maven](https://img.shields.io/maven-central/v/com.github.ngoanh2n/webdrivershooter-testng?label=Maven)](https://mvnrepository.com/artifact/com.github.ngoanh2n/webdrivershooter-testng)
[![GitHub Actions](https://img.shields.io/github/actions/workflow/status/ngoanh2n/webdrivershooter/build.yml?logo=github&label=GitHub%20Actions)](https://github.com/ngoanh2n/webdrivershooter/actions/workflows/build.yml)

# WebDriverShooter for TestNG
**Table of Contents**
<!-- TOC -->
* [Declaration](#declaration)
  * [Gradle](#gradle)
  * [Maven](#maven)
* [Test Structure](#test-structure)
<!-- TOC -->

When using TestNG as a testing framework, `webdrivershooter-testng` should be used.
- `WebDriverShooter` automatically gets WebDriver instance from the current running test by using `org.testng.ITestNGListener` listener.
- You don't need to pass the `WebDriver` instance to the argument of shooting methods.

| webdrivershooter                | webdrivershooter-testng   |
|:--------------------------------|:--------------------------|
| `WebDriverShooter.page(driver)` | `WebDriverShooter.page()` |

# Declaration
## Gradle
Add to `build.gradle`
```gradle
implementation("com.github.ngoanh2n:webdrivershooter-testng:1.1.1")
```

## Maven
Add to `pom.xml`
```xml
<dependency>
    <groupId>com.github.ngoanh2n</groupId>
    <artifactId>webdrivershooter-testng</artifactId>
    <version>1.1.1</version>
</dependency>
```

# Test Structure
1. Must declare a field of `WebDriver` type with any modifiers at current class or parent/abstract class.
2. `WebDriverShooter` can detect `WebDriver` instance after the field is assigned a value.

```java
public class MyTest {
    private WebDriver driver;

    @BeforeClass
    public void beforeClass() {
        driver = new ChromeDriver();
        // WebDriverShooter could find WebDriver instance from here.
        // WebDriverShooter.page(driver) <=> WebDriverShooter.page()
    }
}
```

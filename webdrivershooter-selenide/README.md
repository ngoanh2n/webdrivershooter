[![Java](https://img.shields.io/badge/Java-17-orange)](https://adoptium.net)
[![Maven](https://img.shields.io/maven-central/v/com.github.ngoanh2n/webdrivershooter-selenide?label=Maven)](https://mvnrepository.com/artifact/com.github.ngoanh2n/webdrivershooter-selenide)
[![GitHub Actions](https://img.shields.io/github/actions/workflow/status/ngoanh2n/webdrivershooter/build.yml?logo=github&label=GitHub%20Actions)](https://github.com/ngoanh2n/webdrivershooter/actions/workflows/build.yml)

# WebDriverShooter for Selenide
**Table of Contents**
<!-- TOC -->
* [Declaration](#declaration)
  * [Gradle](#gradle)
  * [Maven](#maven)
* [Test Structure](#test-structure)
<!-- TOC -->

When using Selenide directly, `webdrivershooter-selenide` should be used.
- `WebDriverShooter` automatically get `WebDriver` instance from `com.codeborne.selenide.impl.WebDriverContainer` via `com.codeborne.selenide.WebDriverRunner.getWebDriver()` method.
- You don't need to pass the `WebDriver` instance to the argument of shooting methods.

| webdrivershooter                 | webdrivershooter-selenide   |
|:---------------------------------|:----------------------------|
| `WebDriverShooter.page(driver)`  | `WebDriverShooter.page()`   |

# Declaration
## Gradle
Add to `build.gradle`.
```gradle
implementation("com.github.ngoanh2n:webdrivershooter-selenide:1.1.1")
```

## Maven
Add to `pom.xml`.
```xml
<dependency>
    <groupId>com.github.ngoanh2n</groupId>
    <artifactId>webdrivershooter-selenide</artifactId>
    <version>1.1.1</version>
</dependency>
```

# Test Structure
```java
public class MyTest {
    public void test() {
        Selenide.open("https://github.com/ngoanh2n");
        // WebDriverShooter could find WebDriver instance from here.
        // WebDriverShooter.page(driver) <=> WebDriverShooter.page()
    }
}
```

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.ngoanh2n/webdrivershooter-selenide/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.ngoanh2n/webdrivershooter-selenide)
[![javadoc](https://javadoc.io/badge2/com.github.ngoanh2n/webdrivershooter-selenide/javadoc.svg)](https://javadoc.io/doc/com.github.ngoanh2n/webdrivershooter-selenide)
[![badge-jdk](https://img.shields.io/badge/jdk-17-blue.svg)](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
[![License: MIT](https://img.shields.io/badge/License-MIT-blueviolet.svg)](https://opensource.org/licenses/MIT)

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
implementation("com.github.ngoanh2n:webdrivershooter-selenide:1.0.0")
```

## Maven
Add to `pom.xml`.
```xml
<dependency>
    <groupId>com.github.ngoanh2n</groupId>
    <artifactId>webdrivershooter-selenide</artifactId>
    <version>1.0.0</version>
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

[![GitHub forks](https://img.shields.io/github/forks/ngoanh2n/webdrivershooter.svg?style=social&label=Fork&maxAge=2592000)](https://github.com/ngoanh2n/webdrivershooter/network/members/)
[![GitHub stars](https://img.shields.io/github/stars/ngoanh2n/webdrivershooter.svg?style=social&label=Star&maxAge=2592000)](https://github.com/ngoanh2n/webdrivershooter/stargazers/)
[![GitHub watchers](https://img.shields.io/github/watchers/ngoanh2n/webdrivershooter.svg?style=social&label=Watch&maxAge=2592000)](https://github.com/ngoanh2n/webdrivershooter/watchers/)

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.ngoanh2n/webdrivershooter/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.ngoanh2n/webdrivershooter)
[![javadoc](https://javadoc.io/badge2/com.github.ngoanh2n/webdrivershooter/javadoc.svg)](https://javadoc.io/doc/com.github.ngoanh2n/webdrivershooter)
[![GitHub release](https://img.shields.io/github/release/ngoanh2n/webdrivershooter.svg)](https://github.com/ngoanh2n/webdrivershooter/releases/)
[![badge-jdk](https://img.shields.io/badge/jdk-17-blue.svg)](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
[![License: MIT](https://img.shields.io/badge/License-MIT-blueviolet.svg)](https://opensource.org/licenses/MIT)

**Table of Contents**
<!-- TOC -->
* [WebDriverShooter](#webdrivershooter)
  * [Shoot](#shoot)
  * [Version](#version)
* [Extension](#extension)
  * [Selenide](#selenide)
  * [JUnit5](#junit5)
  * [TestNG](#testng)
* [Declaration](#declaration)
  * [Gradle](#gradle)
  * [Maven](#maven)
* [Usage](#usage)
  * [Target](#target)
  * [Strategy](#strategy)
  * [Screenshot](#screenshot)
  * [Masking](#masking)
  * [Comparison](#comparison)
  * [ShooterOptions](#shooteroptions)
<!-- TOC -->

# WebDriverShooter
## Shoot
- Target: Page, Frame, Element
- Strategy: Viewport, Vertical Scroll, Horizontal Scroll, Full Scroll (Vertical & Horizontal)

## Version
- Selenium: 4.9.1
- Selenide: 6.14.1
- JUnit5: 5.9.3
- TestNG: 7.8.0

# Extension
It automatically provides the current `WebDriver` instance to `com.github.ngoanh2n.wdc.WebDriverShooter`.<br>
You don't need to pass the `WebDriver` instance to the argument of shooting methods.

| With extension             | Without extension                |
|:---------------------------|:---------------------------------|
| `WebDriverShooter.page()`  | `WebDriverShooter.page(driver)`  |

## [Selenide](webdrivershooter-selenide#readme)
When using `Selenide` directly.

## [JUnit5](webdrivershooter-junit5#readme)
When using `JUnit Jupiter` as a testing framework.

## [TestNG](webdrivershooter-testng#readme)
When using `TestNG` as a testing framework.

# Declaration
## Gradle
Add to `build.gradle`.
```gradle
implementation("com.github.ngoanh2n:webdrivershooter:1.0.0")
```

## Maven
Add to `pom.xml`.
```xml
<dependency>
    <groupId>com.github.ngoanh2n</groupId>
    <artifactId>webdrivershooter</artifactId>
    <version>1.0.0</version>
</dependency>
```

# Usage
## Target
- `WebDriverShooter.page(..)`
- `WebDriverShooter.frame(..)`
- `WebDriverShooter.element(..)`

## Strategy
- `ShooterOptions.builder().shootViewport()`
- `ShooterOptions.builder().shootVerticalScroll()`
- `ShooterOptions.builder().shootHorizontalScroll()`
- `ShooterOptions.builder().shootFullScroll()`

## Screenshot
Below API methods are using default `ShooterOptions` with full screenshot.
- `Screenshot screenshot = WebDriverShooter.page(driver)`
- `Screenshot screenshot = WebDriverShooter.frame(frame, driver)`
- `Screenshot screenshot = WebDriverShooter.element(element, driver)`

## Masking
When taking the `iframe`, you have to pass locators instead.
- `Screenshot screenshot = WebDriverShooter.page(elementsToMask, driver)`
- `Screenshot screenshot = WebDriverShooter.frame(frame, locatorsToMask, driver)`
- `Screenshot screenshot = WebDriverShooter.element(element, elementsToMask, driver)`

**Screenshot and mask with customized `ShooterOptions`**
- Mask elements
  ```java
  ShooterOptions options = ShooterOptions
          .builder()
          .maskExceptingElements(elements)
          .build();
  Screenshot screenshot = WebDriverShooter.page(options, driver);
  ```
- Mask all excepting elements
  ```java
  ShooterOptions options = ShooterOptions
          .builder()
          .maskExceptingElements(elements)
          .build();
  Screenshot screenshot = WebDriverShooter.page(options, driver);
  ```

## Comparison
- With the image
  ```java
  Screenshot screenshot = WebDriverShooter.page(driver);
  ImageComparisonResult result = screenshot.compare(image);
  ```
- With the screenshot
  ```java
  driver.get(URL1);
  Screenshot screenshot1 = WebDriverShooter.page(driver);
  
  driver.get(URL2);
  Screenshot screenshot2 = WebDriverShooter.page(driver);
  
  ImageComparisonResult result = screenshot1.compare(screenshot2);
  Assertions.assertTrue(result.isDifferent());
  ```

## ShooterOptions
```java
ShooterOptions.builder()
        .setScrollDelay(300)                              // Set delay duration between scrolling times (Default to 200)
        .setMaskedColor(Color.GRAY)                       // Set color to mask areas (Default to GRAY)
        .checkDevicePixelRatio(true)                      // Indicate to check device pixel ratio or not (Default to true)
        
        .shootViewport()                                  // Mark as taking by viewport strategy
        .shootVerticalScroll()                            // Mark as taking by vertical scroll strategy
        .shootHorizontalScroll()                          // Mark as taking by horizontal scroll strategy
        .shootFullScroll()                                // Mark as taking by full scroll strategy (This is default option)
        
        .maskElements(locatorsToMask)                     // Set locators to mask over screenshot
        .maskElements(elementsToMask)                     // Set elements to mask over screenshot
        .maskExceptingElements(locatorsToIgnoreMasking)   // Set locators are not being masked over screenshot
        .maskExceptingElements(elementsToIgnoreMasking)   // Set elements are not being masked over screenshot
        .build();
```

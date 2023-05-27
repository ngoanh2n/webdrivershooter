[![GitHub forks](https://img.shields.io/github/forks/ngoanh2n/webdrivershooter.svg?style=social&label=Fork&maxAge=2592000)](https://github.com/ngoanh2n/webdrivershooter/network/members/)
[![GitHub stars](https://img.shields.io/github/stars/ngoanh2n/webdrivershooter.svg?style=social&label=Star&maxAge=2592000)](https://github.com/ngoanh2n/webdrivershooter/stargazers/)
[![GitHub watchers](https://img.shields.io/github/watchers/ngoanh2n/webdrivershooter.svg?style=social&label=Watch&maxAge=2592000)](https://github.com/ngoanh2n/webdrivershooter/watchers/)

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.ngoanh2n/webdrivershooter/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.ngoanh2n/webdrivershooter)
[![javadoc](https://javadoc.io/badge2/com.github.ngoanh2n/webdrivershooter/javadoc.svg)](https://javadoc.io/doc/com.github.ngoanh2n/webdrivershooter)
[![GitHub release](https://img.shields.io/github/release/ngoanh2n/webdrivershooter.svg)](https://github.com/ngoanh2n/webdrivershooter/releases/)
[![badge-jdk](https://img.shields.io/badge/jdk-8-blue.svg)](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
[![License: MIT](https://img.shields.io/badge/License-MIT-blueviolet.svg)](https://opensource.org/licenses/MIT)

<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
**Table of Contents**  *generated with [DocToc](https://github.com/thlorenz/doctoc)*

- [WebDriverShooter](#webdrivershooter)
  - [Targets](#targets)
  - [Strategies](#strategies)
  - [Versions](#versions)
- [Extensions](#extensions)
    - [Selenide](#selenide)
    - [JUnit5](#junit5)
    - [TestNG](#testng)
- [Declarations](#declarations)
  - [Gradle](#gradle)
  - [Maven](#maven)
- [Usages](#usages)
  - [ShooterOptions](#shooteroptions)
  - [PageShooter](#pageshooter)
  - [FrameShooter](#frameshooter)
  - [ElementShooter](#elementshooter)
  - [Screenshot](#screenshot)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

# WebDriverShooter

## Targets
- Page
- Frame
- Element

## Strategies
- Viewport
- Vertical Scroll
- Horizontal Scroll
- Full Scroll (Vertical & Horizontal)

## Versions
- Selenium: 4.9.1
- Selenide: 6.14.1
- JUnit5: 5.9.3
- TestNG: 7.8.0

# Extensions
It automatically provides the current WebDriver instance to `com.github.ngoanh2n.wdc.WebDriverShooter`.

You don't need to pass the WebDriver instance to the argument of checker methods.

### [Selenide](webdrivershooter-selenide#readme)
When your automation project is using `Selenide` directly.

### [JUnit5](webdrivershooter-junit5#readme)
When your automation project is using `JUnit Jupiter` as a testing framework.

### [TestNG](webdrivershooter-testng#readme)
When your automation project is using `TestNG` as a testing framework.

# Declarations
## Gradle
Add to `build.gradle`
```gradle
implementation("com.github.ngoanh2n:webdrivershooter:1.0.0")
```

## Maven
Add to `pom.xml`
```xml
<dependency>
    <groupId>com.github.ngoanh2n</groupId>
    <artifactId>webdrivershooter</artifactId>
    <version>1.0.0</version>
</dependency>
```

# Usages
## ShooterOptions
```java
ShooterOptions.builder()
        .setScrollDelay(300)                                // Set delay duration between scrolling times (Default to 200)
        .setMaskedColor(Color.GRAY)                         // Set color to mask areas (Default to GRAY)
        .checkDevicePixelRatio(true)                        // Indicate to check device pixel ratio or not (Default to true)
        
        /* [Shooting Strategies] */                         // Default to shootFullScroll()
        .shootViewport()                                    // Mark as taking by viewport strategy
        //.shootVerticalScroll()                            // Mark as taking by vertical scroll strategy
        //.shootHorizontalScroll()                          // Mark as taking by horizontal scroll strategy
        //.shootFullScroll()                                // Mark as taking by full scroll strategy
        
        .maskElements(bysToMask)                            // Set locators to mask over screenshot
        //.maskElements(elementsToMask)                     // Set elements to mask over screenshot
        //.maskExceptingElements(bysToIgnoreMasking)        // Set locators are not being masked over screenshot
        //.maskExceptingElements(elementsToIgnoreMasking)   // Set elements are not being masked over screenshot
        .build();
```

## PageShooter
- Take full page screenshot
    ```java
    Screenshot screenshot = WebDriverShooter.page(driver);
    ```
- Take full page screenshot and mask some elements
    ```java
    Screenshot screenshot = WebDriverShooter.page(elementsToMask, driver);
    ```
- Take horizontal page screenshot
    ```java
    ShooterOptions options = ShooterOptions.builder()
            .shootHorizontalScroll()
            .build();
    Screenshot screenshot = WebDriverShooter.page(options, driver);
    ```

## FrameShooter
- Take full iframe screenshot
    ```java
    Screenshot screenshot = WebDriverShooter.frame(frame, driver);
    ```
- Take full iframe screenshot and mask some elements (Should be `org.openqa.selenium.By`)
    ```java
    Screenshot screenshot = WebDriverShooter.frame(frame, bysToMask, driver);
    ```
- Take horizontal iframe screenshot
    ```java
    ShooterOptions options = ShooterOptions.builder()
            .shootHorizontalScroll()
            .build();
    Screenshot screenshot = WebDriverShooter.frame(options, frame, driver);
    ```

## ElementShooter
- Take full element screenshot
    ```java
    Screenshot screenshot = WebDriverShooter.element(element, driver);
    ```
- Take full element screenshot and mask some elements
    ```java
    Screenshot screenshot = WebDriverShooter.element(element, elementsToMask, driver);
    ```
- Take horizontal element screenshot
    ```java
    ShooterOptions options = ShooterOptions.builder()
            .shootHorizontalScroll()
            .build();
    Screenshot screenshot = WebDriverShooter.element(options, element, driver);
    ```

## Screenshot
- Save the image
    ```java
    Screenshot screenshot = WebDriverShooter.page(driver);
    screenshot.saveImage()
    //screenshot.saveImage(fileOuput)
    ```
- Save the masked image
    ```java
    Screenshot screenshot = WebDriverShooter.page(driver);
    screenshot.saveMaskedImage()
    //screenshot.saveMaskedImage(fileOuput)
    ```
- Compare with other image
    ```java
    Screenshot screenshot = WebDriverShooter.page(driver);
    ImageComparisonResult result = screenshot.compare(image);
    ```
- Compare with other screenshot
    ```java
    Screenshot screenshot = WebDriverShooter.page(driver);
    ImageComparisonResult result = screenshot.compare(screenshot);
    ```

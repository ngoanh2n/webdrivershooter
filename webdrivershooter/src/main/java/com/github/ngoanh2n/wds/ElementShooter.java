package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebDriver;

class ElementShooter extends WebDriverShooter<ElementOptions, ElementOperator> {
    @Override
    protected ElementOperator byScroll0(ElementOptions options, WebDriver driver) {
        return null;
    }

    @Override
    protected ElementOperator byScrollY(ElementOptions options, WebDriver driver) {
        return null;
    }

    @Override
    protected ElementOperator byScrollX(ElementOptions options, WebDriver driver) {
        return null;
    }

    @Override
    protected ElementOperator byScrollXY(ElementOptions options, WebDriver driver) {
        return null;
    }

    //-------------------------------------------------------------------------------//

    @Override
    protected Screenshot shootScroll0(ElementOptions options, ElementOperator operator, WebDriver driver) {
        return null;
    }

    @Override
    protected Screenshot shootScrollY(ElementOptions options, ElementOperator operator, WebDriver driver) {
        return null;
    }

    @Override
    protected Screenshot shootScrollX(ElementOptions options, ElementOperator operator, WebDriver driver) {
        return null;
    }

    @Override
    protected Screenshot shootScrollXY(ElementOptions options, ElementOperator operator, WebDriver driver) {
        return null;
    }
}

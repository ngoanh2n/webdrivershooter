package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebDriver;

class PageShooter extends WebDriverShooter<PageOptions, PageOperator> {
    @Override
    protected Screenshot shootViewport(PageOptions options, WebDriver driver) {
        return null;
    }

    @Override
    protected Screenshot shootVerticalScroll(PageOptions options, WebDriver driver) {
        return null;
    }

    @Override
    protected Screenshot shootHorizontalScroll(PageOptions options, WebDriver driver) {
        return null;
    }

    @Override
    protected Screenshot shootBothDirectionScroll(PageOptions options, WebDriver driver) {
        return null;
    }

    //-------------------------------------------------------------------------------//

    @Override
    protected PageOperator viewport(PageOptions options, WebDriver driver) {
        return null;
    }

    @Override
    protected PageOperator verticalScroll(PageOptions options, WebDriver driver) {
        return null;
    }

    @Override
    protected PageOperator horizontalScroll(PageOptions options, WebDriver driver) {
        return null;
    }

    @Override
    protected PageOperator bothDirectionScroll(PageOptions options, WebDriver driver) {
        return null;
    }
}

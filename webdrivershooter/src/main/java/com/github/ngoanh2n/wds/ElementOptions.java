package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebElement;

public interface ElementOptions extends ShooterOptions {
    static Builder builder() {
        return new Builder();
    }

    static ElementOptions defaults() {
        return builder().build();
    }

    //-------------------------------------------------------------------------------//

    WebElement element();

    //===============================================================================//

    class Builder extends ShooterOptions.Builder<Builder> {
        private WebElement element;

        public Builder setElement(WebElement element) {
            this.element = element;
            return this;
        }

        public ElementOptions build() {
            return new Defaults(this);
        }
    }

    //===============================================================================//

    class Defaults extends ShooterOptions.Defaults implements ElementOptions {
        protected Defaults(ElementOptions.Builder builder) {
            super(builder);
        }

        @Override
        public WebElement element() {
            return ((ElementOptions.Builder) builder).element;
        }
    }
}

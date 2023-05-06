package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebElement;

public interface PageOptions extends ShooterOptions {
    static <T extends Builder<T>> Builder<T> builder() {
        return new Builder<>();
    }

    static PageOptions defaults() {
        return builder().build();
    }

    //-------------------------------------------------------------------------------//

    boolean isExcepted();

    //===============================================================================//

    @SuppressWarnings("unchecked")
    class Builder<T extends Builder<T>> extends ShooterOptions.Builder<T> {
        protected boolean isExcepted;

        protected Builder() {
            super();
            this.isExcepted = false;
        }

        @Override
        public T ignoreElements(WebElement... elements) {
            this.isExcepted = false;
            return super.ignoreElements(elements);
        }

        public T ignoreExceptingElements(WebElement... elements) {
            this.isExcepted = true;
            this.maskedElements = elements;
            return (T) this;
        }

        public PageOptions build() {
            return new Defaults(this);
        }
    }

    //===============================================================================//

    class Defaults extends ShooterOptions.Defaults implements PageOptions {
        protected Defaults(PageOptions.Builder<?> builder) {
            super(builder);
        }

        @Override
        public boolean isExcepted() {
            return ((PageOptions.Builder<?>) builder).isExcepted;
        }
    }
}

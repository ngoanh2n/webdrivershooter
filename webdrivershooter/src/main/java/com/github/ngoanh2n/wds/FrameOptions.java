package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebElement;

public interface FrameOptions extends PageOptions {
    static Builder builder() {
        return new Builder();
    }

    static FrameOptions defaults() {
        return builder().build();
    }

    //-------------------------------------------------------------------------------//

    WebElement frame();

    //===============================================================================//

    class Builder extends ShooterOptions.Builder<Builder> {
        private WebElement frame;

        public Builder setFrame(WebElement frame) {
            this.frame = frame;
            return this;
        }

        public FrameOptions build() {
            return new Defaults(this);
        }
    }

    //===============================================================================//

    class Defaults extends ShooterOptions.Defaults implements FrameOptions {
        protected Defaults(FrameOptions.Builder builder) {
            super(builder);
        }

        @Override
        public WebElement frame() {
            return ((FrameOptions.Builder) builder).frame;
        }
    }
}

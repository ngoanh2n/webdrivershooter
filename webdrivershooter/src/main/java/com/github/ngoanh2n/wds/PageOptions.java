package com.github.ngoanh2n.wds;

public interface PageOptions extends ShooterOptions {
    static Builder builder() {
        return new Builder();
    }

    static PageOptions defaults() {
        return builder().build();
    }

    //===============================================================================//

    class Builder extends ShooterOptions.Builder<Builder> {
        public PageOptions build() {
            return new Defaults(this);
        }
    }

    //===============================================================================//

    class Defaults extends ShooterOptions.Defaults implements PageOptions {
        protected Defaults(PageOptions.Builder builder) {
            super(builder);
        }
    }
}

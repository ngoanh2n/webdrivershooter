package com.github.ngoanh2n.wds;

public interface PageOptions extends ShooterOptions {
    static <T extends Builder<T>> Builder<T> builder() {
        return new Builder<>();
    }

    static PageOptions defaults() {
        return builder().build();
    }

    //===============================================================================//

    class Builder<T extends Builder<T>> extends ShooterOptions.Builder<T> {
        public PageOptions build() {
            return new Defaults(this);
        }
    }

    //===============================================================================//

    class Defaults extends ShooterOptions.Defaults implements PageOptions {
        protected Defaults(PageOptions.Builder<?> builder) {
            super(builder);
        }
    }
}

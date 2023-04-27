package com.github.ngoanh2n.wds;

public interface ShooterOptions {
    static Builder<?> builder() {
        return new ShooterOptions.Builder<>();
    }

    static ShooterOptions defaults() {
        return builder().build();
    }

    //-------------------------------------------------------------------------------//

    int scrollDelay();

    int shooterStrategy();

    boolean checkDPR();

    //===============================================================================//

    @SuppressWarnings("unchecked")
    class Builder<T extends Builder<?>> {
        protected int scrollDelay;
        protected int shooterStrategy;
        protected boolean checkDevicePixelRatio;

        protected Builder() {
            this.scrollDelay = 400;
            this.shooterStrategy = 4;
            this.checkDevicePixelRatio = true;
        }

        public T shootViewport() {
            this.shooterStrategy = 1;
            return (T) this;
        }

        public T shootVerticalScroll() {
            this.shooterStrategy = 2;
            return (T) this;
        }

        public T shootHorizontalScroll() {
            this.shooterStrategy = 3;
            return (T) this;
        }

        public T shootBothDirectionScroll() {
            this.shooterStrategy = 4;
            return (T) this;
        }

        public T setScrollDelay(int value) {
            this.scrollDelay = value;
            return (T) this;
        }

        public T checkDevicePixelRatio(boolean enabled) {
            this.checkDevicePixelRatio = enabled;
            return (T) this;
        }

        public ShooterOptions build() {
            return new ShooterOptions() {
                @Override
                public int scrollDelay() {
                    return scrollDelay;
                }

                @Override
                public int shooterStrategy() {
                    return shooterStrategy;
                }

                @Override
                public boolean checkDPR() {
                    return checkDevicePixelRatio;
                }
            };
        }
    }
}

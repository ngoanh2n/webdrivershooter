package com.github.ngoanh2n.wds;

public interface ShooterOptions {
    int shooterStrategy();

    boolean checkDevicePixelRatio();

    int delayDurationForScrolling();

    //-------------------------------------------------------------------------------//

    @SuppressWarnings("unchecked")
    class Builder<T extends Builder<?>> {
        protected int shooterStrategy;
        protected boolean checkDevicePixelRatio;
        protected int delayDurationForScrolling;

        protected Builder() {
            this.shooterStrategy = 4;
            this.checkDevicePixelRatio = true;
            this.delayDurationForScrolling = 400;
        }

        public T shootViewport() {
            this.shooterStrategy = 1;
            return (T) this;
        }

        public T shootScrollVertical() {
            this.shooterStrategy = 2;
            return (T) this;
        }

        public T shootScrollHorizontal() {
            this.shooterStrategy = 3;
            return (T) this;
        }

        public T shootScrollBothDirection() {
            this.shooterStrategy = 4;
            return (T) this;
        }

        public T checkDevicePixelRatio(boolean enabled) {
            this.checkDevicePixelRatio = enabled;
            return (T) this;
        }

        public T setDelayDurationForScrolling(int value) {
            this.delayDurationForScrolling = value;
            return (T) this;
        }

        public ShooterOptions build() {
            return new ShooterOptions() {
                @Override
                public int shooterStrategy() {
                    return shooterStrategy;
                }

                @Override
                public int delayDurationForScrolling() {
                    return delayDurationForScrolling;
                }

                @Override
                public boolean checkDevicePixelRatio() {
                    return checkDevicePixelRatio;
                }
            };
        }
    }
}

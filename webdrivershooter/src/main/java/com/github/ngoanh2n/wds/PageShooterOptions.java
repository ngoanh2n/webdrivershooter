package com.github.ngoanh2n.wds;

public interface PageShooterOptions extends ShooterOptions {
    static Builder builder() {
        return new Builder();
    }

    static PageShooterOptions defaults() {
        return builder().build();
    }

    //===============================================================================//

    final class Builder extends ShooterOptions.Builder<Builder> {
        public PageShooterOptions build() {
            return new PageShooterOptions() {
                @Override
                public int delayDurationForScrolling() {
                    return delayDurationForScrolling;
                }

                @Override
                public boolean checkDevicePixelRatio() {
                    return checkDevicePixelRatio;
                }

                @Override
                public int shooterStrategy() {
                    return shooterStrategy;
                }
            };
        }
    }
}

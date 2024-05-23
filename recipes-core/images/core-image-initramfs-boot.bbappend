PACKAGE_INSTALL:append = " \
                            kernel-module-sdhci-of-dwcmshc \
                            kernel-module-reset-berlin \
                            synasdk-drivers-dolphin-pll \
                            synasdk-drivers-syna-clk \
                            synasdk-drivers-berlin-chipid"

PACKAGE_INSTALL:append:myna2 = " \
                                    initramfs-module-debug \
                                    synasdk-drivers-myna2-clks \
                                    synasdk-drivers-pinctrl-myna2"

PACKAGE_INSTALL:append:platypus = " \
                                    synasdk-drivers-platypus-clks \
                                    synasdk-drivers-pinctrl-platypus"

PACKAGE_INSTALL:append:dolphin = " \
                                    synasdk-drivers-dolphin-clks \
                                    synasdk-drivers-pinctrl-dolphin"

PACKAGE_INSTALL:append = " \
                            synasdk-drivers-dolphin-pll \
                            synasdk-drivers-syna-clk "

PACKAGE_INSTALL:append:myna2 = " \
                                    initramfs-module-debug \
                                    synasdk-drivers-myna2-clks"

PACKAGE_INSTALL:append:platypus = " \
                                    synasdk-drivers-platypus-clks"

PACKAGE_INSTALL:append:dolphin = " \
                                    synasdk-drivers-dolphin-clks"
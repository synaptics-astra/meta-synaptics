PACKAGE_INSTALL:append = " \
                            kernel-module-sdhci-of-dwcmshc \
                            kernel-module-reset-berlin"

PACKAGE_INSTALL:append:myna2 = " kernel-module-gpio-regulator"

PACKAGE_INSTALL:append:platypus = " kernel-module-gpio-regulator"

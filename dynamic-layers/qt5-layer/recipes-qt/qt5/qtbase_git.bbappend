# Configuring the features required in each module of QT Packages
PACKAGECONFIG:append:pn-qtbase = ' sm gif sql-sqlite examples pcre '
PACKAGECONFIG:append:pn-qtbase = ' glib fontconfig linuxfb kms gbm xkbcommon eglfs '
PACKAGECONFIG_DEFAULT:remove = ' tests vulkan '

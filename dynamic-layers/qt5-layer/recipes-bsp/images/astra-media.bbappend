# Adding QT packages
IMAGE_INSTALL:append = " \
    qtbase qtdeclarative qtquickcontrols2 qtquickcontrols \
    qtwayland qt3d qtquick3d \
    synasdk-qtrenderingserver \
    qtbase-examples qtdeclarative-examples \
    synasdk-syna-player-framework \
    synasdk-syna-video-player \
"

IMAGE_INSTALL:append:dolphin = " \
    synasdk-syna-ai-player \
"

IMAGE_INSTALL:append:platypus = " \
    synasdk-syna-ai-player \
"

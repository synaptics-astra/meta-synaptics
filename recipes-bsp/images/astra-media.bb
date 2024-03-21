IMAGE_FEATURES:append = " debug-tweaks package-management ssh-server-dropbear"

IMAGE_INSTALL:append = " \
    gdbserver \
    curl \
    kernel-modules \
    linux-firmware-syna \
    weston \
    weston-init \
    weston-examples \
    tee-supplicant \
    i2c-tools \
    synasdk-tee \
    synasdk-drivers-axi-meter \
    synasdk-drivers-fxl6408 \
    synasdk-drivers-bluetooth-lpm \
    synasdk-drivers-bluetooth-rfkill \
    synasdk-drivers-hl7593 \
    synasdk-drivers-rt5739 \
    synasdk-drivers-tps6286x \
    synasdk-drivers-dwc3-syna \
    synasdk-drivers-phy-syna-usb \
    synasdk-videosdk \
    alsa-utils \
    gstreamer1.0-meta-base \
    gstreamer1.0-meta-video \
    gstreamer1.0-plugins-base-app \
    gstreamer1.0-plugins-base-pango \
    gstreamer1.0-plugins-base-opengl \
    gstreamer1.0-plugins-base-rawparse \
    gstreamer1.0-plugins-good-audioparsers \
    gstreamer1.0-plugins-good-flv \
    gstreamer1.0-plugins-good-id3demux \
    gstreamer1.0-plugins-good-imagefreeze \
    gstreamer1.0-plugins-good-isomp4 \
    gstreamer1.0-plugins-good-mpg123 \
    gstreamer1.0-plugins-good-multifile \
    gstreamer1.0-plugins-good-video4linux2 \
    gstreamer1.0-plugins-good-udp \
    gstreamer1.0-plugins-good-rtp\
    gstreamer1.0-plugins-bad-autoconvert \
    gstreamer1.0-plugins-bad-camerabin \
    gstreamer1.0-plugins-bad-jpegformat \
    gstreamer1.0-plugins-bad-mpegtsdemux \
    gstreamer1.0-plugins-bad-videoparsersbad \
    gstreamer1.0-plugins-bad-waylandsink \
    gstreamer1.0-plugins-bad-kms \
    gstreamer1.0-plugins-syna \
    gstreamer1.0-meta-debug \
    synasdk-synap-models \
    synasdk-synap-framework \
    tensorflow-lite \
"

IMAGE_INSTALL:append:myna2 = " \
    synasdk-drivers-dspg-hookswitch \
    synasdk-drivers-dspg-keypad \
    synasdk-drivers-rtl8363nb \
    synasdk-drivers-tlc5917 \
    brcm-patchram-plus \
    gstreamer1.0-libav \
"

PLATYPUS_DOLPHIN_INSTALL = " \
    synasdk-drivers-sm \
    synasdk-drivers-i2c-dyndmx-pinctrl \
    synasdk-drivers-phy-berlin-pcie \
    synasdk-drivers-pcie-berlin \
    synasdk-drivers-berlin-ir \
    synasdk-drivers-syna-hwmon \
    synasdk-brcm-bt-start \
    imgtec-pvr-firmware \
    syna-trusted-app \
    synasdk-synap-module \
    tim-vx \
    libdrm-tests \
"

IMAGE_INSTALL:append:dolphin = " \
    ${PLATYPUS_DOLPHIN_INSTALL} \
"

IMAGE_INSTALL:append:platypus = " \
    ${PLATYPUS_DOLPHIN_INSTALL} \
    synasdk-drivers-sunplus \
"

TOOLCHAIN_TARGET_TASK:append = " synasdk-synap-framework-staticdev"

mount_usb () {
    cat >> ${IMAGE_ROOTFS}/etc/fstab <<EOF

/dev/sda1   /media/usb     auto     noauto,x-systemd.automount     0 2

EOF
}

ROOTFS_POSTPROCESS_COMMAND += "mount_usb; "

LICENSE = "MIT"

inherit core-image

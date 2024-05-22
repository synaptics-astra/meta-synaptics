IMAGE_FEATURES:append = " debug-tweaks package-management ssh-server-dropbear"

TEE_TZK= " \
    tee-supplicant \
    synasdk-tee \
"

TEE_OPTEE = " \
    optee-client \
    optee-os-tadevkit \
    optee-examples \
    optee-test \
"

IMAGE_INSTALL:append = " \
    gdbserver \
    curl \
    kernel-modules \
    linux-firmware-syna \
    weston \
    weston-init \
    weston-examples \
    i2c-tools \
    v4l-utils \
    synasdk-drivers-axi-meter \
    synasdk-drivers-fxl6408 \
    synasdk-drivers-bluetooth-lpm \
    synasdk-drivers-bluetooth-rfkill \
    synasdk-drivers-hl7593 \
    synasdk-drivers-rt5739 \
    synasdk-drivers-tps6286x \
    synasdk-drivers-dwc3-syna \
    synasdk-drivers-phy-syna-usb \
    synasdk-drivers-syna-clk \
    synasdk-demos \
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
    synasdk-macaddr \
    synasdk-modules-load \
    ethtool \
    gstreamer1.0-libav \
"

IMAGE_INSTALL:append:myna2 = " \
    ${TEE_OPTEE} \
    synasdk-drivers-dspg-hookswitch \
    synasdk-drivers-dspg-keypad \
    synasdk-drivers-rtl8363nb \
    synasdk-drivers-tlc5917 \
    synasdk-drivers-myna2-clks \
    brcm-patchram-plus \
    synasdk-brcm-bt-start \
"

PLATYPUS_DOLPHIN_INSTALL = " \
    ${TEE_OPTEE} \
    synasdk-drivers-sm \
    synasdk-drivers-i2c-dyndmx-pinctrl \
    synasdk-drivers-phy-berlin-pcie \
    synasdk-drivers-pcie-berlin \
    synasdk-drivers-berlin-ir \
    synasdk-drivers-syna-hwmon \
    synasdk-drivers-dolphin-pll \
    synasdk-brcm-bt-start \
    imgtec-pvr-firmware \
    syna-trusted-app \
    tim-vx \
    tflite-vx-delegate \
    libdrm-tests \
"

IMAGE_INSTALL:append:dolphin = " \
    ${PLATYPUS_DOLPHIN_INSTALL} \
    synasdk-drivers-dolphin-clks \
"

IMAGE_INSTALL:append:platypus = " \
    ${PLATYPUS_DOLPHIN_INSTALL} \
    synasdk-drivers-sunplus \
    synasdk-drivers-platypus-clks \
"

TOOLCHAIN_TARGET_TASK:append = " synasdk-synap-framework-staticdev"

mount_usb () {
    cat >> ${IMAGE_ROOTFS}/etc/fstab <<EOF

/dev/sda1   /media/usb1     auto     noauto,x-systemd.automount     0 2
/dev/sdb1   /media/usb2     auto     noauto,x-systemd.automount     0 2
/dev/sdc1   /media/usb3     auto     noauto,x-systemd.automount     0 2

EOF
}

add_version () {
    echo "${ASTRA_VERSION}" > ${IMAGE_ROOTFS}/etc/astra_version
}

ROOTFS_POSTPROCESS_COMMAND += "mount_usb; add_version; "

LICENSE = "MIT"

inherit core-image

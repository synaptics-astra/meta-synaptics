IMAGE_INSTALL:append = " \
    android-tools \
    android-tools-adbd \
    glmark2 \
    iperf2 \
"

IMAGE_INSTALL:append:platypus = " \
    bluealsa \
    pulseaudio \
"

IMAGE_INSTALL:append:dolphin = " \
    bluealsa \
    pulseaudio \
"

IMAGE_INSTALL:append:myna2 = " \
    bluealsa \
    pulseaudio \
"

#Fix me
#ROOTFS_POSTPROCESS_COMMAND in android-tools bb file does not work
#Add it here to workaround it
android_tools_enable_devmode() {
    touch ${IMAGE_ROOTFS}/var/usb-debugging-enabled
}
ROOTFS_POSTPROCESS_COMMAND += "android_tools_enable_devmode; "

BAD_RECOMMENDATIONS += "busybox-syslog"

IMAGE_INSTALL:append = " kernel-modules linux-firmware-syna \
    curl lib32-wayland \
    tee-supplicant synasdk-tee \
    weston weston-init imgtec-pvr-firmware \
    alsa-utils \
"

IMAGE_LINGUAS = ""

LICENSE = "MIT"

inherit core-image

#DISTRO_FEATURES:append = " systemd"
#INIT_MANAGER = "systemd"
#VIRTUAL-RUNTIME_init_manager= "systemd"
#DISTRO_FEATURES_BACKFILL_CONSIDERED = "sysvinit"
#VIRTUAL-RUNTIME_dev_manager = "systemd"
#VIRTUAL-RUNTIME_initscripts = ""

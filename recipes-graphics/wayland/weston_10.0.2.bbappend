# Incorporate IMG changes into Weston
FILESEXTRAPATHS:append := "${THISDIR}/weston:"

SRC_URI += "file://0003-libweston-reduce-checks-for-dmabufs-with-DRM-modifie.patch"
SRC_URI += "file://0005-backend-drm-allow-linear-framebuffers-if-no-KMS-modi.patch"
SRC_URI += "file://0006-enable-triple-buffer.patch"

REQUIRED_DISTRO_FEATURES:remove = "${@oe.utils.conditional('VIRTUAL-RUNTIME_init_manager', 'systemd', 'pam', '', d)}"

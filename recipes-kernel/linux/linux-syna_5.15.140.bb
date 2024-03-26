LINUX_VERSION ?= "5.15.140"
PR = "r1"

require linux-syna.inc
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

S = "${WORKDIR}/git"

SRC_URI = " \
    ${SYNA_SRC_LINUX_5_15} \
    ${SYNA_SRC_LINUX_5_15_OVERLAY} \
    file://add-full-hid-support.cfg \
    file://iptables.cfg \
    file://${SYNA_KERNEL_CONFIG_FILE} \
    git://git.yoctoproject.org/yocto-kernel-cache;type=kmeta;name=meta;branch=yocto-5.15;destsuffix=${KMETA} \
"

KMETA = "kernel-meta"

SRCREV_meta = "20e5ef444aa6054cea2acb756a092defeb1abf68"
SRCREV_linux_main = "${SYNA_SRCREV_LINUX_5_15}"
SRCREV_linux_overlay = "${SYNA_SRCREV_LINUX_5_15_OVERLAY}"

SRC_URI += "${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'file://systemd.cfg', '', d)}"
SRC_URI += "${@bb.utils.contains('DISTRO_FEATURES', 'bluetooth', 'file://add-bcm-bt-driver.cfg', '', d)}"

python () {
    # OpenBMC loads in kernel features via other mechanisms so this check
    # in the kernel-yocto.bbclass is not required
    d.setVar("KERNEL_DANGLING_FEATURES_WARN_ONLY","1")
}

do_patch:prepend() {
    cp -r ${WORKDIR}/${SYNA_SOURCE_PREFIX}/linux_5_15/overlay/* ${S}
}

COMPATIBLE_MACHINE = "syna"
PACKAGE_ARCH = "${MACHINE_ARCH}"

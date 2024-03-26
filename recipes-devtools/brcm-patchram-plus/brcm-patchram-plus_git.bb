DESCRIPTION = "Chromium OS Broadcom patchram utility"
HOMEPAGE = "https://chromium.googlesource.com/chromiumos/third_party/broadcom/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${S}/LICENSE.broadcom;md5=4ff9e0bf45dfdad0b7756c07b65ed24a"
S = "${WORKDIR}/git/bluetooth"
SECTION = "console/utils"

DEPENDS = "bluez5"
PR = "r1"
PV = "git+4070e71"

SRCREV = "4070e7161f2f1a1a22027a744eb868500688f0b6"
SRC_URI = "git://chromium.googlesource.com/chromiumos/third_party/broadcom;protocol=http;branch=master \
	   file://BCM4345C5_003.006.006.1066.1126.hcd \
	   file://0001-bt-auto-detect-chip-type-and-download-fw.patch \
	   file://makefile.diff \
	   "

do_install:append() {
	install -d ${D}${bindir}
	install -d ${D}${nonarch_base_libdir}/firmware/bcm/
	install -m 0755 brcm_patchram_plus ${D}${bindir}
	install -m 0755 ../../BCM4345C5_003.006.006.1066.1126.hcd ${D}${nonarch_base_libdir}/firmware/bcm/
}

FILES:${PN} = "${bindir}/*"
FILES:${PN} += "${nonarch_base_libdir}/firmware/bcm/*"


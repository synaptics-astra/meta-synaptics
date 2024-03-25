require optee.inc
SUMMARY = "OP-TEE Trusted OS TA devkit"
DESCRIPTION = "OP-TEE TA devkit for build TAs"
HOMEPAGE = "https://www.op-tee.org/"

LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://${S}/LICENSE;md5=c1f21c4f72f372ef38a5a4aee55ec173"
SRC_URI = "${SYNA_SRC_OPTEE_DEV}"

DEPENDS += "python3-pycryptodome-native"

S = "${WORKDIR}/${SYNA_SOURCE_PREFIX}/tee/optee_dev"

do_install() {
    #install TA devkit
    install -d ${D}${includedir}/optee/export-user_ta/
    for f in ${S}/export-ta_${OPTEE_ARCH}/* ; do
        cp -aR $f ${D}${includedir}/optee/export-user_ta/
    done
    mkdir -p ${D}${nonarch_base_libdir}/optee_armtz/
    install -D -p -m0444 ${S}/export-ta_${OPTEE_ARCH}/ta/*.ta ${D}${nonarch_base_libdir}/optee_armtz/
}

do_deploy() {
	echo "Do not inherit do_deploy from optee-os."
}

#FILES:${PN} = "${includedir}/optee/"
FILES:${PN} += " ${nonarch_base_libdir}/optee_armtz/ "

# Build paths are currently embedded
INSANE_SKIP:${PN}-dev += "buildpaths"

INSANE_SKIP:append = " staticdev"
INSANE_SKIP:append = " arch"
INSANE_SKIP:append = " already-stripped"


INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"

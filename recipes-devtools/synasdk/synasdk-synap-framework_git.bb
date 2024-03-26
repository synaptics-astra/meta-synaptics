DESCRIPTION = "SyNAP framework"
SECTION = "devtools"
LICENSE = "CLOSED"
PR = "r2"

COMPATIBLE_MACHINE = "syna"

SRC_URI = "${SYNA_SRC_FRAMEWORK} \
           file://synap-framework.pc.in \
           "

SRC_URI:append:platypus = " ${SYNA_SRC_SYNAP_DRIVER}"
SRC_URI:append:dolphin = " ${SYNA_SRC_SYNAP_DRIVER}"

SRCREV_synapframework = "${SYNA_SRCREV_FRAMEWORK}"
SRCREV_synapdriver = "${SYNA_SRCREV_SYNAP_DRIVER}"

SRCREV_FORMAT = "synapframework_synapdriver"

PV = "${SYNAP_VERSION}+git${SRCPV}"

S = "${WORKDIR}/${SYNA_SOURCE_PREFIX}/synap/framework"

# synap libraries are not versioned so we need to make sure they end up in the
# ${PN} package and not ${PN}-dev package
SOLIBS = ".so"
FILES_SOLIBSDEV = ""

inherit cmake

do_install:append () {
    install -d ${D}${libdir}/pkgconfig

    sed 's/@@PV@@/${PV}/g' ${WORKDIR}/synap-framework.pc.in > ${WORKDIR}/synap-framework.pc

    install -m 0644 ${WORKDIR}/synap-framework.pc ${D}${libdir}/pkgconfig/synap-framework.pc
}

NPU_DEPENDS = "synasdk-synap-driver synasdk-synap-prebuilts"

DEPENDS:platypus = "${NPU_DEPENDS}"
DEPENDS:dolphin = "${NPU_DEPENDS}"

DEPENDS:append:aarch64 = " tensorflow-lite"

EXTRA_OECMAKE = "\
  -DVSSDK_DIR=${WORKDIR}/${SYNA_SOURCE_PREFIX} \
  -DCMAKE_BUILD_TYPE=Release \
"

EXTRA_OECMAKE:append:aarch64 = " -DENABLE_TFLITERUNTIME=ON"
EXTRA_OECMAKE:append:myna2 = " -DENABLE_EBGRUNTIME=OFF"

INSANE_SKIP:${PN} += "already-stripped"
PACKAGES = "${PN} ${PN}-dbg ${PN}-dev ${PN}-staticdev"

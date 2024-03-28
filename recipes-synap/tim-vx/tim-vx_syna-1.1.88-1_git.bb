DESCRIPTION = "TIMVX C/C++ Library"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d72cd187d764d96d91db827cb65b48a7"

SRCREV = "b0dc5d7436e7c4c09ac83fa1c3e16ef101884a3b"
SRC_URI = "git://git@github.com/synaptics-synap/TIM-VX.git;protocol=https;branch=syna-v1.1.88-1"

PV = "${SYNAP_VERSION}+git${SRCPV}"

S = "${WORKDIR}/git"

inherit cmake

DEPENDS = "synasdk-synap-prebuilts synasdk-synap-framework"

EXTRA_OECMAKE = "\
  -DCMAKE_BUILD_TYPE=Release \
  -DFETCHCONTENT_FULLY_DISCONNECTED=OFF \
  -DCMAKE_POSITION_INDEPENDENT_CODE=ON \
  -DTIM_VX_ENABLE_SYNAP:STRING=ON \
  -DTIM_VX_USE_EXTERNAL_OVXLIB:STRING=ON \
  -DTIM_VX_ENABLE_VIPLITE:STRING=OFF \
  -DSYNAP_LIBDIR:PATH=${STAGING_DIR_TARGET}${libdir} \
  -DSYNAP_INCDIR:PATH=${STAGING_DIR_TARGET}${includedir} \
"

SOLIBS = ".so"
FILES_SOLIBSDEV = ""

INSANE_SKIP:${PN} += "already-stripped"
PACKAGES = "${PN} ${PN}-dbg ${PN}-dev"

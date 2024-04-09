DESCRIPTION = "TFLITE VX DELEGATE C/C++ Library"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://vx/LICENSE;md5=7d6260e4f3f6f85de05af9c8f87e6fb5"

SRCREV_FORMAT = "vx"
SRCREV_vx = "e187174507be6c030c72e7d67e8b59257b519ae6"

SRC_URI = "git://github.com/synaptics-synap/tflite-vx-delegate.git;protocol=https;branch=syna-tf_V2.11.0-1;name=vx;destsuffix=git/vx \
    file://0001-add-build-patch.patch;patchdir=vx \
"

SRCREV_abseil = "b971ac5250ea8de900eae9f95e06548d14cd95fe"

SRC_URI += "\
    git://github.com/abseil/abseil-cpp.git;protocol=https;branch=lts_2023_01_25;name=abseil;destsuffix=git/_deps/abseil-cpp \
"

PV = "${SYNAP_VERSION}+git${SRCPV}"

S = "${WORKDIR}/git"

DEPENDS = "tim-vx tensorflow-lite"

inherit cmake

OECMAKE_SOURCEPATH = "${S}/vx"

EXTRA_OECMAKE:append:aarch64 = "\
  -DCMAKE_BUILD_TYPE=Release \
  -DFETCHCONTENT_FULLY_DISCONNECTED=OFF \
  -DCMAKE_POSITION_INDEPENDENT_CODE=ON \
  -DCMAKE_SYSROOT=${PKG_CONFIG_SYSROOT_DIR} \
  -DTIM_VX_LIB:PATH=${STAGING_DIR_TARGET}${libdir}/libtim-vx.so \
  -DTIM_VX_INC:PATH=${STAGING_DIR_TARGET}${includedir} \
  -DTF_INC_DIR=${STAGING_DIR_TARGET}${includedir} \
  -DTF_SOURCES_DIR=${STAGING_DIR_TARGET}${includedir} \
  -DTFLITE_BINARY_DIR=${STAGING_DIR_TARGET}${libdir} \
  -DABSEIL_SRC_DIR=${WORKDIR}/git/_deps/abseil-cpp \
  -DEIGEN_INC_DIR=${STAGING_DIR_TARGET}${includedir}/eigen3 \
  -DGEMMLOWP_INC_DIR=${STAGING_DIR_TARGET}${includedir}/gemmlowp \
  -DPTHREADPOOL_INC_DIR=${STAGING_DIR_TARGET}${includedir}/pthreadpool/include \
"

SOLIBS = ".so"
FILES_SOLIBSDEV = ""

PACKAGES = "${PN} ${PN}-dbg ${PN}-dev"
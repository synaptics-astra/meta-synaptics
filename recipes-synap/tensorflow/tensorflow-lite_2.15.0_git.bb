DESCRIPTION = "TensorFlow Lite C/C++ Library"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4158a261ca7f2525513e31ba9c50ae98"

SRCREV_FORMAT = "tf"

SRCREV_tf = "53d32166e6fd0f48d00feb3e1a7522625c0bf8d4"
SRC_URI = "git://github.com/synaptics-synap/tensorflow.git;protocol=https;branch=syna-v2.15.0-1;name=tf "

SRC_URI:append:armv7a = "file://0001-fix-unimplemented-functions.patch"

require tensorflow-lite_2.15.0_uri.inc

SRC_URI += "\
    file://tensorflow-lite.pc.in \
"

PV = "${SYNAP_VERSION}+git${SRCPV}"

inherit cmake

S = "${WORKDIR}/git"

# issue: OE doesn't export some env var for fortran compiler
# https://gitlab.com/libeigen/eigen/-/blob/master/CMakeLists.txt?ref_type=heads#L575
# let yocto install by itself, no fortran compiler is needed
# guess install from https://github.com/libigl/eigen/blob/master/CMakeLists.txt
DEPENDS = "libeigen"

do_configure:prepend() {
    cp ${WORKDIR}/LICENSE-2.0.txt ${WORKDIR}/git/_deps/opengl_headers/opengl_headers_LICENSE.txt
    cp ${WORKDIR}/LICENSE-2.0.txt ${WORKDIR}/git/_deps/egl_headers/egl_headers_LICENSE.txt
    rm -rf ${S}/tensorflow/lite/tools/cmake/modules/FindEigen3.cmake
}

OECMAKE_SOURCEPATH = "${S}/tensorflow/lite"
OECMAKE_TARGET_COMPILE = "benchmark_model"

EXTRA_OECMAKE = "\
  -DCMAKE_BUILD_TYPE=Release \
  -DTFLITE_ENABLE_INSTALL=OFF \
  -DTFLITE_BUILD_SHARED_LIB_FAT=ON \
  -DTFLITE_ENABLE_NNAPI=ON \
  -DTFLITE_ENABLE_GPU=ON \
  -DTFLITE_ENABLE_XNNPACK=ON \
  -DXNNPACK_ENABLE_ARM_BF16=OFF \
  -DFETCHCONTENT_FULLY_DISCONNECTED=OFF \
  -DFETCHCONTENT_SOURCE_DIR_ABSEIL-CPP=${S}/_deps/abseil-cpp \
  -DFETCHCONTENT_SOURCE_DIR_CPUINFO=${S}/_deps/cpuinfo \
  -DFETCHCONTENT_SOURCE_DIR_FARMHASH=${S}/_deps/farmhash \
  -DFETCHCONTENT_SOURCE_DIR_FLATBUFFERS=${S}/_deps/flatbuffers \
  -DFETCHCONTENT_SOURCE_DIR_FP16=${S}/_deps/fp16 \
  -DFETCHCONTENT_SOURCE_DIR_GEMMLOWP=${S}/_deps/gemmlowp \
  -DFETCHCONTENT_SOURCE_DIR_ML_DTYPES=${S}/_deps/ml_dtypes \
  -DFETCHCONTENT_SOURCE_DIR_OPENCL_HEADERS=${S}/_deps/opencl_headers \
  -DFETCHCONTENT_SOURCE_DIR_RUY=${S}/_deps/ruy \
  -DFETCHCONTENT_SOURCE_DIR_VULKAN_HEADERS=${S}/_deps/vulkan_headers \
  -DFETCHCONTENT_SOURCE_DIR_EGL_HEADERS=${S}/_deps/egl_headers \
  -DFETCHCONTENT_SOURCE_DIR_OPENGL_HEADERS=${S}/_deps/opengl_headers \
  -DFETCHCONTENT_SOURCE_DIR_XNNPACK=${S}/_deps/xnnpack \
  -DFETCHCONTENT_SOURCE_DIR_FFT2D=${S}/_deps/fft2d/OouraFFT-1.0 \
  -DPTHREADPOOL_SOURCE_DIR=${S}/_deps/pthreadpool \
"

require tensorflow-lite_2.15.0_headers.inc

do_install:append () {
    install -d ${D}/${libdir}
    install -m 0755 ${B}/libtensorflow-lite.so ${D}/${libdir}

    install -d ${D}${bindir}
    install -m 755 ${B}/tools/benchmark/benchmark_model ${D}${bindir}

    install -d ${D}${includedir}/flatbuffers
    install -m 644 ${S}/_deps/flatbuffers/include/flatbuffers/*.h ${D}${includedir}/flatbuffers

    for dir in ${tflite_header_dirs}; do
        install -d ${D}${includedir}/$dir
        install -m 644 ${S}/$dir/*.h ${D}${includedir}/$dir
    done

    rm -rf ${D}${libdir}/*.a
    rm -rf ${D}${datadir}
    rm -rf ${D}${libdir}/cmake
    rm -rf ${D}${libdir}/cmake/flatbuffers
    rm -rf ${D}${libdir}/pkgconfig
    rm -rf ${D}${libdir}/pkgconfig/libcpuinfo.pc
    rm -rf ${D}${libdir}/pkgconfig/flatbuffers.pc
    rm -rf ${D}${libdir}/cmake/gemmlowp

    rm -rf ${D}/_deps/ruy
    rm -rf ${D}/_deps

    install -d ${D}${libdir}/pkgconfig

    sed 's/@@PV@@/${PV}/g' ${WORKDIR}/tensorflow-lite.pc.in > ${WORKDIR}/tensorflow-lite.pc

    install -m 0644 ${WORKDIR}/tensorflow-lite.pc ${D}${libdir}/pkgconfig/tensorflow-lite.pc
}

SOLIBS = ".so"
FILES_SOLIBSDEV = ""

FILES:${PN}-dev = " \
    ${includedir} \
    ${libdir}/pkgconfig/tensorflow-lite.pc \
"

FILES:${PN} += "${libdir}/*.so"
FILES:${PN}-benchmark = "${bindir}/benchmark_model"

INSANE_SKIP:${PN} = "already-stripped"
INSANE_SKIP = "src-uri-bad"
PACKAGES = "${PN} ${PN}-dbg ${PN}-dev ${PN}-benchmark"

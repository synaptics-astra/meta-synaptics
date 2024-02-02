SUMMARY = "ONNX Runtime is a runtime accelerator for Machine Learning models"
HOMEPAGE = "https://onnxruntime.ai"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=0f7e3b1308cb5c00b372a6e78835732d"

SRCREV = "2ac381c55397dffff327cc6efecf6f95a70f90a1"
SRC_URI = "gitsm://github.com/microsoft/onnxruntime.git;protocol=https;branch=rel-1.16.3"

SRC_URI:append:dolphin = " file://0001-mlas-disable-ARMv8-FP16-feature.patch"

require onnxruntime_1.16.3_uri.inc

PV = "${SYNAP_VERSION}+git${SRCPV}"

DEPENDS = "zlib"

S = "${WORKDIR}/git"

inherit cmake pkgconfig

OECMAKE_SOURCEPATH = "${S}/cmake"

EXTRA_OECMAKE = "\
  -DCMAKE_BUILD_TYPE=Release \
  -DFETCHCONTENT_FULLY_DISCONNECTED=OFF \
  -Donnxruntime_BUILD_SHARED_LIB=ON \
  -Donnxruntime_USE_FULL_PROTOBUF:STRING=ON \
  -Donnxruntime_ENABLE_LTO:STRING=ON \
  -Donnxruntime_GENERATE_TEST_REPORTS:STRING=OFF \
  -Donnxruntime_BUILD_UNIT_TESTS:STRING=OFF \
  -Donnxruntime_USE_NNAPI_BUILTIN:STRING=ON \
  -DBUILD_PKGCONFIG_FILES:STRING=ON \
  -DFETCHCONTENT_SOURCE_DIR_ABSEIL_CPP=${S}/_deps/abseil-cpp-20220623.1 \
  -DFETCHCONTENT_SOURCE_DIR_RE2=${S}/_deps/re2-2022-06-01 \
  -DFETCHCONTENT_SOURCE_DIR_GOOGLE_NSYNC=${S}/_deps/nsync-1.23.0 \
  -DFETCHCONTENT_SOURCE_DIR_MIMALLOC=${S}/_deps/mimalloc-2.1.1 \
  -DFETCHCONTENT_SOURCE_DIR_FLATBUFFERS=${S}/_deps/flatbuffers-1.12.0 \
  -DFETCHCONTENT_SOURCE_DIR_PROTOBUF=${S}/_deps/protobuf-21.12 \
  -DFETCHCONTENT_SOURCE_DIR_DATE=${S}/_deps/date-2.4.1 \
  -DFETCHCONTENT_SOURCE_DIR_MP11=${S}/_deps/mp11-boost-1.79.0 \
  -DFETCHCONTENT_SOURCE_DIR_NLOHMANN_JSON=${S}/_deps/json-3.10.5 \
  -DFETCHCONTENT_SOURCE_DIR_PYTORCH_CPUINFO=${S}/_deps/cpuinfo-5916273f79a21551890fd3d56fc5375a78d1598d \
  -DFETCHCONTENT_SOURCE_DIR_ONNX=${S}/_deps/onnx-e2525550194ce3d8a2c4a3af451c9d9b3ae6650e \
  -DFETCHCONTENT_SOURCE_DIR_SAFEINT=${S}/_deps/SafeInt-ff15c6ada150a5018c5ef2172401cb4529eac9c0 \
  -DFETCHCONTENT_SOURCE_DIR_GSL=${S}/_deps/GSL-4.0.0 \
  -DFETCHCONTENT_SOURCE_DIR_EIGEN=${S}/_deps/eigen-e7248b26a1ed53fa030c5c459f7ea095dfd276ac \
  -DFETCHCONTENT_SOURCE_DIR_PROTOC_BINARY=${S}/_deps/protoc_binary \
"

EXTRA_OECMAKE:append = " \
  -Donnxruntime_USE_XNNPACK:STRING=ON \
  -Donnxruntime_ENABLE_CPUINFO:STRING=ON \
  -DFETCHCONTENT_SOURCE_DIR_GOOGLEXNNPACK=${S}/_deps/XNNPACK-003c580e696a774afdc984996ee909b7c8d8128c \
  -DFETCHCONTENT_SOURCE_DIR_PSIMD=${S}/_deps/psimd-072586a71b55b7f8c584153d223e95687148a900 \
  -DFETCHCONTENT_SOURCE_DIR_FP16=${S}/_deps/FP16-0a92994d729ff76a58f692d3028ca1b64b145d91 \
  -DFETCHCONTENT_SOURCE_DIR_FXDIV=${S}/_deps/FXdiv-63058eff77e11aa15bf531df5dd34395ec3017c8 \
  -DFETCHCONTENT_SOURCE_DIR_PTHREADPOOL=${S}/_deps/pthreadpool-1787867f6183f056420e532eec640cba25efafea \
  -DFETCHCONTENT_SOURCE_DIR_microsoft_wil=${S}/_deps/wil-5f4caba4e7a9017816e47becdd918fcc872039ba \
"

INSANE_SKIP = "src-uri-bad"
PACKAGES:prepend = "onnxruntime-providers "
FILES:onnxruntime-providers = "${libdir}/libonnxruntime_providers_shared${SOLIBSDEV}"

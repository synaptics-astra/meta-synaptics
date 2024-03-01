
SRCREV_abseil = "b971ac5250ea8de900eae9f95e06548d14cd95fe"
SRCREV_cpuinfo = "959002f82d7962a473d8bf301845f2af720e0aa4"
SRCREV_farmhash = "0d859a811870d10f53a594927d0d0b97573ad06d"
SRCREV_flatbuffers = "0100f6a5779831fa7a651e4b67ef389a8752bd9b"
SRCREV_fp16 = "0a92994d729ff76a58f692d3028ca1b64b145d91"
SRCREV_gemmlowp = "e844ffd17118c1e17d94e1ba4354c075a4577b88"
SRCREV_ml_dtypes = "780b6d0ee01ffbfac45f7ec5418bc08f2b166483"
SRCREV_opencl_headers = "dcd5bede6859d26833cd85f0d6bbcee7382dc9b3"
SRCREV_ruy = "3286a34cc8de6149ac6844107dfdffac91531e72"
SRCREV_vulkan_headers = "32c07c0c5334aea069e518206d75e002ccd85389"
SRCREV_xnnpack = "90399cc240869fdb216693dc032837b219978b63"
SRCREV_fft2d = "2099dd603ea45409606557c523af24eb"
SRCREV_egl_headers = "649981109e263b737e7735933c90626c29a306f2"
SRCREV_opengl_headers = "0cb0880d91581d34f96899c86fc1bf35627b4b81"
SRCREV_pthreadpool = "4fe0e1e183925bf8cfa6aae24237e724a96479b8"
SRCREV_fxdiv = "63058eff77e11aa15bf531df5dd34395ec3017c8"
SRCREV_psimd = "072586a71b55b7f8c584153d223e95687148a900"


SRC_URI += " \
  git://github.com/abseil/abseil-cpp.git;protocol=https;branch=lts_2023_01_25;name=abseil;destsuffix=git/_deps/abseil-cpp \
  git://github.com/google/farmhash.git;protocol=https;branch=master;name=farmhash;destsuffix=git/_deps/farmhash \
  git://github.com/google/flatbuffers.git;protocol=https;branch=master;name=flatbuffers;destsuffix=git/_deps/flatbuffers \
  git://github.com/google/gemmlowp.git;protocol=https;branch=master;name=gemmlowp;destsuffix=git/_deps/gemmlowp \
  git://github.com/jax-ml/ml_dtypes.git;protocol=https;branch=main;name=ml_dtypes;destsuffix=git/_deps/ml_dtypes \
  git://github.com/KhronosGroup/OpenCL-Headers.git;protocol=https;branch=main;name=opencl_headers;destsuffix=git/_deps/opencl_headers \
  git://github.com/google/ruy.git;protocol=https;branch=master;name=ruy;destsuffix=git/_deps/ruy \
  git://github.com/KhronosGroup/Vulkan-Headers.git;protocol=https;branch=main;name=vulkan_headers;destsuffix=git/_deps/vulkan_headers \
  git://github.com/KhronosGroup/EGL-Registry.git;protocol=https;branch=main;name=egl_headers;destsuffix=git/_deps/egl_headers \
  git://github.com/KhronosGroup/OpenGL-Registry.git;protocol=https;branch=main;name=opengl_headers;destsuffix=git/_deps/opengl_headers \
  https://www.apache.org/licenses/LICENSE-2.0.txt;unpack=0;name=apachelicense \
"
SRC_URI[apachelicense.sha256sum] = "cfc7749b96f63bd31c3c42b5c471bf756814053e847c10f3eb003417bc523d30"

SRC_URI += " \
  git://github.com/pytorch/cpuinfo.git;protocol=https;branch=main;name=cpuinfo;destsuffix=git/_deps/cpuinfo \
  file://0001-fix-cpuinfo-cmake_system_processor-confliction.patch;patchdir=_deps/cpuinfo \
"

SRC_URI += " \
  git://github.com/syna-synap/XNNPACK.git;protocol=https;branch=syna-v2;name=xnnpack;destsuffix=git/_deps/xnnpack \
  file://0001-set-fxdiv-fp16-pthreadpool-cpuinfo-dir.patch;patchdir=_deps/xnnpack \
"

SRC_URI += "git://github.com/Maratyszcza/FXdiv.git;protocol=https;branch=master;name=fxdiv;destsuffix=git/_deps/fxdiv \
  git://github.com/Maratyszcza/psimd.git;protocol=https;branch=master;name=psimd;destsuffix=git/_deps/psimd \
"

SRC_URI += " \
  git://github.com/Maratyszcza/pthreadpool.git;protocol=https;branch=master;name=pthreadpool;destsuffix=git/_deps/pthreadpool \
  file://0001-set-FXDIV_SOURCE_DIR-explicitly.patch;patchdir=_deps/pthreadpool \
"

SRC_URI += " \
  git://github.com/Maratyszcza/FP16.git;protocol=https;branch=master;name=fp16;destsuffix=git/_deps/fp16 \
  file://0001-set-PSIMD_SOURCE_DIR.patch;patchdir=_deps/fp16 \
"

SRC_URI += "https://storage.googleapis.com/mirror.tensorflow.org/github.com/petewarden/OouraFFT/archive/v1.0.tar.gz;protocol=https;name=fft2d;subdir=git/_deps/fft2d"
SRC_URI[fft2d.md5sum] = "2099dd603ea45409606557c523af24eb"
SRC_URI[fft2d.sha256sum] = "5f4dabc2ae21e1f537425d58a49cdca1c49ea11db0d6271e2a4b27e9697548eb"

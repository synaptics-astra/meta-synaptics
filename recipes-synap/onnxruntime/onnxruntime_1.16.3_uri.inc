
LIC_FILES_CHKSUM += "file://_deps/abseil-cpp-20220623.1/LICENSE;md5=df52c6edb7adc22e533b2bacc3bd3915 \
  file://_deps/date-2.4.1/LICENSE.txt;md5=b5d973344b3c7bbf7535f0e6e002d017 \
  file://_deps/flatbuffers-1.12.0/LICENSE.txt;md5=3b83ef96387f14655fc854ddc3c6bd57 \
  file://_deps/flatbuffers-1.12.0/dart/LICENSE;md5=76f7cf9868f30a681efca6c6af69029d \
  file://_deps/flatbuffers-1.12.0/swift/LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57 \
  file://_deps/FP16-0a92994d729ff76a58f692d3028ca1b64b145d91/LICENSE;md5=998fb0b16ad8a4fb8bd41bf3faf2d21c \
  file://_deps/FXdiv-63058eff77e11aa15bf531df5dd34395ec3017c8/LICENSE;md5=0c2eed7ba400a6fea8fec2a582b177e8 \
  file://_deps/XNNPACK-003c580e696a774afdc984996ee909b7c8d8128c/LICENSE;md5=afa8f8a91390ab659c837da57124977c \
  file://_deps/json-3.10.5/LICENSE.MIT;md5=f969127d7b7ed0a8a63c2bbeae002588 \
  file://_deps/json-3.10.5/doc/mkdocs/docs/home/license.md;md5=970ea048f6ea7d04eeb3ba3ef9ebca40 \
  file://_deps/json-3.10.5/test/thirdparty/doctest/LICENSE.txt;md5=646df4c4443ce4b64f61e5f14cbe7acf \
  file://_deps/json-3.10.5/test/thirdparty/fifo_map/LICENSE.MIT;md5=b67209a1e36b682a8226de19d265b1e0 \
  file://_deps/json-3.10.5/third_party/amalgamate/LICENSE.md;md5=f3a63aaea4144ef8441e76dfa961d597 \
  file://_deps/json-3.10.5/third_party/cpplint/LICENSE;md5=a58572e3501e262ddd5da01be644887d \
"

SRC_URI += "https://github.com/abseil/abseil-cpp/archive/refs/tags/20220623.1.zip;protocol=https;name=abseil;subdir=git/_deps/"
SRC_URI[abseil.md5sum] = "72a05e8ae9b3122515e8ff9d30d59c15"
SRC_URI[abseil.sha1sum] = "50c137c88965cba015dfcc8fd5d9b46d23146751"
SRC_URI[abseil.sha256sum] = "54707f411cb62a26a776dad5fd60829098c181700edcd022ea5c2ca49e9b7ef1"

SRC_URI += "https://github.com/HowardHinnant/date/archive/refs/tags/v2.4.1.zip;protocol=https;name=date;subdir=git/_deps/"
SRC_URI[date.md5sum] = "6bb7a7180339566bd36eaeb1785125de"
SRC_URI[date.sha1sum] = "ea99f021262b1d804a872735c658860a6a13cc98"
SRC_URI[date.sha256sum] = "9278d7568afdf6fb880f277d5e344f92c0531459f15643bc945849c2f209dab2"

SRC_URI += "https://gitlab.com/libeigen/eigen/-/archive/e7248b26a1ed53fa030c5c459f7ea095dfd276ac/eigen-e7248b26a1ed53fa030c5c459f7ea095dfd276ac.zip;protocol=https;name=eigen;subdir=git/_deps/"
SRC_URI[eigen.md5sum] = "727e516ba35593ad19052eae935a8538"
SRC_URI[eigen.sha1sum] = "be8be39fdbc6e60e94fa7870b280707069b5b81a"
SRC_URI[eigen.sha256sum] = "f09e0073a0e0d34229103e875e2c79b2a350abb382f5bda7f615554532f56687"

SRC_URI += "https://github.com/google/flatbuffers/archive/refs/tags/v1.12.0.zip;name=flatbuffers;subdir=git/_deps/"
SRC_URI[flatbuffers.md5sum] = "a1afdbf114dec01a861c1b8c917d0fc7"
SRC_URI[flatbuffers.sha1sum] = "ba0a75fd12dbef8f6557a74e611b7a3d0c5fe7bf"
SRC_URI[flatbuffers.sha256sum] = "4b8b21adbfe8a74b90604161afcf87c125a26b86c99327e7a04525080606536c"

SRC_URI += "https://github.com/Maratyszcza/FP16/archive/0a92994d729ff76a58f692d3028ca1b64b145d91.zip;name=fp16;subdir=git/_deps/"
SRC_URI[fp16.md5sum] = "5bc28cf1b11abffd21ab3aaccbfb74ec"
SRC_URI[fp16.sha1sum] = "b985f6985a05a1c03ff1bb71190f66d8f98a1494"
SRC_URI[fp16.sha256sum] = "e66e65515fa09927b348d3d584c68be4215cfe664100d01c9dbc7655a5716d70"

SRC_URI += "https://github.com/Maratyszcza/FXdiv/archive/63058eff77e11aa15bf531df5dd34395ec3017c8.zip;name=fxdiv;subdir=git/_deps/"
SRC_URI[fxdiv.md5sum] = "6f45732f8a9726faec77405af67d26a2"
SRC_URI[fxdiv.sha1sum] = "a5658f4036402dbca7cebee32be57fb8149811e1"
SRC_URI[fxdiv.sha256sum] = "3d7b0e9c4c658a84376a1086126be02f9b7f753caa95e009d9ac38d11da444db"

SRC_URI += "https://github.com/google/nsync/archive/refs/tags/1.23.0.zip;name=nsync;subdir=git/_deps/"
SRC_URI[nsync.md5sum] = "c509bd168201394c479df5028a20a9ba"
SRC_URI[nsync.sha1sum] = "f3233450cf7156fc0bedd1b0e884eddec264897c"
SRC_URI[nsync.sha256sum] = "f0798b4ab5705641c0f8d3dc7feed0451b4c7c294f23ebd4439079a212045d67"

SRC_URI += "https://github.com/google/XNNPACK/archive/003c580e696a774afdc984996ee909b7c8d8128c.zip;name=xnnpack;subdir=git/_deps/"
SRC_URI[xnnpack.md5sum] = "e1624645d1da2626b64ddee2cfe46eb5"
SRC_URI[xnnpack.sha1sum] = "9f192e3f15e1e37ae9c78d53eeea47e45c5eb31c"
SRC_URI[xnnpack.sha256sum] = "32fadc91d9d7d624223b3c4091b66509d3215d1ff6a47ef086cc0809b4978b20"

SRC_URI += "https://github.com/nlohmann/json/archive/refs/tags/v3.10.5.zip;name=json;subdir=git/_deps/"
SRC_URI[json.md5sum] = "accaeb6a75f5972f479ef9139fa65b9e"
SRC_URI[json.sha1sum] = "f257f8dc27c5b8c085dc887b40cddd18ae1f725c"
SRC_URI[json.sha256sum] = "ea4b0084709fb934f92ca0a68669daa0fe6f2a2c6400bf353454993a834bb0bb"

LIC_FILES_CHKSUM += "file://_deps/GSL-4.0.0/LICENSE;md5=363055e71e77071107ba2bb9a54bd9a7"
SRC_URI += "https://github.com/microsoft/GSL/archive/refs/tags/v4.0.0.zip;name=gsl;subdir=git/_deps/"
SRC_URI[gsl.md5sum] = "616723bc9ce630d3f245976d00339ecf"
SRC_URI[gsl.sha1sum] = "cf368104cd22a87b4dd0c80228919bb2df3e2a14"
SRC_URI[gsl.sha256sum] = "eb91fcb10a6aa5ccb1d224e07a56c8ecffe9a1bb601fa1848276ec46a2200bfb"

LIC_FILES_CHKSUM += "file://_deps/wil-5f4caba4e7a9017816e47becdd918fcc872039ba/LICENSE;md5=b98fddd052bb2f5ddbcdbd417ffb26a8"
SRC_URI += "https://github.com/microsoft/wil/archive/5f4caba4e7a9017816e47becdd918fcc872039ba.zip;name=wil;subdir=git/_deps/"
SRC_URI[wil.md5sum] = "95c5aa5839e1b98a0428f50744f4f0f7"
SRC_URI[wil.sha1sum] = "fd119887d0d17c37adf1fc227b054befa28158ad"
SRC_URI[wil.sha256sum] = "e6193cc2910d66ce94e373ad37d60bf434a40144e36cd91e2c4fc75fb01896e7"

LIC_FILES_CHKSUM += "file://_deps/mimalloc-2.1.1/LICENSE;md5=cd47cf280095d74b469beed1f28c8e77"
SRC_URI += "https://github.com/microsoft/mimalloc/archive/refs/tags/v2.1.1.zip;name=mimalloc;subdir=git/_deps/"
SRC_URI[mimalloc.md5sum] = "63a91321dd05ef0ae8d92528638171b6"
SRC_URI[mimalloc.sha1sum] = "d5ee7d34223d0567892db5179849939c8769dc41"
SRC_URI[mimalloc.sha256sum] = "23b1bffb2eff57b1e3cb8edc9410d448db2bb43400206a151e107dcabcad773a"

#mp11-boost-1.79.0
SRC_URI += "https://github.com/boostorg/mp11/archive/refs/tags/boost-1.79.0.zip;name=mp11;subdir=git/_deps/"
SRC_URI[mp11.md5sum] = "d93250e0b4967b5471c6d881c97a895c"
SRC_URI[mp11.sha1sum] = "c8f04e378535ededbe5af52c8f969d2dedbe73d5"
SRC_URI[mp11.sha256sum] = "ed04f5d8b30cad7fc16d0e4c9f670c6df10b01845315fad696015c4e5e5e9cc8"

LIC_FILES_CHKSUM += "file://_deps/onnx-e2525550194ce3d8a2c4a3af451c9d9b3ae6650e/LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"
SRC_URI += "https://github.com/onnx/onnx/archive/e2525550194ce3d8a2c4a3af451c9d9b3ae6650e.zip;name=onnx;subdir=git/_deps/"
SRC_URI[onnx.md5sum] = "8d1c9477ca8a5f8d8a31499a946013bf"
SRC_URI[onnx.sha1sum] = "782f23d788185887f520a90535513e244218e928"
SRC_URI[onnx.sha256sum] = "a2119a7ca12e90103570df36e0205a4a03a95b35a05c99648ff73cbe63c01525"

LIC_FILES_CHKSUM += "file://_deps/protobuf-21.12/LICENSE;md5=37b5762e07f0af8c74ce80a8bda4266b \
                    file://_deps/protobuf-21.12/third_party/utf8_range/LICENSE;md5=ef460f31070fd7184bd9059de2e4b132"
SRC_URI += "https://github.com/protocolbuffers/protobuf/archive/v21.12.zip;name=protobuf;subdir=git/_deps"
SRC_URI[protobuf.md5sum] = "d084cfc9d6f86abbda9aae9e0ae0526a"
SRC_URI[protobuf.sha1sum] = "7cf2733949036c7d52fda017badcab093fe73bfa"
SRC_URI[protobuf.sha256sum] = "6a31b662deaeb0ac35e6287bda2f3369b19836e6c9f8828d4da444346f420298"

SRC_URI += "https://github.com/protocolbuffers/protobuf/releases/download/v21.12/protoc-21.12-linux-x86_64.zip;name=protoc;subdir=git/_deps/protoc_binary"
SRC_URI[protoc.md5sum] = "378c207a19f88e8fa935848d5f4c1107"
SRC_URI[protoc.sha1sum] = "338462004aa5be9fba45b35b5b4be43f69b47a90"
SRC_URI[protoc.sha256sum] = "3a4c1e5f2516c639d3079b1586e703fc7bcfa2136d58bda24d1d54f949c315e8"

LIC_FILES_CHKSUM += "file://_deps/psimd-072586a71b55b7f8c584153d223e95687148a900/LICENSE;md5=1a15870c3c6ac367fa27851881128c00"
SRC_URI += "https://github.com/Maratyszcza/psimd/archive/072586a71b55b7f8c584153d223e95687148a900.zip;name=psimd;subdir=git/_deps/"
SRC_URI[psimd.md5sum] = "ed132bb12d4a63f6fb65f9b874588ae9"
SRC_URI[psimd.sha1sum] = "1f5454b01f06f9656b77e4a5e2e31d7422487013"
SRC_URI[psimd.sha256sum] = "dc615342bcbe51ca885323e51b68b90ed9bb9fa7df0f4419dbfa0297d5e837b7"

LIC_FILES_CHKSUM += "file://_deps/pthreadpool-1787867f6183f056420e532eec640cba25efafea/LICENSE;md5=1609499688b503850848d795dce4da2d"
SRC_URI += "https://github.com/Maratyszcza/pthreadpool/archive/1787867f6183f056420e532eec640cba25efafea.zip;name=pthreadpool;subdir=git/_deps/"
SRC_URI[pthreadpool.md5sum] = "1719ffa9a72db4a0e9a018fcc16cc9fc"
SRC_URI[pthreadpool.sha1sum] = "e43e80781560c5ab404a4da20f34d846f5f5d101"
SRC_URI[pthreadpool.sha256sum] = "ec47d402b43ad763b2a33727f2459144397e8c4f1f4fae9b064576240c562557"

LIC_FILES_CHKSUM += "file://_deps/cpuinfo-5916273f79a21551890fd3d56fc5375a78d1598d/LICENSE;md5=cabca80e8e4a4bbe90e9a4b4ad53f5f4 \
                    file://_deps/cpuinfo-5916273f79a21551890fd3d56fc5375a78d1598d/deps/clog/LICENSE;md5=32150729bbb899c7ce46b1d9a0e2cd44"
SRC_URI += "https://github.com/pytorch/cpuinfo/archive/5916273f79a21551890fd3d56fc5375a78d1598d.zip;name=cpuinfo;subdir=git/_deps/"
SRC_URI[cpuinfo.md5sum] = "2f19e75129b5440fa70a3489dbac3250"
SRC_URI[cpuinfo.sha1sum] = "2be4d2ae321fada97cb39eaf4eeba5f8c85597cf"
SRC_URI[cpuinfo.sha256sum] = "2a160c527d3c58085ce260f34f9e2b161adc009b34186a2baf24e74376e89e6d"

LIC_FILES_CHKSUM += "file://_deps/re2-2022-06-01/LICENSE;md5=3b5c31eb512bdf3cb11ffd5713963760 \
                    file://_deps/re2-2022-06-01/re2/fuzzing/compiler-rt/LICENSE;md5=0bcd48c3bdfef0c9d9fd17726e4b7dab"
SRC_URI += "https://github.com/google/re2/archive/2022-06-01.zip;name=re2;subdir=git/_deps/"
SRC_URI[re2.md5sum] = "478836ff8188ab9e46a62f5081426d26"
SRC_URI[re2.sha1sum] = "aa77313b76e91b531ee7f3e45f004c6a502a5374"
SRC_URI[re2.sha256sum] = "9f3b65f2e0c78253fcfdfce1754172b0f97ffdb643ee5fd67f0185acf91a3f28"

LIC_FILES_CHKSUM += "file://_deps/SafeInt-ff15c6ada150a5018c5ef2172401cb4529eac9c0/LICENSE;md5=052fd523d0ea51ed2fb75a46627e23bf"
SRC_URI += "https://github.com/dcleblanc/SafeInt/archive/ff15c6ada150a5018c5ef2172401cb4529eac9c0.zip;name=safeint;subdir=git/_deps/"
SRC_URI[safeint.md5sum] = "fd9fb59dd2212cf4c8b0f2c2f6abb1ce"
SRC_URI[safeint.sha1sum] = "913a4046e5274d329af2806cb53194f617d8c0ab"
SRC_URI[safeint.sha256sum] = "f5c50f35aed63c9ddab90cb74be31e2ef0d9377452df0265546e8a8afe7d5f2f"
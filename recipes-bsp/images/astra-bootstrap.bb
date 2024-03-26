inherit populate_sdk

TOOLCHAIN_TARGET_TASK:append = " libtirpc-dev lib32-libtirpc-dev"
TOOLCHAIN_TARGET_TASK:append = " lib32-curl-dev lib32-openssl-dev curl-dev openssl-dev"
TOOLCHAIN_TARGET_TASK:append = " lib32-ffmpeg-dev ffmpeg-dev"
TOOLCHAIN_TARGET_TASK:append = " lib32-zlib-dev zlib-dev"

# Do not pollute the initrd image with rootfs features
IMAGE_FEATURES = ""
IMAGE_LINGUAS = ""

LICENSE = "MIT"

TOOLCHAIN_HOST_TASK += "nativesdk-cmake-dev nativesdk-meson-dev"
TOOLCHAIN_HOST_TASK += "nativesdk-patchelf nativesdk-libtirpc-dev"

PR = "r4"

#/*******************************************************************************
#*
#*             Copyright 2020, Beechwoods Software, Inc.
#*
#*******************************************************************************/

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
SRC_URI += " \
    file://0001-remove-opus-from-gstreamer-codecs.patch \
    file://0002-add-h265-to-gstreamer-codecs-when-not-svp-also.patch \
    file://0004-source-buffer-set-to-30M-for-YT4K-non-SVP.patch \
"

PACKAGECONFIG[geolocation] = "-DENABLE_GEOLOCATION=ON,-DENABLE_GEOLOCATION=OFF,geoclue glib-2.0-native"

PACKAGECONFIG:append:syna = " vp9 vp9_hdr"
# Disable geolocation, because it disables the support of mixed content
# (and currently Metrological apps do use mixed content)
#PACKAGECONFIG:append:syna = " geolocation"

# Add a flag to set the size of the MSE video buffer
TARGET_CPPFLAGS:append:syna = " \
    -DSYNA_MEDIASOURCE_VIDEO_BUFFER_SIZE=${SYNA_MEDIASOURCE_VIDEO_BUFFER_SIZE} \
"

# Add Tools so that WPELauncher is built, and install it
EXTRA_OECMAKE:remove = " \
    -DENABLE_TOOLS=OFF \
"

# WPE MUST be compiled with thumb, otherwise it crashes/does not work properly
# Set it here, in case it's not enforced at the DISTRO level
#ARM_INSTRUCTION_SET:syna = "thumb"


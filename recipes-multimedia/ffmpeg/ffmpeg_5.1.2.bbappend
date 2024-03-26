FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}-${PV}:"

SRC_URI:append:syna = " \
    file://0001-avcodec-add-ac4-packet-parser.patch \
    file://0002-avcodec-ac4-fix-ac4-stream-playback-abort-issue.patch \
    file://0003-avformat-mpegts-add-ac4-support.patch \
    file://0004-avcodec-ac3-fix-the-larger-BD-issue.patch \
    file://0005-iosm-tag-dav1-as-Dolby-Vision-av1-stream.patch \
    file://0006-avcodec-v4l2m2m-allow-lower-minimum-buffer-values.patch \
    file://0007-avcodec-v4l2_m2m_dec-resolve-resolution-change.patch \
"


FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI:append = " file://0001-Fixup-asio-old-service-removed-after-boost-1.69.patch"

EXTRA_OECMAKE:append = " -DCPP-NETLIB_BUILD_SHARED_LIBS=ON"

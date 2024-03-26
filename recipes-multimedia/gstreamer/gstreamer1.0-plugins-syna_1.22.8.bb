SUMMARY = "GStreamer Syna Plugins"
DESCRIPTION = "Synaptics Proprietary GStreamer Plugins"
SECTION = "multimedia"
LICENSE = "CLOSED"

require gstreamer1.0-plugins-common.inc

DEPENDS += "gstreamer1.0-plugins-base synasdk-synap-framework json-glib"

EXTRA_OEMESON += " \
   -Dexamples=enabled \
"

SRC_URI = "${SYNA_SRC_GSTREAMER} \
           file://ic.json \
          "

SRCREV = "${SYNA_SRCREV_GSTREAMER}"

PV = "1.22.8+git${SRCPV}"

S = "${WORKDIR}/${SYNA_SOURCE_PREFIX}/application/gstreamer-plugins-syna"

FILES:${PN} = "${datadir}/gst-ai"
do_install:append() {
        install -m 0755 -D ${WORKDIR}/ic.json ${D}${datadir}/gst-ai/ic.json
}

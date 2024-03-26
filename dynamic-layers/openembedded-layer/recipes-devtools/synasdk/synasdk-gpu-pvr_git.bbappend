FILESEXTRAPATHS:append := "${THISDIR}/files:"

RDEPENDS:${PN} += " \
    opencl-icd-loader \
"

SRC_URI += " \
    file://IMG.icd \
"

do_install:append() {
    mkdir -p ${D}/etc/OpenCL/vendors/
    install -m 0755 ${WORKDIR}/IMG.icd ${D}/etc/OpenCL/vendors/
}

FILES:${PN} += " \
    /etc/OpenCL/vendors/IMG.icd \
"

DESCRIPTION = "Synaptics modules load service"
SECTION = "devtools"
LICENSE = "CLOSED"
PR = "r0"

inherit base systemd

SRC_URI = " \
   file://synasdk-modules-load.service \
"
SYNA_KERNEL_MODULE_LOAD = " \
    amp_bm \
    vpu \
    syna_drm \
    pvrsrvkm \
"

SYNA_KERNEL_MODULE_LOAD:append:dolphin = " \
    scaler \
    vvcam_video \
    synap \
"

SYNA_KERNEL_MODULE_LOAD:append:platypus = " \
    synap \
"


do_install() {
    install -d ${D}${sbindir}
    install -d ${D}${systemd_system_unitdir}

	module_load_file=${D}${sbindir}/synasdk-modules-load
	rm -rf ${module_load_file}
	for module in ${SYNA_KERNEL_MODULE_LOAD};do
		echo "modprobe ${module}" >> ${D}${sbindir}/synasdk-modules-load
	done
	chmod +x ${module_load_file}

    install -m 0644 ${WORKDIR}/synasdk-modules-load.service ${D}/${systemd_system_unitdir}/synasdk-modules-load.service
}

SYSTEMD_PACKAGES = "synasdk-modules-load"
SYSTEMD_SERVICE:synasdk-modules-load = "synasdk-modules-load.service"
INITSCRIPT_NAME = "synasdk-modules-load"
INITSCRIPT_PACKAGES = "synasdk-modules-load"
INITSCRIPT_PARAMS = "start 8 5 2 . stop 21 0 1 6 ."

FILES:${PN} = " \
    ${sbindir}/synasdk-modules-load \
    ${systemd_system_unitdir}/synasdk-modules-load.service \
"

PACKAGE_INSTALL:remove = " \
    weston weston-init \
"

#DISTRO_FEATURES = "ext2 ipv4 ipv6 largefile multiarch ${DISTRO_FEATURES_LIBC}"
#DISTRO_FEATURES_NATIVESDK = "ext2 ipv4 ipv6 largefile multiarch ${DISTRO_FEATURES_LIBC}"

DISTRO_FEATURES:remove = "offline_apps wifi bluez5 bluetooth ${EXTRA_BLUETOOTH_STUFF} \
                          netflix_wayland_client waylandopencdm \
                          ${DISTRO_FEATURES_RDK} ${WEBBACKENDS} \
                          ledmgr build_rne rdkshell thunder_security_disable \
                          enable_icrypto_openssl \
                          opencdm clearkey cobalt-plugin playreadycdmi_cryptanium \
                          widevine_hardware cgroup xcal_device lxy cpg-ecfs safec logbacktrace"

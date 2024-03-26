PACKAGECONFIG:append = "fdkaac"
EXTRA_OEMESON:remove = "-Dfdkaac=disabled"

PACKAGECONFIG[fdkaac] = "-Dfdkaac=enabled,-Dfdkaac=disabled,fdk-aac"

do_compile:prepend() {
    sed -ie 's/$(MAKE) $(AM_MAKEFLAGS) all-recursive/$(MAKE) -j1 $(AM_MAKEFLAGS) all-recursive/' ${B}/Makefile
}

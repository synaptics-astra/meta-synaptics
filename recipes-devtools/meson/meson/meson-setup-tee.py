#!/usr/bin/env python3

import os
import string
import sys
import re
import stat
from pathlib import Path

class Template(string.Template):
    delimiter = "@"

class Environ():
    def get_sysroot(self):
        val = os.environ['OECORE_TARGET_SYSROOT']
        p = Path(val)
        p = p.parent / 'arm-eabi-tee'
        return str(p)

    def __getitem__(self, name):
        val = os.environ[name]
        val = val.split()

        if name == 'CC':
            val = ['arm-eabi-gcc']
            val += ['-mfloat-abi=soft', '-march=armv7-a', '-fno-builtin',
                    '--sysroot=%s' % self.get_sysroot()]
        elif name == 'CFLAGS':
            val += ['-D__TRUSTEDAPP__', '-DTZ_3_0', '-fshort-enums', '-Wstrict-prototypes']
        elif name == 'LDFLAGS':
            val = ['{}/usr/lib/crt0.o'.format(self.get_sysroot()),
                    '-Wl,--hash-style=sysv', '-nostdlib',
                    '-lm', '-lgloss', '-lteei', '-lc', '-lgcc',
                    '-T', '{}/usr/lib/ta.lds'.format(self.get_sysroot()), '-Ttext', '0x100'
                  ]
        elif name == 'OECORE_TARGET_SYSROOT':
            val = ['%s' % self.get_sysroot()]

        if len(val) > 1:
            val = ["'%s'" % x for x in val]
            val = ', '.join(val)
            val = '[%s]' % val
        elif val:
            val = "'%s'" % val.pop()
        return val

try:
    sysroot = os.environ['OECORE_NATIVE_SYSROOT']
except KeyError:
    print("Not in environment setup, bailing")
    sys.exit(1)

template_file = os.path.join(sysroot, 'usr/share/meson/meson.cross.template')
cross_file = os.path.join(sysroot, 'usr/share/meson/%smeson.cross' % 'arm-eabi-tee-')

with open(template_file) as in_file:
    template = in_file.read()
    output = Template(template).substitute(Environ())
    with open(cross_file, "w") as out_file:
        out_file.write(output.replace("system = 'linux'", "system = 'teeos'"))

meson_wrapper_file = os.path.join(sysroot, 'usr/bin/meson')
tee_meson_exe = os.path.join(sysroot, 'usr/bin/meson-tee')
with open(meson_wrapper_file) as in_file:
    lines = in_file.readlines()
    with open(tee_meson_exe, "w") as out_file:
        for l in lines:
            out_file.write(l.replace('${TARGET_PREFIX}', 'arm-eabi-tee-'))
    os.chmod(tee_meson_exe, stat.S_IRWXU | stat.S_IRGRP | stat.S_IXGRP)

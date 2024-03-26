#/*******************************************************************************
#*
#*             Copyright 2020, Beechwoods Software, Inc.
#*
#*******************************************************************************/

# Normally, when compiled on ARM, db is configured using
# "--with-mutex=ARM/gcc-assembly" to use SWP instructions that became obsoleted
# on ARMv8. When compiling on ARMv8 these settings are disabled.
MUTEX:armv8 = ""


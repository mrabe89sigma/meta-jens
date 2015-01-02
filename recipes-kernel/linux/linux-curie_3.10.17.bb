# Copyright (C) 2014 Shanghai Zhixing Information Technology Co.Ltd

SUMMARY = "Linux Kernel for Curie Board"
DESCRIPTION = "Linux Kernel for Curie Board"

require recipes-kernel/linux/linux-imx.inc
require recipes-kernel/linux/linux-dtb.inc

DEPENDS += "lzop-native bc-native"

SRCBRANCH = "curie_3.10.17_1.0.1_ga"
LOCALVERSION = "+curie"

SRC_URI = "git://github.com/rehsack/linux-curie.git;branch=${SRCBRANCH};rev=ca3c85c1e290dbc66c95bc1eea2fbc83293bc94a \
           file://0001-Importing-rtl8189es_4.3.0-driver.patch \
           file://0002-include-rtl8189es-driver-in-kernel-build.patch \
           file://0003-Add-platform-specific-modifications-for-Curie.patch \
           file://0004-don-t-printout-debug-message-when-DBG-is-off.patch \
	   file://0099-respect_config_pm_mxs.patch \
	   file://defconfig \
          "

# patches for curie
COMPATIBLE_MACHINE = "(curie)"

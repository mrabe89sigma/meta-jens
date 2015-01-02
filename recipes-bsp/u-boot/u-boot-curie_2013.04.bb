# Copyright (C) 2014 Shanghai Zhixing Information Technology Co.Ltd

DESCRIPTION = "Bootloader for Curie Board"
require recipes-bsp/u-boot/u-boot.inc

PROVIDES += "u-boot"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=1707d6db1d42237583f50183a5651ecb"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

SRCBRANCH = "curie_v2013.04_3.10.17"
SRC_URI = "git://github.com/rehsack/uboot-curie.git;branch=${SRCBRANCH};rev=b1ee64b7153445e77a6a8a5de47910d2ddbeface \
           file://bootsettings.patch \
	   "

S = "${WORKDIR}/git"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(curie)"


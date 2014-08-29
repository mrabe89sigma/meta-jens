FILESEXTRAPATHS_prepend := "${THISDIR}/prd-flash:"

LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

SRC_URI = "file://flash-device.sh \
"

do_install () {
	install -d ${D}${sysconfdir}/init.d

	install -m 0755 ${WORKDIR}/flash-device.sh ${D}${sysconfdir}/init.d

	update-rc.d -r ${D} flash-device.sh start 99 3 5 .
}
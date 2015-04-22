DESCRIPTION = "RDM Zway BLOB"
HOMEPAGE = "http://www.rademacher.de"
LICENSE = "commercial"
LIC_FILES_CHKSUM = "file://${THISDIR}/files/license.txt;md5=3ebe3464e841ddbf115af1f7019017c5"
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

PR = "72"
SRC_URI = "http://internal.rdm.local/blobs/z-way-server-Linux-HomePilot2-v${PV}.tgz;name=server \
	   file://config.xml"

DEPENDS = "hp2-base"
RDEPENDS_${PN} += "hp2-base"
RDEPENDS_${PN} += "libarchive"
RDEPENDS_${PN} += "libxml2"
RDEPENDS_${PN} += "openssl"
RDEPENDS_${PN} += "yajl"
RDEPENDS_${PN} += "curl"
RDEPENDS_${PN} += "v8"
RDEPENDS_${PN} += "zlib"

SRC_URI[server.md5sum] = "0de675f8fc08cf83ccc363404806fb11"
SRC_URI[server.sha256sum] = "1006970f683c5755a1260f56e609a7d4b103900519b264abcae2f80dd2a63c1d"

S = "${WORKDIR}/z-way-server"

INST_DEST_PREFIX="/opt/z-way"
CONF_DEST_PREFIX="/home/homepilot/z-way"

ZW_TTY_DEVICE = "/dev/ttyZWave"

do_install() {
        # create target install folders
        install -d ${D}${INST_DEST_PREFIX}
	install -d ${D}${sysconfdir}

        # Extract tarball into INST_DEST_PREFIX dir of target
	(cd ${S} && tar cf - .) | (cd ${D}${INST_DEST_PREFIX} && tar xf -)
	rm -f ${D}${INST_DEST_PREFIX}/automation/.gitignore \
	      ${D}${INST_DEST_PREFIX}/htdocs/z-way-ha-tv/.gitignore \
	      ${D}${INST_DEST_PREFIX}/htdocs/expert/.gitignore
	rmdir ${D}${INST_DEST_PREFIX}/patches

	# Move config directory into CONF_DEST_PREFIX dir of target
	install -o homepilot -g users -d ${D}${CONF_DEST_PREFIX}
	mv ${D}${INST_DEST_PREFIX}/config ${D}${CONF_DEST_PREFIX}
	mv ${D}${INST_DEST_PREFIX}/automation/storage ${D}${CONF_DEST_PREFIX}
	chown -R homepilot:users ${D}${CONF_DEST_PREFIX}

	# Create link to config
	(cd ${D}${INST_DEST_PREFIX} && ln -s ${CONF_DEST_PREFIX}/config)
	(cd ${D}${INST_DEST_PREFIX}/automation && ln -s ${CONF_DEST_PREFIX}/storage)

	# Create link to zddx configuration
	(cd ${D}${CONF_DEST_PREFIX}/config && rm -rf zddx && ln -sf /home/homepilot/.homepilot/z-way zddx)

	# Move tty-config directory into sysconfig dir of target
	mv ${D}${INST_DEST_PREFIX}/z-get-tty-config ${D}${sysconfdir}
	(cd ${D}${INST_DEST_PREFIX} && ln -s ${sysconfdir}/z-get-tty-config)

	# Install custom config file
	install ${WORKDIR}/config.xml ${D}${sysconfdir}/z-way.conf
	rm -f ${D}${INST_DEST_PREFIX}/config.xml
	ln -sf ${sysconfdir}/z-way.conf ${D}${INST_DEST_PREFIX}/config.xml

	# Edit config file
	sed -i -e "s#\"port\":\"/dev/ttyACM0\"#\"port\":\"${ZW_TTY_DEVICE}\"#" ${D}${CONF_DEST_PREFIX}/storage/configjson-06b2d3b23dce96e1619d2b53d6c947ec.json

	# Clean-up ZDDX device files
	cd ${D}${INST_DEST_PREFIX}/ZDDX/
	rm *.xml
	python2 MakeIndex.py

	# Fix permissions
	# access("automation/modules/ZWave/index.js", R_OK) = -1 EACCES (Permission denied)
	chmod 644 ${D}${INST_DEST_PREFIX}/automation/modules/ZWave/index.js
}

INSANE_SKIP_${PN} += "already-stripped"

FILES_${PN} += "${INST_DEST_PREFIX} \
        ${CONF_DEST_PREFIX} \
	${sysconfdir}"
FILES_${PN}-dbg += "${INST_DEST_PREFIX}/.debug ${INST_DEST_PREFIX}/*/.debug"
FILES_${PN}-dev += "${INST_DEST_PREFIX}/*/*.h"
FILES_${PN}-staticdev += "${INST_DEST_PREFIX}/*/*.a"

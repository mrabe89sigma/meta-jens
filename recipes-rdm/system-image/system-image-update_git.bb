DESCRIPTION = "System::Image::Update helps managing updates of OS images \
in embedded systems"

SECTION = "libs"
LICENSE = "Artistic-1.0 | GPL-2.0"
PR = "r0"

MAINTAINER=	"HP2 Dev Team <verteiler.hp2dev.team@rademacher.com>"
HOMEPAGE=	"https://github.com/rehsack/System-Image-Update"

LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Artistic-1.0;md5=cda03bbdc3c1951996392b872397b798 \
file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

SRC_URI = "git://github.com/rehsack/System-Image-Update.git;rev=3fc3981f85cf1d654b1516e489ed700f1a202faf \
           file://run \
	   file://sysimg_update.json \
"

RDEPENDS_${PN} += "archive-peek-libarchive-perl"
RDEPENDS_${PN} += "crypt-ripemd160-perl"
RDEPENDS_${PN} += "datetime-format-strptime-perl"
RDEPENDS_${PN} += "log-any-adapter-dispatch-perl"
RDEPENDS_${PN} += "moo-perl"
RDEPENDS_${PN} += "moox-configfromfile-perl"
RDEPENDS_${PN} += "moox-log-any-perl"
RDEPENDS_${PN} += "moox-options-perl"
RDEPENDS_${PN} += "moox-role-logger-perl"
RDEPENDS_${PN} += "net-async-http-perl"
RDEPENDS_${PN} += "digest-md5-perl"
RDEPENDS_${PN} += "digest-md6-perl"
RDEPENDS_${PN} += "digest-sha-perl"
RDEPENDS_${PN} += "digest-sha3-perl"
RDEPENDS_${PN} += "daemontools"
RDEPENDS_${PN} += "system-image"

S = "${WORKDIR}/git"

inherit cpan

do_configure_append() {
    oe_runmake manifest
}

SERVICE_ROOT = "${sysconfdir}/daemontools/service"
SYSUPDT_SERVICE_DIR = "${SERVICE_ROOT}/sysimg_update"

do_install_append() {
    install -d -m 755 ${D}${sysconfdir}
    install -m 0644 ${WORKDIR}/sysimg_update.json ${D}${sysconfdir}

    install -d ${D}${SYSUPDT_SERVICE_DIR}
    install -m 0755 ${WORKDIR}/run ${D}${SYSUPDT_SERVICE_DIR}/run
}

FILES_${PN} += "${sysconfdir}"

BBCLASSEXTEND = "native"

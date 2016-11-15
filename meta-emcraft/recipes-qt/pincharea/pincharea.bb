SUMMARY = "Touch Interaction example: PinchArea"
DESCRIPTION = "PinchArea QML Element example"

LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${S}/main.cpp;beginline=8;endline=37;md5=50a4808abd952ef73d7fb1189d41b616"

inherit qt4e

PR = "r0"

SRC_URI = "file://${PN}.tar.bz2"

S = "${WORKDIR}/${PN}"

export examples_prefix = "/opt/examples"

EXTRA_OEMAKE += "INSTALL_ROOT=${D}"
OE_QMAKE_QMAKE = "${WORKDIR}/qmake2"

addtask prepare_qmake_symlink after do_generate_qt_config_file before do_configure

do_prepare_qmake_symlink() {
	ln -sf "${STAGING_BINDIR_NATIVE}/qmake2" ${WORKDIR}
}

do_install() {
	oe_runmake install DESTDIR=${D} INCLUDEDIR=${includedir}
}

do_configure_prepend() {
	sed -i -e "s|\(Prefix\ =\).*|\1${examples_prefix}|" ${WORKDIR}/qt.conf
	sed -i -r -e "/^(Binaries|Headers|Plugins|Mkspecs)/ d" ${WORKDIR}/qt.conf
}

FILES_${PN} += "${examples_prefix}/declarative/touchinteraction/${PN}/*"
FILES_${PN}-dbg += "${examples_prefix}/declarative/touchinteraction/${PN}/.debug/*"
FILES_${PN}-dev += "${examples_prefix}/declarative/touchinteraction/${PN}/*.pro"
FILES_${PN}-dev += "${examples_prefix}/declarative/touchinteraction/${PN}/*.qmlproject"
FILES_${PN}-dev += "${examples_prefix}/declarative/touchinteraction/${PN}/*.cpp"

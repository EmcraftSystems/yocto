SUMMARY = "QtSerialPort"
SECTION = "qt/libs"
LICENSE = "LGPLv2+"
LIC_FILES_CHKSUM = "file://LICENSE.LGPL;md5=fbc093901857fcd118f065f900982c24"

inherit qt4e pkgconfig

BBCLASSEXTEND = "native nativesdk"

SRC_URI = "git://github.com/qtproject/qtserialport.git;branch=qt4-dev"
SRCREV="56ebaecf3a45e0fe54b849bc3ba4f22919ea3de0"

S= "${WORKDIR}/git"

QMAKE_PROFILES = "${S}/qtserialport.pro"
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
	sed -i -e "s|\(Prefix\ =\).*|\1${prefix}|" ${WORKDIR}/qt.conf
	sed -i -r -e "/^(Binaries|Headers|Plugins|Mkspecs)/ d" ${WORKDIR}/qt.conf
	echo "Binaries = ${prefix}/bin" >> ${WORKDIR}/qt.conf
	echo "Headers = ${includedir}/qtopia" >> ${WORKDIR}/qt.conf
	echo "Data = ${prefix}/share/qtopia/" >> ${WORKDIR}/qt.conf
	echo "Plugins = ${libdir}/qtopia/plugins/" >> ${WORKDIR}/qt.conf
	echo "Imports = ${libdir}/qtopia/imports/" >> ${WORKDIR}/qt.conf
}

FILES_${PN}-dev += " \
	${includedir}/QtSerialPort/* \
	${prefix}/share/qtopia/* \
	${libdir}/*.prl \
"

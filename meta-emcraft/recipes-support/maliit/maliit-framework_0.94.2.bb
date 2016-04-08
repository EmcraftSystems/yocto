DESCRIPTION = "A virtual keyboard for touch-screen based user interfaces"
HOMEPAGE = "https://wiki.maliit.org/Main_Page"

LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://LICENSE.LGPL;md5=5c917f6ce94ceb8d8d5e16e2fca5b9ad"

inherit autotools qt4e

BBCLASSEXTEND = "native nativesdk"

SRC_URI = "file://maliit-framework-${PV}.tar.bz2"

RDEPENDS_${PN} = "libqt-embeddedsvg4"

RRECOMMENDS_${PN} = "maliit-plugins"


FILES_${PN} += "\
	${libdir}/qtopia/plugins/inputmethods/*.so \
	${libdir}/maliit/plugins/factories/*.so \
    "

FILES_${PN}-dbg += "\
	${libdir}/qtopia/plugins/inputmethods/.debug* \
	${libdir}/maliit/plugins/factories/.debug* \
	${libdir}/maliit-framework-tests/* \
    "

FILES_${PN}-dev += "${datadir}"

EXTRA_QMAKEVARS_PRE = "\
    PREFIX=${prefix} \
    LIBDIR=${libdir} \
    QT_IM_PLUGIN_PATH=${libdir}/qt4/plugins/inputmethods \
    MALIIT_INSTALL_PRF=${datadir}/qt4/mkspecs/features \
    SCHEMADIR=${sysconfdir}/gconf/schemas \
    CONFIG+=disable-background-translucency \
    CONFIG+=nogtk \
    CONFIG+=local-install \
    CONFIG+=notests \
    CONFIG+=disable-dbus \
    MALIIT_ENABLE_MULTITOUCH=false \
    "

QMAKE_PROFILES = "${S}/maliit-framework.pro"
EXTRA_OEMAKE += "INSTALL_ROOT=${D}"

OE_QMAKE_QMAKE = "${WORKDIR}/qmake2"

addtask prepare_qmake_symlink after do_generate_qt_config_file before do_configure

do_prepare_qmake_symlink() {
	ln -sf "${STAGING_BINDIR_NATIVE}/qmake2" ${WORKDIR}
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

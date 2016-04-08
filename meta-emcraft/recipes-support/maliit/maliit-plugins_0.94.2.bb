DESCRIPTION = "Plugins for a virtual keyboard for touch-screen based user interfaces"
HOMEPAGE = "https://wiki.maliit.org/Main_Page"

LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f29b21caa8e460097bfad9c026a33621"

inherit autotools qt4e

DEPENDS = "maliit-framework"

RDEPENDS_${PN} += "qt4-embedded-plugin-iconengine-svgicon libqt-embeddedsvg4 qt4-embedded-plugin-imageformat-svg"
SRC_URI = "file://maliit-plugins-${PV}.tar.bz2"

QMAKE_PROFILES = "${S}/maliit-plugins.pro"

EXTRA_QMAKEVARS_PRE = "\
    PREFIX=${prefix} \
    LIBDIR=${libdir} \
    MALIIT_DEFAULT_PROFILE=nokia-n9 \
    "

FILES_${PN} += "\
    ${libdir}/maliit \
    ${datadir} \
    "

FILES_${PN}-dbg += "${libdir}/maliit/plugins*/.debug"

EXTRA_OEMAKE += "INSTALL_ROOT=${D}"
CXXFLAGS_append += "-I${STAGING_INCDIR}/maliit -I${STAGING_INCDIR}/maliit/plugins -I${STAGING_INCDIR}/maliit/framework"
OE_QMAKE_MOC +=" -I${STAGING_INCDIR}/maliit -I${STAGING_INCDIR}/maliit/plugins -I${STAGING_INCDIR}/maliit/framework "

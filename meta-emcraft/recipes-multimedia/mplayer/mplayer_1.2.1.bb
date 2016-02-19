DESCRIPTION = "Open Source multimedia player."
SECTION = "multimedia"
HOMEPAGE = "http://www.mplayerhq.hu/"
DEPENDS = "libtheora zlib jpeg libpng libogg libmad x264 lzo"
SRCNAME = "MPlayer"

RDEPENDS_${PN} = "libasound libmp3lame ncurses-libtinfo libmpeg2 libfaad libbz2"
PROVIDES = "mplayer"
RPROVIDES_${PN} = "mplayer"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=91f1cb870c1cc2d31351a4d2595441cb"

SRC_URI = "http://mplayerhq.hu/MPlayer/releases/${SRCNAME}-${PV}.tar.xz \
	file://fbdev_uyvy.patch"

SRC_URI[md5sum] = "7fe68d1961427d0b4e306074e2cac246"
SRC_URI[sha256sum] = "831baf097d899bdfcdad0cb80f33cc8dff77fa52cb306bee5dee6843b5c52b5f"

S = "${WORKDIR}/${SRCNAME}-${PV}"

FILES_${PN} = "${bindir}/mplayer ${libdir} /usr/etc/mplayer/"
FILES_${PN}_mencoder = "${bindir}/mencoder"

inherit autotools pkgconfig

EXTRA_OECONF = " \
	--enable-cross-compile \
	--target=arm-linux \
	--prefix=${D}${prefix} \
	--disable-xf86keysym \
	--disable-tv \
	--disable-vm \
	--disable-gl \
	--disable-pulse \
	--disable-openal \
	--disable-speex \
	--disable-sdl \
	--disable-liba52 \
	--disable-gnutls \
	--disable-tremor \
	--disable-libvorbis \
	--disable-mencoder \
	--cc='${CC}' --as='${CC}' --nm='${NM}' --ar='${AR}' --ranlib='${RANLIB}' \
"
do_configure () {
	sed -i 's|/usr/\S*include[\w/]*||g' ${S}/configure
	sed -i 's|_install_strip="-s"|_install_strip=""|g' ${S}/configure

	cd ${S}
	./configure ${EXTRA_OECONF}
}

do_compile () {
	cd ${S}
	oe_runmake
}

do_install() {
	cd ${S}
	make install
}

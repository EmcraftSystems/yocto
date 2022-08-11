require cmake.inc
inherit native

DEPENDS += "bzip2-replacement-native expat-native xz-native zlib-native curl-native"

SRC_URI += "\
    file://cmlibarchive-disable-ext2fs.patch \
"

SRC_URI[md5sum] = "1c38c67295ca696aeafd8c059d748b38"
SRC_URI[sha256sum] = "0c3a1dcf0be03e40cf4f341dda79c96ffb6c35ae35f2f911845b72dab3559cf8"

B = "${WORKDIR}/build"
do_configure[cleandirs] = "${B}"

# Disable ccmake since we don't depend on ncurses
CMAKE_EXTRACONF = "\
    -DCMAKE_LIBRARY_PATH=${STAGING_LIBDIR_NATIVE} \
    -DBUILD_CursesDialog=0 \
    -DCMAKE_USE_SYSTEM_LIBRARIES=1 \
    -DCMAKE_USE_SYSTEM_LIBRARY_JSONCPP=0 \
    -DCMAKE_USE_SYSTEM_LIBRARY_LIBARCHIVE=0 \
    -DCMAKE_USE_SYSTEM_LIBRARY_LIBUV=0 \
    -DCMAKE_USE_SYSTEM_LIBRARY_LIBRHASH=0 \
    -DENABLE_ACL=0 -DHAVE_ACL_LIBACL_H=0 \
    -DHAVE_SYS_ACL_H=0 \
"

do_configure () {
	${S}/configure --verbose --prefix=${prefix} -- ${CMAKE_EXTRACONF}
}

do_compile() {
	oe_runmake
}

do_install() {
	oe_runmake 'DESTDIR=${D}' install
}

do_compile[progress] = "percent"

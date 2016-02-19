DESCRIPTION = "SIMPLE-MTPFS (Simple Media Transfer Protocol FileSystem) is a file system for Linux (and other operating systems with a FUSE implementation, such as Mac OS X or FreeBSD) capable of operating on files on MTP devices attached via USB to local machine. On the local computer where the SIMPLE-MTPFS is mounted, the implementation makes use of the FUSE (Filesystem in Userspace) kernel module. The practical effect of this is that the end user can seamlessly interact with MTP device files."
HOMEPAGE = "https://github.com/phatina/simple-mtpfs"
SECTION = "libs"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=75859989545e37968a99b631ef42722e"

PR = "r1"

SRC_URI = "git://github.com/phatina/simple-mtpfs.git;rev=80784a0c8c31b812052c5f23781516bb25a4bf93;protocol=https \
           file://simple-mtpfs-add-L-option.patch \
	   "

S = "${WORKDIR}/git"

inherit autotools pkgconfig

EXTRA_OECONF = ""

DEPENDS = "libmtp fuse libusb1"

SUMMARY = "C library for asynchronous DNS requests (including name resolves)"
DESCRIPTION = "This is c-ares, an asynchronous resolver library.  It is intended for \
applications which need to perform DNS queries without blocking, or need to \
perform multiple DNS queries in parallel.  The primary examples of such \
applications are servers which communicate with multiple clients and programs \
with graphical user interfaces."
HOMEPAGE = "http://c-ares.haxx.se/"
SECTION = "net/misc"
PV = "1.10.0"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${S}/ares.h;beginline=2;endline=16;md5=f0afa28d6fcfe31822f4a4fd8aa95727 \
"

SRC_URI = "http://c-ares.haxx.se/download/c-ares-1.10.0.tar.gz \
"
SRC_URI[md5sum] = "1196067641411a75d3cbebe074fd36d8"
SRC_URI[sha256sum] = "3d701674615d1158e56a59aaede7891f2dde3da0f46a6d3c684e0ae70f52d3db"

inherit autotools

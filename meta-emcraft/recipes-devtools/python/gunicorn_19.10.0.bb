SUMMARY = "WSGI HTTP Server for UNIX"
DESCRIPTION = "\
  Gunicorn ‘Green Unicorn’ is a Python WSGI HTTP Server for UNIX. It’s \
  a pre-fork worker model ported from Ruby’s Unicorn project. The \
  Gunicorn server is broadly compatible with various web frameworks, \
  simply implemented, light on server resource usage, and fairly speedy. \
  " 
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f75f3fb94cdeab1d607e2adaa6077752"

SRC_URI = "https://pypi.python.org/packages/source/g/gunicorn/${BPN}-${PV}.tar.gz"
SRC_URI[md5sum] = "dfa07409c60f9dd8501fa0503f0bfbb1"
SRC_URI[sha256sum] = "f9de24e358b841567063629cd0a656b26792a41e23a24d0dcb40224fc3940081"

inherit setuptools

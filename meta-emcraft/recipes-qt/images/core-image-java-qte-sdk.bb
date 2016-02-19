IMAGE_FEATURES += "tools-debug tools-profile \
			tools-sdk dev-pkgs package-management splash \
			ssh-server-openssh"

CORE_IMAGE_EXTRA_INSTALL = "packagegroup-qte-toolchain-target qt4-embedded-tools \
			qt4-embedded-demos qt4-embedded-examples \
			qt4-embedded-plugin-mousedriver-tslib \
			qt4-embedded-plugin-kbddriver-linuxinput \
			tslib tslib-calibrate tslib-tests qt-demo-init \
			packagegroup-base-nfs				\
			ethtool						\
			"

LICENSE = "MIT"

inherit core-image

IMAGE_INSTALL += "\
	      openjdk-7-common	\
	      openjdk-7-java	\
	      openjdk-7-jre	\
	      openjdk-7-vm-cacao	\
	      icu			\
	      openssl-misc		\
	      openssl-engines		\
	      wireless-tools		\
	      wpa-supplicant		\
	      wpa-supplicant-cli	\
	      wpa-supplicant-passphrase	\
	      libnl			\
	      gst-meta-video \
	      gst-plugins-base \
	      gst-plugins-good \
	      gst-plugins-good-video4linux2 \
	      libgudev \
	      gst-plugins-bad \
	      gst-plugins-bad-fbdevsink \
	      gst-plugins-base \
	      gst-plugins-base-alsa \
	      gst-plugins-base-audioconvert \
	      gst-plugins-base-audiorate \
	      gst-plugins-base-audioresample \
	      gst-plugins-base-audiotestsrc \
	      gst-plugins-base-decodebin \
	      gst-plugins-base-decodebin2 \
	      gst-plugins-base-ffmpegcolorspace \
	      gst-plugins-base-playbin \
	      gst-plugins-base-typefindfunctions \
	      gst-plugins-base-volume \
	      gst-plugins-good \
	      gst-plugins-good-audiofx \
	      gst-plugins-good-audioparsers \
	      gst-plugins-good-auparse \
	      gst-plugins-good-autodetect \
	      gst-plugins-good-flac \
	      gst-plugins-good-id3demux \
	      gst-plugins-good-isomp4 \
	      gst-plugins-good-oss4audio \
	      gst-plugins-good-ossaudio \
	      gst-plugins-good-video4linux2 \
	      gst-plugins-good-wavenc \
	      gst-plugins-good-wavparse \
	      log4cxx		\
	      sysklogd			\
	      cronie			\
	      tzdata			\
	      tzdata-africa		\
	      tzdata-americas		\
	      tzdata-antarctica		\
	      tzdata-arctic		\
	      tzdata-asia		\
	      tzdata-atlantic		\
	      tzdata-australia		\
	      tzdata-europe		\
	      tzdata-misc		\
	      tzdata-pacific		\
	      ltrace			\
	      strace			\
	      qt4-embedded-plugin-gfxdriver-gfxtransformed	\
	      openjdk-7-vm-zero					\
	      openjdk-7-vm-jamvm				\
	      "

export IMAGE_BASENAME = "core-image-java-qte-sdk"

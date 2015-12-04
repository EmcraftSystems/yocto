# Makefile

export MCC_LINUX_KERNEL=$(INSTALL_ROOT)/linux
export MCC_LIB_MCC=$(shell pwd)/libmcc-1.03
export MCC_LIB_CMSIS=$(shell pwd)/libcmsis
export MCC_MQX_KERNEL=$(INSTALL_ROOT)/mqx-4.0

all:
	make -C libcmsis all APP_DIR=$(shell pwd)/cmsis

clean:
	make -C libcmsis clean
# end of file

APP_DIR=$(MCC_MQX_KERNEL)/mcc/examples/cmsis
CFLAGS+=-I$(MCC_LINUX_KERNEL)/arch/arm/mach-mvf/mcc
CFLAGS+=-I$(MCC_LINUX_KERNEL)/include
CFLAGS+=-I$(APP_DIR)
CFLAGS+=-I$(MCC_LIB_MCC)/include
CFLAGS+=-Iinclude
CFLAGS+=-I`pwd`/../../mcc-kmod-1.06
INSTALL_PATH=$(DESTDIR)/usr/bin

LDFLAGS+=-L$(MCC_LIB_MCC)/build -lmcc

SRC=mcc.c cmsis.c mem.c
SRCDIR=src/
OBJDIR=build/

OBJ=$(SRC:.c=.o)
CSRC=$(addprefix $(SRCDIR),$(SRC))
COBJ=$(addprefix $(OBJDIR),$(OBJ))

all: $(CSRC) $(OBJDIR)libcmsis.a $(OBJDIR)libcmsis.so

$(OBJDIR)%.o: $(SRCDIR)%.c
	mkdir -p build/
	$(CC) -c -fPIC $< -o $@ $(CFLAGS) $(LDFLAGS)

$(OBJDIR)libcmsis.a: $(COBJ)
	$(AR) rcs $@ $(COBJ)

$(OBJDIR)libcmsis.so: $(COBJ)
	$(CC) -shared -o $@ $(COBJ)

install:
	mkdir -p $(DESTDIR)/usr/{lib,include}
	cp -f $(OBJDIR)/libcmsis.{so,a} $(DESTDIR)/usr/lib
	cp -f include/*.h $(DESTDIR)/usr/include

clean:
	rm -rf build


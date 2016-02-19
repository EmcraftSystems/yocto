/*
  Copyright (C) 2015 Emcraft Systems <www.emcraft.com>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <stdio.h>
#include "i2c-dev.h"


void main(void)
{
	int fd;
	int addr = 0x0;
	int ret;

	if ((fd = open("/dev/i2c-1", O_RDONLY)) < 0) {
		perror("open");
		goto xit;
	}
	if ((ioctl(fd, I2C_SLAVE, 0x68)) < 0) {
		perror("ioctl");
		goto xit;
	}

	if (i2c_smbus_write_byte(fd, addr)) {
		perror("write byte");
		goto xit;
	}
	if ((ret = i2c_smbus_read_byte(fd)) < 0) {
		perror("read byte");
		goto xit;
	}

	printf("Read reg %#x returns %#x\n", addr, ret);
xit:
	return;
}

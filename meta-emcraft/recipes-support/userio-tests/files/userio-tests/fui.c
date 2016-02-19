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
#include <linux/i2c.h>
#include <linux/i2c-dev.h>

#include <err.h>
#include <errno.h>
#include <string.h>
#include <strings.h>
#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>

typedef unsigned char u8;

#define HUB_ADDR_INIT	0x2d
#define HUB_ADDR	0x2c
#define HUB_DEVNAME	"/dev/i2c-1"

static void __attribute__((noreturn)) usage(const char *prog)
{
	fprintf(stderr,	"Usage:\n"
			"    %s a                         - attach the HUB\n"
			"    %s w <reg> <byte1> [<byte2>] - write a register\n"
			"    %s r <reg> <# bytes>         - read one or more registers\n",
			prog, prog, prog);
	exit(1);
}

static int gimme_range_or_die(const char *prog, const char *name, const char *str, int start, int end)
{
	long ret;
	int base = 10;
	char *endptr;

	if (!str) {
		warnx("empty argument %s", name);
		usage(prog);
	}

	if (!strncasecmp(str, "0x", 2))
		base = 16;

	errno = 0;
	ret = strtol(str, &endptr, base);
	if (errno) {
		warn("bad argument %s: '%s'", name, str);
		usage(prog);
	} else if (ret < start || ret > end) {
		warnx("bad argument %s (range is [%d:%d]): '%s'",
				name, start, end, str);
		usage(prog);
	} else if (endptr == str) {
		warnx("bad argument %s: '%s'", name, str);
		usage(prog);
	}

	return (int) ret;
}

static u8 gimme_u8_or_die(const char *prog, const char *name, const char *str)
{
	return (u8) gimme_range_or_die(prog, name, str, 0, 256);
}

/*
 * argv:
 * 0     1
 * name  a
 */
static int fui_attach(int fd)
{
        struct i2c_rdwr_ioctl_data i2c_data;
        struct i2c_msg msg[1];
	u8 buf[3] = { 0xAA, 0x55, 0 };

	i2c_data.msgs = msg;
        i2c_data.nmsgs = 1;

        i2c_data.msgs[0].addr = HUB_ADDR_INIT;
        i2c_data.msgs[0].flags = 0;
        i2c_data.msgs[0].buf = buf;
        i2c_data.msgs[0].len = sizeof buf;

	if (ioctl(fd, I2C_RDWR, &i2c_data) < 0)
		err(1, "ioctl");

	return 0;
}

/*
 * argv:
 * 0     1  2      3        4
 * name  w  <reg>  <byte1>  [<byte2>]
 */
static int fui_write(int fd, int argc, char **argv)
{
        struct i2c_rdwr_ioctl_data i2c_data;
        struct i2c_msg msg[1];
	u8 buf[3];

	if (argc != 4 && argc != 5) usage(argv[0]);

	buf[0] = gimme_u8_or_die(argv[0], "<reg>",   argv[2]);
	buf[1] = gimme_u8_or_die(argv[0], "<byte1>", argv[3]);
	buf[2] = (argc == 5) ? gimme_u8_or_die(argv[0], "<byte2>", argv[4]) : 0;

	i2c_data.msgs = msg;
        i2c_data.nmsgs = 1;
        i2c_data.msgs[0].addr = HUB_ADDR;
        i2c_data.msgs[0].flags = 0;
        i2c_data.msgs[0].buf = buf;
        i2c_data.msgs[0].len = sizeof buf;

	if (ioctl(fd, I2C_RDWR, &i2c_data) < 0)
		err(1, "ioctl");

	return 0;
}

/*
 * argv:
 * 0     1  2      3
 * name  r  <reg>  <# bytes>
 */
static int fui_read(int fd, int argc, char **argv)
{
	size_t i;
	size_t n;
	u8 reg;
	u8 data[256];
        struct i2c_msg msg[2];
        struct i2c_rdwr_ioctl_data i2c_data;

	if (argc != 4) usage(argv[0]);

	reg = gimme_u8_or_die(argv[0], "<reg>",     argv[2]);
	n   = gimme_range_or_die(argv[0], "<# bytes>", argv[3], 0, sizeof data);

	i2c_data.msgs = msg;
        i2c_data.nmsgs = 2;

        i2c_data.msgs[0].addr = HUB_ADDR;
        i2c_data.msgs[0].flags = 0;
        i2c_data.msgs[0].len = 1;
        i2c_data.msgs[0].buf = &reg;

        i2c_data.msgs[1].addr = HUB_ADDR;
        i2c_data.msgs[1].flags = I2C_M_RD;
        i2c_data.msgs[1].len = n;
        i2c_data.msgs[1].buf = data;

        if (ioctl(fd, I2C_RDWR, &i2c_data) < 0)
		err(1, "ioctl");

	if (n) {
		for (i = 0; i < n; i++)
			printf("%02x ", data[i]);
		printf("\n");
	}

	return 0;
}

int main(int argc, char **argv)
{
	int fd;

	fd = open(HUB_DEVNAME, O_RDWR);
	if (fd < 0)
		err(1, "open: %s", HUB_DEVNAME);

	if (argv[1] && !strcasecmp(argv[1], "a"))
		return fui_attach(fd);

	if (argv[1] && !strcasecmp(argv[1], "w"))
		return fui_write(fd, argc, argv);

	if (argv[1] && !strcasecmp(argv[1], "r"))
		return fui_read(fd, argc, argv);

	usage(argv[0]);
}

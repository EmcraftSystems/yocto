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
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <fcntl.h>
#include <errno.h>
#include <linux/input.h>
#include <linux/uinput.h>

#define die(str, args...) do { \
        perror(str); \
        exit(EXIT_FAILURE); \
    } while(0)

int main(void)
{
	int                    fd;
	struct uinput_user_dev uidev;
	struct input_event     ev;
	int                    x, y;
	int                    i;
	int			quit = 0;

	fd = open("/dev/uinput", O_WRONLY | O_NONBLOCK);
	if(fd < 0)
		die("error: open");

	if(ioctl(fd, UI_SET_EVBIT, EV_KEY) < 0)
		die("error: ioctl");
	if(ioctl(fd, UI_SET_KEYBIT, BTN_TOUCH) < 0)
		die("error: ioctl");

	if(ioctl(fd, UI_SET_EVBIT, EV_ABS) < 0)
		die("error: ioctl");
	if(ioctl(fd, UI_SET_ABSBIT, ABS_X) < 0)
		die("error: ioctl");
	if(ioctl(fd, UI_SET_ABSBIT, ABS_Y) < 0)
		die("error: ioctl");

	memset(&uidev, 0, sizeof(uidev));
	snprintf(uidev.name, UINPUT_MAX_NAME_SIZE, "uinput-test");
	uidev.id.bustype = BUS_USB;
	uidev.id.vendor  = 0x1;
	uidev.id.product = 0x1;
	uidev.id.version = 1;
	uidev.absmin[ABS_X] = 0;
	uidev.absmax[ABS_X] = 1024;
	uidev.absmin[ABS_Y] = 0;
	uidev.absmax[ABS_Y] = 1024;
	uidev.absmax[ABS_PRESSURE] = 0;
	uidev.absmax[ABS_PRESSURE] = 255;

	if(write(fd, &uidev, sizeof(uidev)) < 0)
		die("error: write");

	if(ioctl(fd, UI_DEV_CREATE) < 0)
		die("error: ioctl");

	srand(time(NULL));

	while(1) {
		x = 0;
		y = 0;
		switch(getchar()) {
		case 'w':
			y = 200;
			break;
		case 's':
			y = 400;
			break;
		case 'a':
			x = 400;
			break;
		case 'd':
			x = 200;
			break;
		case 'q':
			quit = 1;
			break;
		default:
			break;
		}

		if (quit)
			break;

		if (x == 0 && y == 0)
			continue;

		for (i = 0; i < 10; ++i) {
			memset(&ev, 0, sizeof(ev));
			ev.type = EV_KEY;
			ev.code = BTN_TOUCH;
			ev.value = i == 9 ? 0 : 1;
			if (write(fd, &ev, sizeof(ev)) < 0)
				die("error: write");

			memset(&ev, 0, sizeof(ev));
			ev.type = EV_ABS;
			ev.code = ABS_X;
			ev.value = x;
			if (write(fd, &ev, sizeof(ev)) < 0)
				die("error: write");

			memset(&ev, 0, sizeof(ev));
			ev.type = EV_ABS;
			ev.code = ABS_Y;
			ev.value = y;
			if (write(fd, &ev, sizeof(ev)) < 0)
				die("error: write");

			memset(&ev, 0, sizeof(ev));
			ev.type = EV_ABS;
			ev.code = ABS_PRESSURE;
			ev.value = 255;
			if (write(fd, &ev, sizeof(ev)) < 0)
				die("error: write");

			memset(&ev, 0, sizeof(ev));
			ev.type = EV_SYN;
			ev.code = 0;
			ev.value = 0;
			if (write(fd, &ev, sizeof(ev)) < 0)
				die("error: write");
		}


	}

	if(ioctl(fd, UI_DEV_DESTROY) < 0)
		die("error: ioctl");

	close(fd);

	return 0;
}

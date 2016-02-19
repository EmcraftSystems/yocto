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
#define _GNU_SOURCE

#include <sys/stat.h>
#include <sys/time.h>
#include <sys/types.h>
#include <sys/select.h>

#include <err.h>
#include <stdio.h>
#include <fcntl.h>
#include <ctype.h>
#include <errno.h>
#include <string.h>
#include <stdlib.h>

static void report(const char *prefix, int fd)
{
	ssize_t n;
	char value[1024];

	n = pread(fd, value, sizeof(value), 0);
	if (n < 0)
		err(1, "pread");

	if (value[n-1] == '\n')
		value[n-1] = '\0';
	else
		value[n] = '\0';

	warnx("%s %s", prefix, value);
}

static void lookup(int fd)
{
	int ret;
	fd_set exceptfds;
	struct timeval tv = { 10, 0 };

	FD_ZERO(&exceptfds);
	FD_SET(fd, &exceptfds);
	ret = select(fd+1, 0, 0, &exceptfds, &tv);
	if (ret < 0) {
		err(1, "select");
	} else if (!ret) {
		warnx("select: timeout");
	} else {
		if (!FD_ISSET(fd, &exceptfds)) {
			errx(1, "select: strange...");
		} else {
			report("INTERRUPT, value is", fd);
		}
	}
}

static void __attribute__((noreturn)) usage(void)
{
	fprintf(stderr, "\nusage: %s <gpio> [<edge>]\n", program_invocation_name);
	fprintf(stderr, "\t<gpio> = 1, 2, ...\n");
	fprintf(stderr, "\t<edge> = both [default], rising, falling\n");
	exit(1);
}

/* check that @gpio is an integer >= 1 */
static int bad_gpio(const char *gpio)
{
	int i, x;

	x = atoi(gpio);
	if (x < 1)
		return 1;

	for (i = 0; i < strlen(gpio); i++)
		if (!isdigit(gpio[i]))
			return 1;

	return 0;
}

/* check that @edge is in { "both", "rising", "falling" } */
static int bad_edge(const char *edge)
{
	if (!strcmp(edge, "both"))
		return 0;
	if (!strcmp(edge, "rising"))
		return 0;
	if (!strcmp(edge, "falling"))
		return 0;
	return 1;
}

static void gpio_export(const char *gpio)
{
	int fd;

	fd = open("/sys/class/gpio/export", O_WRONLY);
	if (fd < 0)
		err(1, "open(/sys/class/gpio/export)");
	if (write(fd, gpio, strlen(gpio)) != strlen(gpio))
		err(1, "write(/sys/class/gpio/export)");
	close(fd);
}

static void gpio_edge(const char *gpio, const char *edge)
{
	int fd;
	char path[128];

	snprintf(path, sizeof(path), "/sys/class/gpio/gpio%s/edge", gpio);
	if (access(path, F_OK))
		gpio_export(gpio);

	fd = open(path, O_WRONLY);
	if (fd < 0)
		err(1, "open(%s)", path);
	if (write(fd, edge, strlen(edge)) != strlen(edge))
		err(1, "write(%s), path");
	close(fd);
}


int main(int argc, char **argv)
{
	int fd;
	const char *gpio = NULL;
	const char *edge = "both";
	char value[128];

	if (argc == 2) {
		gpio = argv[1];
	} else if (argc == 3) {
		gpio = argv[1];
		edge = argv[2];
	} else {
		usage();
	}

	if (bad_gpio(gpio)) {
		fprintf(stderr, "%s: bad <gpio> argument: %s\n", *argv, gpio);
		usage();
	}

	if (bad_edge(edge)) {
		fprintf(stderr, "%s: bad <edge> argument: %s\n", *argv, edge);
		usage();
	}

	gpio_edge(gpio, edge);

	snprintf(value, sizeof(value), "/sys/class/gpio/gpio%s/value", gpio);
	fd = open(value, O_RDONLY);
	if (fd < 0)
		err(1, "open: %s", value);

	report("Current value is", fd);
	for ( ;; )
		lookup(fd);

	/* Nevar hppn */
	return 0;
}

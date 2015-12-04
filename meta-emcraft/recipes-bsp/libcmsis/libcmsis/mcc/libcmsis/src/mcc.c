/*
 * Copyright 2013 EmCraft
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

/*****************************************************************************
 * Include files:
 *****************************************************************************/

#include <stdio.h>
#include <unistd.h>
#include <sys/time.h>

// linux
#include <linux/mcc_config.h>
#include <linux/mcc_common.h>
#include <linux/mcc_linux.h>

// mqx
#include "cmsis.h"

// libs
#include <mcc_api.h>

#include "libcmsis.h"

/*****************************************************************************
 * Constants local to this module
 *****************************************************************************/
/*
 * Module name
 */
#define CMSIS_MCC		"CMSIS-MCC"

#define MCC_MQX_NODE_A5		0
#define MCC_MQX_NODE_M4		0

#define MCC_MQX_SEND_PORT	1
#define MCC_MQX_RECV_PORT	2

/*****************************************************************************
 * Variables local to this module
 *****************************************************************************/

/*
 * TBD: use constants
 */
MCC_ENDPOINT	mqx_endpoint_a5 = {0, MCC_MQX_NODE_A5, MCC_MQX_SEND_PORT};
MCC_ENDPOINT	mqx_endpoint_m4 = {1, MCC_MQX_NODE_M4, MCC_MQX_RECV_PORT};

/*****************************************************************************
 * Functions local to this module
 *****************************************************************************/

static float elapsed_seconds(struct timeval t_start, struct timeval t_end)
{
	if(t_start.tv_usec > t_end.tv_usec) {
		t_end.tv_usec += 1000000;
		t_end.tv_sec--;
	}

	return (float)(t_end.tv_sec - t_start.tv_sec) +
	       ((float)(t_end.tv_usec - t_start.tv_usec)) / 1000000.0;
}

/*****************************************************************************
 * Exported functions
 *****************************************************************************/
/*
 * Execute cmsis function described in 'msg' on M4 core
 */
int cmsis_exec(struct cmsis_mcc_msg *msg)
{
	static int	mcc_init;

	MCC_MEM_SIZE	len;
	MCC_INFO_STRUCT	info_data;
	struct timeval	t_start, t_end;
	void		*data;
	int		rv;

	if (!mcc_init) {
		mcc_initialize(mqx_endpoint_a5.node);
		mcc_get_info(mqx_endpoint_a5.node, &info_data);
		dbg(1, "%s: version %s\n", CMSIS_MCC, info_data.version_string);
		mcc_create_endpoint(&mqx_endpoint_a5, MCC_MQX_SEND_PORT);
		mcc_init = 1;
	}

	/* wait until the remote endpoint is created by the other */
	msg->rv = -1;
	while ((rv = mcc_send(&mqx_endpoint_m4, msg, sizeof(*msg),
			      0xffffffff))) {
		if (rv == MCC_ERR_ENDPOINT) {
			dbg(0, "%s: mcc_send(%d) fail, retry...\n", CMSIS_MCC,
			    msg->op);
			sleep(1);
			continue;
		}
    		
		dbg(0, "%s: mcc_send(%d) fail. rv=%d\n", CMSIS_MCC,
		    msg->op, rv);
		goto out;
	}
	dbg(1, "%s: mcc_send(%d) done\n", CMSIS_MCC, msg->op);

	/* wait for response */
	gettimeofday(&t_start, null);
	if (mcc_recv_nocopy(&mqx_endpoint_a5, &data, &len, 5000000)) {
		gettimeofday(&t_end, null);
		dbg(0, "%s: mcc_recv_nocopy(%d) fail after %f sec\n",
		    CMSIS_MCC, msg->op, elapsed_seconds(t_start, t_end));
		rv = 1;
		goto out;
	}

	rv = ((struct cmsis_mcc_msg *)data)->rv;
	dbg(1, "%s: mcc_recv(%d) done\n", CMSIS_MCC, msg->op);
	if (rv != 0)
		dbg(0, "%s: mcc_recv(%d) ERR %d\n", CMSIS_MCC, msg->op, rv);

	mcc_free_buffer(data);
out:
	return rv;
}


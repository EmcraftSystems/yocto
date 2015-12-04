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

// mqx
#include "cmsis.h"

#include "libcmsis.h"
#include "arm_math.h"

/*****************************************************************************
 * Constants local to this module
 *****************************************************************************/
/*
 * Module name
 */
#define CMSIS_CMSIS	"CMSIS-CMSIS"

/*****************************************************************************
 * External prototypes
 *****************************************************************************/

int cmsis_exec(struct cmsis_mcc_msg *msg);

/*****************************************************************************
 * Local prototypes
 *****************************************************************************/

static void fn_p_p_p_u(enum cmsis_op, uint32_t,
		       void *, void *, void *, uint32_t);

/*****************************************************************************
 * Exported API
 *****************************************************************************/

void arm_mult_f32(float32_t *pSrcA, float32_t *pSrcB, float32_t *pDst,
		  uint32_t blockSize)
{
	fn_p_p_p_u(CMSIS_OP_BASIC_MATH_MULT_F32, sizeof(*pSrcA),
		   pSrcA, pSrcB, pDst, blockSize);
}

void arm_mult_q15(q15_t *pSrcA, q15_t *pSrcB, q15_t *pDst, uint32_t blockSize)
{
	fn_p_p_p_u(CMSIS_OP_BASIC_MATH_MULT_Q15, sizeof(*pSrcA),
		   pSrcA, pSrcB, pDst, blockSize);
}

void arm_mult_q31(q31_t *pSrcA, q31_t *pSrcB, q31_t *pDst, uint32_t blockSize)
{
	fn_p_p_p_u(CMSIS_OP_BASIC_MATH_MULT_Q31, sizeof(*pSrcA),
		   pSrcA, pSrcB, pDst, blockSize);
}

void arm_mult_q7(q7_t *pSrcA, q7_t *pSrcB, q7_t *pDst, uint32_t blockSize)
{
	fn_p_p_p_u(CMSIS_OP_BASIC_MATH_MULT_Q7, sizeof(*pSrcA),
		   pSrcA, pSrcB, pDst, blockSize);
}

/*****************************************************************************
 * Functions local to this module
 *****************************************************************************/

static void fn_p_p_p_u(enum cmsis_op op, uint32_t unit,
		       void *p1, void *p2, void *p3, uint32_t len)
{
	struct cmsis_mcc_msg	m;

	m.op = op;
	m.rv = -1;
	CMSIS_ADR_SET(m.prm[0], p1, unit * len);
	CMSIS_ADR_SET(m.prm[1], p2, unit * len);
	CMSIS_ADR_SET(m.prm[2], p3, unit * len);
	m.prm[3] = len;

	dbg(2, "%s: %s %d/%d %p/%p/%p->%x/%x/%x %x\n", CMSIS_CMSIS, __func__,
	    op, unit,
	    p1, p2, p3, m.prm[0], m.prm[1], m.prm[2],
	    m.prm[3]);

	cmsis_exec(&m);
}


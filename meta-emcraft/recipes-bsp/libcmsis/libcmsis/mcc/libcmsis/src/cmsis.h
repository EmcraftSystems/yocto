/*****************************************************************************
 *
 * cmsis.c - CMSIS library MCC wrapper header file
 *
 * Copyright (c) 2013 EmCraft <www.emcraft.com>.
 * All rights reserved.
 *
 *****************************************************************************/

#ifndef _CMSIS_H_
#define _CMSIS_H_

/*****************************************************************************
 * Ñonstants
 *****************************************************************************/
/*
 * Maximum number of CMSIS func operands (parameters)
 */
#define CMSIS_MCC_PRM_MAX	6

/*****************************************************************************
 * Ñ-types
 *****************************************************************************/
/*
 * CMSIS operations (lib functions) supported
 */
enum cmsis_op {
	/*
	 * Basic math functions
	 */
	CMSIS_OP_BASIC_MATH_MULT_F32,
	CMSIS_OP_BASIC_MATH_MULT_Q15,
	CMSIS_OP_BASIC_MATH_MULT_Q31,
	CMSIS_OP_BASIC_MATH_MULT_Q7,

	/*
	 * Fast math functions
	 */

	/*
	 * Complex math functions
	 */

	/*
	 * Filtering functions
	 */

	/*
	 * Matrix functions
	 */

	/*
	 * Transform functions
	 */

	/*
	 * Controller functions
	 */

	/*
	 * Statistics functions
	 */

	/*
	 * Support functions
	 */

	/*
	 * Interpolation functions
	 */

	/*
	 * For internal usage
	 */
	CMSIS_OP_NUM
};

/*
 * Message passed through MCC
 */
struct cmsis_mcc_msg {
	enum cmsis_op	op;
	int		rv;
	unsigned long	prm[CMSIS_MCC_PRM_MAX];
};

#endif

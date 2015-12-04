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

#ifndef _LIB_CMSIS_H_
#define _LIB_CMSIS_H_

/*****************************************************************************
 * Include files:
 *****************************************************************************/

#include "stdint.h"

/*****************************************************************************
 * Debug stuff
 *****************************************************************************/
/*
 * Debug level
 */
#ifndef CMSIS_DBG_LVL
# define CMSIS_DBG_LVL		1	/* Print-out info about errors only  */
#endif

#define dbg(lvl, args...)	if (lvl < CMSIS_DBG_LVL) printf(args)

/*****************************************************************************
 * Memory allocation internal types and constants
 *****************************************************************************/

/*
 * We allocate memory with the following alignment; it also determines the
 * overhead we have per allocation
 */
#define CMSIS_MEM_ALIGN		32

/*
 * Set 'dst' with physical address of 'src' data, and flush ['src';'src+sz']
 * from cache
 */
#define CMSIS_ADR_SET(dst, src, sz)	do {				      \
		__clear_cache((src), (char *)(src) + (sz));		      \
		dst = (unsigned long)(((struct cmsis_mem_dsc *)(	      \
			(char *)(src) - CMSIS_MEM_ALIGN))->phys_addr +	      \
			CMSIS_MEM_ALIGN);				      \
} while (0)

/*
 * Memory descriptor of area allocated with cmsis_mem_alloc()
 */
struct cmsis_mem_dsc {
	uint64_t	phys_addr;
	uint64_t	map_offs;
	uint32_t	valid;

	char		usr_data[1];
};

#endif

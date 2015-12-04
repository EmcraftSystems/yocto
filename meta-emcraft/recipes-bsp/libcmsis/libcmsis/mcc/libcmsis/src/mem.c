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
#include <stdint.h>
#include <unistd.h>
#include <errno.h>
#include <sys/time.h>
#include <sys/mman.h>
#include <sys/ioctl.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>

#include "libcmsis.h"
#include "cmsis_api.h"

/*****************************************************************************
 * Local constants and macros
 *****************************************************************************/
/*
 * Module name
 */
#define CMSIS_MEM		"CMSIS-MEM"

/*
 * Validity lab
 */
#define CMSIS_MEM_VALID		0x12345678

/*****************************************************************************
 * UIO-DMA driver definitions
 *****************************************************************************/

#define UIO_DMA_DEV		"/dev/uio-dma"

/*
 * Caching modes
 */
enum {
	UIO_DMA_CACHE_DEFAULT = 0,
	UIO_DMA_CACHE_DISABLE,
	UIO_DMA_CACHE_WRITECOMBINE
};

#define UIO_DMA_ALLOC _IOW('U', 200, int)
struct uio_dma_alloc_req {
	uint64_t	dma_mask;
	uint16_t	memnode;
	uint16_t	cache;
	uint32_t	flags;
	uint32_t	chunk_size;
	uint32_t	chunk_count;
	uint64_t	mmap_offset;
	uint64_t	phys_start;
	uint64_t	phys_end;
};

#define UIO_DMA_FREE  _IOW('U', 201, int)
struct uio_dma_free_req {
	uint64_t	mmap_offset;
};

/*****************************************************************************
 * Variables local to this module
 *****************************************************************************/

static int	cmsis_mem_fd = -ENODEV;

/*****************************************************************************
 * Local functions
 *****************************************************************************/

static unsigned int ulog2(unsigned long n)
{
	unsigned int i;
	for (i = 0; n != 0; n >>= 1, ++i);
	return i - 1;
}

static inline unsigned long roundup_po2(unsigned long n)
{
	return 1UL << (ulog2(n - 1) + 1); 
}

/*****************************************************************************
 * Exported API
 *****************************************************************************/
/*
 * Allocate memory of size specified
 */
void *cmsis_mem_alloc(size_t size)
{
	struct uio_dma_alloc_req	areq;
	struct cmsis_mem_dsc		*dsc = NULL;
	unsigned int			chunk_size;
	size_t				phys_size;
	void				*p = NULL;
	int				rv, kmem = -ENOMEM;

	/*
	 * Check if we're intied
	 */
	if (cmsis_mem_fd < 0) {
		rv = open(UIO_DMA_DEV, O_RDWR);
		if (rv < 0) {
			dbg(0, "%s: failed open '%s' (%d)\n", CMSIS_MEM,
			    UIO_DMA_DEV, rv);
			goto out;
		}

		cmsis_mem_fd = rv;
	}

	size += CMSIS_MEM_ALIGN;

	/*
	 * Allocate kernel memory: we map user pages with cache disabled;
	 * but in any case must flush them before passing to M4 (since
	 * L2 cache may be ON)
	 */
	areq.cache = UIO_DMA_CACHE_DISABLE;
	areq.dma_mask = ~0ULL;
	areq.memnode = 0;
	areq.mmap_offset = 0;
	areq.flags = 0;

	for (chunk_size = roundup_po2(size); chunk_size; chunk_size >>= 1) {
		areq.chunk_size  = chunk_size;
		areq.chunk_count = (size + chunk_size - 1) / chunk_size;
		kmem = ioctl(cmsis_mem_fd, UIO_DMA_ALLOC, (unsigned long)&areq);
		if (!kmem)
			break;
	}
	if (kmem) {
		dbg(0, "%s: failed alloc mem of %d(+%d)\n", CMSIS_MEM,
		    size - CMSIS_MEM_ALIGN, CMSIS_MEM_ALIGN);
		goto out;
	}

	/*
	 * Check if memory allocated is physically consistent
	 */
	phys_size = areq.chunk_count * areq.chunk_size;
	if (areq.phys_end != areq.phys_start + phys_size - areq.chunk_size) {
		dbg(0, "%s: failed alloc consist mem %llx/%llx/%x [%d %dx%x]\n",
		    CMSIS_MEM, areq.phys_start, areq.phys_end, phys_size,
		    size, areq.chunk_count, areq.chunk_size);
		goto out;
	}

	/*
	 * Map memory allocated to user
	 */
	dsc = mmap(NULL, phys_size, PROT_READ | PROT_WRITE, MAP_SHARED,
		   cmsis_mem_fd, areq.mmap_offset);
	if (dsc == MAP_FAILED) {
		dbg(0, "%s: failed map mem of %d(%d)\n", CMSIS_MEM,
		    phys_size, size);
		dsc = NULL;
		goto out;
	}

	dsc->phys_addr = areq.phys_start;
	dsc->map_offs = areq.mmap_offset;
	dsc->valid    = CMSIS_MEM_VALID;

	p = (char *)dsc + CMSIS_MEM_ALIGN;
out:
	if (!p && !kmem) {
		struct uio_dma_free_req	freq;

		dbg(1, "%s: free %llx\n", CMSIS_MEM, areq.mmap_offset);
		freq.mmap_offset = areq.mmap_offset;
		ioctl(cmsis_mem_fd, UIO_DMA_FREE, (unsigned long)&freq);
		dsc = NULL;
	}

	dbg(1, "%s: alloc %p/0x%llx/0x%llx (%d+%d)\n", CMSIS_MEM, p,
	    dsc ? dsc->phys_addr : 0, dsc ? dsc->map_offs : 0,
	    size - CMSIS_MEM_ALIGN, CMSIS_MEM_ALIGN);

	return p;
}

/*
 * Free memory previously allocated with cmsis_mem_alloc()
 */
void cmsis_mem_free(void *ptr)
{
	struct cmsis_mem_dsc	*dsc;
	struct uio_dma_free_req	freq;

	if (!ptr) {
		dbg(0, "%s: free NULL\n", CMSIS_MEM);
		goto out;
	}

	dsc = (struct cmsis_mem_dsc *)((char *)ptr - CMSIS_MEM_ALIGN);
	if (!dsc || dsc->valid != CMSIS_MEM_VALID) {
		dbg(0, "%s: free bad addr %p (%p/%llx/%x)\n", CMSIS_MEM, ptr,
		    dsc, dsc ? dsc->phys_addr : 0, dsc ? dsc->valid : 0);
		goto out;
	}

	dbg(1, "%s: free %p/0x%llx/0x%llx\n", CMSIS_MEM, ptr, dsc->phys_addr,
	    dsc->map_offs);

	dsc->valid = ~dsc->valid;
	freq.mmap_offset = dsc->map_offs;
	ioctl(cmsis_mem_fd, UIO_DMA_FREE, (unsigned long)&freq);
out:
	return;
}


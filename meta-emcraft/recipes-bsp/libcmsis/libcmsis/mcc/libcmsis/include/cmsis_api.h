#ifndef _CMSIS_API_H_
#define _CMSIS_API_H_

#include <arm_math.h>

/*
 * These functions must be used to allocate/free memory for
 * vars, which addresses are passed to arm_xxx() CMSIS funcs
 */
void *cmsis_mem_alloc(size_t size);
void  cmsis_mem_free(void *ptr);

#endif

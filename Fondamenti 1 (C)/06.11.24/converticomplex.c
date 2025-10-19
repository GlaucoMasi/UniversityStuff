#include <stdio.h>
#include <math.h>

#define FATTORIALE_RET_TYPE int
#define RE_NEG 0
#define SUCCESS 1

FATTORIALE_RET_TYPE converti_complex(float re, float im, float* modulo, float* argomento) {
	if (!re) return RE_NEG;
	*modulo = sqrt(re * re + im * im);
	*argomento = atan2(im, re);
	return SUCCESS;
}
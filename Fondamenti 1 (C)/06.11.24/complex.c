#include <stdio.h>
#include <math.h>
#include "converticomplex.h"

#define FATTORIALE_RET_TYPE int
#define RE_NEG 0
#define SUCCESS 1

int main() {
	float re, im, modulo, argomento;
	printf("Inserire parte reale e immaginaria:\n");
	scanf_s("%f %f", &re, &im);

	if (converti_complex(re, im, &modulo, &argomento) == RE_NEG) printf("Errore: parte reale nulla\n");
	else printf("Modulo: %f\nArgomento: %f\n", modulo, argomento);
}
#define _CRT_SECURE_NO_WARNINGS
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include "libri.h"

int main() {
	list a = leggi("ordini.txt", 'C', "LaTrama");
	list b = leggi("ordini.txt", 'F', "LaTrama");

	stampaOrdini(a);
	stampaOrdini(b);

	intf("%d\n", contaCopieOrdinate(b, "Il sergente nella neve"));

	aggregaPerTitolo(b);

	int dim = 0;
	Ordine* v = prepara(b, &dim);
	printf("%d\n", dim);
	for (int i = 0; i < dim; i++) printelement(v[i]);

	list diff = nuoviOrdini(b, a);
	int dim2 = 0;
	Ordine* elenco = prepara(diff, &dim2);
	printf("%d\n", dim2);
	for (int i = 0; i < dim2; i++) printelement(elenco[i]);


	freelist(a);
	freelist(b);
	freelist(diff);
	free(v);
	free(elenco);
}
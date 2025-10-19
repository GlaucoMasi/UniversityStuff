#define _CRT_SECURE_NO_WARNINGS
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include "metro.h"

int main() {
	list eventi = leggiTutti("eventi.txt");
	print(eventi);

	int dim = 0;
	Tariffa* tariffe = leggiTariffe("tariffe.txt", &dim);
	printf("%d\n", dim);
	for (int i = 0; i < dim; i++) printf("%s %s %f\n", tariffe[i].ingresso, tariffe[i].uscita, tariffe[i].costo);
	
	ordina(tariffe, dim);
	for (int i = 0; i < dim; i++) printf("%s %s %f\n", tariffe[i].ingresso, tariffe[i].uscita, tariffe[i].costo);

	printf("%f", ricerca(tariffe, dim, "Gloucester Road", "Boston Manor"));

	totali(tariffe, dim, eventi);
	
	freelist(eventi);
	free(tariffe);
}
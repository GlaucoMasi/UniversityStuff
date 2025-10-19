#define _CRT_SECURE_NO_WARNINGS
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include "gara.h"

int main() {
	int dim = 0;
	Atleta* atleti = leggiAtleti("risultati.txt", &dim);

	//printf("%d\n", dim);
	//for (int i = 0; i < dim; i++) stampaAtleta(atleti[i]);
	//printf("\n");

	//int dima = 0, dimb = 0, dimc = 0, dimd = 0;
	//Atleta* a = ordinaAtletiPer(atleti, dim, "Nuoto", &dima);
	//Atleta* b = ordinaAtletiPer(atleti, dim, "Bici", &dimb);
	//Atleta* c = ordinaAtletiPer(atleti, dim, "Corsa", &dimc);
	//Atleta* d = ordinaAtletiPer(atleti, dim, "Totale", &dimd);
	//
	//printf("%d %d %d %d\n", dima, dimb, dimc, dimd);
	//for (int i = 0; i < dima; i++) stampaAtleta(a[i]);
	//printf("\n");
	//for (int i = 0; i < dimb; i++) stampaAtleta(b[i]);
	//printf("\n");
	//for (int i = 0; i < dimc; i++) stampaAtleta(c[i]);
	//printf("\n");
	//for (int i = 0; i < dimd; i++) stampaAtleta(d[i]);
	//printf("\n");

	// free(Atleti);
	// free(a);
	// free(b);
	// free(c);
	// free(d);

	//list premiati = atletiPremiati(atleti, dim);

	//premi(atleti, dim);
}
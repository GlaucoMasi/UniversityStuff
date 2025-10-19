#define _CRT_SECURE_NO_WARNINGS
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include "negozio.h"

Formaggio* leggiFormaggi(char* fileName, int* dim) {
	*dim = 0;
	int lenght = 0;
	
	FILE* fp = fopen(fileName, "r");
	if (fp == NULL) {
		printf("Errore nell'apertura del file\n");
		return NULL;
	}

	Formaggio temp;
	while (fscanf(fp, "%s %s %f %f", temp.nome, temp.tipo, &temp.prezzo, &temp.quant) == 4) lenght++;

	*dim = lenght;
	Formaggio* ans = (Formaggio*)(malloc(sizeof(Formaggio) * lenght));

	rewind(fp);
	for (int i = 0; i < lenght; i++) fscanf(fp, "%s %s %f %f", ans[i].nome, ans[i].tipo, &ans[i].prezzo, &ans[i].quant);

	fclose(fp);
	return ans;
}

list filtraFormaggi(Formaggio* elenco, int dim, char* tipo, float maxPrice) {
	list ans = emptylist();

	for (int i = 0; i < dim; i++) {
		if (strcmp(elenco[i].tipo, tipo) != 0) continue;
		if (maxPrice <= 0 || elenco[i].prezzo <= maxPrice) ans = cons(elenco[i], ans);
;	}

	return ans;
}

void stampaFormaggi(list elenco) {
	if (empty(elenco)) printf("\n");
	else {
		printelement(head(elenco));
		stampaFormaggi(tail(elenco));
	}
}

void ordina(Formaggio* v, int dim) {
	Formaggio* temp = (Formaggio*)malloc(sizeof(Formaggio) * dim);
	mergehelp(v, 0, dim, temp);
	free(temp);
}

float controvalore(Formaggio* elenco, int dim, char* tipo) {
	float tot = 0;

	for (int i = 0; i < dim; i++) {
		if (strcmp(elenco[i].tipo, tipo) == 0) tot += elenco[i].prezzo * elenco[i].quant;
	}

	return tot;
}

void stampaControvalori(Formaggio* elenco, int dim) {
	ordina(elenco, dim);

	float tot = elenco[0].prezzo * elenco[0].quant;
	for (int i = 0; i < dim; i++) {
		if (i == 0 || strcmp(elenco[i].tipo, elenco[i - 1].tipo) != 0) {
			printf("Il controvalore per i formaggi di tipo %s è: %f\n", elenco[i].tipo, controvalore(elenco, dim, elenco[i].tipo));
		}
	}
}
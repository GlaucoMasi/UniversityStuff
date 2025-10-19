#define _CRT_SECURE_NO_WARNINGS
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include "gara.h"

Atleta leggiSingoloAtleta(FILE* fp) {
	Atleta temp;
	temp.dim = 0;

	if (fscanf(fp, "%s %s", temp.codiceAtleta, temp.nomeAtleta) == 2) {
		while (fscanf(fp, "%d", &temp.tempi[temp.dim]) == 1 && temp.dim < 3) temp.dim++;
	}else strcpy(temp.codiceAtleta, "0000");

	return temp;
}

Atleta* leggiAtleti(char fileName[], int* dim) {
	*dim = 0;
	Atleta* ans = NULL;
	Atleta temp;
	int lenght = 0;

	FILE* fp = fopen("risultati.txt", "r");
	if (fp == NULL) {
		printf("Errore nell'apertura del file\n");
		return ans;
	}

	while (1) {
		temp = leggiSingoloAtleta(fp);
		if (strcmp(temp.codiceAtleta, "0000") == 0) break;
		lenght++;
	}

	rewind(fp);
	ans = (Atleta*)malloc(sizeof(Atleta) * lenght);

	for (int i = 0; i < lenght; i++) ans[i] = leggiSingoloAtleta(fp);
	
	*dim = lenght;
	fclose(fp);

	return ans;
}

Atleta* ordinaAtletiPer(Atleta* a, int dimA, char* tipo, int* dim) {
	Atleta* nuovo = NULL;
	int lenght = 0;
	*dim = 0;

	for (int i = 0; i < dimA; i++) if (a[i].dim == 3) lenght++;
	
	*dim = lenght;	
	nuovo = (Atleta*)(malloc(sizeof(Atleta) * lenght));

	int idx = 0;
	for (int i = 0; i < dimA; i++) if (a[i].dim == 3) {
		copy(&nuovo[idx], a[i]);
		idx++;
	}

	int r = 1;
	while (r) {
		r = 0;
		for (int i = 0; i < lenght - 1; i++) if (compare(nuovo[i], nuovo[i + 1], tipo) == 1) {
			swap(&nuovo[i], &nuovo[i + 1]);
			r = 1;
		}
	}

	return nuovo;
}

list atletiPremiati(Atleta* atleti, int dimA) {
	int dim = 0;
	Atleta* a = ordinaAtletiPer(atleti, dimA, "Nuoto", &dim);
	Atleta* b = ordinaAtletiPer(atleti, dimA, "Bici", &dim);
	Atleta* c = ordinaAtletiPer(atleti, dimA, "Corsa", &dim);
	Atleta* d = ordinaAtletiPer(atleti, dimA, "Totale", &dim);

	list ans = emptylist();

	for (int i = 0; i < 3 && dim - 1 - i >= 0; i++) {
		if (!count(a[dim - 1 - i], ans)) ans = cons(a[dim - 1 - i], ans);
	}

	for (int i = 0; i < 3 && dim - 1 - i >= 0; i++) {
		if (!count(b[dim - 1 - i], ans)) ans = cons(b[dim - 1 - i], ans);
	}
	
	for (int i = 0; i < 3 && dim - 1 - i >= 0; i++) {
		if (!count(c[dim - 1 - i], ans)) ans = cons(c[dim - 1 - i], ans);
	}

	for (int i = 0; i < 5 && dim - 1 - i >= 0; i++) {
		if (!count(d[dim - 1 - i], ans)) ans = cons(d[dim - 1 - i], ans);
	}
	
	free(a);
	free(b);
	free(c);
	free(d);
	
	return ans;
}

void premi(Atleta* atleti, int dimA) {
	list t = atletiPremiati(atleti, dimA);

	int dim = 0;
	Atleta* a = ordinaAtletiPer(atleti, dimA, "Nuoto", &dim);
	Atleta* b = ordinaAtletiPer(atleti, dimA, "Bici", &dim);
	Atleta* c = ordinaAtletiPer(atleti, dimA, "Corsa", &dim);
	Atleta* d = ordinaAtletiPer(atleti, dimA, "Totale", &dim);

	while (!empty(t)) {
		int tot = 0;

		for (int i = 0; i < 3 && dim - 1 - i >= 0; i++) {
			if (compareall(t->val, a[dim - i - 1])) tot += 100 * (3 - i);
		}

		for (int i = 0; i < 3 && dim - 1 - i >= 0; i++) {
			if (compareall(t->val, b[dim - i - 1])) tot += 100 * (3 - i);
		}

		for (int i = 0; i < 3 && dim - 1 - i >= 0; i++) {
			if (compareall(t->val, c[dim - i - 1])) tot += 100 * (3 - i);
		}

		for (int i = 0; i < 5 && dim - 1 - i >= 0; i++) {
			if (compareall(t->val, d[dim - i - 1])) tot += 100 * (5 - i);
		}

		stampaAtleta(t->val);
		printf("Premio: %d\n", tot);

		t = tail(t);
	}

	free(a);
	free(b);
	free(c);
	free(d);
	freelist(t);
}
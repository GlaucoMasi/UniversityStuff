#define _CRT_SECURE_NO_WARNINGS
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include "basket.h"

Giocatore* leggiGioc(char fileName[], int* dim) {
	*dim = 0;
	FILE* fp = fopen(fileName, "r");

	if (fp == NULL) {
		printf("Errore nell'apertura del file");
		return NULL;
	}

	char nome[sznome];
	int altezza, anno;
	
	int t, lenght = 0;
	while ((t = fscanf(fp, "%s %d %d", nome, &anno, &altezza)) > 0) {
		if (t == 3) lenght++;
	}
		
	rewind(fp);
	*dim = lenght;
	Giocatore* ans = (Giocatore*)(malloc(sizeof(Giocatore) * lenght));
	
	int idx = 0;
	while ((t = fscanf(fp, "%s %d %d", ans[idx].nome, &ans[idx].anno, &ans[idx].altezza)) > 0) {
		if (t == 3) idx++;
	}
		
	fclose(fp);
	return ans;
}

list leggiStagioni(char fileName[]) {
	FILE* fp = fopen(fileName, "r");

	if (fp == NULL) {
		printf("Errore nell'apertura del file");
		return NULL;
	}
	
	list ans = emptylist();
	char nome[sznome], squadra[sznome];
	int anno;

	while (fscanf(fp, "%s %s %d", nome, squadra, &anno) > 0) {
		Stagione t;
		strcpy(t.nome, nome);
		strcpy(t.squadra, squadra);
		t.anno = anno;
		ans = cons(t, ans);
	}

	fclose(fp);
	return ans;
}

void stampaStagioni(list stagioni) {
	if (empty(stagioni)) return;
	printelement(head(stagioni));
	stampaStagioni(tail(stagioni));
}

void mergesort(Giocatore* v, int l, int r, Giocatore* temp) {
	if (l == r - 1) return;

	int m = (l + r) / 2;
	mergesort(v, l, m, temp);
	mergesort(v, m, r, temp);

	int a = l, b = m;
	for (int i = l; i < r; i++) {
		if (a == m || (b != r && compare(v[a], v[b]) > 0)) copy(&temp[i], v[b++]);
		else copy(&temp[i], v[a++]);
	}

	for (int i = l; i < r; i++) copy(&v[i], temp[i]);
}

void ordina(Giocatore* elenco, int dim) {
	Giocatore* temp = (Giocatore*)malloc(sizeof(Giocatore) * dim);
	mergesort(elenco, 0, dim, temp);
	free(temp);
}

Giocatore cerca(char* nome, Giocatore* elenco, int dim) {
	for (int i = 0; i < dim; i++) {
		if (strcmp(elenco[i].nome, nome) == 0) return elenco[i];
	}

	Giocatore ans;
	strcpy(ans.nome, "NA");
	ans.altezza = ans.anno = -1;
	return ans;
}

list clean(list stagioni, Giocatore* elencoGioc, int dimGioc) {
	list ans = emptylist();
	
	while (!empty(stagioni)) {
		element curr = head(stagioni);
		Giocatore g = cerca(curr.nome, elencoGioc, dimGioc);

		if (strcmp(g.nome, "NA") != 0 && g.anno <= curr.anno) ans = cons(curr, ans);

		stagioni = tail(stagioni);
	}

	return ans;
}
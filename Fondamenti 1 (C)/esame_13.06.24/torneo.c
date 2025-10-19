#define _CRT_SECURE_NO_WARNINGS
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include "torneo.h"

list leggi_tutte_tenniste(char* fileName) {
	FILE* fp = fopen(fileName, "r");

	if (fp == NULL) {
		printf("Errore nell'apertura del file");
		return emptylist();
	}
	
	Tennista temp;
	list ans = emptylist();

	while (fscanf(fp, "%s %d %d", temp.nome, &temp.vittorie, &temp.sconfitte) == 3) {
		ans = cons(temp, ans);
	}

	fclose(fp);
	return ans;
}

Tennista* leggi_tenniste_selezionate(char* fileName, char tipo, int* dim) {
	*dim = 0;

	if (tipo != 'V' && tipo != 'S') return NULL;

	FILE* fp = fopen(fileName, "r");

	if (fp == NULL) {
		printf("Errore nell'apertura del file");
		return emptylist();
	}

	Tennista temp;
	int lenght = 0;

	while (fscanf(fp, "%s %d %d", temp.nome, &temp.vittorie, &temp.sconfitte) == 3) {
		if ((tipo == 'V' && temp.vittorie > temp.sconfitte) || (tipo == 'S' && temp.sconfitte > temp.vittorie)) lenght++;
	}

	rewind(fp);
	*dim = lenght;

	if (!lenght) return NULL;

	int idx = 0;
	Tennista* ans = (Tennista*)malloc(sizeof(Tennista) * lenght);
	while (fscanf(fp, "%s %d %d", temp.nome, &temp.vittorie, &temp.sconfitte) == 3) {
		if ((tipo == 'V' && temp.vittorie > temp.sconfitte) || (tipo == 'S' && temp.sconfitte > temp.vittorie)) {
			ans[idx] = temp;
			idx++;
		}
	}

	fclose(fp);
	return ans;
}

Tennista* ordina(list tenniste, int* dim) {
	int lenght = sz(tenniste); *dim = lenght;
	Tennista* v = (Tennista*)malloc(sizeof(Tennista) * lenght);

	for (int i = 0; i < lenght; i++, tenniste = tail(tenniste)) v[i] = head(tenniste);

	element* temp = (element*)malloc(sizeof(element) * lenght);
	mergehelp(v, 0, lenght, temp);
	free(temp);
	return v;
}

list seleziona(char* nome_tennista, list tenniste) {
	list ans = emptylist();
	Tennista curr = find(nome_tennista, tenniste);

	while (!empty(tenniste)) {
		if (abs(head(tenniste).vittorie - curr.vittorie) <= 2 && strcmp(head(tenniste).nome, nome_tennista) != 0) ans = cons(head(tenniste), ans);
		tenniste = tail(tenniste);
	}

	return ans;
}

int totale_diff(list tenniste) {
	if (empty(tenniste)) return 0;
	return head(tenniste).vittorie - head(tenniste).sconfitte + totale_diff(tail(tenniste));
}

int totale_diff_vettore(Tennista* tenniste, int dim) {
	list temp = emptylist();
	for (int i = 0; i < dim; i++) temp = cons(tenniste[i], temp);
	int ans = totale_diff(temp);
	freelist(temp);
	return ans;
}
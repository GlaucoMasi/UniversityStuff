#define _CRT_SECURE_NO_WARNINGS
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include "hr.h"

list leggi_posizioni(char* fileName) {
	list ans = emptylist();

	FILE* fp = fopen(fileName, "r");
	if (fp == NULL) {
		printf("Errore nell'apertura del file\n");
		return ans;
	}
		
	Posizione temp;
	while (fscanf(fp, "%s %s %c %d %d", temp.code, temp.posizione, &temp.settore, &temp.esperienza, &temp.stipendio) == 5) {
		ans = cons(temp, ans);
	}

	fclose(fp);
	return ans;
}

Candidato* leggi_candidati(FILE* fp, int* dim) {
	*dim = 0;
	int lenght = 0;
	
	Candidato temp;
	while (fscanf(fp, "%s %c %d\n", temp.nome, &temp.settore, &temp.esperienza) == 3) lenght++;

	rewind(fp);
	*dim = lenght;
	Candidato* ans = (Candidato*)(malloc(sizeof(Candidato) * lenght));
	
	for (int i = 0; i < lenght; i++) fscanf(fp, "%s %c %d\n", ans[i].nome, &ans[i].settore, &ans[i].esperienza);

	return ans;
}

Candidato* trova_candidati(char* p_id, list p, Candidato* c, int dim_c, int* dim) {
	Posizione pos;
	int found = 0;
	*dim = 0;

	while (!found && !empty(p)) {
		if (strcmp(p_id, head(p).code) == 0) {
			found = 1;
			pos = head(p);
		}

		p = tail(p);
	}

	if (!found) return NULL;

	int tot = 0;
	for (int i = 0; i < dim_c; i++) {
		if (c[i].settore == pos.settore && c[i].esperienza >= pos.esperienza) tot++;
	}

	*dim = tot;
	Candidato* ans = (Candidato*)(malloc(sizeof(Candidato) * tot));

	int idx = 0;
	for (int i = 0; i < dim_c; i++) {
		if (c[i].settore == pos.settore && c[i].esperienza >= pos.esperienza) {
			ans[idx] = c[i];
			idx++;
		}
	}

	return ans;
}

void posizioni_e_candidati(list p, Candidato* c, int dim_c) {
	if (p == emptylist()) return;
	else {
		int ans;
		trova_candidati(head(p).code, p, c, dim_c, &ans);
		printf("%s %s %d\n", head(p).code, head(p).posizione, ans);
		posizioni_e_candidati(tail(p), c, dim_c);
	}
}

Posizione* ordina(list posizioni, char tipo, int* dim) {
	int tot = count(tipo, posizioni);
	*dim = tot;

	if (tot == 0) return NULL;
	
	int idx = 0;
	Posizione* ans = (Posizione*)(malloc(sizeof(Posizione) * tot));

	while (!empty(posizioni)) {
		if (head(posizioni).settore == tipo) {
			ans[idx] = head(posizioni);
			idx++;
		}

		posizioni = tail(posizioni);
	}

	mergesort(ans, tot);
	return ans;
}
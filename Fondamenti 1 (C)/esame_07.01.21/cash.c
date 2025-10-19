#define _CRT_SECURE_NO_WARNINGS
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include "cash.h"

Operazione* leggiTutteOperazioni(char* fileName, int *dim) {
	int lenght = 0;
	*dim = 0;

	FILE* fp = fopen(fileName, "r");

	if (fp == NULL) {
		printf("Errore nell'apertura del file\n");
		return NULL;
	}


	int id = 0;
	float importo = 0;
	char negozio[sznome];

	while (fscanf(fp, "%d %f %s", &id, &importo, negozio) == 3) lenght++;

	rewind(fp);
	Operazione* ans = (Operazione*)(malloc(sizeof(Operazione) * lenght));

	for (int i = 0; i < lenght; i++) {
		fscanf(fp, "%d %f %s", &id, &importo, negozio);
		ans[i].id = id;
		ans[i].importo = importo;
		strcpy(ans[i].negozio, negozio);
	}


	fclose(fp);
	*dim = lenght;
	return ans;
}

list leggiNegozi(char* fileName) {
	FILE* fp = fopen(fileName, "r");

	if (fp == NULL) {
		printf("Errore nell'apertura del file\n");
		return NULL;
	}

	char negozio[sznome];
	char tipo;

	list ans = emptylist();

	while (fscanf(fp, "%s %c", negozio, &tipo) == 2) {
		Negozio temp;
		temp.tipo = tipo;
		strcpy(temp.negozio, negozio);
		ans = cons(temp, ans);
	}

	fclose(fp);
	return ans;
}

void stampaOperazioni(Operazione* v, int dim) {
	if (dim == 0) {
		printf("\n");
		return;
	}
	else {
		stampaOperazione(v[0]);
		stampaOperazioni(v+1, dim-1);
	}
}

void ordina(Operazione* v, int dim) {
	Operazione* temp = (Operazione*)(malloc(sizeof(Operazione) * dim));
	mergesort(v, 0, dim, temp);
	free(temp);
}

int negozioFisico(char* nomeNegozio, list negozi) {
	if (!count(nomeNegozio, negozi)) return 0;
	element t = find(nomeNegozio, negozi);
	return (t.tipo == 'F');
}

Operazione* filtra(Operazione* v, int dim, list negozi, int* dimLog) {
	int lenght = 0;
	Operazione* ans = NULL;

	for (int i = 0; i < dim; i++) if (negozioFisico(v[i].negozio, negozi)) lenght++;
	
	*dimLog = lenght;
	ans = (Operazione*)(malloc(sizeof(Operazione) * lenght));

	int idx = 0;
	for (int i = 0; i < dim; i++) if (negozioFisico(v[i].negozio, negozi)) {
		copyOperazione(&ans[idx], v[i]);
		idx++;
	}

	return ans;
}

float spesaCliente(int idCliente, Operazione* v, int dim) {
	float ans = 0;
	for (int i = 0; i < dim; i++) if (v[i].id == idCliente) ans += v[i].importo;
	return ans;
}
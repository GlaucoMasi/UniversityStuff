#define _CRT_SECURE_NO_WARNINGS
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include "element.h"

void stampaAtleta(Atleta a) {
	printf("%s %s ", a.codiceAtleta, a.nomeAtleta);
	
	int tot = 0;
	for (int i = 0; i < a.dim; i++) {
		printf("%d ", a.tempi[i]);
		tot += a.tempi[i];
	}

	printf("%d\n", (a.dim == 3 ? tot : -1));
}

int compare(Atleta a, Atleta b, char* tipo) {
	if (strcmp(tipo, "Nuoto") == 0) {
		if (a.dim < 1 || b.dim < 1) {
			printf("Un atleta non ha completato la prova\n");
			return 0;
		}

		if (a.tempi[0] < b.tempi[0]) return -1;
		else if (a.tempi[0] == b.tempi[0]) return 0;
		else return 1;
	}
	else if (strcmp(tipo, "Bici") == 0) {
		if (a.dim < 2 || b.dim < 2) {
			printf("Un atleta non ha completato la prova\n");
			return 0;
		}

		if (a.tempi[1] < b.tempi[1]) return -1;
		else if (a.tempi[1] == b.tempi[1]) return 0;
		else return 1;
	}
	else if (strcmp(tipo, "Corsa") == 0) {
		if (a.dim < 3 || b.dim < 3) {
			printf("Un atleta non ha completato la prova\n");
			return 0;
		}

		if (a.tempi[2] < b.tempi[2]) return -1;
		else if (a.tempi[2] == b.tempi[2]) return 0;
		else return 1;
	}
	else if (strcmp(tipo, "Totale") == 0) {
		if (a.dim < 3 || b.dim < 3) {
			printf("Un atleta non ha completato tutte le prove\n");
			return 0;
		}

		int tota = a.tempi[0] + a.tempi[1] + a.tempi[2];
		int totb = b.tempi[0] + b.tempi[1] + b.tempi[2];
		
		if (tota < totb) return -1;
		else if (tota == totb) return 0;
		else return 1;
	}
	else {
		printf("Tipo fornito invalido\n");
		return 0;
	}
}

int compareall(Atleta a, Atleta b) {
	return (strcmp(a.codiceAtleta, b.codiceAtleta) == 0);
}

void copy(Atleta* a, Atleta b) {
	a->dim = b.dim;
	for (int i = 0; i < a->dim; i++) a->tempi[i] = b.tempi[i];
	strcpy(a->nomeAtleta, b.nomeAtleta);
	strcpy(a->codiceAtleta, b.codiceAtleta);
}

void swap(Atleta* a, Atleta* b){
	Atleta temp;
	copy(&temp, *a);
	copy(a, *b);
	copy(b, temp);
}

void printelement(Atleta a) {
	stampaAtleta(a);
}
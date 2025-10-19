#define _CRT_SECURE_NO_WARNINGS
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include "element.h"

void printelement(element a) {
	printf("%s %c\n", a.negozio, a.tipo);
}

int compare(element a, element b) {
	
}

void copy(element* a, element b) {

}

void swap(element* a, element* b){
	element temp;
	copy(&temp, *a);
	copy(a, *b);
	copy(b, temp);
}

// a < b ?
int compareOperazione(Operazione a, Operazione b) {
	if (a.id < b.id) return 1;
	else if (b.id < a.id) return 0;
	else if (a.importo < b.importo) return 1;
	else return 0;
}

void stampaOperazione(Operazione a) {
	printf("%d %f %s\n", a.id, a.importo, a.negozio);
}

void copyOperazione(Operazione* a, Operazione b) {
	a->id = b.id;
	a->importo = b.importo;
	strcpy(a->negozio, b.negozio);
}

void mergesort(Operazione v[], int l, int r, Operazione temp[]) {
	if (l == r - 1) return;

	int m = (l + r) / 2;
	mergesort(v, l, m, temp);
	mergesort(v, m, r, temp);

	int a = l, b = m;
	for (int i = l; i < r; i++) {
		if (a == m || (b != r && compareOperazione(v[b], v[a]))) temp[i] = v[b++];
		else temp[i] = v[a++];
	}

	for (int i = l; i < r; i++) v[i] = temp[i];
}
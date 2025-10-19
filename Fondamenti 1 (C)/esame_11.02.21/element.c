#define _CRT_SECURE_NO_WARNINGS
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include "element.h"

int compare(element a, element b) {
	float importoa = a.prezzo * a.copie, importob = b.prezzo * b.copie;

	if (importoa < importob) return -1;
	else if (importoa > importob) return 1;
	else return strcmp(a.titolo, b.titolo);
}

void copy(element* a, element b) {
	strcpy(a->cliente, b.cliente);
	strcpy(a->fornitore, b.fornitore);
	a->copie = b.copie;
	a->prezzo = b.prezzo;
	strcpy(a->titolo, b.titolo);
}

void swap(element* a, element* b){
	element temp;
	copy(&temp, *a);
	copy(a, *b);
	copy(b, temp);
}

void printelement(element a) {
	printf("%s %s %d %f %s\n", a.cliente, a.fornitore, a.copie, a.prezzo, a.titolo);
}
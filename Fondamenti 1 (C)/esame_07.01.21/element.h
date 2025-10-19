#define _CRT_SECURE_NO_WARNINGS
#include <stdlib.h>
#include <stdio.h>

#define sznome 1025

typedef struct {
	int id;
	float importo;
	char negozio[sznome];
} Operazione;

typedef struct {
	char negozio[sznome];
	char tipo;
} Negozio;

typedef Negozio element;

void printelement(element a);

int compare(element a, element b);

void copy(element* a, element b);

void swap(element* a, element* b);

int compareOperazione(Operazione a, Operazione b);

void stampaOperazione(Operazione a);

void copyOperazione(Operazione* a, Operazione b);

void mergesort(Operazione v[], int l, int r, Operazione temp[]);
#define _CRT_SECURE_NO_WARNINGS
#include <stdlib.h>
#include <stdio.h>

#define sznome 32
#define sztipo 16

typedef struct {
	char nome[sznome];
	char tipo[sztipo];
	float prezzo;
	float quant;
} Formaggio;

typedef Formaggio element;

int compare(element a, element b);
void swap(element* a, element* b);
void printelement(element a);

void bubblesort(element* v, int dim);
void mergehelp(element* v, int l, int r, element* temp);
void insertsort(element* v, int dim);
void quickhelp(element* v, int l, int r);
void quicksort(element* v, int dim);
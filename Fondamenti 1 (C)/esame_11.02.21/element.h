#define _CRT_SECURE_NO_WARNINGS
#include <stdlib.h>
#include <stdio.h>

#define sznomi 64
#define sztitolo 128

typedef struct {
	char cliente[sznomi], fornitore[sznomi];
	int copie;
	float prezzo;
	char titolo[sznomi];
} Ordine;

typedef Ordine element;

int compare(element a, element b);

void copy(element* a, element b);

void swap(element* a, element* b);

void printelement(element a);
#define _CRT_SECURE_NO_WARNINGS
#include <stdlib.h>
#include <stdio.h>

typedef struct {
	char codiceAtleta[5];
	char nomeAtleta[24];
	int tempi[3];
	int dim;
} Atleta;

typedef Atleta element;

void stampaAtleta(Atleta a);

int compare(Atleta a, Atleta b, char* tipo);

int compareall(Atleta a, Atleta b);

void copy(Atleta* a, Atleta b);

void swap(Atleta* a, Atleta* b);

void printelement(Atleta a);
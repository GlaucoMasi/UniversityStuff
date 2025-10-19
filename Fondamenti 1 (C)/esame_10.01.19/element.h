#define _CRT_SECURE_NO_WARNINGS
#include <stdlib.h>
#include <stdio.h>

#define maxsz 256

typedef struct {
	int id;
	char ingresso[maxsz], uscita[maxsz];
} Evento;

typedef struct {
	char ingresso[maxsz], uscita[maxsz];
	float costo;
} Tariffa;

typedef Evento element;

void printelement(element a);

void copy(Tariffa* a, Tariffa b);
void swap(Tariffa* a, Tariffa* b);
int compare(Tariffa a, Tariffa b);
void quickhelp(Tariffa* v, int l, int r);
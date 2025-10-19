#define _CRT_SECURE_NO_WARNINGS
#include <stdlib.h>
#include <stdio.h>

#define sznome 1024

typedef struct {
	char nome[sznome];
	char squadra[sznome];
	int anno;
} Stagione;

typedef struct {
	char nome[sznome];
	int anno;
	int altezza;
} Giocatore;

typedef Stagione element;

int compare(Giocatore g1, Giocatore g2);
void copy(Giocatore* a, Giocatore b);

void printelement(element a);
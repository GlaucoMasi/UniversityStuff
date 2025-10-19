#define _CRT_SECURE_NO_WARNINGS
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include "element.h"

// -1: a < b, 0: a = b, 1: a > b
int compare(Giocatore g1, Giocatore g2) {
	if (g1.altezza != g2.altezza) return (g1.altezza > g2.altezza ? -1 : 1);
	else if (g1.anno != g2.anno) return (g1.anno < g2.anno ? -1 : 1);
	else return strcmp(g1.nome, g2.nome);
}

void copy(Giocatore* a, Giocatore b) {
	a->anno = b.anno;
	a->altezza = b.altezza;
	strcpy(a->nome, b.nome);
}

void printelement(element a) {
	printf("%s %s %d\n", a.nome, a.squadra, a.anno);
}
#define _CRT_SECURE_NO_WARNINGS
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include "list.h"

// Esercizio 1
Giocatore* leggiGioc(char fileName[], int* dim);
list leggiStagioni(char fileName[]);
void stampaStagioni(list stagioni);

// Esercizio 2
void ordina(Giocatore* elenco, int dim);
void mergesort(Giocatore* v, int l, int r, Giocatore* temp);
Giocatore cerca(char* nome, Giocatore* elenco, int dim);

// Esercizio 3
list clean(list stagioni, Giocatore* elencoGioc, int dimGioc);
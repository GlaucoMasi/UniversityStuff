#define _CRT_SECURE_NO_WARNINGS
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include "list.h"

// Esercizio 1
Formaggio* leggiFormaggi(char* fileName, int* dim);
list filtraFormaggi(Formaggio* elenco, int dim, char* tipo, float maxPrice);
void stampaFormaggi(list elenco);

// Esercizio 2
void ordina(Formaggio* elenco, int dim);
float controvalore(Formaggio* elenco, int dim, char* tipo);

// Esercizio 3
void stampaControvalori(Formaggio* elenco, int dim);
#define _CRT_SECURE_NO_WARNINGS
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include "list.h"

// Esercizio 1
list leggi_tutte_tenniste(char* fileName);
Tennista* leggi_tenniste_selezionate(char* fileName, char tipo, int* dim);

// Esercizio 2
Tennista* ordina(list tenniste, int* dim);

// Esercizio 3
list seleziona(char* nome_tennista, list tenniste);
int totale_diff(list tenniste);
int totale_diff_vettore(Tennista* tenniste, int dim);
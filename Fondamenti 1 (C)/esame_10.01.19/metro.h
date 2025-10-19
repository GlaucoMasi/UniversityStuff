#define _CRT_SECURE_NO_WARNINGS
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include "list.h"

Evento leggiUno(FILE* fp);
list leggiTutti(char* fileName);
Tariffa* leggiTariffe(char* fileName, int* dim);

void ordina(Tariffa* v, int dim);
float ricerca(Tariffa* v, int dim, char* ingresso, char* uscita);

void totali(Tariffa* tariffe, int dim, list eventi);
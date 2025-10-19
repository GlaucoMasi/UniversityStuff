#define _CRT_SECURE_NO_WARNINGS
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include "list.h"

// Esercizio 1
list leggi_posizioni(char* fileName);
Candidato* leggi_candidati(FILE* file, int* dim);

// Esercizio 2
Candidato* trova_candidati(char* p_id, list p, Candidato* c, int dim_c, int* dim);
void posizioni_e_candidati(list p, Candidato* c, int dim_c);

// Esercizio 3
Posizione* ordina(list posizioni, char tipo, int* dim);
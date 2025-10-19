#define _CRT_SECURE_NO_WARNINGS
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include "list.h"

Operazione* leggiTutteOperazioni(char* fileName, int* dim);
list leggiNegozi(char* fileName);
void stampaOperazioni(Operazione* v, int dim);

void ordina(Operazione* v, int dim);
int negozioFisico(char* nomeNegozio, list negozi);
Operazione* filtra(Operazione* v, int dim, list negozi, int* dimLog);

float spesaCliente(int idCliente, Operazione* v, int dim);
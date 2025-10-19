#define _CRT_SECURE_NO_WARNINGS
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include "list.h"

list leggi(char fileName[], char select, char nome[]);
void stampaOrdini(list elenco);

int contaCopieOrdinate(list elenco, char* titolo);
void aggregaPerTitolo(list elenco);
Ordine* prepara(list elenco, int* dim);

list nuoviOrdini(list elencoClienti, list elencoFornitori);
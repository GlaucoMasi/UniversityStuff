#define _CRT_SECURE_NO_WARNINGS
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include "list.h"

Atleta leggiSingoloAtleta(FILE* fp);

Atleta* leggiAtleti(char fileName[], int* dim);

Atleta* ordinaAtletiPer(Atleta* a, int dimA, char* tipo, int* dim);

list atletiPremiati(Atleta* a, int dimA);

void premi(Atleta* a, int dimA);
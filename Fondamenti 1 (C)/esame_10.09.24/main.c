#define _CRT_SECURE_NO_WARNINGS
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include "hr.h"

int main() {
	list posizioni = leggi_posizioni("posizioni.txt");
	//print(posizioni);

	int dim;
	FILE* fp = fopen("candidati.txt", "r");
	if (fp == NULL) {
		printf("Errore\n");
		return 0;
	}
	
	Candidato* candidati = leggi_candidati(fp, &dim);
	fclose(fp);
	
	/*
	printf("%d\n", dim);
	for (int i = 0; i < dim; i++) printf("%s %c %d\n", candidati[i].nome, candidati[i].settore, candidati[i].esperienza);
	printf("\n");
	*/

	/*
	int dim1;
	Candidato* poss = trova_candidati("J_007", posizioni, candidati, dim, &dim1);
	printf("%d\n", dim1);
	for (int i = 0; i < dim1; i++) printf("%s %c %d\n", poss[i].nome, poss[i].settore, poss[i].esperienza);
	*/

	/*
	posizioni_e_candidati(posizioni, candidati, dim);
	*/
	
	int dime, dimr;
	Posizione* ordinate_e = ordina(posizioni, 'E', &dime);
	Posizione* ordinate_r = ordina(posizioni, 'R', &dimr);
	for (int i = 0; i < dime; i++) printelement(ordinate_e[i]);
	printf("\n");
	for (int i = 0; i < dimr; i++) printelement(ordinate_r[i]);

	freelist(posizioni);
	free(candidati);
	//free(poss);
	free(ordinate_e);
	free(ordinate_r);
}
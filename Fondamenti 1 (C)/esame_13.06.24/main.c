#define _CRT_SECURE_NO_WARNINGS
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include "torneo.h"

int main() {
	list tutte = leggi_tutte_tenniste("tenniste.txt");
	// print(tutte);

	int dim1, dim2;
	Tennista* vincenti = leggi_tenniste_selezionate("tenniste.txt", 'V', &dim1);
	Tennista* perdenti = leggi_tenniste_selezionate("tenniste.txt", 'S', &dim2);

	printf("TENNISTE CON RECORD VINCENTE:\n");
	for (int i = 0; i < dim1; i++) printelement(vincenti[i]); printf("\n");
	printf("TENNISTE CON RECORD PERDENTE:\n");
	for (int i = 0; i < dim2; i++) printelement(perdenti[i]); printf("\n");

	int dim3;
	Tennista* ordinate = ordina(tutte, &dim3);
	//for (int i = 0; i < dim3; i++) printelement(ordinate[i]); printf("\n");

	list selezionate = seleziona("CarolaBellachioma", tutte);

	int dim4;
	Tennista* controcarola = ordina(selezionate, &dim4);
	printf("TENNISTE CHE POSSONO SFIDARE CarolaBellachioma:\n");
	for (int i = 0; i < dim4; i++) printelement(controcarola[i]); printf("\n");

	printf("Somma delle differenze per le tenniste con record perdente: %d\n", totale_diff_vettore(perdenti, dim2));

	free(vincenti);
	free(perdenti);
	free(ordinate);
	free(controcarola);
	freelist(tutte);
	freelist(selezionate);
}
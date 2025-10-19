#define _CRT_SECURE_NO_WARNINGS
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include "negozio.h"

int main() {
	int dim;
	Formaggio* formaggi = leggiFormaggi("banco.txt", &dim);
	//for (int i = 0; i < dim; i++) printelement(formaggi[i]);

	//stampaFormaggi(filtraFormaggi(formaggi, dim, "erborinato", -3));
	//stampaFormaggi(filtraFormaggi(formaggi, dim, "dolce", 10));
	//stampaFormaggi(filtraFormaggi(formaggi, dim, "buono", 3));
	// così facendo non posso deallocare le liste, solo per test

	//ordina(formaggi, dim);
	//for (int i = 0; i < dim; i++) printelement(formaggi[i]);

	stampaControvalori(formaggi, dim);

	free(formaggi);
}
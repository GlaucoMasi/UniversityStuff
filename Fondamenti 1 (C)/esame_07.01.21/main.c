#define _CRT_SECURE_NO_WARNINGS
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include "cash.h"

int main() {
	int dim = 0;
	Operazione* operazioni = leggiTutteOperazioni("operazioni.txt", &dim);
	//stampaOperazioni(operazioni, dim);

	list negozi = leggiNegozi("negozi.txt");
	//print(negozi);

	//ordina(operazioni, dim);
	//stampaOperazioni(operazioni, dim);

	//printf("%d\n", negozioFisico("FruttaVerduraSNC", negozi));
	//printf("%d\n", negozioFisico("ciaociaociao", negozi));
	//printf("%d\n", negozioFisico("AmazonEUSRL", negozi));

	int dim2 = 0;
	Operazione* fisici = filtra(operazioni, dim, negozi, &dim2);
	//stampaOperazioni(fisici, dim2);

	//printf("%f\n", spesaCliente(12, operazioni, dim));
	//printf("%f\n", spesaCliente(12, fisici, dim2));
	//printf("%f\n", spesaCliente(44, operazioni, dim));
	//printf("%f\n", spesaCliente(8, operazioni, dim));

	int* clienti = (int*)(malloc(sizeof(int) * dim));
	for (int i = 0; i < dim; i++) clienti[i] = operazioni[i].id;

	for (int i = 0; i < dim; i++) {
		int trovato = 0;
		for (int j = 0; j < i; j++) if (clienti[i] == clienti[j]) trovato = 1;
		if (!trovato) printf("%d %f\n", clienti[i], spesaCliente(clienti[i], fisici, dim2));
	}

	free(fisici);
	free(operazioni);
	freelist(negozi);
}
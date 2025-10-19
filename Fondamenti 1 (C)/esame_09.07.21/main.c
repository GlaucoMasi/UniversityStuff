#define _CRT_SECURE_NO_WARNINGS
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include "basket.h"

int main() {
	int dim;
	Giocatore* giocatori = leggiGioc("giocatori.txt", &dim);
	//printf("%d\n", dim);
	//for (int i = 0; i < dim; i++) printf("%s %d %d\n", giocatori[i].nome, giocatori[i].anno, giocatori[i].altezza);

	list stagioni = leggiStagioni("stagioni.txt");
	//stampaStagioni(stagioni); printf("\n");

	//ordina(giocatori, dim);
	//for (int i = 0; i < dim; i++) printf("%s %d %d\n", giocatori[i].nome, giocatori[i].anno, giocatori[i].altezza);

	//Giocatore g1 = cerca("MarkoJaric", giocatori, dim), g2 = cerca("GlaucoMasi", giocatori, dim);
	//printf("%s %d %d\n", g1.nome, g1.anno, g1.altezza);
	//printf("%s %d %d\n", g2.nome, g2.anno, g2.altezza);

	list pulita = clean(stagioni, giocatori, dim);
	//stampaStagioni(pulita);

	char nome[sznome];
	while (strcmp(nome, "NA") != 0) {
		printf("Specificare nome di un giocatore, inserire NA per terminare\n");
		scanf("%s", nome);

		if (strcmp(nome, "NA") != 0) {
			Giocatore g = cerca(nome, giocatori, dim);

			if (strcmp(g.nome, "NA") == 0) printf("Giocatore insistente\n");
			else printf("Anno di nascita: %d\nAltezza: %d\nStagioni giocate: %d\n\n", g.anno, g.altezza, count(g.nome, pulita));
		}
	}

	free(giocatori);
	freelist(stagioni);
	freelist(pulita);
}
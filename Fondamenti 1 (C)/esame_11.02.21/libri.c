#define _CRT_SECURE_NO_WARNINGS
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include "libri.h"

list leggi(char fileName[], char select, char nome[]) {
	FILE* fp = fopen(fileName, "r");

	if (fp == NULL) {
		printf("Errore nell'apetura del file\n");
		return NULL;
	}

	list ans = emptylist();

	char cliente[sznomi], fornitore[sznomi];
	int copie = 0;
	float prezzo = 0;

	while (fscanf(fp, "%s %s %d %f ", cliente, fornitore, &copie, &prezzo) == 4) {
		char c;
		int idx = 0;
		char titolo[sztitolo];

		c = fgetc(fp);
		while (c != '\n' && c != EOF) {
			titolo[idx] = c;
			idx++;
			c = fgetc(fp);
		}
		
		titolo[idx] = '\0';

		if (strcmp(nome, (select == 'C' ? cliente : fornitore)) == 0) {
			element temp;
			strcpy(temp.cliente, cliente);
			strcpy(temp.fornitore, fornitore);
			strcpy(temp.titolo, titolo);
			temp.copie = copie;
			temp.prezzo = prezzo;

			ans = cons(temp, ans);
		}
	}

	fclose(fp);
	return ans;
}

void stampaOrdini(list elenco) {
	print(elenco);
}

int contaCopieOrdinate(list elenco, char* titolo){
	if (empty(elenco)) return 0;
	else return contaCopieOrdinate(elenco->next, titolo) + (strcmp(titolo, elenco->val.titolo) == 0 ? elenco->val.copie : 0);
}

void aggregaPerTitolo(list elenco) {
	list titoli = emptylist();

	while (!empty(elenco)) {
		if (!contaCopieOrdinate(titoli, elenco->val.titolo)) {
			titoli = cons(elenco->val, titoli);
			printf("%s %d\n", elenco->val.titolo, contaCopieOrdinate(elenco, elenco->val.titolo));
		}

		elenco = elenco->next;
	}
}

Ordine* prepara(list elenco, int* dim) {
	list start = elenco;
	int lenght = 0;

	while (!empty(start)) {
		lenght++;
		start = start->next;
	}
	
	*dim = lenght;
	Ordine* ans = (Ordine*)(malloc(sizeof(Ordine) * lenght));
	
	for (int i = 0; i < lenght; i++, elenco = elenco->next) {
		copy(&ans[i], elenco->val);
	}

	int r = 1;
	while (r) {
		r = 0;
		for (int i = 0; i < lenght - 1; i++) {
			if (compare(ans[i], ans[i + 1]) > 0) {
				swap(&ans[i], &ans[i + 1]);
				r = 1;
			}
		}
	}

	return ans;
}

list nuoviOrdini(list elencoClienti, list elencoFornitori) {
	list titoli = emptylist(), ans = emptylist();

	while (!empty(elencoClienti)) {
		if (!contaCopieOrdinate(titoli, elencoClienti->val.titolo)) {
			int chiesti = contaCopieOrdinate(elencoClienti, elencoClienti->val.titolo);
			int comprati = contaCopieOrdinate(elencoFornitori, elencoClienti->val.titolo);

			if (chiesti > comprati) {
				element temp;
				strcpy(temp.cliente, "LaTrama");
				strcpy(temp.fornitore, "IGNOTO");
				temp.copie = chiesti - comprati;
				temp.prezzo = elencoClienti->val.prezzo;
				strcpy(temp.titolo, elencoClienti->val.titolo);
				ans = cons(temp, ans);
			}
			
			titoli = cons(elencoClienti->val, titoli);
		}

		elencoClienti = elencoClienti->next;
	}

	return ans;
}
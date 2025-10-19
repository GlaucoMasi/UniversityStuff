#define _CRT_SECURE_NO_WARNINGS
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include "metro.h"

Evento leggiUno(FILE* fp) {
	Evento ans;

	if (fscanf(fp, "%d", &ans.id) != 1) {
		ans.id = -1;
		ans.ingresso[0] = '\0';
		ans.uscita[0] = '\0';
		return ans;
	}
	
	fgetc(fp);
	char c = '.';
	for (int i = 0; i < maxsz && c != '@'; i++) {
		c = fgetc(fp);
		if (c != '@') ans.ingresso[i] = c;
		else ans.ingresso[i] = '\0';
	}
	
	c = '.';
	for (int i = 0; i < maxsz && c != '\n' && c != EOF; i++) {
		c = fgetc(fp);
		if (c != '\n' && c != EOF) ans.uscita[i] = c;
		else ans.uscita[i] = '\0';
	}

	return ans;
}

list leggiTutti(char* fileName) {
	list ans = emptylist();

	FILE* fp = fopen(fileName, "r");
	if (fp == NULL) {
		printf("Errore nell'apertura del file\n");
		return ans;
	}

	Evento temp;
	do{
		temp = leggiUno(fp);
		if (temp.id != -1) ans = cons(temp, ans);
	} while (temp.id != -1);

	fclose(fp);
	return ans;
}

Tariffa* leggiTariffe(char* fileName, int* dim) {
	*dim = 0;

	FILE* fp = fopen(fileName, "r");
	if (fp == NULL) {
		printf("Errore nell'apertura del file\n");
		return NULL;
	}

	char c;
	float trash;
	int lenght = 0;

	while (fscanf(fp, "%c", &c) == 1) {
		lenght++;

		do {
			c = fgetc(fp);
		} while (c != '@');
		
		do {
			c = fgetc(fp);
		} while (c != '@');

		fscanf(fp, "%f", &trash);
	};

	rewind(fp);
	*dim = lenght;
	Tariffa* ans = (Tariffa*)malloc(sizeof(Tariffa) * lenght);

	for (int i = 0; i < lenght; i++) {
		c = '.';
		for (int j = 0; j < maxsz && c != '@'; j++) {
			c = fgetc(fp);
			if (c != '@') ans[i].ingresso[j] = c;
			else ans[i].ingresso[j] = '\0';
		}

		c = '.';
		for (int j = 0; j < maxsz && c != '@'; j++) {
			c = fgetc(fp);
			if (c != '@') ans[i].uscita[j] = c;
			else ans[i].uscita[j] = '\0';
		}

		fscanf(fp, "%f", &ans[i].costo);
		c = fgetc(fp);
	}

	fclose(fp);
	return ans;
}

void ordina(Tariffa* v, int dim) {
	quickhelp(v, 0, dim-1);
}

float ricerca(Tariffa* v, int dim, char* ingresso, char* uscita) {
	for (int i = 0; i < dim; i++) {
		if (strcmp(v[i].ingresso, ingresso) == 0 && strcmp(v[i].uscita, uscita) == 0) return v[i].costo;
	}

	return 0;
}

void totali(Tariffa* tariffe, int dim, list eventi) {
	list fatte = emptylist();

	while (!empty(eventi)) {
		if (!count(head(eventi), fatte)) {
			float tot = 0;
			list temp = eventi;

			while (!empty(temp)) {
				if (head(temp).id == head(eventi).id) tot += ricerca(tariffe, dim, head(temp).ingresso, head(temp).uscita);
				temp = tail(temp);
			}

			printf("%d %f\n", head(eventi).id, tot);  
			fatte = cons(head(eventi), fatte);
		}

		eventi = tail(eventi);
	}
}
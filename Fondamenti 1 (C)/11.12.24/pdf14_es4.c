#define _CRT_SECURE_NO_WARNINGS
#include <stdlib.h>
#include <string.h>
#include <stdio.h>

typedef struct {
	char tipo[11], marca[11];
	float prezzo;
	int venduti;
} Item;

FILE* articoli(FILE* listino, FILE* venduti, char* tipologia, int* len) {
	Item *ans;
	int different = 0;

	char tipo[11], marca[11];
	float prezzo = 0;

	while (fscanf(listino, "%s %s %f", tipo, marca, &prezzo) == 3) {
		tipo[10] = marca[10] = '\0';
		if (strcmp(tipo, tipologia) == 0) different++;
	}

	ans = (Item*)malloc(sizeof(Item) * different);
	*len = different;
	
	int idx = 0;
	rewind(listino);
	while (fscanf(listino, "%s %s %f", tipo, marca, &prezzo) == 3) {
		tipo[10] = marca[10] = '\0';

		if (strcmp(tipo, tipologia) != 0) continue;
	
		strcpy(ans[idx].tipo, tipo);
		strcpy(ans[idx].marca, marca);
		ans[idx].prezzo = prezzo;
		ans[idx].venduti = 0;
		
		char currtipo[11], currmarca[11];

		while (fscanf(venduti, "%s %s", currtipo, currmarca) == 2) {
			currtipo[10] = currmarca[10] = '\0';
			if (strcmp(tipo, currtipo) == 0 && strcmp(marca, currmarca)) ans[idx].venduti++;
		}

		rewind(venduti);
		idx++;
	}

	return ans;
}

int main() {
	FILE *listino, *venduti;

	fopen_s(&listino, "listino.txt", "r");
	fopen_s(&venduti, "venduti.txt", "r");

	if (listino == NULL || venduti == NULL) return 0;
		
	int len = 0;
	Item* ans = articoli(listino, venduti, "pasta", &len);

	for (int i = 0; i < len; i++) {
		printf("%s %s %f %d\n", ans[i].tipo, ans[i].marca, ans[i].prezzo, ans[i].venduti);
	}
}
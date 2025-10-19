#define _CRT_SECURE_NO_WARNINGS
#include <stdlib.h>
#include <string.h>
#include <stdio.h>

#include "defrimb.h"

void scriviSpese(char* nomeFile) {
	char matricola[15];
	printf("Inserire la propria matricola\n");
	scanf("%s", matricola);
	matricola[7] = '\0';

	strcpy(nomeFile, strcat(matricola, ".txt"));

	FILE* f = fopen(nomeFile, "w");
	if (f == NULL) exit(1);

	int id;
	char tipo[1023];
	float importo;

	while (1) {
		scanf("%d", &id);
		if (id == -1) break;
		scanf("%s %f", tipo, &importo);
		fprintf(f, "%d %s %f\n", id, tipo, importo);
	}

	fclose(f);
}

Spesa* leggiSpese(char* nomeFile, int* dim) {
	Spesa* ans;
	int lenght = 0;

	int id;
	char tipo[1023];
	float importo;

	FILE* f = fopen(nomeFile, "r");
	if (f == NULL) exit(1);

	while (fscanf(f, "%d %s %f", &id, tipo, &importo) == 3) {
		lenght++;
	}
	
	rewind(f);
	*dim = lenght;
	ans = (Spesa*)malloc(sizeof(Spesa) * lenght);
	
	for(int i = 0; i < lenght; i++){
		fscanf(f, "%d%s%f", &id, tipo, &importo);
		ans[i].id = id;
		ans[i].importo = importo;
		strcpy(ans[i].tipo, tipo);
	}

	fclose(f);
	return ans;
}

// v[a] < v[b] ? 
int compare(Spesa a, Spesa b) {
	if (a.id != b.id) return a.id < b.id;
	else if (strcmp(a.tipo, b.tipo) != 0) return (strcmp(a.tipo, b.tipo) < 0);
	else return a.importo < b.importo;
}

void mergesort(Spesa v[], int l, int r, Spesa temp[]) {
	if (l == r - 1) return;

	int m = (l + r) / 2;
	mergesort(v, l, m, temp);
	mergesort(v, m, r, temp);

	int a = l, b = m;
	for (int i = l; i < r; i++) {
		if (a == m || (b != r && compare(v[b], v[a]))) temp[i] = v[b++];
		else temp[i] = v[a++];
	}

	for (int i = l; i < r; i++) v[i] = temp[i];
}

void ordina(Spesa* v, int dim) {
	Spesa* temp = malloc(sizeof(Spesa) * dim);
	mergesort(v, 0, dim, temp);
	free(temp);
}

int same(Spesa a, Spesa b) {
	if (a.id != b.id) return 0;
	if (a.importo != b.importo) return 0;
	if (strcmp(a.tipo, b.tipo) != 0) return 0;
	return 1;
}

Spesa* eliminaDuplicati(Spesa* v, int dim, int* dimNew) {
	Spesa* nuovo = (Spesa*)malloc(dim * sizeof(Spesa));
	
	int idx = 1;
	
	nuovo[0].id = v[0].id;
	nuovo[0].importo = v[0].importo;
	strcpy(nuovo[0].tipo, v[0].tipo);

	for (int i = 1; i < dim; i++) {
		if (!same(v[i - 1], v[i])) {
			nuovo[idx].id = v[i].id;
			nuovo[idx].importo = v[i].importo;
			strcpy(nuovo[idx].tipo, v[i].tipo);
			idx++;
		}
	}

	*dimNew = idx;
	return nuovo;
}

int main() {
	char* nome[15];
	scriviSpese(nome);

	//printf("%s\n", nome);

	int dim = 0;
	Spesa* ans = leggiSpese(nome, &dim);
	
	/*
	printf("%d\n", dim);
	for (int i = 0; i < dim; i++) {
		printf("%d %s %f\n", ans[i].id, ans[i].tipo, ans[i].importo);
	}
	*/

	ordina(ans, dim);

	/*
	for (int i = 0; i < dim; i++) {
		printf("%d %s %f\n", ans[i].id, ans[i].tipo, ans[i].importo);
	}
	*/
		
	int nuova = 0;
	ans = eliminaDuplicati(ans, dim, &nuova);
	
	/*
	printf("%d\n", nuova);
	for (int i = 0; i < nuova; i++) {
		printf("%d %s %f\n", ans[i].id, ans[i].tipo, ans[i].importo);
	}
	*/

	Rimborso* rimborsi = (Rimborso*)malloc(sizeof(Rimborso) * nuova);


}
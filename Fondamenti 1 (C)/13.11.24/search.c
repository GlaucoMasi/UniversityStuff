#include <stdio.h>

#define MAXSZ 100

#define RESULT int
#define FOUND 1
#define NOTFOUND 0

RESULT find(int n, int v[], int element, int* pos) {
	for (int i = 0; i < n; i++) if (v[i] == element) {
		*pos = i+1;
		return FOUND;
	}

	return NOTFOUND;
}

int main() {
	int n, element, pos;
	int v[MAXSZ];

	printf("Inserire la dimensione dell'array, gli elementi e il valore da cercare\n");
	
	scanf_s("%d", &n);
	if (n > MAXSZ) {
		printf("Numero eccessivo di valori\n");
		return 0;
	}

	for (int i = 0; i < n; i++) scanf_s("%d", &v[i]);
	scanf_s("%d", &element);

	if (find(n, v, element, &pos) == FOUND) printf("Elemento trovato in posizione %d\n", pos);
	else printf("Elemento non trovato\n");

	for (int i = 0; i < n; i++) printf("%d ", v[i]);
}
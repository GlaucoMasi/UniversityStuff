#include <stdio.h>
#include <stdlib.h>

#define maxsize 10

int input(int v[]) {
	int a;
	for (int i = 0; i <= maxsize; i++) {
		scanf_s("%d", &a);
		if (a == 0) return i;
		else v[i] = a;
	}
}

int compare(const void *a, const void *b) {
	return (*((int*)a) > *((int*)b));
}

int main() {
	int a[maxsize], b[maxsize];
	printf("Inserire gli elementi degli array, seguiti da due 0\n");

	int dima = input(a), dimb = input(b);

	qsort(a, dima, sizeof(int), compare);
	qsort(b, dimb, sizeof(int), compare);
	
	int share[maxsize];
	int i = 0, j = 0, idx = 0;
	while (i < dima && j < dimb) {
		if (a[i] < b[j]) i++;
		else if (a[i] > b[j]) j++;
		else {
			share[idx] = a[i];
			i++; j++; idx++;
		}
	}
		
	printf("Ci sono %d elementi in comune\n", idx);
	for (int i = 0; i < idx; i++) printf("%d ", share[i]);
}
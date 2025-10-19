#include <stdio.h>
#include <stdlib.h>

#define maxsize 10

#define RESULT int
#define FOUND 1
#define NOTFOUND 0

int input(int v[]) {
	int a;
	for (int i = 0; i <= maxsize; i++) {
		scanf_s("%d", &a);
		if (a == 0) return i;
		else v[i] = a;
	}
}

int compare(const void* a, const void* b) {
	return (*((int*)a) > *((int*)b));
}

int unique(int n, int v[], int comp[]) {
	qsort(v, n, sizeof(int), compare);

	int idx = 1;
	comp[0] = v[0];
	for (int i = 1; i < n; i++) if (v[i] != v[i - 1]) {
		comp[idx] = v[i];
		idx++;
	}

	return idx;
}

int max2(int x, int y) {
	return (x > y ? x : y);
}

int max3(int x, int y, int z) {
	return max2(max2(x, y), z);
}

RESULT find(int n, int v[], int element, int* pos) {
	for (int i = 0; i < n; i++) if (v[i] == element) {
		*pos = i + 1;
		return FOUND;
	}

	return NOTFOUND;
}
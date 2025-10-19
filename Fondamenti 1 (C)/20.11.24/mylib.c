#include <stdio.h>
#include <stdlib.h>

#define maxsize 20

#define BOOL int
#define TRUE 1
#define FALSE 0

// input a vector
int input(int v[]) {
	int a;
	for (int i = 0; i <= maxsize; i++) {
		scanf_s("%d", &a);
		if (a == 0) return i;
		else v[i] = a;
	}
}

// swap values
void swap(int* a, int* b) {
	int t = *a;
	*a = *b;
	*b = t;
}

// compare for sort
int compare(const void* a, const void* b) {
	return (*((int*)a) > *((int*)b));
}

// sort vector
void bubblesort(int n, int a[]) {
	int ord = 0;
	while (!ord) {
		ord = 1;
		for (int i = 0; i < n - 1; i++) if (a[i] > a[i+1]) swap(&a[i], &a[i + 1]), ord = 0;
	}
}

// create vector of uniques and size
int unique(int n, int v[], int comp[]) {
	bubblesort(n, v);

	int idx = 1;
	comp[0] = v[0];
	for (int i = 1; i < n; i++) if (v[i] != v[i - 1]) {
		comp[idx] = v[i];
		idx++;
	}

	return idx;
}

// max of 2 values
int max2(int x, int y) {
	return (x > y ? x : y);
}

// max of 3 values
int max3(int x, int y, int z) {
	return max2(max2(x, y), z);
}

// find element in vector
BOOL find(int n, int v[], int element, int* pos) {
	for (int i = 0; i < n; i++) if (v[i] == element) {
		*pos = i + 1;
		return TRUE;
	}

	return FALSE;
}

// check vectors identity
BOOL comparevect(int n, int m, int a[], int b[]) {
	if (n != m) return 0;
	bubblesort(n, a); bubblesort(m, b);

	for (int i = 0; i < n; i++) if (a[i] != b[i]) return FALSE;
	return TRUE;
}
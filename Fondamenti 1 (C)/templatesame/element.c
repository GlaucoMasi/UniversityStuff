#define _CRT_SECURE_NO_WARNINGS
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include "element.h"

// -1: a < b, 0: a = b, 1: a > b
// Considerare anche il caso a == b per find/count
int compare(element a, element b) {

}

void swap(element* a, element* b){
	element temp = *a;
	*a = *b;
	*b = temp;
}

void printelement(element a) {

}

void bubblesort(element* v, int dim) {
	int ord = 0;
	while (!ord) {
		ord = 1;
		for (int i = 0; i < dim - 1; i++) if (compare(v[i], v[i + 1]) > 0) swap(&v[i], &v[i + 1]), ord = 0;
	}
}

void mergehelp(element* v, int l, int r, element* temp) {
	if (l == r - 1) return;

	int m = (l + r) / 2;
	mergehelp(v, l, m, temp);
	mergehelp(v, m, r, temp);

	int a = l, b = m;
	for (int i = l; i < r; i++) {
		if (a == m || (b != r && compare(v[a], v[b]) > 0)) temp[i] = v[b++];
		else temp[i] = v[a++];
	}

	for (int i = l; i < r; i++) v[i] = temp[i];
}

void mergesort(element* v, int dim) {
	element* temp = (element*)malloc(sizeof(element) * dim);
	mergehelp(v, 0, dim, temp);
	free(temp);
}

void insertsort(element* v, int dim) {
	for (int i = 1; i < dim; i++) {
		int k = 0;
		while (k < i && compare(v[k], v[i]) <= 0) k++;
		if (k != i) for (int t = k; t < i; t++) swap(&v[t], &v[i]);
	}
}

void quickhelp(element* v, int l, int r) {
	if (l >= r) return;

	int i = l, j = r, pivot = r;
	
	while (i < j) {
		while (i < j && compare(v[i], v[pivot]) <= 0) i++;
		while (i < j && compare(v[j], v[pivot]) >= 0) j--;
		if (i != j) swap(&v[i], &v[j]);
	}

	if (i != pivot && compare(v[i], v[pivot]) != 0) {
		swap(&v[i], &v[pivot]);
		pivot = i;
	}

	quickhelp(v, l, pivot - 1);
	quickhelp(v, pivot + 1, r);
}

void quicksort(element* v, int dim) {
	quickhelp(v, 0, dim-1);
}
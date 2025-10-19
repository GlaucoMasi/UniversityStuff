#define _CRT_SECURE_NO_WARNINGS
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include "element.h"

void printelement(element a) {
	printf("%d %s %s\n", a.id, a.ingresso, a.uscita);
}

// -1: a < b, 0: a = b, 1: a > b
int compare(Tariffa a, Tariffa b) {
	int first = strcmp(a.ingresso, b.ingresso);
	if (first != 0) return first;

	int second = strcmp(a.uscita, b.uscita);
	if (second != 0) return second;

	if (a.costo < b.costo) return -1;
	else return 1;
}

void copy(Tariffa* a, Tariffa b) {
	a->costo = b.costo;
	strcpy(a->ingresso, b.ingresso);
	strcpy(a->uscita, b.uscita);
}

void swap(Tariffa* a, Tariffa* b) {
	Tariffa temp;
	copy(&temp, *a);
	copy(a, *b);
	copy(b, temp);
}

void quickhelp(Tariffa* v, int l, int r) {
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
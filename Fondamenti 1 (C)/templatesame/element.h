#define _CRT_SECURE_NO_WARNINGS
#include <stdlib.h>
#include <stdio.h>

typedef struct {

} esempio;

typedef esempio element;

int compare(element a, element b);
void swap(element* a, element* b);
void printelement(element a);

void bubblesort(element* v, int dim);
void mergehelp(element* v, int l, int r, element* temp);
void mergesort(element* v, int dim);
void insertsort(element* v, int dim);
void quickhelp(element* v, int l, int r);
void quicksort(element* v, int dim);
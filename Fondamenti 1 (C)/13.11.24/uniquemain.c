#include <stdio.h>
#include "mylib.h"

#define maxsize 10

int main() {
	int dim, dimans, v[maxsize], ans[maxsize];
	
	printf("Inserire al massimo 10 valori, seguiti dallo 0\n");
	dim = input(v);

	dimans = unique(dim, v, ans);
	
	printf("Valori distinti:\n");
	for (int i = 0; i < dimans; i++) printf("%d ", ans[i]);
}
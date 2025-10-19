#include <stdio.h>
#include "mylib.h"
#define maxsize 20

int compareslow(int n, int m, int a[], int b[]) {
	int used[maxsize] = {0};

	for (int i = 0; i < n; i++) {
		int lastidx = -1;
		for (int j = 0; j < m; j++) if (!used[j] && a[i] == b[j]) lastidx = j;
		
		if (lastidx == -1) return 0;
		used[lastidx] = 1;
	}

	return 1;
}

int main() {
	int n, m, a[maxsize], b[maxsize];
	n = input(a); m = input(b);

	if (comparevect(n, m, a, b)) printf("Same\n");
	else printf("Different\n");
}
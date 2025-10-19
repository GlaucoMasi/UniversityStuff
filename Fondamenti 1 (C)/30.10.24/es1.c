#include <stdio.h>

int mioMax(int x, int y) {
	return (x > y ? x : y);
}

int max3(int x, int y, int z) {
	return mioMax(mioMax(x, y), z);
}

int main() {
	int a, b, c;
	printf("Inserire tre valori\n");
	scanf_s("%d %d %d", &a, &b, &c);

	printf("Valore massimo: %d", max3(a, b, c));
}
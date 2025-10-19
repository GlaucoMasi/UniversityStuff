#include <stdio.h>
#define bool int
#define true 1
#define false 0

bool isPrimo(int x) {
	for (int i = 2; i < x; i++) if (x % i == 0) return false;
	return true;
}

int main() {
	int x;
	printf("Inserire un numero:\n");
	scanf_s("%d", &x);

	printf("Numeri primi tra 1 e N\n");
	for (int i = 2; i <= x; i++) if (isPrimo(i)) printf("%d\n", i);
}
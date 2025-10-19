#include <stdio.h>

int main() {
	int sum = 0, a;
	unsigned long long prod = 1;

	do {
		scanf_s("%d", &a);
		if (a > 0) prod *= a;
		else if (a < 0) sum += a;
	} while (a);

	printf("Sum: %d\nProd: %llu\n", sum, prod);
}
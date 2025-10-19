#include <stdio.h>
#include <stdlib.h>
#define _CRT_SECURE_NO_WARNINGS
#define _CRT_SECURE_NO_DEPRECATE

int main() {
	char a, b, c;
	printf("Tre caratteri:\n");
	scanf_s("%c %c %c", &a, 1, &b, 1, &c, 1);

	char small, big;
	small = min(min(a, b), c );
	big = max(max(a, b), c);

	small = (a < b ? (a < c ? a : c) : (b < c ? b : c));
	big = (a < b ? (a > c ? a : c) : (b > c ? b : c));
	
	printf("Ordine alfabetico:\n");
	printf("%c %c %c", small, (char)(a + b + c - small - big), big);
}
#include <stdio.h>
#include <stdlib.h>

int main() {
	float a, b, c;
	printf("Input costo oggetti:\n");
	scanf_s("%f%f%f", &a, &b, &c);

	float big = a;
	if (b > big) big = b;
	if (c > big) big = c;
	printf("%f", a + b + c - 0.1f * big);
}
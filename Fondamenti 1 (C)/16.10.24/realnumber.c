#include <stdio.h>
#include <stdlib.h>
#define _CRT_SECURE_NO_WARNINGS

int main() {
	float n;
	printf("Input real number:\n");
	scanf_s("%f", &n);

	float abs = ((n >= 0) ? n : -n);
	printf("Absolute value: %f\n", abs);
	printf("Integer part: %d\n", (int)n);
}
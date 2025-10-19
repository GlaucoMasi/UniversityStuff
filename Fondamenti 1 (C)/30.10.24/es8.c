#include <stdio.h>
#include <math.h>

float perimetro(float a, float b, float c) {
	return a + b + c;
}

float area(float a, float b, float c) {
	float p = perimetro(a, b, c) / 2.0;
	return sqrt(p * (p - a) * (p - b) * (p - c));
}

void swap(float* a, float* b) {
	float t = *a;
	*a = *b;
	*b = t;
}

int main() {
	float a, b, c;

	while (1) {
		printf("Inserire tre lati:\n");
		scanf_s("%f %f %f", &a, &b, &c);

		if (a > b) swap(&a, &b);
		if (a > c) swap(&a, &c);
		if (b > c) swap(&b, &c);

		if (a <= 0 || b <= 0 || c <= 0) printf("Valori invalidi, lato <= 0\n");
		else if (a + b < c) printf("Valori invalidi, disuguaglianza triangolare non rispettata\n");
		else break;
	}

	printf("Perimetro: %f\nArea: %f\n", perimetro(a, b, c), area(a, b, c));
}
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main() {
	FILE* bin;
	fopen_s(&bin, "binario.dat", "wb");

	if (bin == NULL) {
		printf("Apertura fallita");
		return 0;
	}

	int a; float b;
	char s[64];

	scanf_s("%d", &a);
	scanf_s("%f", &b);
	scanf_s("%s", s, 64);
	fclose(bin);

	fopen_s(&bin, "binario.dat", "rb");

	if (bin == NULL) {
		printf("Apertura fallita");
		return 0;
	}

	fread(&a, sizeof(int), 1, bin);
	fread(&b, sizeof(float), 1, bin);
	fread(s, 64, 1, bin);

	printf("%d\n%f\n%s", a, b, s);
}
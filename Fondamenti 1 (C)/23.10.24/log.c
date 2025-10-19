#include <stdio.h>
#include <math.h>

int main() {
	int base, val, flagfirst = 1;
	do {
		if (flagfirst) flagfirst = 0;
		else printf("Invalid base and/or val\n");

		printf("Insert base and value:\n");
		scanf_s("%d %d", &base, &val);
	} while (base <= 0 || val <= 0 || base == 1);

	printf("Logarithm: %f", log(val) / log(base));
}
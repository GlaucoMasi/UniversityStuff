#include <stdio.h>

int main() {
	char voto;
	scanf_s("%c", &voto, 1);

	switch (voto) {
		case 'A':
			printf("Ottimo\n");
			break;
		case 'B':
			printf("Distinto\n");
			break;
		case 'C':
			printf("Buono\n");
			break;
		case 'D':
			printf("Sufficiente\n");
			break;
		case 'E':
			printf("Insufficiente\n");
			break;
		case 'F':
			printf("Bocciato\n");
			break;
		default:
			printf("Muori\n");
	}
}
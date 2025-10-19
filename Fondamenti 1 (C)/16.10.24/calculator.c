#include <stdio.h>

int main() {
	int a, b; char c;

	do {
		printf("Espressione:\n");
		scanf_s("%d %c %d", &a, &c, 1, &b);

		switch (c) {
		case '+':
			printf("%d\n", a + b);
			break;
		case '-':
			printf("%d\n", a - b);
			break;
		case '*':
			printf("%d\n", a * b);
			break;
		case '/':
			if (b == 0) printf("Invalid\n");
			else printf("%f\n", a * 1.0 / b);
			break;
		case '.':
			break;
		default:
			printf("Invalid Operand\n");
		}
	} while (c != '.');
}
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main() {
	FILE* t;
	fopen_s(&t, "Prova.txt", "w");

	if (t == NULL) {
		printf("Apertura fallita");
		return 0;
	}

	char s[64];
	while (1) {
		scanf_s("%s", s, 64); s[63] = '\0';
		if (strcmp(s, "fine") == 0) break;
		else fprintf(t, "%s\n", s);
	}

	fclose(t);
}
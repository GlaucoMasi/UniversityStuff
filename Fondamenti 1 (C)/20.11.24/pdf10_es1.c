#include <stdio.h>
#include <string.h>
#define maxsz 100

int conc(char a[], char b[], char c[]) {
	int free = 0;
	while (c[free] != '\0') free++;
	for (int i = 0; i < maxsz && a[i] != '\0'; i++, free++) c[free] = a[i];
	for (int i = 0; i < maxsz && b[i] != '\0'; i++, free++) c[free] = b[i];
	c[free] = '\0';
	return free;
}

int main() {
	char a[maxsz], b[maxsz], c[maxsz];
	scanf_s("%s%s%s", a, maxsz, b, maxsz, c, maxsz);

	// no warnings
	a[maxsz - 1] = b[maxsz - 1] = '\0';

	//conc(a, b, c);
	strcat_s(c, maxsz, a);
	strcat_s(c, maxsz, b);

	printf("%d\n", (int)strlen(c));
	printf("%s", c);
}
#include <stdio.h>

int main() {
	char c;
	do {
		scanf_s("%c%*c", &c, 1);
		if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) printf("%d\n", c);
		else if (c == '0') printf("Terminate\n");
		else printf("Invalid character\n");
	} while (c != '0');
}
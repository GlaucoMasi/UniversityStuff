#include <stdio.h>
#include <math.h>

float calc(int a, int b) {
	return sqrt(a*a+b*b);
}

int main() {
	int n;
	printf("Inserire numero N:\n");
	scanf_s("%d", &n);

	for (int i = 1; i <= n; i++) for (int j = i; j <= n; j++) {
		float c = calc(i, j);
		if(c == (int)c) printf("%d %d %d\n", i, j, (int)c);
	}
}
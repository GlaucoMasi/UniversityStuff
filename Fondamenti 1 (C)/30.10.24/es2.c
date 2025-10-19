#include <stdio.h>

int somma(int n) {
	int ans = 0;
	for (int i = 1; i <= n; i++) ans += i;
	return ans;
}

int somma2(int n) {
	int ans = 0;
	for (int i = 1; i <= n; i++) ans += somma(i);
	return ans;
}

int main() {
	int n;
	printf("Inserire un valore N\n");
	scanf_s("%d", &n);
	printf("SommaDiSomme: %d", somma2(n));
}
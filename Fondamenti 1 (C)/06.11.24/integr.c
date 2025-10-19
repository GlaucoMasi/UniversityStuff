#include <stdio.h>
#define ERROR_TYPE int
#define INTERV 0
#define SUCCESS 1

double f(double x) {
	return x;
}

ERROR_TYPE calc(double n, double a, double b, double* area) {
	if (a > b) return INTERV;
	if (n == 0) return SUCCESS;

	double curr = (b - a) / n;
	calc(n - 1, a + curr, b, area);
	*area += f(a + curr/2.0) * curr;
	return SUCCESS;
}

int main() {
	double n, a, b, area = 0;
	printf("Inserire approssimazione, estremo sinistro ed estremo destro:\n");
	scanf_s("%lf %lf %lf", &n, &a, &b);

	if (calc(n, a, b, &area) == INTERV) printf("Intervallo invalido\n");
	else printf("Area: %f", area);
}
#include <stdlib.h>
#include <string.h>
#include <stdio.h>

typedef struct {
	int id;
	char tipo[1023];
	float importo;
} Spesa;
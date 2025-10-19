#include <stdlib.h>
#include <string.h>
#include <stdio.h>

#include "defspesa.h"

typedef struct {
	int id;
	int dim;
	Spesa spese[];
} Rimborso;
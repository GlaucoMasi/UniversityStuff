#define _CRT_SECURE_NO_WARNINGS
#include <stdlib.h>
#include "element.h"

typedef struct item {
	element val;
	struct item* next;
} item;

typedef item* list;

int empty(list i);
element head(list i);
list tail(list i);
list emptylist();
list cons(element a, list i);
void freelist(list i);
void print(list i);
int count(char nome[], list i);
element find(char nome[], list i);
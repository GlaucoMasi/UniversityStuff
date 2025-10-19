#define _CRT_SECURE_NO_WARNINGS
#include <stdlib.h>
#include "list.h"

int empty(list i) {
	return (i == NULL);
}

element head(list i) {
	if (empty(i)) exit(1);
	return i->val;
}

list tail(list i) {
	if (empty(i)) return NULL;
	return i->next;
}

list emptylist() {
	return NULL;
}

list cons(element a, list i) {
	list t = (list)malloc(sizeof(item));
	t->val = a;
	t->next = i;
	return t;
}

void freelist(list i) {
	if (empty(i)) return;
	freelist(i->next);
	free(i);
}

void print(list i) {
	if (empty(i)) printf("\n");
	else {
		printelement(i->val);
		print(i->next);
	}
}

int count(element a, list i) {
	if (empty(i)) return 0;
	return count(a, i->next) + (compare(a, i->val) == 0);
}
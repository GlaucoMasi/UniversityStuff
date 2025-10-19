#define _CRT_SECURE_NO_WARNINGS
#include <stdlib.h>
#include "list.h"

int empty(list i) {
	return (i == NULL);
}

element head(list i) {
	if (empty(i)) exit(-1);
	else return i->val;
}

list tail(list i) {
	if (empty(i)) exit(-2);
	else return i->next;
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
	freelist(tail(i));
	free(i);
}

void print(list i) {
	if (empty(i)) printf("\n");
	else {
		printelement(head(i));
		print(tail(i));
	}
}

int count(element a, list i) {
	if (empty(i)) return 0;
	return count(a, tail(i)) + (compare(a, head(i)) == 0);
}
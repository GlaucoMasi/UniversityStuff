#define _CRT_SECURE_NO_WARNINGS
#include <stdlib.h>
#include <string.h>
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

int count(char nome[], list i) {
	if (empty(i)) return 0;
	return count(nome, i->next) + (strcmp(i->val.negozio, nome) == 0);
}

element find(char nome[], list i) {
	if (strcmp(i->val.negozio, nome) == 0) return i->val;
	else return find(nome, i->next);
}
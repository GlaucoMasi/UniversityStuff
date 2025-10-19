#define _CRT_SECURE_NO_WARNINGS
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include "element.h"

void printelement(element a) {

}

int compare(element a, element b) {
	
}

void copy(element* a, element b) {

}

void swap(element* a, element* b){
	element temp;
	copy(&temp, *a);
	copy(a, *b);
	copy(b, temp);
}
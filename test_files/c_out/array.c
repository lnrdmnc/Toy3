#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#define BUFFER_SIZE 1024*4

// Funzioni di supporto
char* buffer;
char* string_concat(char* s1, char* s2) {
    char* ns = malloc(strlen(s1) + strlen(s2) + 1);
    strcpy(ns, s1);
    strcat(ns, s2);
    return ns;
}

char* int2str(int n) {
    char buffer[BUFFER_SIZE];
    int len = sprintf(buffer, "%d", n);
    char *ns = malloc(len + 1);
    sprintf(ns, "%d", n);
    return ns;
}

char* char2str(char c) {
    char *ns = malloc(2);
    sprintf(ns, "%c", c);
    return ns;
}

char* float2str(float f) {
    char buffer[BUFFER_SIZE];
    int len = sprintf(buffer, "%f", f);
    char *ns = malloc(len + 1);
    sprintf(ns, "%f", f);
    return ns;
}

char* bool2str(int b) {
    char* ns = NULL;
    if(b) {
        ns = malloc(5);
        strcpy(ns, "true");
    } else {
        ns = malloc(6);
        strcpy(ns, "false");
    }
    return ns;
}

int a[5];

int main() {
a[0] = 10;

    return 0;
}

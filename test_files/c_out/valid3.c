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

void scoping_fun(int n, int m, char* *message);
int glob_fun();
void scoping_fun(int n, int m, char* *message){*message = "level 1";
if(n <= 1){*message = "level 2.1";
if(m <= 1){*message = "level 3.1";
printf("%s\n", *message);
}else{if(m > 1 && m < 5){*message = "level 3.2";
printf("%s\n", *message);
}else{*message = "level 3.3";
printf("%s\n", *message);
}}printf("%s\n", *message);
}else{*message = "level 2.2";
if(m <= 1){*message = "level 3.4";
printf("%s\n", *message);
}else{if(m > 1 && m < 5){*message = "level 3.5";
printf("%s\n", *message);
}else{*message = "level 3.6";
printf("%s\n", *message);
}}printf("%s\n", *message);
}printf("%s\n", *message);
}
int glob_fun(){return 100;
}
char* message = "level 0";

int n,m,k;

int main() {
k = 6;

while (k >= 1){printf("%s", "Inserisci n: ");
scanf("%d", &n);
getchar();
printf("%s", "Inserisci m: ");
scanf("%d", &m);
getchar();
printf("%d%s%d%s\n", m, " e ", n, "I valori inseriti sono ");
scoping_fun(n, m, &message);
k = k - 1;
}
printf("%s\n", message);

printf("%d\n", glob_fun());

    return 0;
}

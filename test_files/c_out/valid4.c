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

void moltiplicazione_fun(double x, double y, double *res, int *grande);
char* saluto_fun();
double sommagrande,sommapiccola;

int i = 0;

double x,y,risultato;

int grande,nonusata;

void moltiplicazione_fun(double x, double y, double *res, int *grande){double risultato = x * y,nonusata;
if(x * y >= 100){*grande = 1;
}else{*grande = 0;
}*res = risultato;
}
char* saluto_fun(){return "ciao";
}
int main() {
sommagrande = 0;
sommapiccola = 0;

printf("%s\n", "Questo programma permette di svolgere una serie di moltiplicazioni");

printf("%s\n", "sommando i risultati < 100 in sommagrande e quelli < 100 in sommapiccola");

i = -1;

while (i <= 0){char* saluto = "ciao";
printf("%s", "Quante moltiplicazioni vuoi svolgere? (inserire intero > 0)");
scanf("%d", &i);
getchar();
printf("%s\n", saluto);
}
while (i > 0){x = -1;
y = -1;
while (!(x > 0 && y > 0)){char* saluto = "byebye";
printf("%s%d%s\n", ": inserisci due numeri positivi", i, "Moltiplicazione ");
scanf("%lf", &y);
getchar();
scanf("%lf", &x);
getchar();
printf("%s\n", saluto);
}moltiplicazione_fun(x, y, &risultato, &grande);
printf("%f\n", risultato);
if(grande){printf("%s\n", "il risultato è grande");
sommagrande = sommagrande + risultato;
}else{printf("%s\n", "il risultato è piccolo");
sommapiccola = sommapiccola + risultato;
}i = i - 1;
}
printf("%f%s\n", sommagrande, "\n sommagrande");

printf("%f%s\n", sommapiccola, "sommapiccola ");

    return 0;
}

# 🖥️ Compilatore — Versione Breve

Questo progetto implementa un **compilatore completo** per un piccolo linguaggio imperativo.  
È composto da: **Lexer (JFlex)**, **Parser (CUP)**, **AST**, **Symbol Table & Scope**, **Type Checker** e **Generatore di Codice C**.

---

## 🔤 Simboli del Linguaggio (Spiegati)

### ▶ Parole Chiave
- `program`, `begin`, `end` → struttura del programma  
- `def` → definizione funzione/procedura  
- `ref` → parametro passato per riferimento  
- `return` → restituisce un valore  
- `if then else`, `while do` → controllo di flusso  
- `int`, `bool`, `double`, `string`, `char` → tipi primitivi  
- `true`, `false` → booleani

### ▶ Operatori
- **Aritmetici:** `+ - * /`  
- **Relazionali:** `> < >= <= == <>`  
- **Logici:** `and or not`

### ▶ Simboli Sintattici
- `:=` → assegnazione  
- `=` → inizializzazione nelle dichiarazioni  
- `|` → definizione multipla di variabili  
- `<<` → input  
- `>>`, `!>>` → output (senza / con newline)  
- `,` e `;` → separatori  
- `{ }` e `( )` → blocchi ed espressioni  

---

## 🌳 Struttura Principale del Compilatore

### 1️⃣ **Lexer (JFlex)**
Trasforma il testo in token: identificatori, numeri, stringhe, operatori, keyword, simboli.  
Gestisce escape, caratteri, commenti, tipi, operatori e I/O.

### 2️⃣ **Parser (CUP)**
Costruisce l’**AST** secondo la grammatica.  
Gestisce:
- dichiarazioni  
- funzioni e procedure  
- parametri normali e `ref`  
- espressioni  
- assegnazioni  
- input/output  
- if / else / while  

Precedenze e associatività sono definite correttamente.

### 3️⃣ **AST (Abstract Syntax Tree)**
Comprende nodi per:
- costanti e operatori  
- identificatori e chiamate di funzione  
- dichiarazioni di variabili e parametri  
- funzioni/procedure  
- statement (`if`, `while`, `assign`, `read`, `write`, `return`)  
- struttura del programma  

Il progetto contiene **circa 45 nodi complessivi** tra AST e utility.

### 4️⃣ **Symbol Table & Scope**
Ogni blocco (program, funzione, if, while, body) crea una nuova tabella.  
Vengono memorizzati:
- variabili  
- parametri con tipo e flag `ref`  
- firme delle funzioni  

Gestisce visibilità, shadowing e verifica presenza/dichiarazione.

### 5️⃣ **Type Checker**
Controlla:
- tipo delle espressioni  
- compatibilità operatori-tipologie  
- corretto uso dei parametri `ref`  
- numero e tipo dei parametri nelle FunCall  
- coerenza dei return nelle funzioni/procedure  

Segnala errori statici dettagliati.

### 6️⃣ **Generatore di Codice C**
Crea codice C equivalente:
- funzioni → `nome_fun`  
- parametri ref → puntatori  
- stringhe gestite tramite runtime dedicato  
- printf/scanf generati in base ai tipi  
- blocchi tradotti in `{…}` C standard  

---

## 📌 Struttura del Progetto (breve)
jflexcup/    → lexer + parser
node/        → AST (espressioni, dichiarazioni, statements, tipi)
visitor/     → scope, typecheck, codice C
utils/       → tabelle dei simboli e firme

---

## 🧭 Compilazione (breve)
jflex lexical_specification.flex
cup parser.cup
javac -cp .:java-cup-11b.jar /.java
java Main input.txt
gcc -o output program.c
./output

---

## 📌 Nota
Alcune componenti e aggiornamenti aggiuntivi sono presenti nel *branch di sviluppo*.

---

## 🏁 Conclusione
Il compilatore fornisce tutte le fasi essenziali per un linguaggio imperativo: analisi, costruzione AST, controllo semantico e generazione del codice C. È modulare, estensibile e facilmente mantenibile.

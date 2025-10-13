package visitor;

import node.ASTNode;
import node.Stat;
import node.Type;
import node.Visitor;
import node.body.BodyOp;
import node.defdecl.Decl;
import node.defdecl.DefDecl;
import node.expr.Expr;
import node.expr.LetExprOp;
import node.expr.constant.*;
import node.expr.operation.BinaryOp;
import node.expr.operation.FunCall;
import node.expr.operation.UnaryOp;
import node.pardecl.ParDecl;
import node.pardecl.ParVar;
import node.program.ProgramOp;
import node.stat.*;
import node.vardecl.VarDecl;
import node.vardecl.VarInit;
import visitor.utils.TabellaDeiSimboli;
import visitor.utils.RigaTabellaDeiSimboli;
import visitor.utils.TipoFunzione;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Classe GeneratoreCodiceC - Visitor per la generazione di codice C
 *
 * Questa classe implementa il pattern Visitor per attraversare l'AST (Abstract Syntax Tree)
 * e generare codice C equivalente al programma Toy3 di partenza.
 */
public class GeneratoreCodiceC implements Visitor {
    // Mappa che associa i nomi delle funzioni ai loro parametri di riferimento
    private HashMap<String, ArrayList<Boolean>> firms = new HashMap<>();

    /**
     * Costruttore della classe GeneratoreCodiceC
     */
    public GeneratoreCodiceC() {
        return;
    }

    // --- GESTIONE DELLE COSTANTI ---

    /**
     * Genera codice C per una costante di tipo double
     * @param doubleNode nodo che rappresenta un valore double
     * @return stringa contenente il valore numerico
     */
    @Override
    public String visit(DoubleNode doubleNode) {
        return String.valueOf(doubleNode.getCostant());
    }

    /**
     * Genera codice C per una costante booleana false
     * @param falseNode nodo che rappresenta il valore false
     * @return "0" (rappresentazione C del valore false)
     */
    @Override
    public String visit(FalseNode falseNode) {
        return "0";
    }

    /**
     * Genera codice C per una costante stringa
     * @param stringNode nodo che rappresenta una stringa
     * @return stringa racchiusa tra virgolette
     */
    @Override
    public String visit(StringNode stringNode) {
        return "\"" + stringNode.getConstant() + "\"";
    }

    /**
     * Genera codice C per una costante carattere
     * @param charNode nodo che rappresenta un carattere
     * @return carattere racchiuso tra apici singoli
     */
    @Override
    public String visit(CharNode charNode) {
        return "\'" + charNode.getCostant() + "\'";
    }

    /**
     * Genera codice C per una costante booleana true
     * @param trueNode nodo che rappresenta il valore true
     * @return "1" (rappresentazione C del valore true)
     */
    @Override
    public String visit(TrueNode trueNode) {
        return "1";
    }

    /**
     * Genera codice C per una costante intera
     * @param integerNode nodo che rappresenta un valore intero
     * @return stringa contenente il valore numerico
     */
    @Override
    public String visit(IntegerNode integerNode) {
        return String.valueOf(integerNode.getValue());
    }

    // --- GESTIONE DELLE OPERAZIONI BINARIE E UNARIE ---

    /**
     * Genera codice C per un'operazione binaria (es. +, -, *, /, &&, ||, ==, !=, ecc.)
     * @param binaryOp nodo che rappresenta un'operazione binaria
     * @return stringa contenente l'espressione C corrispondente
     */
    @Override
    public String visit(BinaryOp binaryOp) {
        StringBuilder builder = new StringBuilder();
        Expr leftOperand = binaryOp.getLeft();
        Expr rightOperand = binaryOp.getRight();
        String operator = binaryOp.getOperator();
        builder.append(generateBinaryExpr(operator, leftOperand, rightOperand));
        return builder.toString();
    }

    /**
     * Genera codice C per un'operazione unaria (es. -, not)
     * @param unaryOp nodo che rappresenta un'operazione unaria
     * @return stringa contenente l'espressione C corrispondente
     */
    @Override
    public String visit(UnaryOp unaryOp) {
        StringBuilder builder = new StringBuilder();
        Expr operand = unaryOp.getOperand();
        String operator = unaryOp.getOperator();
        builder.append(generateUnaryExpressionCode(unaryOp));
        return builder.toString();
    }

    // --- GESTIONE DEGLI IDENTIFICATORI E CHIAMATE DI FUNZIONE ---

    /**
     * Genera codice C per un identificatore (nome di variabile)
     * @param identifier nodo che rappresenta un identificatore
     * @return nome della variabile, preceduto da * se è un riferimento
     */
    @Override
    public String visit(Identifier identifier) {
        StringBuilder builder = new StringBuilder();
        if (identifier.isRef()) {
            builder.append("*");  // Dereferenziazione per parametri passati per riferimento
        }
        builder.append(identifier.getName());
        return builder.toString();
    }

    /**
     * Genera codice C per una chiamata di funzione
     * @param funCall nodo che rappresenta una chiamata di funzione
     * @return stringa contenente la chiamata di funzione C con tutti i parametri
     */
    @Override
    public String visit(FunCall funCall) {
        StringBuilder builder = new StringBuilder();
        Identifier id = funCall.getId();
        builder.append(id.accept(this)).append("_fun(");
        ArrayList<Boolean> references = firms.get(id.getName());
        List<Expr> exprs = funCall.getArguments();

        if (exprs != null) {
            // Ordine inverso dei parametri per compatibilità con lo stack
            for (int i = exprs.size() -1; i >= 0; i--) {
                Expr espressione_corrente = exprs.get(i);
                String exprString = (String) espressione_corrente.accept(this);

                // Gestione speciale per i parametri passati per riferimento
                if (references.get(i) == true){
                    builder.append("&");  // Operatore di indirizzamento
                }
                builder.append(exprString);
                if(i != 0){
                    builder.append(", ");
                }
            }
        }
        builder.append(")");
        return builder.toString();
    }

    // --- GESTIONE DEGLI STATEMENT DI I/O ---

    /**
     * Genera codice C per un'operazione di lettura (INPUT)
     * @param readOp nodo che rappresenta un'operazione di lettura
     * @return codice C per leggere variabili usando scanf
     */
    @Override
    public String visit(ReadOp readOp) {
        StringBuilder result = new StringBuilder();
        for (Identifier id : readOp.getList()) {
            Type type = id.getType();
            String varName = (String) id.accept(this);

            // Gestione diversa a seconda del tipo di dato
            if (type == Type.STRING) {
                // Per le stringhe serve allocazione dinamica della memoria
                result.append("buffer = (char*) malloc((1024*5)*sizeof(char));\n");
                result.append("scanf(\"%[^\\n]\", buffer);\n");
                result.append(varName).append(" = (char*) malloc(strlen(buffer) + 1);\n");
                result.append("strcpy(").append(varName).append(", buffer);\n");
                result.append("free(buffer);\n");
            } else if (type == Type.INTEGER || type == Type.BOOLEAN) {
                result.append("scanf(\"%d\", &").append(varName).append(");\n");
            } else if (type == Type.DOUBLE) {
                result.append("scanf(\"%lf\", &").append(varName).append(");\n");
            } else if (type == Type.CHAR) {
                result.append("scanf(\"%c\", &").append(varName).append(");\n");
            }
            result.append("getchar();\n");  // Consuma il carattere di newline
        }
        return result.toString();
    }

    /**
     * Genera codice C per un'operazione di scrittura (OUTPUT)
     * @param writeOp nodo che rappresenta un'operazione di scrittura
     * @return codice C per stampare variabili usando printf
     */
    @Override
    public String visit(WriteOp writeOp) {
        StringBuilder printArgs = new StringBuilder();
        StringBuilder printString = new StringBuilder("printf(\"");
        List<Expr> expressions = writeOp.getExpressions();

        for (int i = 0; i < expressions.size(); i++) {
            Expr expr = expressions.get(i);
            String expression = (String) expr.accept(this);
            Type type = expr.getType();
            String specifier = getStringSpecifier(type);  // %d, %f, %s, %c

            // Sostituisce i caratteri di newline con la sequenza di escape
            if (expression.contains("\n")) {
                expression = expression.replace("\n", "\\n");
            }

            printString.append(specifier);
            printArgs.append(expression);
            if (i < expressions.size() - 1) {
                printArgs.append(", ");
            }
        }

        // Aggiunge newline se richiesto (writeln vs write)
        if (writeOp.isNewLine()) {
            printString.append("\\n");
        }
        printString.append("\", ").append(printArgs).append(");\n");
        return printString.toString();
    }

    // --- GESTIONE DELLE ASSEGNAZIONI ---

    /**
     * Genera codice C per un'operazione di assegnazione
     * @param assignOp nodo che rappresenta un'assegnazione
     * @return codice C per assegnare valori alle variabili
     */
    @Override
    public String visit(AssignOp assignOp) {
        StringBuilder builder = new StringBuilder();
        ArrayList<Identifier> ids = assignOp.getVariables();
        List<Expr> exprs = assignOp.getExpressions();

        // Processa le assegnazioni in ordine inverso
        for (int i = ids.size() - 1; i >= 0; i--) {
            String id = (String) ids.get(i).accept(this);
            String expr = (String) exprs.get(i).accept(this);
            builder.append(id).append(" = ").append(expr).append(";\n");
        }
        return builder.toString();
    }

    // --- GESTIONE DEL CONTROLLO DI FLUSSO ---

    /**
     * Genera codice C per un'istruzione if-then
     * @param ifThen nodo che rappresenta un'istruzione if-then
     * @return codice C per l'istruzione condizionale
     */
    @Override
    public String visit(IfThenNode ifThen) {
        StringBuilder builder = new StringBuilder();
        String condition = (String) ifThen.getEspressione().accept(this);
        String body = (String) ifThen.getBody().accept(this);

        // Rimuove le parentesi graffe extra dal corpo
        body = body.trim();
        if (body.startsWith("{") && body.endsWith("}")) {
            body = body.substring(1, body.length() - 1).trim();
        }
        builder.append("if(").append(condition).append(")").append(body);
        return builder.toString();
    }

    /**
     * Genera codice C per un'istruzione if-then-else
     * @param ifThenElse nodo che rappresenta un'istruzione if-then-else
     * @return codice C per l'istruzione condizionale con ramo else
     */
    @Override
    public String visit(IfThenElse ifThenElse) {
        StringBuilder builder = new StringBuilder();
        String condition = (String) ifThenElse.getEspressione().accept(this);
        String ifBody = (String) ifThenElse.getIfthenStatement().accept(this);
        String elseBody = (String) ifThenElse.getElseStatement().accept(this);
        builder.append("if(").append(condition).append(")").append(ifBody).append("else").append(elseBody);
        return builder.toString();
    }

    /**
     * Genera codice C per un ciclo while
     * @param whileOp nodo che rappresenta un ciclo while
     * @return codice C per il ciclo
     */
    @Override
    public String visit(WhileOp whileOp) {
        String condition = (String) whileOp.getEspr().accept(this);
        String body = (String) whileOp.getBody().accept(this);
        return "while (" + condition + ")" + body ;
    }

    /**
     * Genera codice C per un'istruzione return
     * @param returnOp nodo che rappresenta un'istruzione return
     * @return codice C per il return
     */
    @Override
    public String visit(ReturnStat returnOp) {
        return "return " + returnOp.getExpr().accept(this) + ";\n";
    }

    // --- GESTIONE DELLE DICHIARAZIONI ---

    /**
     * Genera codice C per la dichiarazione di una variabile
     * @param varDecl nodo che rappresenta una dichiarazione di variabile
     * @return codice C per la dichiarazione
     */
    @Override
    public String visit(VarDecl varDecl) {
        StringBuilder builder = new StringBuilder();

        if (varDecl.getCostant() != null) {
            // Dichiarazione con inizializzazione da costante
            builder.append(getCType(Type.getTypeFromExpr(varDecl.getCostant()))).append(" ");
            builder.append(varDecl.getVariables().get(0).accept(this));
            builder.append(" = ");
            builder.append(varDecl.getCostant().accept(this));
        } else {
            // Dichiarazione normale con tipo esplicito
            builder.append(getCType(varDecl.getType())).append(" ");
            int size = varDecl.getVariables().size() - 1;
            boolean isString = false;

            if(varDecl.getType() == Type.STRING)
                isString = true;

            // Genera la lista delle variabili
            for (int i = size; i >= 0; i--) {
                if (isString && i != size) builder.append("*");  // Puntatori per stringhe multiple
                if (i == 0) {
                    builder.append(varDecl.getVariables().get(i).accept(this));
                } else {
                    builder.append(varDecl.getVariables().get(i).accept(this)).append(",");
                }
            }
        }
        builder.append(";\n");
        return builder.toString();
    }

    /**
     * Genera codice C per l'inizializzazione di una variabile
     * @param varInit nodo che rappresenta l'inizializzazione di una variabile
     * @return codice C per l'inizializzazione
     */
    @Override
    public String visit(VarInit varInit) {
        StringBuilder builder = new StringBuilder();
        builder.append(varInit.getId().accept(this));
        if (varInit.getInitValue() != null) {
            builder.append(" = ").append(varInit.getInitValue().accept(this));
        }
        return builder.toString();
    }

    /**
     * Genera codice C per la dichiarazione di un parametro di funzione
     * @param parDecl nodo che rappresenta la dichiarazione di un parametro
     * @return codice C per il parametro
     */
    @Override
    public String visit(ParDecl parDecl) {
        StringBuilder builder = new StringBuilder();
        int size = parDecl.getVariables().size() - 1;
        String ref = "";
        ParVar variable;

        // Genera la lista dei parametri in ordine inverso
        for (int i = size; i >= 0; i--) {
            variable = parDecl.getVariables().get(i);
            if (i == 0) {
                builder.append(getCType(parDecl.getType())).append(" ").append(ref).append(variable.accept(this));
            } else {
                builder.append(getCType(parDecl.getType())).append(" ").append(ref).append(variable.accept(this)).append(",");
            }
        }
        return builder.toString();
    }

    /**
     * Genera codice C per una variabile parametro
     * @param parVar nodo che rappresenta una variabile parametro
     * @return nome della variabile parametro
     */
    @Override
    public String visit(ParVar parVar) {
        StringBuilder builder = new StringBuilder();
        builder.append(parVar.getId().accept(this));
        return builder.toString();
    }

    // --- GESTIONE DEL CORPO DEL PROGRAMMA ---

    /**
     * Genera codice C per il corpo di una funzione o blocco
     * @param body nodo che rappresenta un corpo (blocco di codice)
     * @return codice C racchiuso tra parentesi graffe
     */
    @Override
    public String visit(BodyOp body) {
        StringBuilder builder = new StringBuilder();
        builder.append("{");

        // Genera le dichiarazioni di variabili locali
        for (VarDecl decl : body.getDichiarazioni()) {
            builder.append(decl.accept(this));
        }

        // Genera le istruzioni del corpo
        for (Stat stmt : body.getStatements()) {
            builder.append(stmt.accept(this));
            if(stmt instanceof FunCall){
                builder.append(";").append("\n");  // Aggiunge ; per chiamate di funzione statement
            }
        }
        builder.append("}");
        return builder.toString();
    }

    // --- GESTIONE DELLE ESPRESSIONI 'LET' ---
// Servono per collezionare le funzioni di supporto generate per i 'let'.
    private StringBuilder letFunctions = new StringBuilder();
    private int letFunctionCounter = 0; // Garantisce che ogni funzione 'let' abbia un nome unico.

// ...

    // Questo metodo viene chiamato quando il visitor incontra un nodo LetExprOp nell'AST.
    @Override
    public String visit(LetExprOp op) {
        // 1. Incrementa il contatore per creare un nome di funzione unico (es. __let_expression_1, __let_expression_2, etc.).
        letFunctionCounter++;
        String functionName = "__let_expression_" + letFunctionCounter;

        // 2. Recupera il tipo di ritorno del 'let', che il TypeChecker ha già calcolato e salvato nel nodo.
        Type returnType = op.getType();

        // 3. Inizia a costruire la stringa che definisce la funzione C di supporto.
        //    Es: "int __let_expression_1() {\n"
        letFunctions.append(getCType(returnType)).append(" ").append(functionName).append("() {\n");

        // 4. Genera il codice C per le dichiarazioni di variabili all'interno del 'let'.
        //    Questo crea lo scope locale della funzione.
        for (VarDecl decl : op.getDeclarations()) {
            letFunctions.append(indent((String) decl.accept(this), 1));
        }

        // 5. Genera il codice C per gli statements all'interno del blocco 'given'.
        for (Stat stat : op.getGivenBody()) {
            letFunctions.append(indent((String) stat.accept(this), 1));
        }

        // 6. Genera l'istruzione di ritorno della funzione C. Il valore restituito
        //    è il risultato della valutazione dell'espressione nel blocco 'is'.
        letFunctions.append(indent("return " + op.getIsExpr().accept(this) + ";\n", 1));

        // 7. Chiude la definizione della funzione C.
        letFunctions.append("}\n\n");

        // 8. IMPORTANTE: Questo metodo NON restituisce il corpo della funzione.
        //    Restituisce solo una stringa che rappresenta la CHIAMATA a quella funzione.
        //    Es: "__let_expression_1()"
        //    Questo permette di inserire il risultato del 'let' in qualsiasi punto del codice,
        //    come ad esempio `risultato = __let_expression_1();`.
        return functionName + "()";
    }



    // --- GESTIONE DEL PROGRAMMA PRINCIPALE ---

    /**
     * Genera il codice C completo per il programma principale
     * @param programOp nodo che rappresenta l'intero programma
     * @return codice C completo con header, funzioni e main
     */
    @Override
    public String visit(ProgramOp programOp) {
        StringBuilder builder = new StringBuilder();
        setupFirms(programOp);  // Configura le firme delle funzioni
        builder.append(buildHeader());  // Aggiunge gli include e le funzioni di utility

        // Genera le dichiarazioni forward delle funzioni
        if (programOp.getDeclarations() != null) {
            for (Decl decl : programOp.getDeclarations()) {
                if (decl instanceof DefDecl) {
                    builder.append(generateFunctionDeclaration((DefDecl) decl)).append("\n");
                }
            }
            // Genera le definizioni complete delle funzioni
            for (Decl decl : programOp.getDeclarations()) {
                builder.append(decl.accept(this)).append("\n");
            }
        }

        // Crea un buffer temporaneo per generare il codice del blocco 'main'.
        // Durante la visita degli statements del main, verranno chiamati i metodi `visit`
        // per ogni nodo, incluso `visit(LetExprOp)`, che popolerà il nostro buffer `letFunctions`.
        StringBuilder mainCode = new StringBuilder();
        if (programOp.getVarDeclarations() != null) {
            for (Decl decl : programOp.getVarDeclarations()) {
                mainCode.append(indent((String) decl.accept(this), 1));
            }
        }
        if (programOp.getStatements() != null) {
            for (Stat stmt : programOp.getStatements()) {
                mainCode.append(indent((String) stmt.accept(this), 1));
            }
        }

        // Ora che abbiamo visitato tutto e `letFunctions` è pieno, possiamo assemblare il file finale.
        // 1. Aggiungi le funzioni di supporto generate per i 'let'. Vanno PRIMA del main.
        builder.append(letFunctions.toString());


        // 2. Aggiungi la funzione main.
        builder.append("int main() {\n");

        // 3. Inserisci il codice del blocco main che abbiamo generato nel buffer.
        builder.append(mainCode.toString());

        // 4. Aggiungi il return finale e chiudi il main.
        builder.append("    return 0;\n");
        builder.append("}\n");

        // Restituisci il codice C completo.
        return builder.toString();
    }

    /**
     * Genera codice C per la definizione di una funzione
     * @param defDecl nodo che rappresenta la definizione di una funzione
     * @return codice C per la funzione completa
     */
    @Override
    public String visit(DefDecl defDecl) {
        StringBuilder builder = new StringBuilder();
        String firm = (String) generateSignature(defDecl);
        builder.append(firm);
        builder.append(defDecl.getBody().accept(this));
        return builder.toString();
    }

    // --- METODI DI UTILITÀ ---

    /**
     * Converte un tipo Toy3 nel corrispondente tipo C
     * @param t tipo Toy3
     * @return stringa contenente il tipo C corrispondente
     */
    private String getCType(Type t) {
        if (t == null) return "void";
        switch (t) {
            case STRING: return "char*";
            case CHAR: return "char";
            case DOUBLE: return "double";
            case INTEGER, BOOLEAN: return "int";
            default: return "";
        }
    }

    /**
     * Costruisce l'header del file C con include e funzioni di utility
     * @return stringa contenente l'header completo
     */
    private String buildHeader() {
        StringBuilder header = new StringBuilder();
        header.append("#include <stdio.h>\n");
        header.append("#include <stdlib.h>\n");
        header.append("#include <string.h>\n");
        header.append("#include <math.h>\n");
        header.append("#define BUFFER_SIZE 1024*4\n\n");

        header.append("// Funzioni di supporto\n");
        header.append("char* buffer;\n");

        // Funzione per concatenazione di stringhe
        header.append("char* string_concat(char* s1, char* s2) {\n");
        header.append("    char* ns = malloc(strlen(s1) + strlen(s2) + 1);\n");
        header.append("    strcpy(ns, s1);\n");
        header.append("    strcat(ns, s2);\n");
        header.append("    return ns;\n");
        header.append("}\n\n");

        // Funzioni di conversione da primitivi a stringa
        header.append("char* int2str(int n) {\n");
        header.append("    char buffer[BUFFER_SIZE];\n");
        header.append("    int len = sprintf(buffer, \"%d\", n);\n");
        header.append("    char *ns = malloc(len + 1);\n");
        header.append("    sprintf(ns, \"%d\", n);\n");
        header.append("    return ns;\n");
        header.append("}\n\n");

        header.append("char* char2str(char c) {\n");
        header.append("    char *ns = malloc(2);\n");
        header.append("    sprintf(ns, \"%c\", c);\n");
        header.append("    return ns;\n");
        header.append("}\n\n");

        header.append("char* float2str(float f) {\n");
        header.append("    char buffer[BUFFER_SIZE];\n");
        header.append("    int len = sprintf(buffer, \"%f\", f);\n");
        header.append("    char *ns = malloc(len + 1);\n");
        header.append("    sprintf(ns, \"%f\", f);\n");
        header.append("    return ns;\n");
        header.append("}\n\n");

        header.append("char* bool2str(int b) {\n");
        header.append("    char* ns = NULL;\n");
        header.append("    if(b) {\n");
        header.append("        ns = malloc(5);\n");
        header.append("        strcpy(ns, \"true\");\n");
        header.append("    } else {\n");
        header.append("        ns = malloc(6);\n");
        header.append("        strcpy(ns, \"false\");\n");
        header.append("    }\n");
        header.append("    return ns;\n");
        header.append("}\n\n");

        return header.toString();
    }

    /**
     * Genera la dichiarazione forward di una funzione
     * @param decl definizione della funzione
     * @return stringa contenente la dichiarazione
     */
    private String generateFunctionDeclaration(DefDecl decl) {
        return generateSignature(decl) + ";";
    }

    /**
     * Genera la firma (signature) di una funzione
     * @param decl definizione della funzione
     * @return stringa contenente la firma
     */
    private String generateSignature(DefDecl decl) {
        StringBuilder builder = new StringBuilder();

        // Tipo di ritorno
        builder.append(getCType(decl.getType())).append(" ");

        // Nome funzione con suffisso "_fun"
        builder.append(decl.getId().accept(this)).append("_fun(");

        // Parametri in ordine inverso
        if (decl.getList() != null && !decl.getList().isEmpty()) {
            for (int i = decl.getList().size() - 1; i >= 0; i--) {
                builder.append(decl.getList().get(i).accept(this));
                if (i > 0) {
                    builder.append(", ");
                }
            }
        }
        builder.append(")");
        return builder.toString();
    }

    /**
     * Configura le firme delle funzioni per gestire i parametri per riferimento
     * @param node nodo del programma principale
     */
    private void setupFirms(ASTNode node) {
        ProgramOp programNode = (ProgramOp) node;
        TabellaDeiSimboli table = programNode.getTabellaDeiSimboliProgram();
        ArrayList<RigaTabellaDeiSimboli> riga = table.getRigaLista();
        if (riga != null) {
            for (RigaTabellaDeiSimboli row : riga) {
                if (row.getFirma() instanceof TipoFunzione) {
                    String name = row.getId();
                    ArrayList<Boolean> refs = ((TipoFunzione) row.getFirma()).getReference();
                    firms.put(name, refs);
                }
            }
        }
    }

    /**
     * Genera il codice C per un'espressione binaria
     * @param operator operatore binario
     * @param left operando sinistro
     * @param right operando destro
     * @return stringa contenente l'espressione C
     */
    private String generateBinaryExpr(String operator, Expr left, Expr right) {
        boolean arithOp = operator.equals("PLUS") || operator.equals("MINUS") ||
                operator.equals("TIMES") || operator.equals("DIV");
        boolean relop = operator.equals("LT") || operator.equals("LE") ||
                operator.equals("GT") || operator.equals("GE") ||
                operator.equals("EQ") || operator.equals("NE");
        boolean logicOp = operator.equals("AND") || operator.equals("OR");
        Type leftType = left.getType();
        Type rightType = right.getType();

        // Gestione delle operazioni aritmetiche
        if (arithOp && leftType == Type.INTEGER && rightType == Type.INTEGER) {
            return left.accept(this) + " " + getSymbolFromString(operator) + " " + right.accept(this);
        } else if (arithOp && leftType == Type.DOUBLE && rightType == Type.DOUBLE) {
            return left.accept(this) + " " + getSymbolFromString(operator) + " " + right.accept(this);
        } else if (arithOp && leftType == Type.INTEGER && rightType == Type.DOUBLE) {
            return left.accept(this) + " " + getSymbolFromString(operator) + " " + right.accept(this);
        } else if (arithOp && leftType == Type.DOUBLE && rightType == Type.INTEGER) {
            return left.accept(this) + " " + getSymbolFromString(operator) + " " + right.accept(this);
        }
        // Gestione delle operazioni logiche
        else if (logicOp && leftType == Type.BOOLEAN && rightType == Type.BOOLEAN) {
            return left.accept(this) + " " + getSymbolFromString(operator) + " " + right.accept(this);
        }
        // Gestione delle operazioni relazionali
        else if (relop && leftType == Type.INTEGER && rightType == Type.INTEGER) {
            return left.accept(this) + " " + getSymbolFromString(operator) + " " + right.accept(this);
        } else if (relop && leftType == Type.DOUBLE && rightType == Type.INTEGER) {
            return left.accept(this) + " " + getSymbolFromString(operator) + " " + right.accept(this);
        } else if (relop && leftType == Type.INTEGER && rightType == Type.DOUBLE) {
            return left.accept(this) + " " + getSymbolFromString(operator) + " " + right.accept(this);
        } else if (relop && leftType == Type.DOUBLE && rightType == Type.DOUBLE) {
            return left.accept(this) + " " + getSymbolFromString(operator) + " " + right.accept(this);
        }
        // Gestione della concatenazione di stringhe
        else if (operator.equals("PLUS") && (leftType == Type.STRING || rightType == Type.STRING)) {
            return "string_concat(" + convertValueToString((String) left.accept(this), leftType) + ", " +
                    convertValueToString((String) right.accept(this), rightType) + ")";
        }
        // Gestione del confronto tra stringhe
        else if (operator.equals("EQ") && leftType == Type.STRING && rightType == Type.STRING) {
            return "strcmp(" + right.accept(this) + ", " + left.accept(this) + ") == 0";
        } else if (operator.equals("NE") && leftType == Type.STRING && rightType == Type.STRING) {
            return "strcmp(" + right.accept(this) + ", " + left.accept(this) + ") != 0";
        }
        else {
            throw new RuntimeException("Operazione non supportata: " + operator + " con tipi " + leftType + " e " + rightType);
        }
    }

    /**
     * Genera il codice C per un'espressione unaria
     * @param node nodo che rappresenta un'operazione unaria
     * @return stringa contenente l'espressione C
     */
    private String generateUnaryExpressionCode(UnaryOp node) {
        Expr expr = node.getOperand();
        String operator = node.getOperator();
        if (operator.equals("MINUS")) {
            return "-" + expr.accept(this);
        } else if (operator.equals("NOT")) {
            return "!(" + expr.accept(this) + ")";
        } else {
            throw new RuntimeException("Operatore unario non riconosciuto: " + operator);
        }
    }

    /**
     * Converte un valore nel suo equivalente stringa utilizzando le funzioni di utility
     * @param expression espressione da convertire
     * @param type tipo dell'espressione
     * @return chiamata alla funzione di conversione appropriata
     */
    private String convertValueToString(String expression, Type type) {
        switch (type) {
            case INTEGER: return "int2str(" + expression + ")";
            case CHAR: return "char2str(" + expression + ")";
            case DOUBLE: return "float2str(" + expression + ")";
            case BOOLEAN: return "bool2str(" + expression + ")";
            case STRING: return expression;
            default: return "";
        }
    }

    /**
     * Converte un operatore Toy3 nel corrispondente simbolo C
     * @param op operatore in formato stringa
     * @return simbolo C corrispondente
     */
    private String getSymbolFromString(String op) {
        return switch (op) {
            case "MINUS" -> "-";
            case "TIMES" -> "*";
            case "PLUS" -> "+";
            case "DIV" -> "/";
            case "AND" -> "&&";
            case "OR" -> "||";
            case "NOT" -> "!";
            case "LT" -> "<";
            case "LE" -> "<=";
            case "EQ" -> "==";
            case "GE" -> ">=";
            case "GT" -> ">";
            case "NE" -> "!=";
            default -> throw new IllegalArgumentException("Operatore sconosciuto: " + op);
        };
    }

    /**
     * Restituisce il specificatore di formato printf per un dato tipo
     * @param type tipo di dato
     * @return specificatore di formato (%d, %f, %s, %c)
     */
    private String getStringSpecifier(Type type) {
        switch (type) {
            case INTEGER: return "%d";
            case DOUBLE: return "%f";
            case BOOLEAN: return "%d";
            case STRING: return "%s";
            case CHAR: return "%c";
            default: return "";
        }
    }

    /**
     * Verifica se un parametro di funzione è passato per riferimento
     * @param functionName nome della funzione
     * @param paramIndex indice del parametro
     * @return true se il parametro è per riferimento, false altrimenti
     */
    private boolean isReferenceParameter(String functionName, int paramIndex) {
        if (firms.containsKey(functionName)) {
            ArrayList<Boolean> refs = firms.get(functionName);
            if (paramIndex < refs.size()) {
                return refs.get(paramIndex);
            }
        }
        return false;
    }

    /**
     * Aggiunge indentazione al codice per migliorare la leggibilità
     * @param code codice da indentare
     * @param level livello di indentazione
     * @return codice indentato
     */
    private String indent(String code, int level) {
        String indent = "    ".repeat(level);
        return indent + code.replace("\n", "\n" + indent);
    }
}
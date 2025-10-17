package visitor;

import java_cup.runtime.SyntaxTreeDFS;
import node.ASTNode;
import node.Stat;
import node.Type;
import node.Visitor;
import node.body.BodyOp;
import node.defdecl.Decl;
import node.defdecl.DefDecl;
import node.expr.Expr;
import node.expr.constant.*;
import node.expr.operation.BinaryOp;
import node.expr.operation.FunCall;
import node.expr.operation.UnaryOp;
import node.pardecl.ParDecl;
import node.pardecl.ParVar;
import node.program.ProgramOp;
import node.stat.*;
import node.vardecl.ArrayType;
import node.vardecl.VarDecl;
import node.vardecl.VarInit;
import org.w3c.dom.Node;
import visitor.utils.RigaTabellaDeiSimboli;
import visitor.utils.TabellaDeiSimboli;
import visitor.utils.TipoFunzione;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Classe TypeCheck - Visitor per il controllo dei tipi (Type Checking)
 *
 * Questa classe implementa il pattern Visitor per attraversare l'AST (Abstract Syntax Tree)
 * e verificare la correttezza dei tipi secondo le regole semantiche del linguaggio Toy3.
 *
 * Esegue:
 * - Controllo compatibilità tipi nelle operazioni
 * - Verifica corrispondenza parametri formali/attuali nelle chiamate di funzione
 * - Controllo presenza istruzioni return nelle funzioni
 * - Inferenza e assegnazione tipi a ogni nodo dell'AST
 * - Controlli semantici specifici (assegnazioni, espressioni condizionali, ecc.)
 *
 * Utilizza le tabelle dei simboli costruite da ScopeVisitor per la risoluzione dei tipi.
 */
public class TypeCheck implements Visitor {
    // Stack che mantiene le tabelle dei simboli per gli scope attivi
    private Stack<TabellaDeiSimboli> typeenv = new Stack<TabellaDeiSimboli>();
    // Tabella dei simboli corrente per ottimizzazione accesso
    private TabellaDeiSimboli current_table;

    /**
     * Visita il nodo principale del programma e controlla la correttezza dei tipi
     *
     * Verifica:
     * - Correttezza delle dichiarazioni globali (funzioni/variabili)
     * - Correttezza delle dichiarazioni locali nel blocco BEGIN-END
     * - Correttezza di tutti gli statement del programma principale
     *
     * @param programOp nodo che rappresenta l'intero programma
     * @return null (assegna Type.NOTYPE al programma)
     */
    @Override
    public Object visit(ProgramOp programOp) {
        // Inserisce la tabella dei simboli globale nello stack
        typeenv.add(programOp.getTabellaDeiSimboliProgram());

        // Controlla tutte le dichiarazioni globali (funzioni e variabili)
        if(programOp.getDeclarations() != null) {
            for (Decl decl : programOp.getDeclarations()) {
                ASTNode node = (ASTNode) decl;
                node.accept(this);
            }
        }

        // Inserisce la tabella dei simboli del blocco BEGIN-END
        typeenv.add(programOp.getTabellaBegEnd());

        // Controlla tutte le dichiarazioni locali nel blocco BEGIN-END
        if(programOp.getVarDeclarations() != null) {
            for (Decl decl : programOp.getVarDeclarations()) {
                ASTNode node = (ASTNode) decl;
                node.accept(this);
            }
        }

        // Controlla tutti gli statement del programma principale
        if(programOp.getStatements() != null) {
            for (Stat stat : programOp.getStatements()) {
                ASTNode node = (ASTNode) stat;
                Type type = (Type) node.accept(this);
                if (type == null) {
                    System.err.println("Statement nullo: " + stat.getClass().getSimpleName());
                    throw new RuntimeException("Statements not valid.");
                }
            }
        }

        // Rimuove le tabelle dallo stack (pulizia)
        typeenv.pop(); // BeginEndTable pop
        typeenv.pop(); // Program pop
        programOp.setType(Type.NOTYPE);

        return null;
    }

    /**
     * Visita una definizione di funzione/procedura e controlla la correttezza dei tipi
     *
     * Verifica:
     * - Presenza di return statement nelle funzioni (non nelle procedure)
     * - Compatibilità tipo return con tipo dichiarato della funzione
     * - Assenza di return statement nelle procedure
     * - Correttezza di tutte le dichiarazioni e statement nel corpo
     *
     * @param defDecl nodo che rappresenta una definizione di funzione/procedura
     * @return null (non restituisce tipi)
     */
    @Override
    public Object visit(DefDecl defDecl) {
        Type type = defDecl.getType();
        BodyOp bodyOp;
        current_table = defDecl.getBody().getTabellaDeiSimboli();
        typeenv.add(defDecl.getTabellaDeiSimboli());
        Node node;

        // Controlla tutti i parametri della funzione
        if (defDecl.getList() != null) {
            for (ParDecl decl : defDecl.getList()) {
                decl.accept(this);
            }
        }

        // Controlli specifici per procedure vs funzioni
        if (defDecl.getType() == null) {
            // PROCEDURA: non deve avere statement return
            for (Stat statement : defDecl.getBody().getStatements()) {
                if (statement instanceof ReturnStat) {
                    throw new RuntimeException("A proc cannot have a return statement!");
                }
            }
        } else {
            // FUNZIONE: deve avere almeno un return statement con tipo corretto
            boolean error = true;
            for (Stat statement : defDecl.getBody().getStatements()) {
                if (statement instanceof ReturnStat) {
                    error = false;
                    Type tipoTemporaneo = null;
                    ReturnStat returnStatement = (ReturnStat) statement;
                    if (returnStatement.getType() == null) {
                        tipoTemporaneo = (Type) returnStatement.getExpr().accept(this);
                    }
                    // Verifica compatibilità tipo return con tipo dichiarato
                    if (tipoTemporaneo != defDecl.getType()) {
                        throw new RuntimeException("The return type for " + defDecl.getId().getName() + " is incorrect");
                    }
                }
            }
            if (error) {
                throw new RuntimeException("Missing return statement" + " " + defDecl.getId().getName());
            }
        }

        bodyOp = defDecl.getBody();

        // Controlla tutte le dichiarazioni locali nel corpo della funzione
        if (bodyOp.getDichiarazioni() != null) {
            for (VarDecl decls : bodyOp.getDichiarazioni()) {
                decls.accept(this);
            }
        }

        // Controlla tutti gli statement nel corpo della funzione
        if (bodyOp.getStatements() != null) {
            for (Stat statement : bodyOp.getStatements()) {
                Type typeToCheck = (Type) statement.accept(this);
                if (typeToCheck == null) {
                    throw new RuntimeException("The current statement " + statement + " is wrong.");
                }
            }
        }

        bodyOp.setType(Type.NOTYPE);
        typeenv.pop(); // Rimuove lo scope della funzione
        return null;
    }

    /**
     * Visita una dichiarazione di variabile e controlla la compatibilità dei tipi
     *
     * Verifica che i valori di inizializzazione siano compatibili con:
     * - Il tipo dichiarato esplicitamente, oppure
     * - Il tipo inferito dalla costante
     *
     * @param varDecl nodo che rappresenta una dichiarazione di variabile
     * @return null (non restituisce tipi)
     */
    @Override
    public Object visit(VarDecl varDecl) {
        ArrayList<VarInit> variabiliDichiarate = (ArrayList<VarInit>) varDecl.getVariables();

        if (variabiliDichiarate != null) {
            for (VarInit var : variabiliDichiarate) {
                if (var.getInitValue() != null) {
                    Type tipoVar = (Type) var.accept(this);

                    if (varDecl.getCostant() != null) {
                        // Dichiarazione con tipo esplicito: controlla compatibilità
                        if (tipoVar != varDecl.getType()) {
                            throw new RuntimeException("The variable " + var.getId().getName() +
                                    " initialized with: " + var.getInitValue() +
                                    " does not match the declaration type: " + varDecl.getType());
                        }
                    } else {
                        // Dichiarazione con inferenza: controlla con tipo inferito
                        Type inferred = Type.getTypeFromExpr(var.getInitValue());
                        if (tipoVar != inferred) {
                            throw new RuntimeException("The variable " + var.getId().getName() +
                                    " initialized with: " + var.getInitValue() +
                                    " does not match the inferred type: " + inferred);
                        }
                    }
                }
            }
        }

        // Controlla la costante se presente (per inferenza di tipo)
        Expr constant = varDecl.getCostant();
        if (constant != null) {
            constant.accept(this);
        }

        return null;
    }

    /**
     * Visita l'inizializzazione di una variabile
     * Determina e assegna il tipo in base al valore di inizializzazione
     *
     * @param varInit nodo che rappresenta l'inizializzazione di una variabile
     * @return null (assegna il tipo al nodo)
     */
    @Override
    public Object visit(VarInit varInit) {
        if(varInit.getInitValue() != null) {
            varInit.getInitValue().accept(this);
            varInit.setReturnType(varInit.getInitValue().getType());
        } else {
            varInit.setReturnType(null);
        }
        return null;
    }

    /**
     * Visita un'operazione binaria e controlla la compatibilità dei tipi
     *
     * Verifica che gli operandi siano compatibili con l'operatore e
     * determina il tipo risultante dell'operazione
     *
     * @param binaryOp nodo che rappresenta un'operazione binaria
     * @return tipo risultante dell'operazione
     */
    @Override
    public Object visit(BinaryOp binaryOp) {
        Expr leftOperand = binaryOp.getLeft();
        Expr rightOperand = binaryOp.getRight();

        // Prima controlla i tipi degli operandi
        leftOperand.accept(this);
        rightOperand.accept(this);

        // Poi determina il tipo dell'operazione
        binaryOp.setType(this.OpTypeChecker(binaryOp));
        return binaryOp.getType();
    }

    /**
     * Visita un'operazione unaria e controlla la compatibilità del tipo
     *
     * Verifica che l'operando sia compatibile con l'operatore unario
     *
     * @param unaryOp nodo che rappresenta un'operazione unaria
     * @return tipo risultante dell'operazione
     */
    @Override
    public Object visit(UnaryOp unaryOp) {
        Expr operand = unaryOp.getOperand();
        operand.accept(this);
        unaryOp.setType(this.unaryChecker(unaryOp));
        return unaryOp.getType();
    }

    /**
     * Visita una chiamata di funzione e controlla la correttezza dei parametri
     *
     * Verifica:
     * - Esistenza della funzione nelle tabelle dei simboli
     * - Corrispondenza numero parametri formali/attuali
     * - Compatibilità tipi parametri formali/attuali
     * - Correttezza parametri per riferimento (devono essere identificatori)
     *
     * @param funCall nodo che rappresenta una chiamata di funzione
     * @return tipo restituito dalla funzione (o NOTYPE per procedure)
     */
    @Override
    public Object visit(FunCall funCall) {
        Stack<TabellaDeiSimboli> cloned = (Stack<TabellaDeiSimboli>) typeenv.clone();
        TipoFunzione type = lookupFunction(funCall.getId(), cloned);
        ArrayList<Boolean> reference = type.getReference();

        // Verifica esistenza della funzione
        if (type == null) {
            throw new RuntimeException("The function or procedure " + funCall.getId().getName() + " has been not declared");
        }

        // Verifica parametri se la funzione ne ha
        if (type.getInputType() != null) {
            int numberOfFormalParameters = type.getInputType().size();
            int numberOfActualParameters = funCall.getArguments().size();

            // Verifica corrispondenza numero parametri
            if (numberOfActualParameters != numberOfFormalParameters) {
                throw new RuntimeException("The function or procedure " + funCall.getId().getName() + " the actual parameters does not match to formal parameters.");
            }

            // Verifica compatibilità tipi dei parametri
            for (int i = 0; i < numberOfActualParameters; i++) {
                Type actualParameters = (Type) funCall.getArguments().get(i).accept(this);
                Type formalParameters = type.getInputType().get(i);

                if (actualParameters != formalParameters) {
                    throw new RuntimeException("The parameters " + funCall.getArguments().get(i) + "( position " + i + "; type " + funCall.getArguments().get(i).getType() + ") has a different number actual type from the formal one");
                }
            }

            // Verifica parametri per riferimento
            if (reference != null && !reference.isEmpty()) {
                for (int i = 0; i < numberOfActualParameters; i++) {
                    boolean hasRef = reference.get(i);
                    Expr expr = funCall.getArguments().get(i);

                    // I parametri per riferimento devono essere identificatori (variabili)
                    if (hasRef && !(expr instanceof Identifier)) {
                        throw new RuntimeException("The referenced parameters " + funCall.getArguments().get(i) + "( position " + i + "; type " + funCall.getArguments().get(i).getType() + ") is not available");
                    }
                }
            }
        }

        // Assegna il tipo di ritorno alla chiamata
        if (type.getOutputType() != null) {
            funCall.setType(type.getType());
        } else {
            funCall.setType(Type.NOTYPE); // Procedure
        }

        return funCall.getType();
    }

    /**
     * Visita un identificatore e determina il suo tipo
     *
     * Cerca l'identificatore nelle tabelle dei simboli e ne determina il tipo
     *
     * @param identifier nodo che rappresenta un identificatore
     * @return tipo dell'identificatore
     */
    @Override
    public Object visit(Identifier identifier) {
        Stack<TabellaDeiSimboli> clona = (Stack<TabellaDeiSimboli>) typeenv.clone();
        System.out.println(clona);
        Type type = lookupVariable(identifier, clona);
        if (type == null) {
            throw new RuntimeException("Variable" + identifier + " is not declared.");
        }
        identifier.setType(type);
        return identifier.getType();
    }

    // --- VISITE PER LE COSTANTI ---
    // Ogni costante assegna il proprio tipo e lo restituisce

    /**
     * Visita una costante carattere
     * @return Type.CHAR
     */
    @Override
    public Object visit(CharNode charNode) {
        charNode.setType(Type.CHAR);
        return charNode.getType();
    }

    /**
     * Visita una costante double
     * @return Type.DOUBLE
     */
    @Override
    public Object visit(DoubleNode doubleNode) {
        doubleNode.setType(Type.DOUBLE);
        return doubleNode.getType();
    }

    /**
     * Visita una costante intera
     * @return Type.INTEGER
     */
    @Override
    public Object visit(IntegerNode integerNode) {
        integerNode.setType(Type.INTEGER);
        return integerNode.getType();
    }

    /**
     * Visita una costante stringa
     * @return Type.STRING
     */
    @Override
    public Object visit(StringNode stringNode) {
        stringNode.setType(Type.STRING);
        return stringNode.getType();
    }

    /**
     * Visita una costante true
     * @return Type.BOOLEAN
     */
    @Override
    public Object visit(TrueNode trueNode) {
        trueNode.setType(Type.BOOLEAN);
        return trueNode.getType();
    }

    /**
     * Visita una costante false
     * @return Type.BOOLEAN
     */
    @Override
    public Object visit(FalseNode falseNode) {
        falseNode.setType(Type.BOOLEAN);
        return falseNode.getType();
    }

    // --- VISITE PER GLI STATEMENT ---

    /**
     * Visita un'operazione di assegnazione e controlla la compatibilità dei tipi
     *
     * Verifica che ogni variabile sia compatibile con l'espressione assegnata.
     * Permette la conversione implicita da integer a double.
     *
     * @param assignOp nodo che rappresenta un'assegnazione
     * @return Type.NOTYPE
     */
    @Override
    public Object visit(AssignOp assignOp) {
        ArrayList<Identifier> variabiliAssegnate = assignOp.getVariables();

        // visita LHS
        for (Identifier id: variabiliAssegnate) {
            id.accept(this);
        }

        ArrayList<Expr> expressions = (ArrayList<Expr>) assignOp.getExpressions();

        // visita RHS
        for (Expr expr: expressions) {
            expr.accept(this);
        }

        // compatibilità tipi
        for (int i = 0; i < variabiliAssegnate.size(); i++) {
            Identifier lhsId = variabiliAssegnate.get(i);
            Type lhsType = lhsId.getType();            // oggi: ARRAY per 'a'
            Type rhsType = expressions.get(i).getType();

            // NOVITÀ: se è un accesso indicizzato (a[...]),
            // il tipo dell'l-value è il tipo ELEMENTO dell'array.
            if (lhsId.hasIndex()) {
                // opzionale: verifica che la variabile sia davvero un array
                if (lhsType != Type.ARRAY) {
                    throw new RuntimeException(lhsId.getName() + " non è un array.");
                }
                // Se il tuo linguaggio supporta solo array di int:
                lhsType = Type.INTEGER;

                // (facoltativo) bound-check: se hai la dimensione salva, verifica lhsId.getIndex() < dimensione
                // altrimenti lo salti: l’indice è già costante per sintassi.
            }

            // implicit int -> double
            if (lhsType == Type.DOUBLE && rhsType == Type.INTEGER) {
                // ok
            } else if (lhsType != rhsType) {
                throw new RuntimeException(
                        "The variable " + lhsId.getName() +
                                " is not compatible with the expression " + expressions.get(i) + ", are not the same."
                );
            }
        }

        assignOp.setType(Type.NOTYPE);
        return assignOp.getType();
    }


    /**
     * Visita un'operazione di scrittura
     * Controlla i tipi di tutte le espressioni da stampare
     *
     * @param writeOp nodo che rappresenta un'operazione di scrittura
     * @return Type.NOTYPE
     */
    @Override
    public Object visit(WriteOp writeOp) {
        TabellaDeiSimboli table = typeenv.peek();
        writeOp.setTabella(table);
        if (writeOp.getExpressions() != null) {
            for (Expr expressions : writeOp.getExpressions()) {
                expressions.accept(this);
            }
        }
        writeOp.setType(Type.NOTYPE);
        return writeOp.getType();
    }

    /**
     * Visita un'istruzione if-then e controlla la correttezza dei tipi
     *
     * Verifica:
     * - La condizione deve essere di tipo boolean
     * - Il corpo deve avere tipo NOTYPE
     *
     * @param ifThen nodo che rappresenta un'istruzione if-then
     * @return Type.NOTYPE
     */
    @Override
    public Object visit(IfThenNode ifThen) {
        typeenv.add(ifThen.getTabellaDeiSimboli());

        // Verifica che la condizione sia booleana
        Expr expr = ifThen.getEspressione();
        Type tipoExpr = (Type) expr.accept(this);
        if (tipoExpr != Type.BOOLEAN) {
            throw new RuntimeException("The expression in the if statement is not boolean, but is"+tipoExpr);
        }

        // Verifica che il corpo sia corretto
        BodyOp body = ifThen.getBody();
        Type bodyType = (Type) body.accept(this);
        if (bodyType != Type.NOTYPE) {
            throw new RuntimeException("The expression if then is not boolean, but is + " + bodyType);
        }

        typeenv.pop();
        ifThen.setType(Type.NOTYPE);
        return ifThen.getType();
    }

    /**
     * Visita un'istruzione if-then-else e controlla la correttezza dei tipi
     *
     * Verifica:
     * - La condizione deve essere di tipo boolean
     * - Entrambi i corpi (then e else) devono avere tipo NOTYPE
     *
     * @param ifThenElse nodo che rappresenta un'istruzione if-then-else
     * @return Type.NOTYPE
     */
    @Override
    public Object visit(IfThenElse ifThenElse) {
        typeenv.add(ifThenElse.getTabellaDeiSimboli());

        // Verifica che la condizione sia booleana
        Expr expr = ifThenElse.getEspressione();
        Type tipoExpr = (Type) expr.accept(this);
        if (tipoExpr != Type.BOOLEAN) {
            throw new RuntimeException("The expression in the if then else statement is not boolean, but is"+tipoExpr);
        }

        // Verifica che il corpo then sia corretto
        BodyOp bodyif = ifThenElse.getIfthenStatement();
        Type bodyTypeIf = (Type) bodyif.accept(this);
        if (bodyTypeIf != Type.NOTYPE) {
            throw new RuntimeException("The expression if then is not boolean, but is + " + bodyTypeIf);
        }

        // Verifica che il corpo else sia corretto
        BodyOp bodyElse = ifThenElse.getElseStatement();
        Type bodyTypeElse = (Type) bodyElse.accept(this);
        if (bodyTypeElse != Type.NOTYPE) {
            throw new RuntimeException("The expression if then is not boolean, but is + " + bodyTypeElse);
        }

        typeenv.pop();
        ifThenElse.setType(Type.NOTYPE);
        return ifThenElse.getType();
    }

    /**
     * Visita un'operazione di lettura
     * Controlla i tipi di tutte le variabili da leggere
     *
     * @param readOp nodo che rappresenta un'operazione di lettura
     * @return Type.NOTYPE
     */
    @Override
    public Object visit(ReadOp readOp) {
        if (readOp.getList() != null) {
            for (Identifier variables : readOp.getList()) {
                variables.accept(this);
            }
        }
        readOp.setType(Type.NOTYPE);
        return readOp.getType();
    }

    /**
     * Visita un ciclo while e controlla la correttezza dei tipi
     *
     * Verifica:
     * - La condizione deve essere di tipo boolean
     * - Il corpo deve avere tipo NOTYPE
     *
     * @param whileOp nodo che rappresenta un ciclo while
     * @return Type.NOTYPE
     */
    @Override
    public Object visit(WhileOp whileOp) {
        typeenv.add(whileOp.getTabellaDeiSimboli());

        // Verifica che la condizione sia booleana
        Expr expr = whileOp.getEspr();
        Type tipoExpr = (Type) expr.accept(this);
        if(tipoExpr != Type.BOOLEAN){
            throw new RuntimeException("The expression in the while statement is not boolean.");
        }

        // Verifica che il corpo sia corretto
        BodyOp body = whileOp.getBody();
        body.accept(this);
        if(body.getType() != Type.NOTYPE){
            throw new RuntimeException("The body is not NOTYPE.");
        }

        whileOp.setType(Type.NOTYPE);
        typeenv.pop();
        return whileOp.getType();
    }

    /**
     * Visita un'istruzione return
     * Controlla il tipo dell'espressione di ritorno
     *
     * @param returnOp nodo che rappresenta un'istruzione return
     * @return tipo dell'espressione di ritorno
     */
    @Override
    public Object visit(ReturnStat returnOp) {
        Expr result = returnOp.getExpr();
        Type exprType = (Type) result.accept(this);
        returnOp.setType(Type.NOTYPE);
        return exprType; // Restituisce il tipo dell'espressione, non NOTYPE
    }

    /**
     * Visita la dichiarazione di un parametro
     * Controlla tutte le variabili parametro
     *
     * @param parDecl nodo che rappresenta la dichiarazione di un parametro
     * @return null
     */
    @Override
    public Object visit(ParDecl parDecl) {
        if(parDecl.getVariables() != null) {
            for (ParVar var: parDecl.getVariables()) {
                var.accept(this);
            }
        }
        return null;
    }

    /**
     * Visita una variabile parametro
     * Assegna il tipo dell'identificatore
     *
     * @param parVar nodo che rappresenta una variabile parametro
     * @return tipo dell'identificatore
     */
    @Override
    public Object visit(ParVar parVar) {
        parVar.setType(parVar.getId().getType());
        return parVar.getId().getType();
    }

    /**
     * Visita il corpo di un blocco
     * Controlla tutte le dichiarazioni e statement nel corpo
     *
     * @param bodyOp nodo che rappresenta un corpo/blocco
     * @return Type.NOTYPE
     */
    @Override
    public Object visit(BodyOp bodyOp) {
        typeenv.add(bodyOp.getTabellaDeiSimboli());

        // Controlla tutte le dichiarazioni nel corpo
        if (bodyOp.getDichiarazioni() != null){
            for(VarDecl bodyNode : bodyOp.getDichiarazioni()) {
                bodyNode.accept(this);
            }
        }

        // Controlla tutti gli statement nel corpo
        if (bodyOp.getStatements() != null){
            for(Stat stats : bodyOp.getStatements()) {
                Type type = (Type) stats.accept(this);
                if(type == null)
                    throw new RuntimeException("Statements not valid.");
            }
        }

        bodyOp.setType(Type.NOTYPE);
        typeenv.pop();
        return bodyOp.getType();
    }

    // --- METODI DI UTILITÀ PER LA RICERCA NELLE TABELLE DEI SIMBOLI ---

    /**
     * Clona lo stack delle tabelle dei simboli per ricerche non distruttive
     *
     * @param environment stack originale da clonare
     * @return nuovo stack clonato
     */
    public Stack<TabellaDeiSimboli> clonaTypeEnvironment(Stack<TabellaDeiSimboli> environment) {
        Stack<TabellaDeiSimboli> clone = new Stack<>();
        for (TabellaDeiSimboli current: environment) {
            clone.push(current);
        }
        return clone;
    }

    @Override
    public Object visit(ArrayAccess arrayAccess) {

        Identifier id = arrayAccess.getId();
        id.accept(this);

        // Recupera il tipo della variabile
        Type idType = id.getType();

        //  Controlla che sia un array
        if (idType != Type.ARRAY) {
            throw new RuntimeException("Errore: " + id.getName() + " non è un array.");
        }

        // Analizza l'espressione dell'indice
        Expr indexExpr = arrayAccess.getIndexExpr();
        indexExpr.accept(this);

        // L'indice deve essere una costante intera
        if (!(indexExpr instanceof IntegerNode)) {
            throw new RuntimeException("Errore: l'indice deve essere una costante intera.");
        }

        int indexValue = (int) ((IntegerNode) indexExpr).getValue();

        //  Recupera informazioni di tipo array dalla tabella dei simboli
        // Se hai aggiunto una struttura "ArrayType" legata alla variabile,
        // puoi recuperarla dalla symbol table

        // Prendiamo la tabella corrente
        TabellaDeiSimboli tabella = typeenv.peek();
        RigaTabellaDeiSimboli riga = tabella.getRiga(id, "variable");

        if (riga == null) {
            throw new RuntimeException("Errore: variabile " + id.getName() + " non dichiarata.");
        }

        if (!(riga.getFirma() instanceof visitor.utils.FirmaVariabile firma)) {
            throw new RuntimeException("Errore interno: la firma della variabile non è corretta.");
        }

        // Se la firma rappresenta un array, deve contenere il maxIndex
        // (puoi averlo salvato in ArrayType o in un campo extra)
        // Esempio se usi ArrayType:
        if (firma.getType() == Type.ARRAY && riga.getFirma() instanceof node.vardecl.ArrayType arrInfo) {
            if (indexValue >= arrInfo.getDimension()) {
                throw new RuntimeException("Errore: indice " + indexValue +
                        " fuori limite massimo (" + arrInfo.getDimension() + ") per array " + id.getName());
            }
            // L'accesso a un elemento ha il tipo base dell'array
            arrayAccess.setType(arrInfo.getType());
            return arrInfo.getType();
        }

        // Se non hai un oggetto ArrayType ma solo Type.ARRAY
        // puoi impostare genericamente il tipo base dell'array come INTEGER (o altro tipo noto)
        arrayAccess.setType(Type.INTEGER); // o il tipo base noto
        return Type.INTEGER;
    }


    /**
     * Cerca una variabile nelle tabelle dei simboli e ne restituisce il tipo
     *
     * @param inode identificatore da cercare
     * @param typeEnvironment stack delle tabelle dei simboli
     * @return tipo della variabile, null se non trovata
     */
    public Type lookupVariable (Identifier inode, Stack<TabellaDeiSimboli> typeEnvironment) {
        Stack<TabellaDeiSimboli> clonedTypeEnvironment = clonaTypeEnvironment(typeEnvironment);
        if (typeEnvironment != null) {
            for (TabellaDeiSimboli tabellaDeiSimboli : clonedTypeEnvironment) {
                if (tabellaDeiSimboli.contains(inode, "variable")) {
                    return tabellaDeiSimboli.getRiga(inode, "variable").getFirma().getType();
                }
            }
        }
        return null;
    }

    /**
     * Cerca una funzione/procedura nelle tabelle dei simboli e ne restituisce la firma
     *
     * @param node identificatore della funzione da cercare
     * @param typeEnvironment stack delle tabelle dei simboli
     * @return firma della funzione (TipoFunzione), null se non trovata
     */
    public TipoFunzione lookupFunction(Identifier node, Stack<TabellaDeiSimboli> typeEnvironment) {
        Stack<TabellaDeiSimboli> clonedTypeEnvironment = clonaTypeEnvironment(typeEnvironment);
        for (TabellaDeiSimboli tab : clonedTypeEnvironment) {
            if (tab.contains(node, "function")) {
                return (TipoFunzione) tab.getRiga(node, "function").getFirma();
            } else if (tab.contains(node, "procedure")) {
                return (TipoFunzione) tab.getRiga(node, "procedure").getFirma();
            }
        }
        return null;
    }

    // --- METODI DI CONTROLLO TIPI PER OPERAZIONI ---

    /**
     * Controlla la correttezza dei tipi per un'operazione unaria
     *
     * Regole supportate:
     * - MINUS: applicabile a INTEGER e DOUBLE, restituisce stesso tipo
     * - NOT: applicabile solo a BOOLEAN, restituisce BOOLEAN
     *
     * @param node nodo che rappresenta l'operazione unaria
     * @return tipo risultante dell'operazione, null se non valida
     */
    public Type unaryChecker(UnaryOp node) {
        Expr expression = node.getOperand();
        String operator = node.getOperator();
        boolean minusCheck = operator.equals("MINUS");
        boolean notCheck = operator.equals("NOT");

        // Operatore MINUS: supporta INTEGER e DOUBLE
        if (minusCheck && expression.getType() == Type.INTEGER) {
            return Type.INTEGER;
        }
        if (minusCheck && expression.getType() == Type.DOUBLE) {
            return Type.DOUBLE;
        }

        // Operatore NOT: supporta solo BOOLEAN
        if (notCheck && expression.getType() == Type.BOOLEAN) {
            return Type.BOOLEAN;
        }

        return null; // Operazione non valida
    }

    /**
     * Controlla la correttezza dei tipi per un'operazione binaria
     *
     * Implementa le regole di type checking per:
     * - Operazioni aritmetiche: +, -, *, / (con promozione int->double)
     * - Operazioni relazionali: <, <=, >, >=, ==, != (restituiscono boolean)
     * - Operazioni logiche: AND, OR (solo tra boolean)
     * - Concatenazione stringhe: + tra stringhe o con altri tipi
     * - Confronto stringhe: ==, != tra stringhe
     *
     * @param operation nodo che rappresenta l'operazione binaria
     * @return tipo risultante dell'operazione
     * @throws RuntimeException se l'operazione non è supportata
     */
    public Type OpTypeChecker(BinaryOp operation) {
        Expr leftOperand = operation.getLeft();
        Expr rightOperand = operation.getRight();
        String operator = operation.getOperator();

        // Classificazione degli operatori
        boolean arithOpCheck = operator.equals("PLUS") || operator.equals("MINUS") ||
                operator.equals("TIMES") || operator.equals("DIV");
        boolean relop = operator.equals("LT") || operator.equals("LE") ||
                operator.equals("GT") || operator.equals("GE") ||
                operator.equals("EQ") || operator.equals("NE");
        boolean booleanCheck = operator.equals("AND") || operator.equals("OR");

        // OPERAZIONI ARITMETICHE
        // INTEGER op INTEGER -> INTEGER
        if (arithOpCheck && leftOperand.getType() == Type.INTEGER && rightOperand.getType() == Type.INTEGER) {
            return Type.INTEGER;
        }
        // DOUBLE op DOUBLE -> DOUBLE
        else if (arithOpCheck && leftOperand.getType() == Type.DOUBLE && rightOperand.getType() == Type.DOUBLE) {
            return Type.DOUBLE;
        }
        // INTEGER op DOUBLE -> DOUBLE (promozione)
        else if (arithOpCheck && leftOperand.getType() == Type.INTEGER && rightOperand.getType() == Type.DOUBLE) {
            return Type.DOUBLE;
        }
        // DOUBLE op INTEGER -> DOUBLE (promozione)
        else if (arithOpCheck && leftOperand.getType() == Type.DOUBLE && rightOperand.getType() == Type.INTEGER) {
            return Type.DOUBLE;
        }

        // OPERAZIONI LOGICHE
        // BOOLEAN op BOOLEAN -> BOOLEAN
        else if (booleanCheck && leftOperand.getType() == Type.BOOLEAN && rightOperand.getType() == Type.BOOLEAN) {
            return Type.BOOLEAN;
        }

        // OPERAZIONI RELAZIONALI (restituiscono sempre BOOLEAN)
        // INTEGER relop INTEGER -> BOOLEAN
        else if (relop && leftOperand.getType() == Type.INTEGER && rightOperand.getType() == Type.INTEGER) {
            return Type.BOOLEAN;
        }
        // DOUBLE relop INTEGER -> BOOLEAN
        else if (relop && leftOperand.getType() == Type.DOUBLE && rightOperand.getType() == Type.INTEGER) {
            return Type.BOOLEAN;
        }
        // INTEGER relop DOUBLE -> BOOLEAN
        else if (relop && leftOperand.getType() == Type.INTEGER && rightOperand.getType() == Type.DOUBLE) {
            return Type.BOOLEAN;
        }
        // DOUBLE relop DOUBLE -> BOOLEAN
        else if (relop && leftOperand.getType() == Type.DOUBLE && rightOperand.getType() == Type.DOUBLE) {
            return Type.BOOLEAN;
        }

        // OPERAZIONI SU STRINGHE
        // STRING + X -> STRING (concatenazione con conversione automatica)
        else if (operator.equals("PLUS") && (leftOperand.getType() == Type.STRING || rightOperand.getType() == Type.STRING)) {
            return Type.STRING;
        }
        // STRING != STRING -> BOOLEAN
        else if (operator.equals("NE") && (leftOperand.getType() == Type.STRING && rightOperand.getType() == Type.STRING)) {
            return Type.BOOLEAN;
        }
        // STRING == STRING -> BOOLEAN
        else if (operator.equals("EQ") && (leftOperand.getType() == Type.STRING && rightOperand.getType() == Type.STRING)) {
            return Type.BOOLEAN;
        }

        // OPERAZIONE NON SUPPORTATA
        else {
            throw new RuntimeException("The assignment does not have a match in the table! " +
                    leftOperand + " " + operation.getOperator() + " " + rightOperand);
        }
    }
}
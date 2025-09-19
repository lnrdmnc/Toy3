package visitor;

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
import node.vardecl.VarDecl;
import node.vardecl.VarInit;
import visitor.utils.*;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Classe ScopeVisitor - Visitor per l'analisi degli scope e la costruzione delle tabelle dei simboli
 *
 * Questa classe implementa il pattern Visitor per attraversare l'AST (Abstract Syntax Tree)
 * e costruire le tabelle dei simboli per ogni scope del programma. Gestisce la visibilità
 * delle variabili, funzioni e procedure nei vari contesti (globale, locale, parametri).
 *
 * Lo stack typeenv mantiene le tabelle dei simboli attive durante l'attraversamento,
 * implementando la semantica degli scope annidati.
 */
public class ScopeVisitor implements Visitor {
    // Stack che mantiene le tabelle dei simboli per gli scope attivi
    private Stack<TabellaDeiSimboli> typeenv = new Stack<>();

    /**
     * Costruttore della classe ScopeVisitor
     * Inizializza lo stack delle tabelle dei simboli vuoto
     */
    public ScopeVisitor() {

    }

    /**
     * Visita il nodo principale del programma e gestisce gli scope globali
     *
     * Crea due scope principali:
     * 1. PROGRAM: per dichiarazioni globali (funzioni, procedure, variabili globali)
     * 2. BEGIN-END: per il corpo principale del programma
     *
     * @param programOp nodo che rappresenta l'intero programma
     * @return null (non restituisce valori)
     */
    @Override
    public Object visit(ProgramOp programOp) {
        // Crea e inserisce la tabella dei simboli per lo scope PROGRAM
        TabellaDeiSimboli tabellaProgramma = new TabellaDeiSimboli(new ArrayList<>(), "PROGRAM");
        typeenv.push(tabellaProgramma);

        // Visita dichiarazioni globali (funzioni, procedure, variabili)
        ArrayList<Decl> decls = programOp.getDeclarations();
        for (Decl decl : decls) {
            String name;
            String kind;
            Firma type;
            Type returnType = null;
            ArrayList<Boolean> refList = new ArrayList<>();
            ArrayList<Type> inputTypes = new ArrayList<>();

            if (decl instanceof DefDecl defDecl) {
                // Gestione definizioni di funzioni/procedure
                name = defDecl.getId().getName();
                kind = (defDecl.getType() == null) ? "procedure" : "function";
                returnType = defDecl.getType();

                // Analizza i parametri della funzione
                if (defDecl.getList() != null) {
                    for (ParDecl parDecl : defDecl.getList()) {
                        inputTypes.add(parDecl.getType());
                        for (ParVar parVar : parDecl.getVariables()) {
                            refList.add(parVar.isReference()); // Memorizza se è passato per riferimento
                        }
                    }
                } else {
                    inputTypes = null; // Funzione senza parametri
                }

                // Crea la firma della funzione con tipi di input, output e riferimenti
                type = new TipoFunzione(inputTypes, returnType, refList);
                RigaTabellaDeiSimboli riga = new RigaTabellaDeiSimboli(name, kind, type);
                tabellaProgramma.aggiungiRiga(riga);

            } else if (decl instanceof VarDecl varDecl) {
                // Gestione dichiarazioni di variabili globali
                for (VarInit var : varDecl.getVariables()) {
                    name = var.getId().getName();
                    kind = "variable";

                    // Determina il tipo della variabile
                    if (varDecl.getType() == null) {
                        // Inferenza di tipo da costante
                        if (!controllaDichiarazioneVariabile(varDecl)) {
                            throw new RuntimeException("Errore: dichiarazione variabile non valida per " + name);
                        }
                        type = new FirmaVariabile(varDecl.getCostant());
                    } else {
                        // Tipo esplicito
                        type = new FirmaVariabile(varDecl.getType());
                    }

                    RigaTabellaDeiSimboli riga = new RigaTabellaDeiSimboli(name, kind, type);
                    System.out.println(tabellaProgramma);
                    System.out.println("Aggiungo simbolo: " + riga.getId() + " di tipo " + riga.getTipo());

                    tabellaProgramma.aggiungiRiga(riga);
                }
            }

            decl.accept(this); // Visita ricorsiva della dichiarazione
        }

        // Salva la tabella dei simboli globale nel nodo programma
        programOp.setTabellaDeiSimboliProgram(tabellaProgramma);

        // Inizio del blocco BEGIN-END (corpo principale del programma)
        TabellaDeiSimboli tabellaBeginEnd = new TabellaDeiSimboli("BEGIN-END");
        typeenv.push(tabellaBeginEnd);

        // Gestione dichiarazioni interne (scope BEGIN-END)
        ArrayList<VarDecl> innerDecls = programOp.getVarDeclarations();
        if (innerDecls != null) {
            for (VarDecl varDecl : innerDecls) {
                for (VarInit var : varDecl.getVariables()) {
                    String name = var.getId().getName();
                    String kind = "variable";
                    Firma type;

                    // Determina il tipo della variabile locale
                    if (varDecl.getType() == null) {
                        if (varDecl.getCostant() == null) {
                            throw new RuntimeException("Errore: dichiarazione variabile non valida per " + name);
                        }
                        type = new FirmaVariabile(varDecl.getCostant());
                    } else {
                        type = new FirmaVariabile(varDecl.getType());
                    }

                    RigaTabellaDeiSimboli riga = new RigaTabellaDeiSimboli(name, kind, type);
                    tabellaBeginEnd.aggiungiRiga(riga);
                }
                varDecl.accept(this);
            }
        }

        // Salva la tabella dei simboli per BEGIN-END
        programOp.setTabellaBegEnd(tabellaBeginEnd);

        // Visita le istruzioni del corpo principale
        ArrayList<Stat> statements = programOp.getStatements();
        if (statements != null) {
            for (Stat stat : statements) {
                stat.accept(this);
            }
        }

        // Pulizia dello stack: chiusura degli scope
        typeenv.pop(); // Chiude scope BEGIN-END
        typeenv.pop(); // Chiude scope PROGRAM

        return null;
    }

    /**
     * Visita la definizione di una funzione o procedura
     *
     * Crea un nuovo scope per la funzione e inserisce:
     * - I parametri formali nella tabella dei simboli
     * - Le variabili locali dichiarate nel corpo
     *
     * @param defDecl nodo che rappresenta una definizione di funzione/procedura
     * @return null (non restituisce valori)
     */
    @Override
    public Object visit(DefDecl defDecl) {
        // Crea una nuova tabella dei simboli per la funzione
        TabellaDeiSimboli tabella = new TabellaDeiSimboli(new ArrayList<>(), defDecl.getId().getName());
        typeenv.add(tabella);
        ArrayList<ParDecl> list = defDecl.getList();

        // Inserisce i parametri formali nella tabella dei simboli
        if(list != null) {
            for (ParDecl parDecl : list) {
                Firma tipo = new FirmaVariabile(parDecl.getType());

                for (ParVar parVar : parDecl.getVariables()) {
                    // Crea una riga per ogni parametro, marcando se è per riferimento
                    RigaTabellaDeiSimboli riga = new RigaTabellaDeiSimboli(
                            parVar.getId().getName(),
                            "variable",
                            tipo,
                            parVar.isReference()
                    );
                    System.out.println("rigaaa:" + riga);
                    tabella.aggiungiRiga(riga);
                }
                parDecl.accept(this);
            }
        }

        // Analizza il corpo della funzione/procedura
        BodyOp body = defDecl.getBody();

        // Inserisce le dichiarazioni locali nella tabella dei simboli
        ArrayList<VarDecl> decls = body.getDichiarazioni();
        for (VarDecl decl : decls) {
            Firma tipo;

            // Determina il tipo della variabile locale
            if (decl.getType() == null) {
                if (decl.getCostant() == null || !controllaDichiarazioneVariabile(decl)) {
                    throw new RuntimeException("Errore: dichiarazione variabile non valida.");
                }
                tipo = new FirmaVariabile(decl.getCostant());
            } else {
                tipo = new FirmaVariabile(decl.getType());
            }

            // Inserisce tutte le variabili dichiarate insieme
            for (VarInit var : decl.getVariables()) {
                RigaTabellaDeiSimboli riga = new RigaTabellaDeiSimboli(
                        var.getId().getName(),
                        "variable",
                        tipo
                );
                tabella.aggiungiRiga(riga);
            }

            decl.accept(this); // Visita la dichiarazione locale
        }

        // Salva la tabella nella definizione della funzione
        defDecl.setTabellaDeiSimboli(tabella);

        // Visita le istruzioni del corpo della funzione
        ArrayList<Stat> statements = body.getStatements();
        for (Stat stat : statements) {
            stat.accept(this);
        }

        // Visita l'identificatore della funzione (opzionale)
        defDecl.getId().accept(this);

        typeenv.pop(); // Chiude lo scope della funzione
        return null;
    }

    /**
     * Visita una dichiarazione di variabile
     * Associa la tabella dei simboli corrente al nodo e visita i componenti
     *
     * @param varDecl nodo che rappresenta una dichiarazione di variabile
     * @return null (non restituisce valori)
     */
    @Override
    public Object visit(VarDecl varDecl) {
        TabellaDeiSimboli tabellaDeiSimboli = typeenv.peek();
        varDecl.setTabellaDeiSimboli(tabellaDeiSimboli);

        Type varType = varDecl.getType();

        // Visita la costante se presente (per inferenza di tipo)
        if(varDecl.getCostant() != null){
            varDecl.getCostant().accept(this);
        }

        // Visita tutte le variabili dichiarate
        if(varDecl.getVariables() != null){
            for (VarInit varInit : varDecl.getVariables()) {
                varInit.accept(this);
            }
        }
        return null;
    }

    /**
     * Visita l'inizializzazione di una variabile
     * Associa la tabella dei simboli e visita identificatore e valore iniziale
     *
     * @param varInit nodo che rappresenta l'inizializzazione di una variabile
     * @return null (non restituisce valori)
     */
    @Override
    public Object visit(VarInit varInit) {
        TabellaDeiSimboli tabella = typeenv.peek();
        varInit.setTabellaDeiSimboli(tabella);
        varInit.getId().accept(this);

        // Visita il valore di inizializzazione se presente
        if(varInit.getInitValue() != null){
            varInit.getInitValue().accept(this);
        }
        return null;
    }

    /**
     * Visita un'operazione binaria
     * Associa la tabella dei simboli e visita gli operandi
     *
     * @param binaryOp nodo che rappresenta un'operazione binaria
     * @return null (non restituisce valori)
     */
    @Override
    public Object visit(BinaryOp binaryOp) {
        TabellaDeiSimboli tabellaDeiSimboli = typeenv.peek();
        binaryOp.setTabellaDeiSimboli(tabellaDeiSimboli);
        binaryOp.getRight().accept(this);
        binaryOp.getLeft().accept(this);
        return null;
    }

    /**
     * Visita un'operazione unaria
     * Associa la tabella dei simboli e visita l'operando
     *
     * @param unaryOp nodo che rappresenta un'operazione unaria
     * @return null (non restituisce valori)
     */
    @Override
    public Object visit(UnaryOp unaryOp) {
        TabellaDeiSimboli tabella = typeenv.peek();
        unaryOp.setTabellaDeiSimboli(tabella);
        unaryOp.getOperand().accept(this);
        return null;
    }

    /**
     * Visita una chiamata di funzione
     * Associa la tabella dei simboli e visita identificatore e argomenti
     *
     * @param funCall nodo che rappresenta una chiamata di funzione
     * @return null (non restituisce valori)
     */
    @Override
    public Object visit(FunCall funCall) {
        TabellaDeiSimboli tabella = typeenv.peek();
        funCall.setTabellaDeiSimboli(tabella);
        funCall.getId().accept(this);

        // Visita tutti gli argomenti della chiamata
        if (funCall.getArguments() != null){
            for (Expr arg : funCall.getArguments()) {
                arg.accept(this);
            }
        }
        return null;
    }

    /**
     * Visita un identificatore e determina se è un riferimento
     *
     * Attraversa lo stack delle tabelle dei simboli per trovare l'identificatore
     * e determina se è stato dichiarato come parametro per riferimento
     *
     * @param identifier nodo che rappresenta un identificatore
     * @return null (non restituisce valori)
     */
    @Override
    public Object visit(Identifier identifier) {
        // Prende la tabella attuale
        TabellaDeiSimboli tabellaCorrente = typeenv.peek();
        identifier.setTabellaDeiSimboli(tabellaCorrente);

        // Clona lo stack per analisi approfondita senza modificarlo
        Stack<TabellaDeiSimboli> stackClone = clonaTypeEnv(typeenv);

        // Cerca l'identificatore in tutti gli scope, dal più interno al più esterno
        for (int i = stackClone.size() - 1; i >= 0; i--) {
            TabellaDeiSimboli tabella = stackClone.get(i);
            RigaTabellaDeiSimboli riga = tabella.getRiga(identifier, "variable");

            if (riga != null) {
                // Se trovato, controlla se è un parametro per riferimento
                if (riga.isRef()) {
                    identifier.setRef(true);
                }
            }
        }

        return null;
    }

    // --- VISITE PER LE COSTANTI ---
    // Tutte associano la tabella dei simboli corrente al nodo

    /**
     * Visita una costante carattere
     */
    @Override
    public Object visit(CharNode charNode) {
        TabellaDeiSimboli tabella = typeenv.peek();
        charNode.setTabellaDeiSimboli(tabella);
        return null;
    }

    /**
     * Visita una costante double
     */
    @Override
    public Object visit(DoubleNode doubleNode) {
        TabellaDeiSimboli tabella = typeenv.peek();
        doubleNode.setTabellaDeiSimboli(tabella);
        return null;
    }

    /**
     * Visita una costante intera
     */
    @Override
    public Object visit(IntegerNode integerNode) {
        TabellaDeiSimboli tabella = typeenv.peek();
        integerNode.setTabellaDeiSimboli(tabella);
        return null;
    }

    /**
     * Visita una costante stringa
     */
    @Override
    public Object visit(StringNode stringNode) {
        TabellaDeiSimboli tabella = typeenv.peek();
        stringNode.setTabellaDeiSimboli(tabella);
        return null;
    }

    /**
     * Visita una costante true
     */
    @Override
    public Object visit(TrueNode trueNode) {
        TabellaDeiSimboli tabella = typeenv.peek();
        trueNode.setTabellaDeiSimboli(tabella);
        return null;
    }

    /**
     * Visita una costante false
     */
    @Override
    public Object visit(FalseNode falseNode) {
        TabellaDeiSimboli tabella = typeenv.peek();
        falseNode.setTabellaDeiSimboli(tabella);
        return null;
    }

    // --- VISITE PER GLI STATEMENT ---

    /**
     * Visita un'operazione di assegnazione
     *
     * Controlla la semantica delle assegnazioni multiple:
     * - Non sono ammesse chiamate di funzione in assegnazioni multiple
     *
     * @param assignOp nodo che rappresenta un'assegnazione
     * @return null (non restituisce valori)
     */
    @Override
    public Object visit(AssignOp assignOp) {
        TabellaDeiSimboli tabella = typeenv.peek();
        assignOp.setTabellaDeiSimboli(tabella);

        // Visita tutte le variabili target dell'assegnazione
        if(assignOp.getVariables() != null){
            for (Identifier id : assignOp.getVariables()) {
                id.accept(this);
            }
        }

        // Visita tutte le espressioni da assegnare
        if(assignOp.getExpressions() != null){
            for (Expr expr : assignOp.getExpressions()) {
                expr.accept(this);
            }
        }

        // Controllo semantico: assegnazioni multiple non possono contenere chiamate di funzione
        if(assignOp.getVariables().size() > 1){
            if(assignOp.getExpressions() != null){
                for (Expr expr : assignOp.getExpressions()) {
                    if(expr instanceof FunCall){
                        throw new RuntimeException("FunCall not allowed in multiple assignment");
                    }
                }
            }
        }

        return null;
    }

    /**
     * Visita un'operazione di scrittura (output)
     * Associa la tabella dei simboli e visita tutte le espressioni da stampare
     *
     * @param writeOp nodo che rappresenta un'operazione di scrittura
     * @return null (non restituisce valori)
     */
    @Override
    public Object visit(WriteOp writeOp) {
        TabellaDeiSimboli tabella = typeenv.peek();
        writeOp.setTabella(tabella);

        if(writeOp.getExpressions() != null){
            for (Expr expr : writeOp.getExpressions()) {
                expr.accept(this);
            }
        }
        return null;
    }

    /**
     * Visita un'istruzione if-then
     * Crea un nuovo scope per il blocco then e visita condizione e corpo
     *
     * @param ifThen nodo che rappresenta un'istruzione if-then
     * @return null (non restituisce valori)
     */
    @Override
    public Object visit(IfThenNode ifThen) {
        // Crea un nuovo scope per il blocco if-then
        TabellaDeiSimboli tabella = new TabellaDeiSimboli("IF-THEN");
        typeenv.add(tabella);

        ifThen.getEspressione().accept(this);  // Visita la condizione
        ifThen.getBody().accept(this);         // Visita il corpo del then

        typeenv.pop(); // Chiude lo scope
        ifThen.setTabellaDeiSimboli(tabella);

        return null;
    }

    /**
     * Visita un'istruzione if-then-else
     * Crea un nuovo scope e visita condizione, corpo then e corpo else
     *
     * @param ifThenElse nodo che rappresenta un'istruzione if-then-else
     * @return null (non restituisce valori)
     */
    @Override
    public Object visit(IfThenElse ifThenElse) {
        // Crea un nuovo scope per il blocco if-then-else
        TabellaDeiSimboli tabella = new TabellaDeiSimboli("IF-THEN-ELSE");
        typeenv.add(tabella);

        ifThenElse.getEspressione().accept(this);       // Visita la condizione
        ifThenElse.getIfthenStatement().accept(this);   // Visita il corpo then
        ifThenElse.getElseStatement().accept(this);     // Visita il corpo else

        typeenv.pop(); // Chiude lo scope
        ifThenElse.setTabellaDeiSimboli(tabella);
        return null;
    }

    /**
     * Visita un'operazione di lettura (input)
     * Associa la tabella dei simboli e visita tutti gli identificatori da leggere
     *
     * @param readOp nodo che rappresenta un'operazione di lettura
     * @return null (non restituisce valori)
     */
    @Override
    public Object visit(ReadOp readOp) {
        TabellaDeiSimboli tabella = typeenv.peek();
        readOp.setTabellaDeiSimboli(tabella);

        if(readOp.getList() != null){
            for(Identifier i : readOp.getList()){
                i.accept(this);
            }
        }

        return null;
    }

    /**
     * Visita un ciclo while
     * Crea un nuovo scope per il corpo del ciclo e visita condizione e corpo
     *
     * @param whileOp nodo che rappresenta un ciclo while
     * @return null (non restituisce valori)
     */
    @Override
    public Object visit(WhileOp whileOp) {
        // Crea un nuovo scope per il ciclo while
        TabellaDeiSimboli tabella = new TabellaDeiSimboli("WHILE");
        typeenv.push(tabella);

        whileOp.getEspr().accept(this);  // Visita la condizione
        whileOp.getBody().accept(this);  // Visita il corpo del ciclo

        typeenv.pop(); // Chiude lo scope
        whileOp.setTabellaDeiSimboli(tabella);
        return null;
    }

    /**
     * Visita un'istruzione return
     * Associa la tabella dei simboli e visita l'espressione di ritorno
     *
     * @param returnOp nodo che rappresenta un'istruzione return
     * @return null (non restituisce valori)
     */
    @Override
    public Object visit(ReturnStat returnOp) {
        TabellaDeiSimboli tabella = typeenv.peek();
        returnOp.setTabella(tabella);
        returnOp.getExpr().accept(this);
        return null;
    }

    /**
     * Visita la dichiarazione di un parametro
     * Associa la tabella dei simboli e visita tutte le variabili parametro
     *
     * @param parDecl nodo che rappresenta la dichiarazione di un parametro
     * @return null (non restituisce valori)
     */
    @Override
    public Object visit(ParDecl parDecl) {
        TabellaDeiSimboli tabella = typeenv.peek();
        parDecl.setTabellaDeiSimboli(tabella);

        if (parDecl.getVariables() != null){
            for(ParVar variables : parDecl.getVariables()) {
                variables.accept(this);
            }
        }
        return null;
    }

    /**
     * Visita una variabile parametro
     * Associa la tabella dei simboli e visita l'identificatore
     *
     * @param parVar nodo che rappresenta una variabile parametro
     * @return null (non restituisce valori)
     */
    @Override
    public Object visit(ParVar parVar) {
        TabellaDeiSimboli table = typeenv.peek();
        parVar.setTabellaDeiSimboli(table);
        parVar.getId().accept(this);
        return null;
    }

    /**
     * Visita il corpo di un blocco (funzione, if, while, ecc.)
     *
     * Crea un nuovo scope per il corpo e inserisce:
     * - Tutte le dichiarazioni locali nella tabella dei simboli
     * - Visita tutte le istruzioni del corpo
     *
     * @param bodyOp nodo che rappresenta un corpo/blocco
     * @return null (non restituisce valori)
     */
    @Override
    public Object visit(BodyOp bodyOp) {
        TabellaDeiSimboli fatherTable = typeenv.peek();
        TabellaDeiSimboli bodyTable = new TabellaDeiSimboli("Body Table" + " " + fatherTable.getNome());

        typeenv.add(bodyTable);

        // Inserisce le dichiarazioni locali nella tabella dei simboli del corpo
        if (bodyOp.getDichiarazioni() != null){
            for(VarDecl vars : bodyOp.getDichiarazioni()) {
                Firma type = new FirmaVariabile(vars.getType());

                // Gestisce l'inferenza di tipo da costante
                if(vars.getType() == null) {
                    if(!controllaDichiarazioneVariabile(vars)) {
                        throw new RuntimeException("Incorrect Variable declaration");
                    }
                    type = new FirmaVariabile(vars.getCostant());
                }

                // Inserisce tutte le variabili nella tabella
                for(VarInit var: vars.getVariables()){
                    RigaTabellaDeiSimboli row = new RigaTabellaDeiSimboli(
                            var.getId().getName(),
                            "variable",
                            type
                    );
                    bodyTable.aggiungiRiga(row);
                }

                vars.accept(this);
            }
        }

        // Visita tutte le istruzioni del corpo
        if (bodyOp.getStatements() != null){
            for(Stat stats : bodyOp.getStatements()) {
                stats.accept(this);
            }
        }

        bodyOp.setTabellaDeiSimboli(bodyTable);
        typeenv.pop(); // Chiude lo scope del corpo

        return null;
    }

    @Override
    public Object visit(InitDoForStep initDoForStep) {
        // Crea un nuovo scope per il costrutto init-do-for-step
        TabellaDeiSimboli tabella = new TabellaDeiSimboli("INIT-DO-FOR-STEP");
        typeenv.push(tabella);

        // Gestisce le variabili di inizializzazione
        if (initDoForStep.getInitScope() != null) {
            for (VarDecl decl : initDoForStep.getInitScope()) {
                // Inserisce la variabile nella tabella dei simboli
                for (VarInit varInit : decl.getVariables()) {
                    String name = varInit.getId().getName();
                    Firma tipo;
                    if (decl.getType() != null) {
                        // Tipo esplicito
                        tipo = new FirmaVariabile(decl.getType());
                    } else if (decl.getCostant() != null) {
                        tipo = new FirmaVariabile(decl.getCostant());
                    } else {
                        throw new RuntimeException("Errore: dichiarazione variabile non valida per " + name);
                    }

                    RigaTabellaDeiSimboli riga = new RigaTabellaDeiSimboli(name, "variable", tipo);
                    tabella.aggiungiRiga(riga);

                    decl.accept(this);
                }
            }
        }

            // Visita il corpo del do
            initDoForStep.getDoBody().accept(this);

            // Visita la condizione se presente
            if (initDoForStep.getCondition() != null) {
                System.out.print(initDoForStep.getCondition());
                initDoForStep.getCondition().accept(this);
            }

        // Visita le assegnazioni di step se presenti
        if (initDoForStep.getStepExprs() != null) {
            initDoForStep.getStepExprs().accept(this);
        }

            initDoForStep.setTabellaDeiSimboli(tabella);
            typeenv.pop(); // Chiude lo scope
            return null;

        }

    // --- METODI DI UTILITÀ ---

    /**
     * Controlla la validità di una dichiarazione di variabile con inferenza di tipo
     *
     * Una dichiarazione è valida se:
     * - Ha una sola variabile senza valore iniziale, oppure
     * - Ha più variabili tutte senza valore iniziale
     *
     * @param dichiarazione dichiarazione di variabile da controllare
     * @return true se la dichiarazione è valida, false altrimenti
     */
    public boolean controllaDichiarazioneVariabile(VarDecl dichiarazione) {
        int numeroVariabili = dichiarazione.getVariables().size();

        if (numeroVariabili == 1) {
            VarInit variabile = dichiarazione.getVariables().get(0);
            if (variabile.getInitValue() == null) {
                return true;
            }
        }

        return false;
    }

    /**
     * Clona lo stack delle tabelle dei simboli per analisi non distruttiva
     *
     * @param ambienteTipi stack originale da clonare
     * @return nuovo stack con le stesse tabelle
     */
    public Stack<TabellaDeiSimboli> clonaTypeEnv(Stack<TabellaDeiSimboli> ambienteTipi) {
        Stack<TabellaDeiSimboli> copia = new Stack<>();
        for (TabellaDeiSimboli tabellaCorrente : ambienteTipi) {
            copia.push(tabellaCorrente);
        }
        return copia;
    }
}
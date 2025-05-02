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
import visitor.utils.Firma;
import visitor.utils.FirmaVariabile;
import visitor.utils.RigaTabellaDeiSimboli;
import visitor.utils.TabellaDeiSimboli;

import java.util.ArrayList;
import java.util.Stack;

public class ScopeVisitor implements Visitor {
    private Stack<TabellaDeiSimboli> typeenv = new Stack<>();


    public ScopeVisitor() {

    }

    @Override
    public Object visitProgramOp(ProgramOp programOp) {
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
                name = defDecl.getId().getName();
                kind = (defDecl.getType() == null) ? "procedure" : "function";
                returnType = defDecl.getType();

                if (defDecl.getList() != null) {
                    for (ParDecl parDecl : defDecl.getList()) {
                        inputTypes.add(parDecl.getType());
                        for (ParVar parVar : parDecl.getVariables()) {
                            refList.add(parVar.isReference());
                        }
                    }
                } else {
                    inputTypes = null;
                }

                type = new FirmaVariabile(inputTypes, returnType, refList);
                RigaTabellaDeiSimboli riga = new RigaTabellaDeiSimboli(name, kind, type);
                tabellaProgramma.aggiungiRiga(riga);

            } else if (decl instanceof VarDecl varDecl) {
                for (VarInit var : varDecl.getVariables()) {
                    name = var.getId().getName();
                    kind = "variable";

                    if (varDecl.getType() == null) {
                        if (varDecl.getCostant() == null) {
                            throw new RuntimeException("Errore: dichiarazione variabile non valida per " + name);
                        }
                        type = new FirmaVariabile(varDecl.getCostant());
                    } else {
                        type = new FirmaVariabile(varDecl.getType());
                    }

                    RigaTabellaDeiSimboli riga = new RigaTabellaDeiSimboli(name, kind, type);
                    tabellaProgramma.aggiungiRiga(riga);
                }
            }

            decl.accept(this);
        }

        // Salva la tabella dei simboli globale
        programOp.setTabellaDeiSimboliProgram(tabellaProgramma);

        // Inizio del blocco BEGIN-END
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

        // Visita le istruzioni
        ArrayList<StatOp> statements = programOp.getStatements();
        if (statements != null) {
            for (StatOp stat : statements) {
                stat.accept(this);
            }
        }

        // Pulizia dello stack: chiusura scope
        typeenv.pop(); // fine BEGIN-END
        typeenv.pop(); // fine PROGRAM

        return null;
    }



    @Override
    public Object visitDefDecl(DefDecl defDecl) {
        TabellaDeiSimboli tabella= new TabellaDeiSimboli(new ArrayList<>(),defDecl.getId().getName());
        typeenv.add(tabella);
        ArrayList<ParDecl> list=defDecl.getList();

        if(list != null) {
            for (ParDecl parDecl : list) {
                Firma tipo = new FirmaVariabile(parDecl.getType());

                for (ParVar parVar : parDecl.getVariables()) {
                    RigaTabellaDeiSimboli riga = new RigaTabellaDeiSimboli(parVar.getId().getName(), "variable", tipo, parVar.isReference());
                    tabella.aggiungiRiga(riga);
                }
                parDecl.accept(this);

            }
        }
        // Corpo della funzione/procedura
        BodyOp body = defDecl.getBody();

        // Dichiarazioni locali nel corpo
        ArrayList<VarDecl> decls = body.getDichiarazioni();
        for (VarDecl decl : decls) {
            Firma tipo;

            if (decl.getType() == null) {
                if (decl.getCostant() == null || !controllaDichiarazioneVariabile(decl)) {
                    throw new RuntimeException("Errore: dichiarazione variabile non valida.");
                }
                tipo = new FirmaVariabile(decl.getCostant());
            } else {
                tipo = new FirmaVariabile(decl.getType());
            }

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

        // Salva la tabella nella definizione
        defDecl.setTabellaDeiSimboli(tabella);

        // Visita le istruzioni del corpo
        ArrayList<StatOp> statements = body.getStatements();
        for (StatOp stat : statements) {
            stat.accept(this);
        }

        // Visita dell'identificatore (opzionale, se necessario)
        defDecl.getId().accept(this);

        typeenv.pop(); // Chiude lo scope
        return null;
    }



    @Override
    public Object visitVarDecl(VarDecl varDecl) {
        TabellaDeiSimboli tabellaDeiSimboli= typeenv.peek();
        varDecl.setTabellaDeiSimboli(tabellaDeiSimboli);

        Type varType = varDecl.getType();
        if(varDecl.getCostant() != null){
            varDecl.getCostant().accept(this);
        }
        if(varDecl.getVariables() != null){
            for (VarInit varInit : varDecl.getVariables()) {
                varInit.accept(this);

            }
        }
        return null;
    }

    @Override
    public Object visitVarInit(VarInit varInit) {
        TabellaDeiSimboli tabella= typeenv.peek();
        varInit.setTabellaDeiSimboli(tabella);
        varInit.getId().accept(this);

        if(varInit.getInitValue() != null){
            varInit.getInitValue().accept(this);
        }
        return null;
    }

    @Override
    public Object visitBinaryOp(BinaryOp binaryOp) {
        TabellaDeiSimboli tabellaDeiSimboli= typeenv.peek();
        binaryOp.setTabellaDeiSimboli(tabellaDeiSimboli);
        binaryOp.getRight().accept(this);
        binaryOp.getLeft().accept(this);
        return null;
    }

    @Override
    public Object visitUnaryOp(UnaryOp unaryOp) {
        TabellaDeiSimboli tabella= typeenv.peek();
        unaryOp.setTabellaDeiSimboli(tabella);
        unaryOp.getOperand().accept(this);
        return null;
    }

    @Override
    public Object visitFunCall(FunCall funCall) {
        TabellaDeiSimboli tabella= typeenv.peek();
        funCall.setTabellaDeiSimboli(tabella);

        funCall.getId().accept(this);
        if (funCall.getArguments()!= null){
            for (Expr arg : funCall.getArguments()) {
                arg.accept(this);
            }
        }
        return null;
    }

    @Override
    public Object visitIdentifier(Identifier identifier) {
        // Prende la tabella attuale
        TabellaDeiSimboli tabellaCorrente = typeenv.peek();
        identifier.setTabellaDeiSimboli(tabellaCorrente);

        // Clona lo stack per analisi approfondita
        Stack<TabellaDeiSimboli> stackClone = clonaTypeEnv(typeenv);

        for (int i = stackClone.size() - 1; i >= 0; i--) {
            TabellaDeiSimboli tabella = stackClone.get(i);
            RigaTabellaDeiSimboli riga = tabella.getRigaLista(identifier, "variable");

            if (riga != null) {
                if (riga.isRef())) {
                    identifier.setRef(true);
                }
            }
        }

        return null;
    }

    @Override
    public Object visitIdentifier(CharNode charNode) {
        TabellaDeiSimboli tabella= typeenv.peek();
        charNode.setTabellaDeiSimboli(tabella);
        return null;
    }

    @Override
    public Object visitIdentifier(DoubleNode doubleNode) {
        TabellaDeiSimboli tabella= typeenv.peek();
        doubleNode.setTabellaDeiSimboli(tabella);
        return null;
    }

    @Override
    public Object visitIdentifier(IntegerNode integerNode) {
        TabellaDeiSimboli tabella= typeenv.peek();
        integerNode.setTabellaDeiSimboli(tabella);
        return null;
    }

    @Override
    public Object visitIdentifier(StringNode stringNode) {
        TabellaDeiSimboli tabella= typeenv.peek();
        stringNode.setTabellaDeiSimboli(tabella);
        return null;
    }

    @Override
    public Object visitIdentifier(TrueNode trueNode) {
        TabellaDeiSimboli tabella= typeenv.peek();
        trueNode.setTabellaDeiSimboli(tabella);
        return null;
    }

    @Override
    public Object visitAssignOp(AssignOp assignOp)
    {
        TabellaDeiSimboli tabella= typeenv.peek();
        assignOp.setTabellaDeiSimboli(tabella);

        if(assignOp.getVariables() != null){
            for (Identifier id : assignOp.getVariables()) {
                id.accept(this);
            }
        }
        if(assignOp.getExpressions() != null){
            for (Expr expr : assignOp.getExpressions()) {
                expr.accept(this);
            }
        }

        //controllo assegnazioni multiple
        if(assignOp.getVariables().size()>1){
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

    @Override
    public Object visitWriteOperationNode(WriteOp writeOp) {

        TabellaDeiSimboli tabella= typeenv.peek();
        writeOp.setTabella(tabella);
        if(writeOp.getExpressions() != null){
            for (Expr expr : writeOp.getExpressions()) {
                expr.accept(this);
            }
        }
        return null;
    }

    @Override
    public Object visitIfThen(IfThenNode ifThen) {

        TabellaDeiSimboli tabella= new TabellaDeiSimboli("IF-THEN");
        typeenv.add(tabella);
        ifThen.getEspressione().accept(this);
        ifThen.getBody().accept(this);
        typeenv.pop();
        ifThen.setTabellaDeiSimboli(tabella);

        return null;
    }

    @Override
    public Object visitIfThenElse(IfThenElse ifThenElse) {
        TabellaDeiSimboli tabella= new TabellaDeiSimboli("IF-THEN-ELSE");
        typeenv.add(tabella);
        ifThenElse.getEspressione().accept(this);
        ifThenElse.getIfthenStatement().accept(this);
        ifThenElse.getElseStatement().accept(this);
        typeenv.pop();
        ifThenElse.setTabellaDeiSimboli(tabella);
        return null;
    }

    @Override
    public Object visitReadOp(ReadOp readOp) {
        TabellaDeiSimboli tabella= typeenv.peek();
        readOp.setTabellaDeiSimboli(tabella);
        if(readOp.getList()!= null){
            for(Identifier i:readOp.getList()){
                i.accept(this);
            }
        }

        return null;
    }

    @Override
    public Object visitWhileOp(WhileOp whileOp) {
        TabellaDeiSimboli tabella= new TabellaDeiSimboli("WHILE");
        typeenv.push(tabella);
        whileOp.setTabellaDeiSimboli(tabella);
        whileOp.getBody().accept(this);
        typeenv.pop();
        whileOp.setTabellaDeiSimboli(tabella);
        return null;
    }

    @Override
    public Object visitReturnOp(ReturnStat returnOp) {
        TabellaDeiSimboli tabella= typeenv.peek();
        returnOp.setTabella(tabella);
        returnOp.getExpr().accept(this);
        return null;
    }

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


    public Stack<TabellaDeiSimboli> clonaTypeEnv(Stack<TabellaDeiSimboli> ambienteTipi) {
        Stack<TabellaDeiSimboli> copia = new Stack<>();
        for (TabellaDeiSimboli tabellaCorrente : ambienteTipi) {
            copia.push(tabellaCorrente);
        }
        return copia;
    }

}

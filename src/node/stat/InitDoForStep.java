package node.stat;

import node.ASTNode;
import node.Stat;
import node.Type;
import node.Visitor;
import node.body.BodyOp;
import node.expr.Expr;
import node.vardecl.VarDecl;

import node.vardecl.VarInit;
import visitor.utils.TabellaDeiSimboli;

import java.util.ArrayList;

public class InitDoForStep extends ASTNode implements Stat {
    private ArrayList<VarDecl> initScope;      // Variabili di inizializzazione
    private BodyOp doBody;                    // Corpo del ciclo
    private Expr condition;                   // Condizione (opzionale)
    private ArrayList<Expr> stepExprs;        // Espressioni di step (opzionali)
    private TabellaDeiSimboli tabellaDeiSimboli;
    private Type type;

    public InitDoForStep(ArrayList<VarDecl> initVars, BodyOp doBody, Expr condition, ArrayList<Expr> stepExprs) {
        this.initScope = initVars;
        this.doBody = doBody;
        this.condition = condition;
        this.stepExprs = stepExprs;
    }

    public ArrayList<VarDecl> getInitScope() {
        return initScope;
    }

    public void setInitScope(ArrayList<VarDecl> initVars) {
        this.initScope = initVars;
    }

    public BodyOp getDoBody() {
        return doBody;
    }

    public void setDoBody(BodyOp doBody) {
        this.doBody = doBody;
    }

    public Expr getCondition() {
        return condition;
    }

    public void setCondition(Expr condition) {
        this.condition = condition;
    }

    public ArrayList<Expr> getStepExprs() {
        return stepExprs;
    }

    public void setStepExprs(ArrayList<Expr> stepExprs) {
        this.stepExprs = stepExprs;
    }

    public TabellaDeiSimboli getTabellaDeiSimboli() {
        return tabellaDeiSimboli;
    }

    public void setTabellaDeiSimboli(TabellaDeiSimboli tabellaDeiSimboli) {
        this.tabellaDeiSimboli = tabellaDeiSimboli;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}


package node.stat;

import node.ASTNode;
import node.Stat;
import node.Type;
import node.Visitor;
import node.body.BodyOp;
import node.expr.Expr;
import org.codehaus.plexus.util.FastMap;
import visitor.utils.TabellaDeiSimboli;

public class IfThenElse extends ASTNode implements Stat {

    private BodyOp elseStatement;
    private Expr espressione;
    private BodyOp ifthenStatement;
    private Type type;
    private TabellaDeiSimboli tabellaDeiSimboli;;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public TabellaDeiSimboli getTabellaDeiSimboli() {
        return tabellaDeiSimboli;
    }

    public void setTabellaDeiSimboli(TabellaDeiSimboli tabellaDeiSimboli) {
        this.tabellaDeiSimboli = tabellaDeiSimboli;
    }

    public IfThenElse(Expr espressione, BodyOp ifthenStatement, BodyOp elseStatement) {
        this.elseStatement = elseStatement;
        this.ifthenStatement = ifthenStatement;
        this.espressione = espressione;
    }

    public BodyOp getElseStatement() {
        return elseStatement;
    }

    public void setElseStatement(BodyOp elseStatement) {
        this.elseStatement = elseStatement;
    }

    public void setEspressione(Expr espressione) {
        this.espressione = espressione;
    }

    public BodyOp getIfthenStatement() {
        return ifthenStatement;
    }

    public void setIfthenStatement(BodyOp ifthenStatement) {
        this.ifthenStatement = ifthenStatement;
    }

    public Expr getEspressione() {
        return espressione;
    }



    @Override
    public String toString() {
        return "IfThenElse{" +
                "espressione=" + espressione +
                ", thenStatement=" + ifthenStatement +
                ", elseStatement=" + elseStatement +
                '}';
    }



    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}

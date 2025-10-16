package node.stat;

import node.ASTNode;
import node.Stat;
import node.Type;
import node.Visitor;
import node.body.BodyOp;
import node.expr.Expr;

import visitor.utils.TabellaDeiSimboli;

import java.util.ArrayList;

public class CascadeForOp extends ASTNode implements Stat  {

    private ArrayList<AssignOp> initExpr;
    private Expr condExpr;
    private ArrayList<AssignOp> updateExpr;
    private BodyOp body;
    private TabellaDeiSimboli tabellaDeiSimboli;
    private Type type;


    public CascadeForOp(ArrayList<AssignOp> initExpr, Expr condExpr, ArrayList<AssignOp> updateExpr, BodyOp body) {
        this.initExpr = initExpr;
        this.condExpr = condExpr;
        this.updateExpr = updateExpr;
        this.body = body;
    }

    public ArrayList<AssignOp> getInitExpr() {
        return initExpr;
    }

    public void setInitExpr(ArrayList<AssignOp> initExpr) {
        this.initExpr = initExpr;
    }

    public Expr getCondExpr() {
        return condExpr;
    }

    public void setCondExpr(Expr condExpr) {
        this.condExpr = condExpr;
    }

    public ArrayList<AssignOp> getUpdateExpr() {
        return updateExpr;
    }

    public void setUpdateExpr(ArrayList<AssignOp> updateExpr) {
        this.updateExpr = updateExpr;
    }

    public BodyOp getBody() {
        return body;
    }

    public void setBody(BodyOp body) {
        this.body = body;
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

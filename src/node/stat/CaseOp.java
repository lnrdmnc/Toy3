package node.stat;

import node.ASTNode;
import node.Stat;
import node.Visitor;
import node.expr.Expr;
import visitor.utils.TabellaDeiSimboli;

import java.util.ArrayList;

public class CaseOp extends ASTNode implements Stat {
    private TabellaDeiSimboli tabellaDeiSimboli;
    private ArrayList<Stat> statements;
    private Expr caseExpr;

    public CaseOp(Expr caseExpr, ArrayList<Stat> statements) {
        this.caseExpr = caseExpr;
        this.statements = statements;
    }

    public CaseOp(ArrayList<Stat> statements) {
        this.statements = statements;
    }



    public Expr getCaseExpr() {
        return caseExpr;
    }

    public void setCaseExpr(Expr caseExpr) {
        this.caseExpr = caseExpr;
    }

    public ArrayList<Stat> getStatements() {
        return statements;
    }

    public void setStatements(ArrayList<Stat> statements) {
        this.statements = statements;
    }


    public TabellaDeiSimboli getTabellaDeiSimboli() {
        return tabellaDeiSimboli;
    }

    public void setTabellaDeiSimboli(TabellaDeiSimboli tabellaDeiSimboli) {
        this.tabellaDeiSimboli = tabellaDeiSimboli;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}

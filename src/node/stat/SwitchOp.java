package node.stat;

import node.ASTNode;
import node.Stat;
import node.Type;
import node.Visitor;
import node.expr.Expr;
import visitor.utils.TabellaDeiSimboli;

import java.util.ArrayList;

public class SwitchOp extends ASTNode implements Stat {
    private Expr expr;
    private ArrayList<CaseOp> caseList; //sono più di un case
    private TabellaDeiSimboli tabellaDeiSimboli;
    private CaseOp defaultCase;


    public CaseOp getDefaultCase() {
        return defaultCase;
    }

    public void setDefaultCase(CaseOp defaultCase) {
        this.defaultCase = defaultCase;
    }

    private Type type;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public SwitchOp(Expr expr, ArrayList<CaseOp> caseList, CaseOp defaultCase) {
        this.expr = expr;
        this.caseList = caseList;
        this.defaultCase = defaultCase;
    }

    public TabellaDeiSimboli getTabellaDeiSimboli() {
        return tabellaDeiSimboli;
    }

    public void setTabellaDeiSimboli(TabellaDeiSimboli tabellaDeiSimboli) {
        this.tabellaDeiSimboli = tabellaDeiSimboli;
    }
    public Expr getExpr() {
        return expr;
    }

    public void setExpr(Expr expr) {
        this.expr = expr;
    }

    public ArrayList<CaseOp> getCaseList() {
        return caseList;
    }

    public void setCaseList(ArrayList<CaseOp> caseList) {
        this.caseList = caseList;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}

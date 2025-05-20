package node.expr.operation;

import node.ASTNode;
import node.Type;
import node.Visitor;
import node.expr.Expr;
import visitor.utils.TabellaDeiSimboli;

public class BinaryOp implements Expr  {

    private String operator; // Es. "+", "-", ">", "AND"
    private Expr left;       // Operando sinistro
    private Expr right;     // Operando destro

    private TabellaDeiSimboli tabellaDeiSimboli;
    private Type type;

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

    public BinaryOp(Object left, Object operator, Object right) {
        this.operator =(String) operator;
        this.left = (Expr) left;
        this.right = (Expr) right;
    }

    public String getOperator() {
        return operator;
    }

    public Expr getLeft() {
        return left;
    }

    public Expr getRight() {
        return right;
    }


    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
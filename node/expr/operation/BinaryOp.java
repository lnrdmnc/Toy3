package node.expr.operation;

import node.expr.Expr;

public class BinaryOp extends Expr {

    private final String operator; // Es. "+", "-", ">", "AND"
    private final Expr left;       // Operando sinistro
    private final Expr right;      // Operando destro

    public BinaryOp(String operator, Expr left, Expr right) {
        super("FunCall");
        this.operator = operator;
        this.left = left;
        this.right = right;
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

}
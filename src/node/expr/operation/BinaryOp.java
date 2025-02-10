package node.expr.operation;

import node.ASTNode;
import node.expr.Expr;

public class BinaryOp  extends ASTNode implements Expr  {

    private String operator; // Es. "+", "-", ">", "AND"
    private Expr left;       // Operando sinistro
    private Expr right;      // Operando destro

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
    public void accept(ASTNode v) {
        v.accept(this);
    }
}
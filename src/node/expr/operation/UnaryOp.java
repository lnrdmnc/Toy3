package node.expr.operation;

import node.ASTNode;
import node.expr.Expr;

public class UnaryOp extends ASTNode implements Expr  {

    private String operator; // Operatore unario (es: "-" o "not")
    private Expr operand;    // Operando su cui agisce l'operatore

    public UnaryOp(String operator, Expr operand) {
        this.operator = operator;
        this.operand = operand;
    }
    // Get per l'operatore
    public String getOperator() {
        return operator;
    }

    // Get per l'operando
    public Expr getOperand() {
        return operand;
    }

    @Override
    public void accept(ASTNode v) {
        v.accept(this);
    }
}

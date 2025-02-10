package node.expr.operation;

import node.expr.Expr;

public class UnaryOp extends Expr {

    private final String operator; // Operatore unario (es: "-" o "not")
    private final Expr operand;    // Operando su cui agisce l'operatore

    public UnaryOp(String operator, Expr operand) {
        super("UnaryOp"); // Nome per debugging
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
}

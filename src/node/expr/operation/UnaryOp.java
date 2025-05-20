package node.expr.operation;

import node.ASTNode;

import node.Type;
import node.Visitor;
import node.expr.Expr;
import visitor.utils.TabellaDeiSimboli;

public class UnaryOp implements Expr  {

    private TabellaDeiSimboli tabellaDeiSimboli;
    private String operator; // Operatore unario (es: "-" o "not")
    private Expr operand;    // Operando su cui agisce l'operatore child
    private Type type; // tipo

    public TabellaDeiSimboli getTabellaDeiSimboli() {
        return tabellaDeiSimboli;
    }

    public void setTabellaDeiSimboli(TabellaDeiSimboli tabellaDeiSimboli) {
        this.tabellaDeiSimboli = tabellaDeiSimboli;
    }

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
    public Type getType() {
        return type;
    }

    @Override
    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}

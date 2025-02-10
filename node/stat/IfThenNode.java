package node.stat;

import node.ASTNode;
import node.Stat;
import node.expr.Expr;

public class IfThenNode extends Stat {

    private Expr espressione;
    private ASTNode thenStatement;

    public IfThenNode(Expr espressione, ASTNode then) {
        this.thenStatement = then;
        this.espressione = espressione;
    }

    public Expr getEspressione() {
        return espressione;
    }

    public ASTNode getThenStatement() {
        return thenStatement;
    }

    @Override
    public String toString() {
        return "IfThenNode{" +
                "espressione=" + espressione +
                ", thenStatement=" + thenStatement +
                '}';
    }
}

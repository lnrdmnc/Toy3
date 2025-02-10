package node.stat;

import node.ASTNode;
import node.Stat;
import node.expr.Expr;

public class IfThenElse extends Stat {

    private ASTNode elseStatement;
    private Expr espressione;
    private ASTNode thenStatement;

    public IfThenElse(Expr espressione, ASTNode then, ASTNode elseStatement) {
        this.elseStatement = elseStatement;
        this.thenStatement = then;
        this.espressione = espressione;
    }

    public ASTNode getElseStatement() {
        return elseStatement;
    }

    public Expr getEspressione() {
        return espressione;
    }

    public ASTNode getThenStatement() {
        return thenStatement;
    }

    @Override
    public String toString() {
        return "IfThenElse{" +
                "elseStatement=" + elseStatement +
                ", espressione=" + espressione +
                ", thenStatement=" + thenStatement +
                '}';
    }
}

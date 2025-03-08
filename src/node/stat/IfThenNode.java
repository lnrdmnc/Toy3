package node.stat;

import node.ASTNode;
import node.Stat;
import node.Type;
import node.expr.Expr;
import visitor.utils.TabellaDeiSimboli;

public class IfThenNode extends ASTNode implements Stat {

    private Expr espressione;
    private ASTNode thenStatement;
    private TabellaDeiSimboli tabellaDeiSimboli;
    private Type type;


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

    @Override
    public void accept(ASTNode v) {
        v.accept(this);
    }
}

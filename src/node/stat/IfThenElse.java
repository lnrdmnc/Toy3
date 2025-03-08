package node.stat;

import node.ASTNode;
import node.Stat;
import node.Type;
import node.expr.Expr;
import org.codehaus.plexus.util.FastMap;
import visitor.utils.TabellaDeiSimboli;

public class IfThenElse extends ASTNode  implements Stat {

    private ASTNode elseStatement;
    private Expr espressione;
    private ASTNode thenStatement;
    private Type type;
    private TabellaDeiSimboli tabellaDeiSimboli;;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public TabellaDeiSimboli getTabellaDeiSimboli() {
        return tabellaDeiSimboli;
    }

    public void setTabellaDeiSimboli(TabellaDeiSimboli tabellaDeiSimboli) {
        this.tabellaDeiSimboli = tabellaDeiSimboli;
    }

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

    @Override
    public void accept(ASTNode v) {
        v.accept(this);
    }
}

package node.expr.constant;

import node.ASTNode;
import node.Type;
import node.expr.Expr;
import visitor.utils.TabellaDeiSimboli;

import java.lang.String;

public class DoubleNode extends ASTNode implements  Expr{

    private Object costant;
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

    public DoubleNode(Object value) {
        this.costant = (double) value;
    }

    public Object getCostant() {
        return costant;

    }

    @Override
    public String toString() {
        return "Double{" +
                "costant=" + costant +
                '}';
    }

    @Override
    public void accept(ASTNode v) {
        v.accept(this);
    }
}

package node.pardecl;

import node.ASTNode;
import node.Type;
import node.Visitor;
import node.expr.constant.Identifier;
import visitor.utils.TabellaDeiSimboli;

public class ParVar extends ASTNode {

    private boolean isReference; // se vera passo per riferimento.
    private Identifier id;

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

    public ParVar(boolean isReference, Identifier id) {
        this.isReference = isReference;
        this.id = id;
    }

    public boolean isReference() {
        return isReference;
    }

    public void setReference(boolean reference) {
        isReference = reference;
    }

    public Identifier getId() {
        return id;
    }

    public void setId(Identifier id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ParVar{" +
                "isReference=" + isReference +
                ", id=" + id +
                '}';
    }

    @Override
    public Object accept(Visitor visitor) {
       return visitor.visit(this);
    }
}

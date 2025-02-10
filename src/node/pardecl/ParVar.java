package node.pardecl;

import node.ASTNode;
import node.expr.constant.Identifier;

public class ParVar extends ASTNode {

    private boolean isReference; // se vera passo per riferimento.
    private Identifier id;

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
    public void accept(ASTNode v) {
        v.accept(this);
    }
}

package node.vardecl;

import node.ASTNode;
import node.Type;
import node.Visitor;
import visitor.utils.TabellaDeiSimboli;

public class ArrayType extends ASTNode {
    private Type type;
    private int dimension;
    private TabellaDeiSimboli tabellaDeiSimboli;


    public ArrayType(Type type, int dimension) {
        this.type = type;
        this.dimension = dimension;
    }

    public Type getType() {
        return type;
    }

    public int getDimension() {
        return dimension;
    }



    public void setType(Type type) {
        this.type = type;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public TabellaDeiSimboli getTabellaDeiSimboli() {
        return tabellaDeiSimboli;
    }

    public void setTabellaDeiSimboli(TabellaDeiSimboli tabellaDeiSimboli) {
        this.tabellaDeiSimboli = tabellaDeiSimboli;
    }

    @Override
    public Object accept(Visitor v) {
        return null;
    }
}

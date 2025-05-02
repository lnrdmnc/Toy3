package node.expr.constant;

import node.ASTNode;
import node.Type;
import node.expr.Expr;
import visitor.utils.TabellaDeiSimboli;

public class Identifier extends ASTNode implements  Expr {

    private String name;
    private TabellaDeiSimboli tabellaDeiSimboli;
    private Type type;
    private boolean ref;

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
    public Identifier(Object name) {
        this.name = (String) name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRef() {
        return ref;
    }

    public void setRef(boolean ref) {
        this.ref = ref;
    }

    @Override
    public String toString() {
        return "Identifier{" +
                "name=" + name +
                '}';
    }

    @Override
    public void accept(ASTNode v) {
        v.accept(this);
    }
}

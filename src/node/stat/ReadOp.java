package node.stat;

import node.ASTNode;
import node.Stat;
import node.Type;
import node.Visitor;
import node.expr.constant.Identifier;
import visitor.ScopeVisitor;
import visitor.utils.TabellaDeiSimboli;

import java.util.ArrayList;

public class ReadOp implements Stat {

    private ArrayList<Identifier> list;

    private Type type;
    private TabellaDeiSimboli tabellaDeiSimboli;

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

    public ReadOp(ArrayList<Identifier> list) {
        this.list = list;
    }

    public ReadOp() {
        this.list = new ArrayList<>();
    }

    public void addList(Identifier identifier) {
        this.list.add(identifier);
    }

    public ArrayList<Identifier> getList() {
        return list;
    }

    public void setList(ArrayList<Identifier> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "ReadOp{" +
                "list=" + list +
                '}';
    }

    @Override
    public Object accept(ScopeVisitor v) {
        return null;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}

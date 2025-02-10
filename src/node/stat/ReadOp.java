package node.stat;

import node.ASTNode;
import node.Stat;
import node.expr.constant.Identifier;

import java.util.ArrayList;

public class ReadOp extends ASTNode implements Stat {

    private ArrayList<Identifier> list;

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
    public void accept(ASTNode v) {
        v.accept(this);
    }
}

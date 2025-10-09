package node.stat;

import node.ASTNode;
import node.Stat;
import node.Type;
import node.Visitor;
import node.expr.Expr;
import node.expr.constant.Identifier;
import node.expr.operation.FunCall;
import visitor.utils.TabellaDeiSimboli;

import java.util.ArrayList;

public class MapOp extends ASTNode implements Expr{

    private String op; //ADD o MUL
    private ArrayList<FunCall> funCallArrayList;
    private TabellaDeiSimboli tabellaDeiSimboli;
    private Type type;



    public MapOp(String op, ArrayList<FunCall> funCallArrayList) {
        this.op = op;
        this.funCallArrayList = funCallArrayList;

    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public ArrayList<FunCall> getFunCallArrayList() {
        return funCallArrayList;
    }
    public void setFunCallArrayList(ArrayList<FunCall> funCallArrayList) {
        this.funCallArrayList = funCallArrayList;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

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
}

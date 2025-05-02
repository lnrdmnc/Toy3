package node.defdecl;

import node.ASTNode;
import node.Type;
import node.Visitor;
import node.body.BodyOp;
import node.expr.constant.Identifier;
import node.pardecl.ParDecl;
import visitor.utils.TabellaDeiSimboli;

import java.util.ArrayList;

public class DefDecl extends ASTNode implements Decl {

    private Identifier id;
    private Type type;
    private BodyOp body;
    private ArrayList<ParDecl> list;
    private TabellaDeiSimboli tabellaDeiSimboli;

    public TabellaDeiSimboli getTabellaDeiSimboli() {
        return tabellaDeiSimboli;
    }

    public void setTabellaDeiSimboli(TabellaDeiSimboli tabellaDeiSimboli) {
        this.tabellaDeiSimboli = tabellaDeiSimboli;
    }

    // Costruttore con lista di parametri
    // Funzione  con parametri
    public DefDecl(Object list,Object id, Object type, Object body ) {
        this.list = (ArrayList<ParDecl>) list;
        this.id = (Identifier) id;
        this.type = (Type) type;
        this.body = (BodyOp) body;

    }

    // Funzione senza parametri
    // Costruttore senza parametri
    public DefDecl(Object id, Object type, Object body){
        this.id=(Identifier) id;
        this.type= (Type) type;
        this.body=(BodyOp) body;
    }

    public Identifier getId() {
        return id;
    }

    public void setId(Identifier id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public BodyOp getBody() {
        return body;
    }

    public void setBody(BodyOp body) {
        this.body = body;
    }

    public ArrayList<ParDecl> getList() {
        return list;
    }

    public void setList(ArrayList<ParDecl> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "DefDecl{" +
                "id=" + id +
                ", type=" + type +
                ", body=" + body +
                ", list=" + list +
                '}';
    }

    @Override
    public void accept(ASTNode v) {
        v.accept(this);
    }

    @Override
    public Object accept(Visitor v) {
        return v.visitDefDecl(this);
    }
}

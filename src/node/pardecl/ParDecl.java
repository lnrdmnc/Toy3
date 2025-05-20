package node.pardecl;
import node.vardecl.VarDecl;
import node.Type;
import node.*;
import visitor.utils.TabellaDeiSimboli;

import java.util.List;

public class ParDecl extends ASTNode {
    private Type type;           // Tipo del parametro (int, bool, string, ecc.)
    private List<ParVar> variables;// Lista di nomi di parametri

    private TabellaDeiSimboli tabellaDeiSimboli;

    public void setType(Type type) {
        this.type = type;
    }

    public void setVariables(List<ParVar> variables) {
        this.variables = variables;
    }

    public TabellaDeiSimboli getTabellaDeiSimboli() {
        return tabellaDeiSimboli;
    }

    public void setTabellaDeiSimboli(TabellaDeiSimboli tabellaDeiSimboli) {
        this.tabellaDeiSimboli = tabellaDeiSimboli;
    }

    public ParDecl(Type type, List<ParVar> variables) {
        this.type = type;
        this.variables = variables;
    }

    // Getter per il tipo
    public Type getType() {
        return type;
    }

    // Getter per la lista di variabili
    public List<ParVar> getVariables() {
        return variables;
    }


    @Override
    public String toString() {
        return "ParDecl{" +
                "type=" + type +
                ", variables=" + variables +
                '}';
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}

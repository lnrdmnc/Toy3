package node.pardecl;
import node.vardecl.VarDecl;
import node.Type;
import node.*;
import java.util.List;

public class ParDecl extends ASTNode {
    private Type type;           // Tipo del parametro (int, bool, string, ecc.)
    private List<ParVar> variables;    // Lista di nomi di parametri

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
    public void accept(ASTNode v) {
        v.accept(this);
    }
}

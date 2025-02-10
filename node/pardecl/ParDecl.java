package node.pardecl;
import node.vardecl.VarDecl;
import node.Type;
import node.*;
import java.util.List;

public class ParDecl extends ASTNode {
    private Type type;           // Tipo del parametro (int, bool, string, ecc.)
    private List<ParVar> variables;    // Lista di nomi di parametri
    private  boolean isReference; // Indica se il parametro è passato per riferimento (OUT)

    public ParDecl(Type type, List<ParVar> variables, boolean isReference) {
        super("ParDecl");
        this.type = type;
        this.variables = variables;
        this.isReference = isReference;
    }

    // Getter per il tipo
    public Type getType() {
        return type;
    }

    // Getter per la lista di variabili
    public List<ParVar> getVariables() {
        return variables;
    }

    // Getter per il flag di riferimento
    public boolean isReference() {
        return isReference;
    }


    @Override
    public String toString() {
        return "ParDecl{" +
                "type=" + type +
                ", variables=" + variables +
                ", isReference=" + isReference +
                '}';
    }
}

package node.vardecl;
import java.lang.reflect.Type;
import java.util.List;
import node.ASTNode;
import node.Decl;

public class VarDecl extends ASTNode {

    private final List<VarInit> variables; // Lista di variabili (es. "x | y = 5")
    private final Type type;     // Tipo (es. "int", "bool")

    public VarDecl(List<VarInit> variables, Type type) {
        super("VarDecl");
        this.variables = variables;
        this.type = type;
    }

    // Getter
    public List<VarInit> getVariables() { return variables; }
    public Type getType() { return type; }

}


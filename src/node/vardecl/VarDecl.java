package node.vardecl;
import java.util.ArrayList;
import java.util.List;
import node.ASTNode;
import node.Type;
import node.defdecl.Decl;
import node.expr.Expr;

public class VarDecl extends ASTNode implements Decl {


    private ArrayList<VarInit> variables; // Lista di variabili (es. "x | y = 5")
    private Type type;     // Tipo (es. "int", "bool")
    private Expr costant;

    public VarDecl(Object variables, Object typeOrConstant) {
        this.variables = (ArrayList<VarInit>) variables;
        if (typeOrConstant instanceof Type) {
        this.type = (Type) typeOrConstant;
        } else if (typeOrConstant instanceof Expr) {
        this.costant = (Expr) typeOrConstant;
        }else {
            throw new IllegalArgumentException("Invalid type for typeOrConstant: " + typeOrConstant.getClass().getName());
        }
    }

    // Getter
    public List<VarInit> getVariables() { return variables; }
    public Type getType() { return type; }
    public Expr getCostant() {
        return costant;
    }

    @Override
    public void accept(ASTNode v) {
        v.accept(this);
    }
}


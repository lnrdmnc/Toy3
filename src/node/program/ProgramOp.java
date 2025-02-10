package node.program;

import node.ASTNode;
import node.defdecl.Decl;
import node.Stat;
import node.vardecl.VarDecl;

import java.util.List;

public class ProgramOp extends ASTNode {
    private List<Decl> declarations;  // Corrisponde a "Decls"
    private List<VarDecl> varDeclarations; // Corrisponde a "VarDecls"
    private List<Stat> statements; // Corrisponde a "Statements"

    public ProgramOp(List<Decl> declarations, List<VarDecl> varDeclarations, List<Stat> statements) {
        this.declarations = declarations;
        this.varDeclarations = varDeclarations;
        this.statements = statements;
    }

    // Getter methods
    public List<Decl> getDeclarations() { return declarations; }
    public List<VarDecl> getVarDeclarations() { return varDeclarations; }
    public List<Stat> getStatements() { return statements; }


    @Override
    public void accept(ASTNode v) {
        v.accept(this);
    }
}
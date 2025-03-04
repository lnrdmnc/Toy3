package node.program;

import node.ASTNode;
import node.Type;
import node.Visitor;
import node.defdecl.Decl;
import node.Stat;
import node.vardecl.VarDecl;
import visitor.utils.TabellaDeiSimboli;

import java.util.ArrayList;
import java.util.List;

public class ProgramOp extends ASTNode {

    private TabellaDeiSimboli tabellaDeiSimboliProgram;
    private TabellaDeiSimboli tabellaBegEnd;
    private Type type;

    public TabellaDeiSimboli getTabellaDeiSimboliProgram() {
        return tabellaDeiSimboliProgram;
    }

    public void setTabellaDeiSimboliProgram(TabellaDeiSimboli tabellaDeiSimboliProgram) {
        this.tabellaDeiSimboliProgram = tabellaDeiSimboliProgram;
    }

    public TabellaDeiSimboli getTabellaBegEnd() {
        return tabellaBegEnd;
    }

    public void setTabellaBegEnd(TabellaDeiSimboli tabellaBegEnd) {
        this.tabellaBegEnd = tabellaBegEnd;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setDeclarations(ArrayList<Decl> declarations) {
        this.declarations = declarations;
    }

    public void setVarDeclarations(ArrayList<VarDecl> varDeclarations) {
        this.varDeclarations = varDeclarations;
    }

    public void setStatements(ArrayList<Stat> statements) {
        this.statements = statements;
    }

    private ArrayList<Decl> declarations;  // Corrisponde a "Decls" o
    private ArrayList<VarDecl> varDeclarations; // Corrisponde a "VarDecls" i
    private ArrayList<Stat> statements; // Corrisponde a "Statements"

    public ProgramOp(ArrayList<Decl> declarations, ArrayList<VarDecl> varDeclarations, ArrayList<Stat> statements) {
        this.declarations = declarations;
        this.varDeclarations = varDeclarations;
        this.statements = statements;
    }


    public ArrayList<Decl> getDeclarations() { return declarations; }
    public ArrayList<VarDecl> getVarDeclarations() { return varDeclarations; }
    public ArrayList<Stat> getStatements() { return statements; }

    @Override
    public String toString() {
        return "ProgramOp{" +
                "declarations=" + declarations +
                ", varDeclarations=" + varDeclarations +
                ", statements=" + statements +
                '}';
    }

    @Override
    public Object accept(Visitor v) {
       return  v.visitProgramOp(this);
    }
}
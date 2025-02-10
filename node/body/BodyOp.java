package node.body;

import node.ASTNode;
import node.Stat;
import node.vardecl.VarDecl;

import java.util.ArrayList;

public class BodyOp extends ASTNode {

    private ArrayList<VarDecl> dichiarazioni;
    private ArrayList<Stat> statements;

    public BodyOp(ArrayList<VarDecl> dichiarazioni, ArrayList<Stat> statements) {
        super("BodyOp");
        this.dichiarazioni = dichiarazioni;
        this.statements = statements;
    }

    public ArrayList<Stat> getStatements() {
        return statements;
    }

    public void setStatements(ArrayList<Stat> statements) {
        this.statements = statements;
    }

    public ArrayList<VarDecl> getDichiarazioni() {
        return dichiarazioni;
    }

    public void setDichiarazioni(ArrayList<VarDecl> dichiarazioni) {
        this.dichiarazioni = dichiarazioni;
    }

    public void aggiungiStatement(Stat statement) {
        if (statement != null) {
            statements.add(statement);
        }
    }

    public void aggiungiDichiarazione(VarDecl dichiarazione){
        if(dichiarazione != null){
            dichiarazioni.add(dichiarazione);
        }
    }

}

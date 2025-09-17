package node.program;

import node.ASTNode;
import node.Type;
import node.Visitor;
import node.defdecl.Decl;
import node.Stat;
import node.vardecl.VarDecl;
import visitor.utils.TabellaDeiSimboli;

import java.util.ArrayList;

public class ProgramOp extends ASTNode {

    // Tabella dei simboli associata al programma
    private TabellaDeiSimboli tabellaDeiSimboliProgram;

    // Tabella dei simboli per il blocco Begin-End
    private TabellaDeiSimboli tabellaBegEnd;

    // Tipo del nodo
    private Type type;

    // Dichiarazioni esterne (Decls)
    private ArrayList<Decl> declarations;

    // Dichiarazioni interne (VarDecls)
    private ArrayList<VarDecl> varDeclarations;

    // Istruzioni (Statements)
    private ArrayList<Stat> statements;

    /**
     * Costruttore della classe ProgramOp.
     * Inizializza dichiarazioni, dichiarazioni interne e istruzioni.
     *
     * @param declarations Dichiarazioni esterne
     * @param varDeclarations Dichiarazioni interne
     * @param statements Istruzioni
     */
    public ProgramOp(ArrayList<Decl> declarations, ArrayList<VarDecl> varDeclarations, ArrayList<Stat> statements) {
        this.declarations = declarations;
        this.varDeclarations = varDeclarations;
        this.statements = statements;
    }

    /**
     * Restituisce la tabella dei simboli associata al programma.
     * @return Tabella dei simboli
     */
    public TabellaDeiSimboli getTabellaDeiSimboliProgram() {
        return tabellaDeiSimboliProgram;
    }

    /**
     * Imposta la tabella dei simboli per il programma.
     * @param tabellaDeiSimboliProgram Tabella dei simboli da associare
     */
    public void setTabellaDeiSimboliProgram(TabellaDeiSimboli tabellaDeiSimboliProgram) {
        this.tabellaDeiSimboliProgram = tabellaDeiSimboliProgram;
    }

    /**
     * Restituisce la tabella dei simboli per il blocco Begin-End.
     * @return Tabella dei simboli
     */
    public TabellaDeiSimboli getTabellaBegEnd() {
        return tabellaBegEnd;
    }

    /**
     * Imposta la tabella dei simboli per il blocco Begin-End.
     * @param tabellaBegEnd Tabella dei simboli da associare
     */
    public void setTabellaBegEnd(TabellaDeiSimboli tabellaBegEnd) {
        this.tabellaBegEnd = tabellaBegEnd;
    }

    /**
     * Restituisce il tipo del nodo.
     * @return Tipo del nodo
     */
    public Type getType() {
        return type;
    }

    /**
     * Imposta il tipo del nodo.
     * @param type Tipo da associare
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * Restituisce le dichiarazioni esterne.
     * @return Dichiarazioni esterne
     */
    public ArrayList<Decl> getDeclarations() {
        return declarations;
    }

    /**
     * Imposta le dichiarazioni esterne.
     * @param declarations Dichiarazioni esterne da associare
     */
    public void setDeclarations(ArrayList<Decl> declarations) {
        this.declarations = declarations;
    }

    /**
     * Restituisce le dichiarazioni interne.
     * @return Dichiarazioni interne
     */
    public ArrayList<VarDecl> getVarDeclarations() {
        return varDeclarations;
    }

    /**
     * Imposta le dichiarazioni interne.
     * @param varDeclarations Dichiarazioni interne da associare
     */
    public void setVarDeclarations(ArrayList<VarDecl> varDeclarations) {
        this.varDeclarations = varDeclarations;
    }

    /**
     * Restituisce le istruzioni.
     * @return Istruzioni
     */
    public ArrayList<Stat> getStatements() {
        return statements;
    }

    /**
     * Imposta le istruzioni.
     * @param statements Istruzioni da associare
     */
    public void setStatements(ArrayList<Stat> statements) {
        this.statements = statements;
    }

    /**
     * Restituisce una rappresentazione in stringa dell'oggetto ProgramOp.
     * @return Rappresentazione in stringa di ProgramOp
     */
    @Override
    public String toString() {
        return "ProgramOp{" +
                "declarations=" + declarations +
                ", varDeclarations=" + varDeclarations +
                ", statements=" + statements +
                '}';
    }

    /**
     * Metodo di accettazione per il Visitor.
     * Permette di applicare un'operazione definita da un Visitor alla classe ProgramOp.
     * @param v Visitor da applicare
     * @return Risultato dell'operazione del Visitor
     */
    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
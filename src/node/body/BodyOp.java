package node.body;

import node.ASTNode;
import node.Stat;
import node.Type;
import node.Visitor;
import node.vardecl.VarDecl;
import visitor.utils.TabellaDeiSimboli;

import java.util.ArrayList;

/**
 * La classe BodyOp rappresenta il corpo di un'operazione o di una struttura
 * che contiene dichiarazioni di variabili e istruzioni (statements).
 * Estende la classe ASTNode, quindi fa parte dell'albero sintattico astratto (AST).
 */
public class BodyOp extends ASTNode {

    // Lista delle dichiarazioni di variabili presenti nel corpo
    private ArrayList<VarDecl> dichiarazioni;

    // Lista delle istruzioni (statements) presenti nel corpo
    private ArrayList<Stat> statements;

    // Tabella dei simboli associata al corpo, utile per la gestione degli scope
    private TabellaDeiSimboli tabellaDeiSimboli;

    // Tipo associato al corpo, se necessario
    private Type type;

    /**
     * Restituisce la tabella dei simboli associata al corpo.
     * @return TabellaDeiSimboli
     */
    public TabellaDeiSimboli getTabellaDeiSimboli() {
        return tabellaDeiSimboli;
    }

    /**
     * Imposta la tabella dei simboli per il corpo.
     * @param tabellaDeiSimboli Tabella dei simboli da associare
     */
    public void setTabellaDeiSimboli(TabellaDeiSimboli tabellaDeiSimboli) {
        this.tabellaDeiSimboli = tabellaDeiSimboli;
    }

    /**
     * Restituisce il tipo associato al corpo.
     * @return Tipo del corpo
     */
    public Type getType() {
        return type;
    }

    /**
     * Imposta il tipo associato al corpo.
     * @param type Tipo da associare
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * Costruttore della classe BodyOp.
     * Inizializza le liste di dichiarazioni e istruzioni.
     * @param dichiarazioni Lista di dichiarazioni di variabili
     * @param statements Lista di istruzioni
     */
    public BodyOp(ArrayList<VarDecl> dichiarazioni, ArrayList<Stat> statements) {
        this.dichiarazioni = dichiarazioni;
        this.statements = statements;
    }

    /**
     * Restituisce la lista delle istruzioni presenti nel corpo.
     * @return Lista di Stat
     */
    public ArrayList<Stat> getStatements() {
        return statements;
    }

    /**
     * Imposta la lista delle istruzioni per il corpo.
     * @param statements Lista di istruzioni da associare
     */
    public void setStatements(ArrayList<Stat> statements) {
        this.statements = statements;
    }

    /**
     * Restituisce la lista delle dichiarazioni di variabili presenti nel corpo.
     * @return Lista di VarDecl
     */
    public ArrayList<VarDecl> getDichiarazioni() {
        return dichiarazioni;
    }

    /**
     * Imposta la lista delle dichiarazioni di variabili per il corpo.
     * @param dichiarazioni Lista di dichiarazioni da associare
     */
    public void setDichiarazioni(ArrayList<VarDecl> dichiarazioni) {
        this.dichiarazioni = dichiarazioni;
    }

    /**
     * Aggiunge un'istruzione alla lista delle istruzioni del corpo.
     * @param statement Istruzione da aggiungere
     */
    public void aggiungiStatement(Stat statement) {
        if (statement != null) {
            statements.add(statement);
        }
    }

    /**
     * Aggiunge una dichiarazione alla lista delle dichiarazioni del corpo.
     * @param dichiarazione Dichiarazione da aggiungere
     */
    public void aggiungiDichiarazione(VarDecl dichiarazione){
        if(dichiarazione != null){
            dichiarazioni.add(dichiarazione);
        }
    }

    /**
     * Metodo di accettazione per il Visitor.
     * Permette di applicare un'operazione definita da un Visitor alla classe BodyOp.
     * @param v Visitor da applicare
     * @return Risultato dell'operazione del Visitor
     */
    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
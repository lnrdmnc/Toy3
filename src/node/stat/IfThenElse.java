package node.stat;

import node.ASTNode;
import node.Stat;
import node.Type;
import node.Visitor;
import node.body.BodyOp;
import node.expr.Expr;
import visitor.utils.TabellaDeiSimboli;

/**
 * La classe IfThenElse rappresenta un'istruzione condizionale nell'AST.
 * Gestisce un'espressione condizionale, un blocco "then" e un blocco "else" opzionale.
 */
public class IfThenElse extends ASTNode implements Stat {

    // Blocco "else" (opzionale)
    private BodyOp elseStatement;

    // Espressione condizionale
    private Expr espressione;

    // Blocco "then"
    private BodyOp ifthenStatement;

    // Tipo del nodo
    private Type type;

    // Tabella dei simboli associata all'istruzione
    private TabellaDeiSimboli tabellaDeiSimboli;

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
     * Restituisce la tabella dei simboli associata all'istruzione.
     * @return Tabella dei simboli
     */
    public TabellaDeiSimboli getTabellaDeiSimboli() {
        return tabellaDeiSimboli;
    }

    /**
     * Imposta la tabella dei simboli per l'istruzione.
     * @param tabellaDeiSimboli Tabella dei simboli da associare
     */
    public void setTabellaDeiSimboli(TabellaDeiSimboli tabellaDeiSimboli) {
        this.tabellaDeiSimboli = tabellaDeiSimboli;
    }

    /**
     * Costruttore della classe IfThenElse.
     * Inizializza l'espressione condizionale, il blocco "then" e il blocco "else".
     *
     * @param espressione Espressione condizionale
     * @param ifthenStatement Blocco "then"
     * @param elseStatement Blocco "else" (opzionale)
     */
    public IfThenElse(Expr espressione, BodyOp ifthenStatement, BodyOp elseStatement) {
        this.elseStatement = elseStatement;
        this.ifthenStatement = ifthenStatement;
        this.espressione = espressione;
    }

    /**
     * Restituisce il blocco "else".
     * @return Blocco "else"
     */
    public BodyOp getElseStatement() {
        return elseStatement;
    }

    /**
     * Imposta il blocco "else".
     * @param elseStatement Blocco "else" da associare
     */
    public void setElseStatement(BodyOp elseStatement) {
        this.elseStatement = elseStatement;
    }

    /**
     * Imposta l'espressione condizionale.
     * @param espressione Espressione condizionale da associare
     */
    public void setEspressione(Expr espressione) {
        this.espressione = espressione;
    }

    /**
     * Restituisce il blocco "then".
     * @return Blocco "then"
     */
    public BodyOp getIfthenStatement() {
        return ifthenStatement;
    }

    /**
     * Imposta il blocco "then".
     * @param ifthenStatement Blocco "then" da associare
     */
    public void setIfthenStatement(BodyOp ifthenStatement) {
        this.ifthenStatement = ifthenStatement;
    }

    /**
     * Restituisce l'espressione condizionale.
     * @return Espressione condizionale
     */
    public Expr getEspressione() {
        return espressione;
    }

    /**
     * Restituisce una rappresentazione in stringa dell'oggetto IfThenElse.
     * @return Rappresentazione in stringa di IfThenElse
     */
    @Override
    public String toString() {
        return "IfThenElse{" +
                "espressione=" + espressione +
                ", thenStatement=" + ifthenStatement +
                ", elseStatement=" + elseStatement +
                '}';
    }

    /**
     * Metodo di accettazione per il Visitor.
     * Permette di applicare un'operazione definita da un Visitor alla classe IfThenElse.
     * @param v Visitor da applicare
     * @return Risultato dell'operazione del Visitor
     */
    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
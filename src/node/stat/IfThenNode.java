package node.stat;

import node.ASTNode;
import node.Stat;
import node.Type;
import node.Visitor;
import node.body.BodyOp;
import node.expr.Expr;
import visitor.utils.TabellaDeiSimboli;

/**
 * La classe IfThenNode rappresenta un'istruzione condizionale "if-then" nell'AST.
 * Gestisce un'espressione condizionale e un blocco "then".
 */
public class IfThenNode extends ASTNode implements Stat {

    // Espressione condizionale
    private Expr espressione;

    // Tabella dei simboli associata all'istruzione
    private TabellaDeiSimboli tabellaDeiSimboli;

    // Blocco "then"
    private BodyOp body;

    // Tipo del nodo
    private Type type;

    /**
     * Costruttore della classe IfThenNode.
     * Inizializza l'espressione condizionale e il blocco "then".
     *
     * @param espressione Espressione condizionale
     * @param body Blocco "then"
     */
    public IfThenNode(Expr espressione, BodyOp body) {
        this.espressione = espressione;
        this.body = body;
    }

    /**
     * Restituisce l'espressione condizionale.
     * @return Espressione condizionale
     */
    public Expr getEspressione() {
        return espressione;
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
    public BodyOp getBody() {
        return body;
    }

    /**
     * Imposta il blocco "then".
     * @param body Blocco "then" da associare
     */
    public void setBody(BodyOp body) {
        this.body = body;
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
     * Restituisce una rappresentazione in stringa dell'oggetto IfThenNode.
     * @return Rappresentazione in stringa di IfThenNode
     */
    @Override
    public String toString() {
        return "IfThenNode{" +
                "espressione=" + espressione +
                ", body=" + body +
                '}';
    }

    /**
     * Metodo di accettazione per il Visitor.
     * Permette di applicare un'operazione definita da un Visitor alla classe IfThenNode.
     * @param v Visitor da applicare
     * @return Risultato dell'operazione del Visitor
     */
    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
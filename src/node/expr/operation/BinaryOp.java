package node.expr.operation;

import node.Type;
import node.Visitor;
import node.expr.Expr;
import visitor.utils.TabellaDeiSimboli;

/**
 * La classe BinaryOp rappresenta un'operazione binaria nell'AST.
 * Contiene un operatore e due operandi (sinistro e destro).
 */
public class BinaryOp implements Expr {

    // Operatore dell'operazione binaria (es. "+", "-", "AND")
    private String operator;

    // Operando sinistro
    private Expr left;

    // Operando destro
    private Expr right;

    // Tabella dei simboli associata al nodo
    private TabellaDeiSimboli tabellaDeiSimboli;

    // Tipo del nodo
    private Type type;

    /**
     * Costruttore della classe BinaryOp.
     * Inizializza l'operatore e gli operandi.
     *
     * @param left Operando sinistro
     * @param operator Operatore
     * @param right Operando destro
     */
    public BinaryOp(Object left, Object operator, Object right) {
        this.operator = (String) operator;
        this.left = (Expr) left;
        this.right = (Expr) right;
    }

    /**
     * Restituisce la tabella dei simboli associata al nodo.
     * @return TabellaDeiSimboli
     */
    public TabellaDeiSimboli getTabellaDeiSimboli() {
        return tabellaDeiSimboli;
    }

    /**
     * Imposta la tabella dei simboli per il nodo.
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
     * Restituisce l'operatore dell'operazione binaria.
     * @return Operatore
     */
    public String getOperator() {
        return operator;
    }

    /**
     * Restituisce l'operando sinistro.
     * @return Operando sinistro
     */
    public Expr getLeft() {
        return left;
    }

    /**
     * Restituisce l'operando destro.
     * @return Operando destro
     */
    public Expr getRight() {
        return right;
    }

    /**
     * Metodo di accettazione per il Visitor.
     * Permette di applicare un'operazione definita da un Visitor alla classe BinaryOp.
     * @param visitor Visitor da applicare
     * @return Risultato dell'operazione del Visitor
     */
    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
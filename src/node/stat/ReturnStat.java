package node.stat;

import node.ASTNode;
import node.Stat;
import node.Type;
import node.Visitor;
import node.expr.Expr;
import visitor.utils.TabellaDeiSimboli;

/**
 * La classe ReturnStat rappresenta un'istruzione di ritorno nell'AST.
 * Gestisce un'espressione opzionale da restituire e la tabella dei simboli associata.
 */
public class ReturnStat extends ASTNode implements Stat {

    // Tabella dei simboli associata all'istruzione
    private TabellaDeiSimboli tabella;

    // Espressione opzionale da restituire
    private Expr expr;

    // Tipo del nodo
    private Type type;

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
    public TabellaDeiSimboli getTabella() {
        return tabella;
    }

    /**
     * Imposta la tabella dei simboli per l'istruzione.
     * @param tabella Tabella dei simboli da associare
     */
    public void setTabella(TabellaDeiSimboli tabella) {
        this.tabella = tabella;
    }

    /**
     * Restituisce l'espressione opzionale da restituire.
     * @return Espressione opzionale
     */
    public Expr getExpr() {
        return expr;
    }

    /**
     * Imposta l'espressione opzionale da restituire.
     * @param expr Espressione da associare
     */
    public void setExpr(Expr expr) {
        this.expr = expr;
    }

    /**
     * Costruttore della classe ReturnStat.
     * Inizializza l'espressione opzionale da restituire.
     *
     * @param object Espressione opzionale
     */
    public ReturnStat(Object object) {
        this.expr = (Expr) object;
    }

    /**
     * Restituisce una rappresentazione in stringa dell'oggetto ReturnStat.
     * @return Rappresentazione in stringa di ReturnStat
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "expr='" + expr + '\'' +
                '}';
    }

    /**
     * Metodo di accettazione per il Visitor.
     * Permette di applicare un'operazione definita da un Visitor alla classe ReturnStat.
     * @param v Visitor da applicare
     * @return Risultato dell'operazione del Visitor
     */
    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
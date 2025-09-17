package node.stat;

import node.ASTNode;
import node.Stat;
import node.Type;
import node.Visitor;
import node.body.BodyOp;
import node.expr.Expr;
import visitor.utils.TabellaDeiSimboli;

/**
 * La classe WhileOp rappresenta un'istruzione di ciclo "while" nell'AST.
 * Gestisce un'espressione condizionale e un corpo di istruzioni da eseguire iterativamente.
 */
public class WhileOp extends ASTNode implements Stat {

    // Tipo del nodo
    private Type type;

    // Tabella dei simboli associata al ciclo
    private TabellaDeiSimboli tabellaDeiSimboli;

    // Espressione condizionale del ciclo
    private Expr espr;

    // Corpo del ciclo
    private BodyOp body;

    /**
     * Costruttore della classe WhileOp.
     * Inizializza l'espressione condizionale e il corpo del ciclo.
     *
     * @param espr Espressione condizionale
     * @param body Corpo del ciclo
     */
    public WhileOp(Object espr, Object body) {
        this.espr = (Expr) espr;
        this.body = (BodyOp) body;
    }

    /**
     * Restituisce l'espressione condizionale del ciclo.
     * @return Espressione condizionale
     */
    public Expr getEspr() {
        return espr;
    }

    /**
     * Restituisce il corpo del ciclo.
     * @return Corpo del ciclo
     */
    public BodyOp getBody() {
        return body;
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
     * Restituisce la tabella dei simboli associata al ciclo.
     * @return Tabella dei simboli
     */
    public TabellaDeiSimboli getTabellaDeiSimboli() {
        return tabellaDeiSimboli;
    }

    /**
     * Imposta la tabella dei simboli per il ciclo.
     * @param tabellaDeiSimboli Tabella dei simboli da associare
     */
    public void setTabellaDeiSimboli(TabellaDeiSimboli tabellaDeiSimboli) {
        this.tabellaDeiSimboli = tabellaDeiSimboli;
    }

    /**
     * Metodo di accettazione per il Visitor.
     * Permette di applicare un'operazione definita da un Visitor alla classe WhileOp.
     * @param v Visitor da applicare
     * @return Risultato dell'operazione del Visitor
     */
    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
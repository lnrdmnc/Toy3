package node.expr.operation;

import node.Type;
import node.Visitor;
import node.expr.Expr;
import visitor.utils.TabellaDeiSimboli;

/**
 * La classe UnaryOp rappresenta un'operazione unaria nell'AST.
 * Contiene un operatore unario e un singolo operando.
 */
public class UnaryOp implements Expr {

    // Tabella dei simboli associata al nodo
    private TabellaDeiSimboli tabellaDeiSimboli;

    // Operatore unario (es: "-", "not")
    private String operator;

    // Operando su cui agisce l'operatore
    private Expr operand;

    // Tipo del nodo
    private Type type;

    /**
     * Costruttore della classe UnaryOp.
     * Inizializza l'operatore e l'operando.
     *
     * @param operator Operatore unario
     * @param operand Operando
     */
    public UnaryOp(String operator, Expr operand) {
        this.operator = operator;
        this.operand = operand;
    }

    /**
     * Restituisce la tabella dei simboli associata al nodo.
     * @return Tabella dei simboli
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
     * Restituisce l'operatore unario.
     * @return Operatore
     */
    public String getOperator() {
        return operator;
    }

    /**
     * Restituisce l'operando.
     * @return Operando
     */
    public Expr getOperand() {
        return operand;
    }

    /**
     * Restituisce il tipo del nodo.
     * @return Tipo del nodo
     */
    @Override
    public Type getType() {
        return type;
    }

    /**
     * Imposta il tipo del nodo.
     * @param type Tipo da associare
     */
    @Override
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * Metodo di accettazione per il Visitor.
     * Permette di applicare un'operazione definita da un Visitor alla classe UnaryOp.
     * @param visitor Visitor da applicare
     * @return Risultato dell'operazione del Visitor
     */
    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
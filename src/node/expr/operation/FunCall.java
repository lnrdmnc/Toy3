package node.expr.operation;

import node.ASTNode;
import node.Stat;
import node.Type;
import node.Visitor;
import node.expr.Expr;
import node.expr.constant.Identifier;
import visitor.utils.TabellaDeiSimboli;

import java.util.List;

/**
 * La classe FunCall rappresenta una chiamata a funzione nell'AST.
 * Contiene il nome della funzione, gli argomenti e altre proprietà rilevanti.
 */
public class FunCall extends ASTNode implements Expr, Stat {

    // Nome della funzione
    private String functionName;

    // Argomenti della funzione
    private List<Expr> arguments;

    // Identificatore della funzione
    private Identifier id;

    // Tabella dei simboli associata al nodo
    private TabellaDeiSimboli tabellaDeiSimboli;

    // Tipo del nodo
    private Type type;

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
     * Metodo di accettazione per il Visitor.
     * Permette di applicare un'operazione definita da un Visitor alla classe FunCall.
     * @param visitor Visitor da applicare
     * @return Risultato dell'operazione del Visitor
     */
    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
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
     * Costruttore della classe FunCall con identificatore e argomenti.
     * @param id Identificatore della funzione
     * @param arguments Argomenti della funzione
     */
    public FunCall(Object id, Object arguments) {
        this.id = (Identifier) id;
        this.arguments = (List<Expr>) arguments;
    }

    /**
     * Costruttore della classe FunCall con solo identificatore.
     * @param id Identificatore della funzione
     */
    public FunCall(Object id) {
        this.id = (Identifier) id;
    }

    /**
     * Costruttore della classe FunCall con identificatore di tipo Identifier.
     * @param id Identificatore della funzione
     */
    public FunCall(Identifier id) {
        this.id = id;
    }

    /**
     * Restituisce il nome della funzione.
     * @return Nome della funzione
     */
    public String getFunctionName() {
        return functionName;
    }

    /**
     * Restituisce gli argomenti della funzione.
     * @return Argomenti della funzione
     */
    public List<Expr> getArguments() {
        return arguments;
    }

    /**
     * Restituisce l'identificatore della funzione.
     * @return Identificatore della funzione
     */
    public Identifier getId() {
        return id;
    }
}
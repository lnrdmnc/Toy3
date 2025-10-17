package node.vardecl;

import java.util.ArrayList;
import java.util.List;
import node.ASTNode;
import node.Type;
import node.Visitor;
import node.defdecl.Decl;
import node.expr.Expr;
import visitor.utils.TabellaDeiSimboli;

/**
 * La classe VarDecl rappresenta una dichiarazione di variabili nell'AST.
 * Gestisce una lista di variabili, un tipo o una costante, e la tabella dei simboli associata.
 */
public class VarDecl extends ASTNode implements Decl {

    private ArrayType arrayInfo;
    // Lista di variabili (es. "x | y = 5")
    private ArrayList<VarInit> variables;

    // Espressione costante associata alla dichiarazione
    private Expr costant;

    // Tabella dei simboli associata alla dichiarazione
    private TabellaDeiSimboli tabellaDeiSimboli;

    // Tipo delle variabili (es. "int", "bool")
    private Type type;

    /**
     * Restituisce la tabella dei simboli associata alla dichiarazione.
     * @return Tabella dei simboli
     */
    public TabellaDeiSimboli getTabellaDeiSimboli() {
        return tabellaDeiSimboli;
    }

    /**
     * Imposta la tabella dei simboli per la dichiarazione.
     * @param tabellaDeiSimboli Tabella dei simboli da associare
     */
    public void setTabellaDeiSimboli(TabellaDeiSimboli tabellaDeiSimboli) {
        this.tabellaDeiSimboli = tabellaDeiSimboli;
    }

    /**
     * Restituisce il tipo delle variabili.
     * @return Tipo delle variabili
     */
    public Type getType() {
        return type;
    }

    /**
     * Imposta il tipo delle variabili.
     * @param type Tipo da associare
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * Costruttore della classe VarDecl.
     * Inizializza la lista di variabili e il tipo o la costante associata.
     *
     * @param variables Lista di variabili
     * @param typeOrConstant Tipo o costante associata
     */
    public VarDecl(Object variables, Object typeOrConstant) {
        this.variables = (ArrayList<VarInit>) variables;

        if(typeOrConstant instanceof ArrayType at){

            this.arrayInfo = at;        // <-- conserviamo l’oggetto con dimensione + elemType
            this.type = Type.ARRAY;     // <-- importante: il "tipo" della variabile è ARRAY
        }else if (typeOrConstant instanceof Type) {
            this.type = (Type) typeOrConstant;
        } else if (typeOrConstant instanceof Expr) {
            this.costant = (Expr) typeOrConstant;
        } else {
            throw new IllegalArgumentException("Tipo non valido per typeOrConstant: " + typeOrConstant.getClass().getName());
        }
    }

    /**
     * Restituisce la lista di variabili.
     * @return Lista di variabili
     */
    public List<VarInit> getVariables() {
        return variables;
    }

    /**
     * Restituisce la costante associata alla dichiarazione.
     * @return Costante associata
     */
    public Expr getCostant() {
        return costant;
    }

    public ArrayType getArrayInfo() {
        return arrayInfo;
    }

    /**
     * Metodo di accettazione per il Visitor.
     * Permette di applicare un'operazione definita da un Visitor alla classe VarDecl.
     * @param v Visitor da applicare
     * @return Risultato dell'operazione del Visitor
     */
    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
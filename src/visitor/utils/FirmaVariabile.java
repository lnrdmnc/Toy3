package visitor.utils;

import node.Type;
import node.expr.Expr;
import node.expr.constant.*;
import java.util.ArrayList;

/**
 * La classe FirmaVariabile rappresenta la firma di una variabile nell'AST.
 * Gestisce il tipo della variabile e fornisce metodi per il confronto e la clonazione.
 */
public class FirmaVariabile implements Firma, Cloneable {

    // Tipo della variabile
    private Type type;

    /**
     * Costruttore che inizializza il tipo della variabile.
     * @param type Tipo della variabile
     */
    public FirmaVariabile(Type type) {
        this.type = type;
    }

    /**
     * Costruttore di default della classe FirmaVariabile.
     */
    public FirmaVariabile() {

    }

    /**
     * Costruttore che inizializza il tipo della variabile in base a un'espressione costante.
     * @param constant Espressione costante da cui derivare il tipo
     */
    public FirmaVariabile(Expr constant) {
        if(constant instanceof CharNode){
            this.type = Type.CHAR;
        }
        else if(constant instanceof DoubleNode){
            this.type = Type.DOUBLE;
        }
        else if(constant instanceof FalseNode){
            this.type = Type.BOOLEAN;
        }
        else if(constant instanceof IntegerNode){
            this.type = Type.INTEGER;
        }
        else if(constant instanceof StringNode){
            this.type = Type.STRING;
        }
        else if(constant instanceof TrueNode){
            this.type = Type.BOOLEAN;
        }
    }

    /**
     * Restituisce il tipo della variabile.
     * @return Tipo della variabile
     */
    @Override
    public Type getType() {
        return type;
    }

    /**
     * Imposta il tipo della variabile.
     * @param type Tipo da associare
     * @return Tipo impostato
     */
    public Type setType(Type type) {
        return this.type = type;
    }

    /**
     * Crea e restituisce una copia della firma della variabile.
     * @return Clonazione della firma della variabile
     */
    public Firma clone() {
        FirmaVariabile clone = new FirmaVariabile();
        if (this.type != null) {
            clone.type = this.type; // Assumiamo che `Type` implementi `Cloneable`
        }
        return clone;
    }

    /**
     * Restituisce una lista di tipi multipli (non implementato, restituisce null).
     * @return Lista di tipi multipli o null
     */
    @Override
    public ArrayList<Type> getMultipleTypes() {
        return null;
    }

    /**
     * Confronta il tipo della variabile con un nuovo tipo.
     * @param nuovoTipo Tipo da confrontare
     * @return True se i tipi corrispondono, altrimenti false
     */
    public Boolean comparaTipi(Type nuovoTipo){
        Boolean result = false;
        if(this.type == nuovoTipo){
            result = true;
        }
        return result;
    }

    /**
     * Restituisce una rappresentazione in stringa dell'oggetto FirmaVariabile.
     * @return Rappresentazione in stringa di FirmaVariabile
     */
    @Override
    public String toString() {
        return "FirmaVariabile{" +
                "type=" + type +
                '}';
    }
}
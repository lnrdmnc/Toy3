package visitor.utils;

import node.Type;
import node.expr.Expr;
import node.expr.constant.*;

import java.util.ArrayList;

/**
 * La classe TipoFunzione rappresenta la firma di una funzione.
 * Gestisce i tipi di input, il tipo di output e le informazioni sui riferimenti.
 */
public class TipoFunzione implements Firma, Cloneable {

    // Lista dei tipi di input della funzione
    public ArrayList<Type> inputType;

    // Tipo di output della funzione
    public Type outputType;

    // Lista che indica se i parametri di input sono passati per riferimento
    public ArrayList<Boolean> reference;

    /**
     * Costruttore di default della classe TipoFunzione.
     */
    public TipoFunzione() {
    }

    /**
     * Costruttore che inizializza i tipi di input e il tipo di output della funzione.
     * @param inputType Lista dei tipi di input
     * @param outputType Tipo di output
     */
    public TipoFunzione(ArrayList<Type> inputType, Type outputType) {
        this.inputType = inputType;
        this.outputType = outputType;
    }

    /**
     * Costruttore che inizializza i tipi di input, il tipo di output e i riferimenti.
     * @param inputType Lista dei tipi di input
     * @param outputType Tipo di output
     * @param reference Lista dei riferimenti per i parametri di input
     */
    public TipoFunzione(ArrayList<Type> inputType, Type outputType, ArrayList<Boolean> reference) {
        this.inputType = inputType;
        this.outputType = outputType;
        this.reference = reference;
    }

    /**
     * Costruttore che inizializza solo il tipo di output della funzione.
     * @param outputType Tipo di output
     */
    public TipoFunzione(Type outputType) {
        this.outputType = outputType;
    }

    /**
     * Crea e restituisce una copia della firma della funzione.
     * @return Clonazione della firma della funzione
     */
    public Firma clone() {
        TipoFunzione cloned = new TipoFunzione();
        if (this.inputType != null) {
            cloned.inputType = new ArrayList<>();
        }
        for (Type type : this.inputType) {
            cloned.inputType.add(type);
        }

        if (this.outputType != null) {
            cloned.outputType = this.outputType;
        }

        return cloned;
    }

    /**
     * Restituisce il tipo di output della funzione.
     * @return Tipo di output
     */
    @Override
    public Type getType() {
        return this.outputType;
    }

    /**
     * Imposta i tipi di input della funzione.
     * @param inputType Lista dei tipi di input
     */
    public void setInputType(ArrayList<Type> inputType) {
        this.inputType = inputType;
    }

    /**
     * Restituisce i tipi di input della funzione.
     * @return Lista dei tipi di input
     */
    public ArrayList<Type> getInputType() {
        return inputType;
    }

    /**
     * Restituisce i tipi multipli (equivalenti ai tipi di input).
     * @return Lista dei tipi di input
     */
    @Override
    public ArrayList<Type> getMultipleTypes() {
        return inputType;
    }

    /**
     * Restituisce il tipo di output della funzione.
     * @return Tipo di output
     */
    public Type getOutputType() {
        return outputType;
    }

    /**
     * Imposta il tipo di output della funzione.
     * @param outputType Tipo di output
     */
    public void setOutputType(Type outputType) {
        this.outputType = outputType;
    }

    /**
     * Restituisce la lista dei riferimenti per i parametri di input.
     * @return Lista dei riferimenti
     */
    public ArrayList<Boolean> getReference() {
        return reference;
    }

    /**
     * Imposta la lista dei riferimenti per i parametri di input.
     * @param reference Lista dei riferimenti
     */
    public void setReference(ArrayList<Boolean> reference) {
        this.reference = reference;
    }

    /**
     * Restituisce una rappresentazione in stringa dell'oggetto TipoFunzione.
     * @return Rappresentazione in stringa di TipoFunzione
     */
    @Override
    public String toString() {
        return "TipoFunzione{" +
                "inputType=" + inputType +
                ", outputType=" + outputType +
                ", reference=" + reference +
                '}';
    }
}
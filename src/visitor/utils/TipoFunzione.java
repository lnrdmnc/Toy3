package visitor.utils;

import node.Type;
import node.expr.Expr;
import node.expr.constant.*;

import java.util.ArrayList;

public class TipoFunzione implements Firma, Cloneable{

    public ArrayList<Type> inputType;
    public Type outputType;
    public Boolean reference;

    public TipoFunzione(){

    }

    public TipoFunzione (ArrayList<Type> inputType, Type outputType) {
        this.inputType = inputType;
        this.outputType = outputType;
    }

    public TipoFunzione (ArrayList<Type> inputType, Type outputType, Boolean reference) {
        this.inputType = inputType;
        this.outputType = outputType;
        this.reference = reference;
    }


    public TipoFunzione(Type outputType) {
        this.outputType = outputType;
    }

    public Firma clone() {
        TipoFunzione cloned = new TipoFunzione();
        if(this.inputType !=null){
            cloned.inputType= new ArrayList<>();
        }
        for(Type type : this.inputType){
            cloned.inputType.add(type);
        }

        if(this.outputType != null){
            cloned.outputType = this.outputType;
        }

        return cloned;
    }

    @Override
    public Type getType() {
        return this.outputType;
    }

    public void setInputType(ArrayList<Type> inputType) {
        this.inputType = inputType;
    }

    public ArrayList<Type> getInputType() {
        return inputType;
    }

    @Override
    public ArrayList<Type> getMultipleTypes() {
        return inputType;
    }

    public Type getOutputType() {
        return outputType;
    }

    public void setOutputType(Type outputType) {
        this.outputType = outputType;
    }

    public Boolean getReference() {
        return reference;
    }

    public void setReference(Boolean reference) {
        this.reference = reference;
    }

    @Override
    public String toString() {
        return "TipoFunzione{" +
                "inputType=" + inputType +
                ", outputType=" + outputType +
                ", reference=" + reference +
                '}';
    }
}

package visitor.utils;

import node.Type;
import node.expr.Expr;
import node.expr.constant.*;
import java.util.ArrayList;

public class FirmaVariabile implements Firma, Cloneable {

    private Type type;

    public FirmaVariabile(Type type) {
        this.type = type;
    }

    public FirmaVariabile() {

    }

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

    @Override
    public Type getType() {
        return type;
    }

    public Type setType(Type type) {
        return this.type = type;
    }

    public Firma clone()  {
        FirmaVariabile clone = new FirmaVariabile();
        if (this.type != null) {
            clone.type = this.type; // Assumiamo che `Type` implementi `Cloneable`
        }
        return clone;
    }

    @Override
    public ArrayList<Type> getMultipleTypes() {
        return null;
    }

    public Boolean comparaTipi(Type nuovoTipo){
        Boolean result =false;
        if(this.type == nuovoTipo){
            result = true;
        }
        return result;
    }

    @Override
    public String toString() {
        return "FirmaVariabile{" +
                "type=" + type +
                '}';
    }
}

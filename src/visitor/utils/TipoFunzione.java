package visitor.utils;

import node.Type;
import node.expr.Expr;
import node.expr.constant.*;

import java.util.ArrayList;

public class TipoFunzione implements Firma, Cloneable{

    public Type type;
    @Override

    public Firma clone() {
        TipoFunzione cloned = new TipoFunzione();
        if(this.type!=null){
            cloned.type=this.type;
        }
        return cloned;
    }

    public TipoFunzione(Expr constant) {
        if(constant instanceof CharNode){
            this.type = Type.CHAR;
        } else if(constant instanceof DoubleNode){
            this.type = Type.DOUBLE;
        } else if(constant instanceof FalseNode){
            this.type = Type.BOOLEAN;
        } else if(constant instanceof IntegerNode){
            this.type = Type.INTEGER;
        } else if(constant instanceof StringNode){
            this.type = Type.STRING;
        } else if(constant instanceof TrueNode){
            this.type = Type.BOOLEAN;
        }
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public ArrayList<Type> getMultipleTypes() {
        return null;
    }

    public TipoFunzione() {

    }

    public TipoFunzione(Type type) {
        this.type=type;
    }

    public Boolean comparaTipi(Type nuovoTipo){

        Boolean result = false;
        if(this.type == nuovoTipo){
            result = true;
        }
        return result;
    }
}

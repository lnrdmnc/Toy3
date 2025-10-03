package node;

import node.expr.Expr;
import node.expr.constant.*;

public enum Type {
    INTEGER,
    BOOLEAN,
    DOUBLE,
    STRING,
    CHAR,
    RGB,
    NOTYPE;

    public static Type getTypeFromExpr(Expr constant){
        if(constant instanceof CharNode){
            return Type.CHAR;
        } else if(constant instanceof DoubleNode){
            return Type.DOUBLE;
        } else if(constant instanceof FalseNode){
            return Type.BOOLEAN;
        } else if(constant instanceof IntegerNode){
            return Type.INTEGER;
        } else if(constant instanceof StringNode){
            return Type.STRING;
        } else if(constant instanceof TrueNode) {
            return Type.BOOLEAN;
        }
        return null;
    }
}

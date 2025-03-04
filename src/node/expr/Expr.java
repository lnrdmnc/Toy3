package node.expr;


import node.ASTNode;
import node.Type;
import node.Visitor;

public interface Expr{
    Object accept(Visitor visitor);
    Type getType();
    void setType(Type type);
}

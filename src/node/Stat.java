package node;

import visitor.ScopeVisitor;

public interface Stat extends ASTNode
{
    Object accept(Visitor v);

}

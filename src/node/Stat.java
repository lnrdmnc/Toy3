package node;

import visitor.ScopeVisitor;

public interface Stat
{
    Object accept(Visitor v);

}

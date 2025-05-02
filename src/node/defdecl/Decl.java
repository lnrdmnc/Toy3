package node.defdecl;

import node.Visitor;

public interface Decl {

    Object accept(Visitor v);
}

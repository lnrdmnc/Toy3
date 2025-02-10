package node.stat;

import node.ASTNode;
import node.Stat;
import node.body.BodyOp;
import node.expr.Expr;

public class WhileOp extends ASTNode implements  Stat {

    private Expr espr;
    private BodyOp body;

    public WhileOp(Object espr, Object body) {
        this.espr = (Expr) espr;
        this.body = (BodyOp) body;
    }

    public Expr getEspr() {
        return espr;
    }

    public BodyOp getBody() {
        return body;
    }

    @Override
    public void accept(ASTNode v) {
        v.accept(this);
    }
}

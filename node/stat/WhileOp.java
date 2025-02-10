package node.stat;

import node.Stat;
import node.body.BodyOp;
import node.expr.Expr;

public class WhileOp extends Stat {

    private Expr espr;
    private BodyOp body;

    public WhileOp(Expr espr, BodyOp body) {
        super("WhileOp");
        this.espr = espr;
        this.body = body;
    }

    public Expr getEspr() {
        return espr;
    }

    public BodyOp getBody() {
        return body;
    }
}

package node.expr.constant;

import java.lang.String;

public class FalseNode {

    private boolean costant;

    public FalseNode() {
        this.costant = false;
    }

    public boolean getCostant() {
        return costant;
    }

    @Override
    public String toString() {
        return "False{" +
                "costant=" + costant +
                '}';
    }
}

package visitor;

import java_cup.runtime.SyntaxTreeDFS;
import node.ASTNode;
import node.Stat;
import node.Type;
import node.Visitor;
import node.body.BodyOp;
import node.defdecl.Decl;
import node.defdecl.DefDecl;
import node.expr.Expr;
import node.expr.constant.*;
import node.expr.operation.BinaryOp;
import node.expr.operation.FunCall;
import node.expr.operation.UnaryOp;
import node.pardecl.ParDecl;
import node.program.ProgramOp;
import node.stat.*;
import node.vardecl.VarDecl;
import node.vardecl.VarInit;
import org.w3c.dom.Node;
import visitor.utils.TabellaDeiSimboli;
import visitor.utils.TipoFunzione;

import java.util.ArrayList;
import java.util.Stack;

public class TypeCheck implements Visitor {

    private Stack<TabellaDeiSimboli> typeenv;
    private TabellaDeiSimboli current_table;


    @Override
    public Object visitProgramOp(ProgramOp programOp) {
        typeenv.add(programOp.getTabellaDeiSimboliProgram());
        if(programOp.getDeclarations() != null){
            for(Decl decl : programOp.getDeclarations())
            {
                ASTNode node = (ASTNode)decl;
                ASTNode.accept(this);

            }

            typeenv.add(programOp.getTabellaBegEnd());
            if(typeenv.add(programOp.getTabellaBegEnd()) != null){
                for(Decl decl : programOp.getVarDeclarations()))
                {
                    ASTNode node = (ASTNode) decl;
                    ASTNode.accept(this);

                }
            }

            if(typeenv.add(programOp.getStatements()!= null)) {
                for(Stat stat : programOp.getStatements())
                {
                    ASTNode node = (ASTNode) stat;
                    Type type = (Type) node.accept(this);
                    if(type==null){
                        throw new RuntimeException("Statements not valid.");
                    }
                    ASTNode.accept(this);
                }
                typeenv.pop();// BeginEndTable pop
                typeenv.pop(); // Program pop
                programOp.setType(Type.NOTYPE);
                return null;
            }
        }
        return null;
    }

    @Override
    public Object visitDefDecl(DefDecl defDecl) {
        Type type = defDecl.getType();
        BodyOp bodyOp;
        current_table = defDecl.getBody().getTabellaDeiSimboli();
        typeenv.add(defDecl.getTabellaDeiSimboli());
        Node node;

        if (defDecl.getList() != null) {
            for (ParDecl decl : defDecl.getList()) {
                decl.accept(this);
            }
        }

        if (defDecl.getType() == null) { // check for return statement in proc
            for (Stat statement : defDecl.getBody().getStatements()) {
                if (statement instanceof ReturnStat) throw new RuntimeException("A proc cannot have a return statement!");
            }
        } else { // checks for function
            boolean error = true;
            for (Stat statement : defDecl.getBody().getStatements()) {
                if (statement instanceof ReturnStat) {
                    error = false;
                    Type tipoTemporaneo = null;
                    ReturnStat returnStatement = (ReturnStat) statement;
                    if (returnStatement.getType() == null) {
                        tipoTemporaneo = (Type) returnStatement.getExpr().accept(this);
                    }
                    if (tipoTemporaneo != defDecl.getType()) {
                        throw new RuntimeException("The return type for " + defDecl.getId().getName() + " is incorrect");
                    }
                }
            }
            if (error) {
                throw new RuntimeException("Missing return statement" + " " + defDecl.getId().getName());
            }
        }

        bodyOp = defDecl.getBody();

        if (bodyOp.getDichiarazioni() != null) {
            for (VarDecl decls : bodyOp.getDichiarazioni()) {
                decls.accept(this);
            }
        }

        if (bodyOp.getStatements() != null) {
            for (Stat statement : bodyOp.getStatements()) {
                Type typeToCheck = (Type) statement.accept(this);
                if (typeToCheck == null) {
                    throw new RuntimeException("The current statement " + statement + " is wrong.");
                }
            }
        }

        bodyOp.setType(Type.NOTYPE);
        typeenv.pop();
        return null;
    }

    @Override
    public Object visitVarDecl(VarDecl varDecl) {
        ArrayList<VarInit> variabiliDichiarate = (ArrayList<VarInit>) varDecl.getVariables();
        if(variabiliDichiarate != null) {
            for(VarInit var : variabiliDichiarate) {
                Type TipoVariabiliDichiarate = var.accept(this);
                if(TipoVariabiliDichiarate != varDecl.getType()) {
                    throw new RuntimeException("The variable " + var.getId().getName()+  "' initialized with: " + var.getInitValue() + " does not match the declaration type: " + varDecl.getType());
                }else
                // Se il tipo non è dichiarato, lo determina dalla costante di inizializzazione
                {
                    Type iniConstType = Type.getTypeFromExpr(varDecl.getCostant());
                    if(variabiliDichiarate!= iniConstType){
                        throw new RuntimeException("The variable " + var.getId().getName() + " initialized with: " + var.getInitValue() + " does not match the declaration type: " + varDecl.getType());
                    }
                }
            }
        }
        Expr constant= varDecl.getCostant();
        if(constant!=null){
            constant.accept(this);
        }
        return null;
    }

    @Override
    public Object visitVarInit(VarInit varInit) {
       if(varInit.getInitValue() != null) {
           varInit.getInitValue().accept(this);
           varInit.setReturnType(varInit.getInitValue().getType());
       }
       else
       {
           varInit.setReturnType(null);
       }
       return null;
    }

    @Override
    public Object visitBinaryOp(BinaryOp binaryOp) {
        Expr leftOperand = binaryOp.getLeft();
        Expr rightOperand = binaryOp.getRight();
        leftOperand.accept(this);
        rightOperand.accept(this);
        binaryOp.setType(this.OpTypeChecker(binaryOp));
        return binaryOp.getType();
        return null;
    }

    @Override
    public Object visitUnaryOp(UnaryOp unaryOp) {
        Expr operand = unaryOp.getOperand();
        operand.accept(this);
        unaryOp.setType(this.unaryChecker(unaryOp));
        return unaryOp.getType();

    }



    @Override
    public Object visitFunCall(FunCall funCall) {
        Stack<TabellaDeiSimboli> cloned = (Stack<TabellaDeiSimboli>) typeenv.clone();
        TipoFunzione type = lookupFunction(funCall.getId(), cloned);
        ArrayList<Boolean> reference = type.getReference();

        if (type == null) {
            throw new RuntimeException("The function or procedure " + funCall.getId().getName() + " has been not declared");
        }

        if (type.getInputType() != null) {
            int numberOfFormalParameters = type.getInputType().size();
            int numberOfActualParameters = funCall.getArguments().size();

            if (numberOfActualParameters != numberOfFormalParameters) {
                throw new RuntimeException("The function or procedure " + funCall.getId().getName() + " the actual parameters does not match to formal parameters.");
            }

            for (int i = 0; i < numberOfActualParameters; i++) {
                Type actualParameters = (Type) funCall.getArguments().get(i).accept(this);
                Type formalParameters = type.getInputType().get(i);

                if (actualParameters != formalParameters) {
                    throw new RuntimeException("The parameters " + funCall.getArguments().get(i) + "( position " + i + "; type " + funCall.getExpressions().get(i).getType() + ") has a different number actual type from the formal one");
                }
            }

            for (int i = 0; i < numberOfActualParameters; i++) {
                boolean hasRef = reference.get(i);
                Expr expr = funCall.getArguments().get(i);

                if (hasRef && !(expr instanceof Identifier)) {
                    throw new RuntimeException("The referenced parameters " + funCall.getArguments().get(i) + "( position " + i + "; type " + funCall.getExpressions().get(i).getType() + ") is not available");
                }
            }
        }

        if (type.getOutputType() != null) {
            funCall.setType(type.getType());
        } else {
            funCall.setType(Type.NOTYPE);
        }

        funCall.setType(type.getType());

        return funCall.getType();
    }

    @Override
    public Object visitIdentifier(Identifier identifier) {
        Stack<TabellaDeiSimboli> clona = typeenv.clone();
        Type type = lookupVariable(identifier, clona);
        if (type == null) {
            throw new RuntimeException("Variable is not declared.");
        }
        identifier.setType(type);
        return identifier.getType();
    }

    @Override
    public Object visitIdentifier(CharNode charNode) {
        charNode.setType(Type.CHAR);
        return charNode.getType();
    }

    @Override
    public Object visitIdentifier(DoubleNode doubleNode) {
        doubleNode.setType(Type.DOUBLE);
        return doubleNode.getType();
    }

    @Override
    public Object visitIdentifier(IntegerNode integerNode) {
        integerNode.setType(Type.INTEGER);
        return integerNode.getType();
    }

    @Override
    public Object visitIdentifier(StringNode stringNode) {
        stringNode.setType(Type.STRING);
        return stringNode.getType();
    }

    @Override
    public Object visitIdentifier(TrueNode trueNode) {
        trueNode.setType(Type.BOOLEAN);
        return trueNode.getType();
    }

    @Override
    public Object visitAssignOp(AssignOp assignOp) {
        ArrayList<Identifier> variabiliAssegnate= assignOp.getVariables();
        for (Identifier id: variabiliAssegnate){
            id.accept(this);
        }

        ArrayList<Expr> expressions= ArrayList<Expr> assignOp.getExpressions();
        for(Expr expr: expressions){
            expr.accept(this);
        }

        for(int i=0; i<variabiliAssegnate.size(); i++){
            Type tipoVariabiliAssegnate= variabiliAssegnate.get(i).getType();
            Type tipoEspressioni= expressions.get(i).getType();
            if(tipoVariabiliAssegnate== Type.DOUBLE && tipoEspressioni== Type.INTEGER){
                System.out.println("ok  Type check assign op");
            } else if(tipoVariabiliAssegnate!= tipoEspressioni){

                throw new RuntimeException("The variable " + variabiliAssegnate.get(i).getName() + " is not compatible with the expression " + expressions.get(i) + ", are not the same.");
            }
        }
        assignOp.setType(Type.NOTYPE);
        return assignOp.getType();
    }

    @Override
    public Object visitWriteOperationNode(WriteOp writeOp) {

       TabellaDeiSimboli table = typeenv.peek();
        writeOp.setTabella(table);

        if (writeOp.getExpressions() != null) {
            for (Expr expressions : writeOp.getExpressions()) {
                expressions.accept(this);
            }
        }

        writeOp.setType(Type.NOTYPE);
        return writeOp.getType();
        return null;
    }

    @Override
    public Object visitIfThen(IfThenNode ifThen) {
        typeenv.add(ifThen.getTabellaDeiSimboli());
        Expr expr = ifThen.getEspressione();
        Type tipoExpr = (Type) expr.accept(this);
        if (tipoExpr != Type.BOOLEAN) {
            throw new RuntimeException("The expression in the if statement is not boolean, but is"+tipoExpr);
        }
        BodyOp body = ifThen.getBody();
        return ifThen.getType();
    }

    @Override
    public Object visitIfThenElse(IfThenElse ifThenElse) {
        return null;
    }

    @Override
    public Object visitReadOp(ReadOp readOp) {
        if (readOp.getList() != null) {
            for (Identifier variables : readOp.getList()) {
                variables.accept(this);
            }
        }
        readOp.setType(Type.NOTYPE);
        return readOp.getType();
    }

    @Override
    public Object visitWhileOp(WhileOp whileOp) {

        typeenv.add(whileOp.getTabellaDeiSimboli());
        Expr expr =whileOp.getEspr();
        Type tipoExpr= (Type) expr.accept(this);
        if(tipoExpr!= Type.BOOLEAN){
            throw new RuntimeException("The expression in the while statement is not boolean.");
        }
        BodyOp body= whileOp.getBody();
        body.accept(this);

        if(body.getType()!= Type.NOTYPE){
            throw new RuntimeException("The body is not NOTYPE.");
        }

        whileOp.setType(Type.NOTYPE);
        typeenv.pop();
        return whileOp.getType();
    }

    @Override
    public Object visitReturnOp(ReturnStat returnOp) {

        Expr result = returnOp.getExpr();
        Type exprType = (Type) result.accept(this);
        returnOp.setType(Type.NOTYPE);
        return null;
    }


    public Type lookupVariable (Identifier idNode, Stack<TabellaDeiSimboli> typeEnvironment) {
        Stack<TabellaDeiSimboli> clonedTypeEnvironment = clonaTypeEnvironment(typeEnvironment);
        if (typeEnvironment != null) {
            for (TabellaDeiSimboli tabellaDeiSimboli : clonedTypeEnvironment) {
                if (tabellaDeiSimboli.contains(idNode, "variable")) {
                    return tabellaDeiSimboli.getRigaLista(idNode, "variable").getFirm().getType();
                }
            }
        }
        return null;
    }

    public Stack<TabellaDeiSimboli> clonaTypeEnvironment(Stack<TabellaDeiSimboli> environment) {
        Stack<TabellaDeiSimboli> clone = new Stack<>();
        for (TabellaDeiSimboli current: environment) {
            clone.push(current);
        }
        return clone;
    }

    public TipoFunzione lookupFunction(Identifier idNode, Stack<TabellaDeiSimboli> typeEnvironment) {
        Stack<TabellaDeiSimboli> clonedTypeEnvironment = clonaTypeEnvironment(typeEnvironment);
        for (TabellaDeiSimboli tab : clonedTypeEnvironment) {
            if (tab.contains(idNode, "function")) {
                return (TipoFunzione) tab.getRigaLista(idNode, "function").getFirm();
            } else if (tab.contains(idNode, "procedure")) {
                return (TipoFunzione) tab.getRigaLista(idNode, "procedure").getFirm();
            }
        }
        return null;
    }

    public Type unaryChecker(UnaryOp node) {
        Expr expression = node.getOperand();
        String operator = node.getOperator();
        boolean minusCheck = operator.equals("MINUS");
        boolean notCheck = operator.equals("NOT");

        if (minusCheck && expression.getType() == Type.INTEGER) {
            return Type.INTEGER;
        }

        if (minusCheck && expression.getType() == Type.DOUBLE) {
            return Type.DOUBLE;
        }

        if (notCheck && expression.getType() == Type.BOOLEAN) {
            return Type.BOOLEAN;
        }
        return null;
    }

    public Type OpTypeChecker(BinaryOp operation) {
        Expr leftOperand = operation.getLeft();
        Expr rightOperand = operation.getRight();
        String operator = operation.getOperator();

        boolean arithOpCheck = operator.equals("PLUS") || operator.equals("MINUS") || operator.equals("TIMES") || operator.equals("DIV");
        boolean relop = operator.equals("LT") || operator.equals("LE") || operator.equals("GT") || operator.equals("GE") || operator.equals("EQ") || operator.equals("NE");
        boolean booleanCheck = operator.equals("AND") || operator.equals("OR");

        if (arithOpCheck && leftOperand.getType() == Type.INTEGER && rightOperand.getType() == Type.INTEGER) {
            return Type.INTEGER;
        } else if (arithOpCheck && leftOperand.getType() == Type.DOUBLE && rightOperand.getType() == Type.DOUBLE) {
            return Type.DOUBLE;
        } else if (arithOpCheck && leftOperand.getType() == Type.INTEGER && rightOperand.getType() == Type.DOUBLE) {
            return Type.DOUBLE;
        } else if (arithOpCheck && leftOperand.getType() == Type.DOUBLE && rightOperand.getType() == Type.INTEGER) {
            return Type.DOUBLE;
        } else if (booleanCheck && leftOperand.getType() == Type.BOOLEAN && rightOperand.getType() == Type.BOOLEAN) {
            return Type.BOOLEAN;
        } else if (relop && leftOperand.getType() == Type.INTEGER && rightOperand.getType() == Type.INTEGER) {
            return Type.BOOLEAN;
        } else if (relop && leftOperand.getType() == Type.DOUBLE && rightOperand.getType() == Type.INTEGER) {
            return Type.BOOLEAN;
        } else if (relop && leftOperand.getType() == Type.INTEGER && rightOperand.getType() == Type.DOUBLE) {
            return Type.BOOLEAN;
        } else if (relop && leftOperand.getType() == Type.DOUBLE && rightOperand.getType() == Type.DOUBLE) {
            return Type.BOOLEAN;
        } else if (operator.equals("PLUS") && (leftOperand.getType() == Type.STRING || rightOperand.getType() == Type.STRING)) {
            return Type.STRING;
        } else if (operator.equals("NE") && (leftOperand.getType() == Type.STRING && rightOperand.getType() == Type.STRING)) {
            return Type.BOOLEAN;
        } else if (operator.equals("EQ") && (leftOperand.getType() == Type.STRING && rightOperand.getType() == Type.STRING)) {
            return Type.BOOLEAN;
        } else {
            throw new RuntimeException("The assignment does not have a match in the table! " + leftOperand + " " + operation.getOperator() + " " + rightOperand);
        }
    }

}

package visitor;

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
import node.pardecl.ParVar;
import node.program.ProgramOp;
import node.stat.*;
import node.vardecl.VarDecl;
import node.vardecl.VarInit;
import visitor.utils.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GeneratoreCodiceC implements Visitor {

    private HashMap<String, ArrayList<Boolean>> firms = new HashMap<>();

    public GeneratoreCodiceC() {
       return;
    }

    // --- COSTANTI ---

    @Override
    public String visit(DoubleNode doubleNode) {
        return String.valueOf(doubleNode.getCostant());
    }

    @Override
    public String visit(FalseNode falseNode) {
        return "0";
    }

    @Override
    public String visit(StringNode stringNode) {
        return "\"" + stringNode.getConstant() + "\"";
    }

    @Override
    public String visit(CharNode charNode) {
        return "\'" + charNode.getCostant() + "\'";
    }

    @Override
    public String visit(TrueNode trueNode) {
        return "1";
    }

    @Override
    public String visit(IntegerNode integerNode) {
        return String.valueOf(integerNode.getValue());
    }

    // --- OPERAZIONI BINARIE E UNARIE ---

    @Override
    public String visit(BinaryOp binaryOp) {
        StringBuilder builder = new StringBuilder();
        Expr leftOperand = binaryOp.getLeft();
        Expr rightOperand = binaryOp.getRight();
        String operator = binaryOp.getOperator();
        builder.append(generateBinaryExpr(operator, leftOperand, rightOperand));
        return builder.toString();
    }

    @Override
    public String visit(UnaryOp unaryOp) {
        StringBuilder builder = new StringBuilder();
        Expr operand = unaryOp.getOperand();
        String operator = unaryOp.getOperator();
        builder.append(generateUnaryExpressionCode(unaryOp));
        return builder.toString();
    }

    // --- IDENTIFICATORI E CHIAMATE DI FUNZIONE ---

    @Override
    public String visit(Identifier identifier) {
        StringBuilder builder = new StringBuilder();
        if (identifier.isRef()) {
            builder.append("*");
        }
        builder.append(identifier.getName());
        return builder.toString();
    }

    @Override
    public String visit(FunCall funCall) {
        StringBuilder builder = new StringBuilder();
        Identifier id = funCall.getId();
        builder.append(id.accept(this)).append("_fun(");
        List<Boolean> references = firms.get(id.getName());
        List<Expr> exprs = funCall.getArguments();

        if (exprs != null) {
            for (int i = exprs.size() - 1; i >= 0; i--) {
                Expr expr = exprs.get(i);
                String exprContent = (String) expr.accept(this);

                if (references.get(i)) {
                    builder.append("&");
                }
                builder.append(exprContent);

                if (i != 0) {
                    builder.append(", ");
                }
            }
        }

        builder.append(")");
        return builder.toString();
    }

    // --- STATEMENT I/O ---

    @Override
    public String visit(ReadOp readOp) {
        StringBuilder result = new StringBuilder();
        for (Identifier id : readOp.getList()) {
            Type type = id.getType();
            String varName = (String) id.accept(this);

            if (type == Type.STRING) {
                result.append("buffer = (char*) malloc((1024*5)*sizeof(char));\n");
                result.append("scanf(\"%[^\n]\", buffer);\n");
                result.append(varName).append(" = (char*) malloc(strlen(buffer) + 1);\n");
                result.append("strcpy(").append(varName).append(", buffer);\n");
                result.append("free(buffer);\n");
            } else if (type == Type.INTEGER || type == Type.BOOLEAN) {
                result.append("scanf(\"%d\", &").append(varName).append(");\n");
            } else if (type == Type.DOUBLE) {
                result.append("scanf(\"%lf\", &").append(varName).append(");\n");
            } else if (type == Type.CHAR) {
                result.append("scanf(\"%c\", &").append(varName).append(");\n");
            }

            result.append("getchar();\n");
        }
        return result.toString();
    }

    @Override
    public String visit(WriteOp writeOp) {
        StringBuilder printArgs = new StringBuilder();
        StringBuilder printString = new StringBuilder("printf(\"");

        List<Expr> expressions = writeOp.getExpressions();
        int size = expressions.size() - 1;

        for (int i = size; i >= 0; i--) {
            Expr expr = expressions.get(i);
            String expression = (String) expr.accept(this);
            Type type = expr.getType();
            String specifier = getStringSpecifier(type);
            printString.append(specifier);
            printArgs.append(expression).append(",");
        }

        if (printArgs.length() > 0) {
            printArgs = new StringBuilder(printArgs.substring(0, printArgs.length() - 1));
        }

        if (writeOp.isNewLine()) {
            printString.append("\\n");
        }

        printString.append("\", ").append(printArgs).append(");");
        return printString.toString();
    }

    // --- ASSEGNAZIONI ---

    @Override
    public String visit(AssignOp assignOp) {
        StringBuilder builder = new StringBuilder();
        ArrayList<Identifier> ids = assignOp.getVariables();
        List<Expr> exprs = assignOp.getExpressions();

        for (int i = ids.size() - 1; i >= 0; i--) {
            String id = (String) ids.get(i).accept(this);
            String expr = (String) exprs.get(i).accept(this);
            builder.append(id).append(" = ").append(expr).append(";\n");
        }

        return builder.toString();
    }

    // --- CONTROL FLOW ---

    @Override
    public String visit(IfThenNode ifThen) {
        String condition = (String) ifThen.getEspressione().accept(this);
        String body = (String)ifThen.getBody().accept(this);
        return "if (" + condition + ")" + body;
    }

    @Override
    public String visit(IfThenElse ifThenElse) {
        String condition = (String) ifThenElse.getEspressione().accept(this);
        String ifBody = (String) ifThenElse.getIfthenStatement().accept(this);
        String elseBody = (String) ifThenElse.getElseStatement().accept(this);
        return "if (" + condition + ")" + ifBody + "else" + elseBody;
    }

    @Override
    public String visit(WhileOp whileOp) {
        String condition =(String) whileOp.getEspr().accept(this);
        String body = (String) whileOp.getBody().accept(this);
        return "while (" + condition + ")" + body;
    }

    @Override
    public String visit(ReturnStat returnOp) {
        return "return " + returnOp.getExpr().accept(this) + ";";
    }

    // --- DICHIARAZIONI ---

    @Override
    public String visit(VarDecl varDecl) {
        StringBuilder builder = new StringBuilder();
        if (varDecl.getCostant() != null) {
            builder.append(getCType(Type.getTypeFromExpr(varDecl.getCostant()))).append(" ");
            builder.append(varDecl.getVariables().get(0).accept(this));
            builder.append(" = ");
            builder.append(varDecl.getCostant().accept(this));
        } else {
            builder.append(getCType(varDecl.getType())).append(" ");
            int size = varDecl.getVariables().size() - 1;
            boolean isString = varDecl.getType() == Type.STRING;

            for (int i = size; i >= 0; i--) {
                if (isString && i != size) builder.append("*");
                if (i == 0) {
                    builder.append(varDecl.getVariables().get(i).accept(this));
                } else {
                    builder.append(varDecl.getVariables().get(i).accept(this)).append(",");
                }
            }
        }

        builder.append(";");
        return builder.toString();
    }

    @Override
    public String visit(VarInit varInit) {
        StringBuilder builder = new StringBuilder();
        builder.append(varInit.getId().accept(this));
        if (varInit.getInitValue() != null) {
            builder.append(" = ").append(varInit.getInitValue().accept(this));
        }
        return builder.toString();
    }

    @Override
    public String visit(ParDecl parDecl) {
        StringBuilder builder = new StringBuilder();
        int size = parDecl.getVariables().size() - 1;
        for (int i = size; i >= 0; i--) {
            ParVar variable = parDecl.getVariables().get(i);
            if (i == 0) {
                builder.append(getCType(parDecl.getType())).append(" ").append(variable.accept(this));
            } else {
                builder.append(getCType(parDecl.getType())).append(" ").append(variable.accept(this)).append(",");
            }
        }
        return builder.toString();
    }

    @Override
    public String visit(ParVar parVar) {
        StringBuilder builder = new StringBuilder();
        builder.append(parVar.getId().accept(this));
        return builder.toString();
    }

    // --- CORPO DEL PROGRAMMA ---

    @Override
    public String visit(BodyOp body) {
        StringBuilder builder = new StringBuilder("{");
        for (VarDecl decl : body.getDichiarazioni()) {
            builder.append(decl.accept(this));
        }
        for (Stat stmt : body.getStatements()) {
            builder.append(stmt.accept(this));
            if (stmt instanceof FunCall) {
                builder.append(";\n");
            }
        }
        builder.append("}");
        return builder.toString();
    }

    // --- PROGRAMMA PRINCIPALE ---

    @Override
    public String visit(ProgramOp programOp) {
        StringBuilder builder = new StringBuilder();
        setupFirms(programOp);
        builder.append(buildHeader());

        if (programOp.getDeclarations()!= null) {
            for (Decl decl : programOp.getDeclarations()) {
                if (decl instanceof DefDecl) {
                    builder.append(generateFunctionDeclaration((DefDecl) decl)).append("\n");
                }
            }
            for (Decl decl : programOp.getDeclarations()) {
                builder.append(decl.accept(this)).append("\n");
            }
        }

        builder.append("int main() {\n");
        if (programOp.getVarDeclarations() != null) {
            for (Decl decl : programOp.getVarDeclarations()) {
                builder.append(decl.accept(this)).append("\n");
            }
        }
        if (programOp.getStatements() != null) {
            for (Stat stmt : programOp.getStatements()) {
                builder.append(stmt.accept(this)).append("\n");
                if (stmt instanceof FunCall) {
                    builder.append(";\n");
                }
            }
        }

        builder.append("    return 0;\n}\n");
        return builder.toString();
    }

    @Override
    public String visit(DefDecl defDecl) {
        StringBuilder builder = new StringBuilder();
        String firm = (String) generateSignature(defDecl);
        builder.append(firm);
        builder.append(defDecl.getBody().accept(this));
        return builder.toString();
    }

    // --- METODI AUSILIARI ---

    private String getCType(Type t) {
        if (t == null) return "void";
        switch (t) {
            case STRING: return "char*";
            case CHAR: return "char";
            case DOUBLE: return "double";
            case INTEGER:
            case BOOLEAN: return "int";
            default: return "";
        }
    }

    private String buildHeader() {
        StringBuilder header = new StringBuilder();
        header.append("#include <stdio.h>\n");
        header.append("#include <stdlib.h>\n");
        header.append("#include <string.h>\n");
        header.append("#include <math.h>\n");
        header.append("#define BUFFER_SIZE 1024*4\n");
        header.append("\n// Funzioni di supporto\n");
        header.append("char* buffer;\n");
        header.append("char* string_concat(char* s1, char* s2) {\n");
        header.append("    char* ns = malloc(strlen(s1) + strlen(s2) + 1);\n");
        header.append("    strcpy(ns, s1);\n");
        header.append("    strcat(ns, s2);\n");
        header.append("    return ns;\n");
        header.append("}\n");

        // Altre funzioni simili a quelle del file originale...

        return header.toString();
    }

    private String generateFunctionDeclaration(DefDecl decl) {
        return generateSignature(decl) + ";";
    }

    private String generateSignature(DefDecl decl) {
        StringBuilder builder = new StringBuilder();
        if (decl.getType() == Type.STRING) {
            builder.append("char* ");
        } else if (decl.getType() == null) {
            builder.append("void ");
        } else {
            builder.append(getCType(decl.getType())).append(" ");
        }
        builder.append(decl.getId().accept(this)).append("_fun(");
        if (decl.getList() != null) {
            for (int i = decl.getList().size() - 1; i >= 0; i--) {
                if (i > 0) builder.append(",");
                builder.append(decl.getList().get(i).accept(this));
            }
        }
        builder.append(")");
        return builder.toString();
    }

    private void setupFirms(ASTNode node) {
        ProgramOp programNode = (ProgramOp) node;
        TabellaDeiSimboli table = programNode.getTabellaDeiSimboliProgram();
        if (table != null) {
            for (RigaTabellaDeiSimboli row : table.getRigaLista()) {
                if (row.getFirma() instanceof TipoFunzione) {
                    String name = row.getId();
                    ArrayList<Boolean> refs = ((TipoFunzione) row.getFirma()).getReference();
                    firms.put(name, refs);
                }
            }
        }
    }

    private String generateBinaryExpr(String operator, Expr left, Expr right) {
        // Implementa la logica per generare il codice C per operazioni binarie
        boolean arithOp = operator.equals("PLUS") || operator.equals("MINUS") ||
                operator.equals("TIMES") || operator.equals("DIV");
        boolean relop = operator.equals("LT") || operator.equals("LE") ||
                operator.equals("GT") || operator.equals("GE") ||
                operator.equals("EQ") || operator.equals("NE");
        boolean logicOp = operator.equals("AND") || operator.equals("OR");

        Type leftType = left.getType();
        Type rightType = right.getType();

        if (arithOp && leftType == Type.INTEGER && rightType == Type.INTEGER) {
            return left.accept(this) + " " + getSymbolFromString(operator) + " " + right.accept(this);
        } else if (arithOp && leftType == Type.DOUBLE && rightType == Type.DOUBLE) {
            return left.accept(this) + " " + getSymbolFromString(operator) + " " + right.accept(this);
        } else if (arithOp && leftType == Type.INTEGER && rightType == Type.DOUBLE) {
            return left.accept(this) + " " + getSymbolFromString(operator) + " " + right.accept(this);
        } else if (arithOp && leftType == Type.DOUBLE && rightType == Type.INTEGER) {
            return left.accept(this) + " " + getSymbolFromString(operator) + " " + right.accept(this);
        } else if (logicOp && leftType == Type.BOOLEAN && rightType == Type.BOOLEAN) {
            return left.accept(this) + " " + getSymbolFromString(operator) + " " + right.accept(this);
        } else if (relop && leftType == Type.INTEGER && rightType == Type.INTEGER) {
            return left.accept(this) + " " + getSymbolFromString(operator) + " " + right.accept(this);
        } else if (relop && leftType == Type.DOUBLE && rightType == Type.INTEGER) {
            return left.accept(this) + " " + getSymbolFromString(operator) + " " + right.accept(this);
        } else if (relop && leftType == Type.INTEGER && rightType == Type.DOUBLE) {
            return left.accept(this) + " " + getSymbolFromString(operator) + " " + right.accept(this);
        } else if (relop && leftType == Type.DOUBLE && rightType == Type.DOUBLE) {
            return left.accept(this) + " " + getSymbolFromString(operator) + " " + right.accept(this);
        } else if (operator.equals("PLUS") && (leftType == Type.STRING || rightType == Type.STRING)) {
            return "string_concat(" + convertValueToString((String) left.accept(this), leftType) + ", " +
                    convertValueToString((String) right.accept(this), rightType) + ")";
        } else if (operator.equals("EQ") && leftType == Type.STRING && rightType == Type.STRING) {
            return "strcmp(" + right.accept(this) + ", " + left.accept(this) + ") == 0";
        } else if (operator.equals("NE") && leftType == Type.STRING && rightType == Type.STRING) {
            return "strcmp(" + right.accept(this) + ", " + left.accept(this) + ") != 0";
        } else {
            throw new RuntimeException("Operazione non supportata: " + operator + " con tipi " + leftType + " e " + rightType);
        }
    }

    private String generateUnaryExpressionCode(UnaryOp node) {
        Expr expr = node.getOperand();
        String operator = node.getOperator();

        if (operator.equals("MINUS")) {
            return "-" + expr.accept(this);
        } else if (operator.equals("NOT")) {
            return "!(" + expr.accept(this) + ")";
        } else {
            throw new RuntimeException("Operatore unario non riconosciuto: " + operator);
        }
    }

    private String convertValueToString(String expression, Type type) {
        switch (type) {
            case INTEGER: return "int2str(" + expression + ")";
            case CHAR: return "char2str(" + expression + ")";
            case DOUBLE: return "float2str(" + expression + ")";
            case BOOLEAN: return "bool2str(" + expression + ")";
            case STRING: return expression;
            default: return "";
        }
    }

    private String getSymbolFromString(String op) {
        return switch (op) {
            case "MINUS" -> "-";
            case "TIMES" -> "*";
            case "PLUS" -> "+";
            case "DIV" -> "/";
            case "AND" -> "&&";
            case "OR" -> "||";
            case "NOT" -> "!";
            case "LT" -> "<";
            case "LE" -> "<=";
            case "EQ" -> "==";
            case "GE" -> ">=";
            case "GT" -> ">";
            case "NE" -> "!=";
            default -> throw new IllegalArgumentException("Operatore sconosciuto: " + op);
        };
    }

    private String getStringSpecifier(Type type) {
        switch (type) {
            case INTEGER: return "%d";
            case DOUBLE: return "%f";
            case BOOLEAN: return "%d";
            case STRING: return "%s";
            case CHAR: return "%c";
            default: return "";
        }
    }
}

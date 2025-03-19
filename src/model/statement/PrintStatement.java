package model.statement;

import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import exceptions.StatementException;
import model.adt.IMap;
import model.expressions.IExpression;
import model.state.ProgramState;
import model.types.IType;

public class PrintStatement implements IStatement {
    private IExpression expression;

    public PrintStatement(IExpression expression) {
        this.expression = expression;
    }
    public ProgramState execute(ProgramState state) throws StatementException, KeyNotFoundException, ExpressionException {
        var res = expression.evaluate(state.getSymTable(), state.getHeap());
        state.getOut().add(res);

        return null;
    }

    @Override
    public String toString() {
        return "print(" + expression.toString() + ")";
    }

    @Override
    public IMap<String, IType> typecheck(IMap<String, IType> typeEnv) throws StatementException, KeyNotFoundException, ExpressionException {
        expression.typecheck(typeEnv);
        return typeEnv;
    }

    public IStatement deepCopy() {
        return new PrintStatement(expression.deepCopy());
    }
}

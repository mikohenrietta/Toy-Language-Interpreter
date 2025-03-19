package model.statement;

import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import exceptions.StatementException;
import model.adt.IMap;
import model.expressions.IExpression;
import model.expressions.NotExpression;
import model.state.ProgramState;
import model.types.BoolType;
import model.types.IType;

public class RepeatStatement implements IStatement{
    private IStatement statement;
    private IExpression expression;

    public RepeatStatement(IStatement statement, IExpression expression) {
        this.statement = statement;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, KeyNotFoundException, ExpressionException {
        IStatement newStat = new CompoundStatement(statement, new WhileStatement(new NotExpression(expression), statement));

        state.getExeStack().push(newStat);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new RepeatStatement(statement.deepCopy(), expression.deepCopy());
    }

    @Override
    public IMap<String, IType> typecheck(IMap<String, IType> typeEnv) throws StatementException, KeyNotFoundException, ExpressionException {
        IType typeCond = expression.typecheck(typeEnv);
        if(typeCond.equals(new BoolType())){
            statement.typecheck(typeEnv.deepCopy());
            return typeEnv;
        }
        throw new StatementException("Condition is not a boolean type");
    }

    @Override
    public String toString() {
        return "repeat "+statement.toString()+" until "+expression.toString();
    }
}

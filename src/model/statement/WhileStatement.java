package model.statement;

import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import exceptions.StatementException;
import model.adt.IMap;
import model.expressions.IExpression;
import model.state.ProgramState;
import model.types.BoolType;
import model.types.IType;
import model.values.BoolValue;
import model.values.IValue;

public class WhileStatement implements IStatement {
    IStatement statement;
    IExpression condition;
    public WhileStatement(IExpression condition, IStatement statement) {
        this.condition = condition;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, KeyNotFoundException, ExpressionException {
        IValue value = condition.evaluate(state.getSymTable(), state.getHeap());
        if(!value.getType().equals(new BoolType())){
            throw new StatementException("Condition is not a boolean");
        }
        if(((BoolValue) value).getValue()){
            state.getExeStack().push(this.deepCopy());
            state.getExeStack().push(statement);
        }
        return null;
    }

    @Override
    public IMap<String, IType> typecheck(IMap<String, IType> typeEnv) throws StatementException, KeyNotFoundException, ExpressionException {
        IType typeCond = condition.typecheck(typeEnv);
        if(typeCond.equals(new BoolType())){
            statement.typecheck(typeEnv.deepCopy());
            return typeEnv;
        }
        throw new StatementException("Condition is not a boolean type");
    }

    @Override
    public String toString() {
        return "while(" + condition.toString() + ") {" + statement.toString() + "}";
    }
    @Override
    public IStatement deepCopy() {
        return new WhileStatement(condition, statement.deepCopy());
    }
}

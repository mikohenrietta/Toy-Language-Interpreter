package model.statement;

import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import exceptions.StatementException;
import model.adt.IMap;
import model.adt.IStack;
import model.state.ProgramState;
import model.types.IType;

public class CompoundStatement implements IStatement {
    IStatement firstStatement;
    IStatement secondStatement;

    public CompoundStatement(IStatement firstStatement, IStatement secondStatement) {
        this.firstStatement = firstStatement;
        this.secondStatement = secondStatement;
    }

    @Override
    public String toString()
    {
        return"(" + firstStatement.toString() + "; " + secondStatement.toString() + ")";
    }

    public ProgramState execute(ProgramState state) throws StatementException {
        IStack<IStatement> stack = state.getExeStack();
        stack.push(secondStatement);
        stack.push(firstStatement);
        return null;
    }

    @Override
    public IMap<String, IType> typecheck(IMap<String, IType> typeEnv) throws StatementException, KeyNotFoundException, ExpressionException {
        return secondStatement.typecheck(firstStatement.typecheck(typeEnv));
    }

    public IStatement deepCopy(){
        return new CompoundStatement(firstStatement.deepCopy(), secondStatement.deepCopy());
    }
}
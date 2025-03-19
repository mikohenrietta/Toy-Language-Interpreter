package model.statement;

import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import exceptions.StatementException;
import model.adt.IMap;
import model.state.ProgramState;
import model.types.IType;

public class NoOperation implements IStatement {
    public NoOperation(){}

    public ProgramState execute(ProgramState state) {
        return null;
    }
    @Override
    public IStatement deepCopy() {
        return null;
    }

    @Override
    public IMap<String, IType> typecheck(IMap<String, IType> typeEnv) throws StatementException, KeyNotFoundException, ExpressionException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "NoOperation";
    }
}

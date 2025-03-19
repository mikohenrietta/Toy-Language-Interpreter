package model.statement;

import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import exceptions.StatementException;
import model.adt.IMap;
import model.state.ProgramState;
import model.types.IType;

public interface IStatement {
    ProgramState execute(ProgramState state)throws StatementException, KeyNotFoundException, ExpressionException;
    IStatement deepCopy();
    IMap<String, IType> typecheck(IMap<String, IType> typeEnv) throws StatementException, KeyNotFoundException, ExpressionException;
}

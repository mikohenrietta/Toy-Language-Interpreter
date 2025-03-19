package model.statement;

import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import exceptions.StatementException;
import model.adt.IMap;
import model.state.ProgramState;
import model.types.IType;

public class VariableDeclarationStatement implements IStatement {
    private String variableName;
    IType type;
    public VariableDeclarationStatement(String variableName, IType type) {
        this.variableName = variableName;
        this.type = type;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, KeyNotFoundException, ExpressionException {
        if(state.getSymTable().contains(variableName)) {
            throw new StatementException("Variable already declared");
        }
        state.getSymTable().insert(variableName, type.defaultValue());
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new VariableDeclarationStatement(variableName, type);
    }

    @Override
    public IMap<String, IType> typecheck(IMap<String, IType> typeEnv) throws StatementException, KeyNotFoundException, ExpressionException {
        typeEnv.insert(variableName, type);
        return typeEnv;
    }

    @Override
    public String toString() {
        return type + " " + variableName;
    }
}

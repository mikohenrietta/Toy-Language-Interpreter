package model.statement;

import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import exceptions.StatementException;
import model.adt.IMap;
import model.expressions.IExpression;
import model.state.ProgramState;
import model.types.IType;
import model.types.StringType;
import model.values.IValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseReadFileStatement implements IStatement {
    IExpression expression;
    public CloseReadFileStatement(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, KeyNotFoundException, ExpressionException {
        IValue res = expression.evaluate(state.getSymTable(), state.getHeap());
        if(!res.getType().equals(new StringType()))
            throw new ExpressionException("Expression is not of string type");
        BufferedReader reader = state.getFileTable().get(((StringValue)res).getValue());
        if( reader == null) {
            throw new KeyNotFoundException("File not found");
        }
        try {
            reader.close();
        }
        catch (IOException e) {
            throw new StatementException("Error closing file");
        }
        state.getFileTable().remove(((StringValue)res).getValue());
        return null;
    }

    @Override
    public IMap<String, IType> typecheck(IMap<String, IType> typeEnv) throws StatementException, KeyNotFoundException, ExpressionException {
        IType typexp = expression.typecheck(typeEnv);
        if(typexp.equals(new StringType()))
            return typeEnv;
        else
            throw new StatementException(typexp + " is not a string type");
    }

    @Override
    public String toString(){
        return "closeReadFile(" + expression.toString() +")";
    }
    @Override
    public IStatement deepCopy() {
        return new CloseReadFileStatement(expression.deepCopy());
    }
}

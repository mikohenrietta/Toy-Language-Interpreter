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
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenReadFileStatement implements IStatement {
    private IExpression expression;
    public OpenReadFileStatement(IExpression expression) {
        this.expression = expression;
    }

    public ProgramState execute(ProgramState state) throws StatementException, KeyNotFoundException, ExpressionException {
        IValue res = expression.evaluate(state.getSymTable(), state.getHeap());
        if(!res.getType().equals(new StringType()))
            throw new ExpressionException("Expression is not of string type");
        if(state.getFileTable().contains(((StringValue)res).getValue()))
            throw new ExpressionException("File is already in File Table");
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(((StringValue)res).getValue()));
        }
        catch(Exception e) {
            throw new StatementException(e.getMessage());
        }
        state.getFileTable().insert(((StringValue)res).getValue(), reader);
        return null;
    }

    @Override
    public IMap<String, IType> typecheck(IMap<String, IType> typeEnv) throws StatementException, KeyNotFoundException, ExpressionException {
        IType typexp = expression.typecheck(typeEnv);
        if(typexp.equals(new StringType()))
            return typeEnv;
        else
            throw new StatementException("Expression is not of string type");
    }

    @Override
    public String toString(){
        return "openReadFile(" + expression.toString() + ")";
    }
    @Override
    public IStatement deepCopy() {
        return new OpenReadFileStatement(expression.deepCopy());
    }
}

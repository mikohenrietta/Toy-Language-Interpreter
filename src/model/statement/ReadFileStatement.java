package model.statement;

import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import exceptions.StatementException;
import model.adt.IMap;
import model.expressions.IExpression;
import model.state.ProgramState;
import model.types.BoolType;
import model.types.IType;
import model.types.IntType;
import model.types.StringType;
import model.values.IValue;
import model.values.IntValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements IStatement{
    private IExpression expression;
    private String variableName;

    public ReadFileStatement(IExpression expression, String variableName) {
        this.expression = expression;
        this.variableName = variableName;
    }
    public ProgramState execute(ProgramState state) throws StatementException, KeyNotFoundException, ExpressionException {
        var t = state.getSymTable();
        if(!t.contains(variableName)) {
            throw new StatementException("Variable name not defined");
        }
        if(!t.get(variableName).getType().equals(new IntType())){
            throw new StatementException("Variable is not of type INT");
        }
        IValue res = expression.evaluate(state.getSymTable(), state.getHeap());
        if(!res.getType().equals(new StringType())){
            throw new StatementException("Variable is not of type STRING");
        }

        BufferedReader r = state.getFileTable().get(((StringValue)res).getValue());
        try {
            String readResult = r.readLine();
            if(readResult == "") {
                readResult = "0";
            }
            int parsedResult = Integer.parseInt(readResult);
            state.getSymTable().insert(variableName, new IntValue(parsedResult));
            return null;
        }
        catch(IOException e) {
            throw new StatementException("Error on reading");
        }

    }

    @Override
    public IMap<String, IType> typecheck(IMap<String, IType> typeEnv) throws StatementException, KeyNotFoundException, ExpressionException {
        IType typexp = expression.typecheck(typeEnv);
        IType typevar = typeEnv.get(variableName);
        if(typexp.equals(new StringType())){
            if(typevar.equals(new IntType()))
                return typeEnv;
            else
                throw new StatementException("Variable is not of type INT");
        }
        else{
            throw new StatementException("Expression is not of type STRING");
        }
    }

    @Override
    public String toString(){
        return "readFile(" + expression.toString() + "," + variableName + ")";
    }
    public IStatement deepCopy() {
        return new ReadFileStatement(expression.deepCopy(), variableName);
    }
}

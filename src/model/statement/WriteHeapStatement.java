package model.statement;

import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import exceptions.StatementException;
import model.adt.IMap;
import model.expressions.IExpression;
import model.state.ProgramState;
import model.types.IType;
import model.types.RefType;
import model.values.IValue;
import model.values.RefValue;

public class WriteHeapStatement implements IStatement {
    private String varName;
    private IExpression expression;
    public WriteHeapStatement(String varName, IExpression expression) {
        this.varName = varName;
        this.expression = expression;
    }

    public ProgramState execute(ProgramState state) throws StatementException, KeyNotFoundException, ExpressionException {
        if(!state.getSymTable().contains(varName)){
            throw new KeyNotFoundException(varName + " not found");
        }
        IValue value = state.getSymTable().get(varName);
        if(!(value.getType() instanceof RefType)){
            throw new StatementException("Variable is not of type ref");
        }
        int address = ((RefValue)value).getAddress();
        if(!state.getHeap().exists(address)){
            throw new StatementException("Variable is not in heap");
        }
        IValue expVal = expression.evaluate(state.getSymTable(), state.getHeap());
        if(!((RefValue)value).getInnerType().equals(expVal.getType())){
            throw new StatementException("Variable and expression type mismatch");
        }
        state.getHeap().set(address, expVal);
        return null;
    }

    @Override
    public IMap<String, IType> typecheck(IMap<String, IType> typeEnv) throws StatementException, KeyNotFoundException, ExpressionException {
        IType typevar = typeEnv.get(varName);
        IType typexp = expression.typecheck(typeEnv);
        if(typevar.equals(new RefType(typexp)))
            return typeEnv;
        else throw new StatementException("Type of var doesn't match type of expression");
    }

    @Override
    public String toString(){
        return "wH(" + varName + ", " + expression + ")";
    }
    @Override
    public IStatement deepCopy() {
        return new WriteHeapStatement(varName, expression.deepCopy());
    }
}

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

public class HeapAllocation implements IStatement{
    private String name;
    private IExpression expression;
    public HeapAllocation(String name, IExpression expression) {
        this.name = name;
        this.expression = expression;
    }
    public ProgramState execute(ProgramState state) throws StatementException, KeyNotFoundException, ExpressionException {
        if(!state.getSymTable().contains((name))){
            throw new KeyNotFoundException("Name is not defined");
        }
        IValue value = state.getSymTable().get(name); // getting the value with this name
        if(!(value.getType() instanceof RefType)){
            throw new StatementException("Variable is not of type ref");
        } // checking if type of evaluated value is ref
        IValue expVal = expression.evaluate(state.getSymTable(), state.getHeap());// evaluating expression
        if(!((RefValue)value).getInnerType().equals(expVal.getType())){
            throw new StatementException("Variable and expression does not match");
        } // if their type does not match throw exception
        int addr = state.getHeap().allocate(expVal); // puts it in values, assigns a new address and returns that
        RefValue newVal = new RefValue(addr, expVal.getType()); // create a new ref value with the addr allocated and the expression type
        state.getSymTable().update(name, newVal); // update in symbol table the value of name
        return null;
    }

    @Override
    public IMap<String, IType> typecheck(IMap<String, IType> typeEnv) throws StatementException, KeyNotFoundException, ExpressionException {
        IType typevar = typeEnv.get(name);
        IType typexp = expression.typecheck(typeEnv);
        if(typevar.equals(new RefType(typexp)))
            return typeEnv;
        else throw new StatementException("Type of var doesn't match type of expression");
    }

    @Override
    public String toString() {
        return "new(" + name + ", " + expression + ")";
    }
    @Override
    public IStatement deepCopy() {
        return new HeapAllocation(name, expression.deepCopy());
    }
}

package model.expressions;
import exceptions.KeyNotFoundException;
import model.adt.IHeap;
import model.adt.IMap;
import model.state.ProgramState;
import model.types.IType;
import model.values.IValue;

public class VariableExpression implements IExpression {
    private String variable;
    public VariableExpression(String variable) {
        this.variable = variable;
    }

    public IValue evaluate(IMap<String, IValue> symTable, IHeap heap) throws KeyNotFoundException {
        return symTable.get(variable);
    }

    @Override
    public IType typecheck(IMap<String, IType> typeEnv) throws KeyNotFoundException {
        return typeEnv.get(variable);
    }

    @Override
    public String toString() {
        return variable;
    }

    public IExpression deepCopy() {
        return new VariableExpression(variable);
    }
}

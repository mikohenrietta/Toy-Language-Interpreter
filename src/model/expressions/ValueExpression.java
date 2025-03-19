package model.expressions;

import exceptions.KeyNotFoundException;
import model.adt.IHeap;
import model.adt.IMap;
import model.state.ProgramState;
import model.types.IType;
import model.values.IValue;

public class ValueExpression implements IExpression {
    private IValue value;
    public ValueExpression(IValue value) {
        this.value = value;
    }

    public IValue evaluate(IMap<String, IValue> symTable, IHeap heap) {
        return value;
    }
    public IType typecheck(IMap<String, IType> typeEnv) {
        return value.getType();
    }
    @Override
    public String toString() {
        return value.toString();
    }

    public IExpression deepCopy() {
        return new ValueExpression(value);
    }
}

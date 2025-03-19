package model.values;

import model.types.IType;
import model.types.IntType;

public class IntValue implements IValue {
    private int value;

    public IntValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public IType getType() {
        return new IntType();
    }

    @Override
    public boolean equals(IValue other){
        return other.getType() instanceof IntValue && this.value == ((IntValue) other).value && other.getType() == this.getType();
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}

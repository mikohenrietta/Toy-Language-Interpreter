package model.values;

import model.types.BoolType;
import model.types.IType;

public class BoolValue implements IValue {
    private boolean value;

    public BoolValue(boolean value) {
        this.value = value;
    }
    public boolean getValue() {
        return value;
    }
    public IType getType() {
        return new BoolType();
    }
    @Override
    public boolean equals(IValue other) {
        return other.getType() instanceof BoolValue && this.value == ((BoolValue)other).value && other.getType() == this.getType();
    }

    @Override
    public String toString() {
        return value ? "true" : "false";
    }
}

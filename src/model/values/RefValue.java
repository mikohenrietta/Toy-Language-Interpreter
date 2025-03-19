package model.values;

import model.types.IType;
import model.types.RefType;

public class RefValue implements IValue {
    private int address;
    private IType type;

    public RefValue(int address, IType type) {
        this.address = address;
        this.type = type;
    }
    public int getAddress() {
        return address;
    }
    public IType getType() {
        return new RefType(type);
    }
    public IType getInnerType() {
        return type;
    }
    public boolean equals(IValue other) {
        return other instanceof RefValue && address == ((RefValue) other).getAddress() && this.type == ((RefValue) other).getType();
    }
    @Override
    public String toString() {
        return "[address=" + address + ", type=" + type + "]";
    }
}

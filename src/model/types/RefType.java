package model.types;

import model.values.IValue;
import model.values.RefValue;

public class RefType implements IType{
    private IType inner;

    public RefType(IType inner) {
        this.inner = inner;
    }
    public IType getInner() {
        return inner;
    }
    public void setInner(IType newInner) {
        this.inner = newInner;
    }
    public boolean equals(IType other) {
        return other instanceof RefType && inner.equals(((RefType)other).getInner());
    }
    public IValue defaultValue(){
        return new RefValue(0, inner);
    }
    @Override
    public String toString() {
        return "ref("+inner.toString()+")";
    }
}

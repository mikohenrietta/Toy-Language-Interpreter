package model.values;


import model.types.IType;
import model.types.StringType;

public class StringValue implements IValue {
    private String value;
    public StringValue(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
    @Override
    public boolean equals(IValue other){
        return other instanceof StringValue && this.value.equals(((StringValue)other).value);
    }
    @Override
    public String toString() {
        return String.valueOf(value);
    }
    @Override
    public IType getType() {
        return new StringType();
    }
}

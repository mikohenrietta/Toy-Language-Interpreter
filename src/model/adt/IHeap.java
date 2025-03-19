package model.adt;

import exceptions.KeyNotFoundException;
import model.values.IValue;

import java.util.Map;

public interface IHeap {
    int allocate(IValue value);
    void delete(int address) throws KeyNotFoundException;
    boolean exists(int address);
    void set(int address, IValue value);
    IValue get(int address) throws KeyNotFoundException;
    Map<Integer, IValue> getValues();
    void setContent(Map<Integer, IValue> content);
}

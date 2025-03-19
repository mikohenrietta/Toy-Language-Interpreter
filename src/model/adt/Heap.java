package model.adt;

import exceptions.KeyNotFoundException;
import model.values.IValue;

import java.util.HashMap;
import java.util.Map;

public class Heap implements IHeap{
    private Map<Integer, IValue> values;
    private int freeAddr;
    public Heap() {
        values = new HashMap<Integer, IValue>();
        freeAddr = 1;
    }
    public int allocate(IValue value) {
        // we need to check the addresses up until the free address
        for(int i = 1; i < freeAddr; i++) {
            if(!values.containsKey(i))
            {
                values.put(i, value);
                return i;
            }
        }
        values.put(freeAddr, value);
        freeAddr++;
        return freeAddr - 1;
    }
    public boolean exists(int addr){
        return values.containsKey(addr);
    }
    public void set(int addr, IValue value) {
        values.replace(addr, value);
    }
    public IValue get(int addr) throws KeyNotFoundException {
        return values.get(addr);
    }
    public void delete(int addr) throws KeyNotFoundException{
        values.remove(addr);
    }
    public Map<Integer, IValue> getValues() {
        return values;
    }

    @Override
    public void setContent(Map<Integer, IValue> content) {
        values = content;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Integer key: values.keySet()){
            str.append(key.toString() + " -> " + values.get(key).toString() + "\n");
        }
        return "Heap contains:\n" + str.toString();
    }
}

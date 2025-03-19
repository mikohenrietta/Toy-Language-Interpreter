package model.expressions;

import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import model.adt.IHeap;
import model.adt.IMap;
import model.types.IType;
import model.types.IntType;
import model.types.RefType;
import model.values.IValue;
import model.values.RefValue;

public class ReadHeap implements IExpression {
    private IExpression expression;
    public ReadHeap(IExpression expression) {
        this.expression = expression;
    }
    public IExpression deepCopy() {
        return new ReadHeap(expression.deepCopy());
    }
    public IValue evaluate(IMap<String, IValue> symtable, IHeap  heap) throws KeyNotFoundException, ExpressionException {
        IValue valueExpression = expression.evaluate(symtable, heap);
        if(valueExpression.getType().equals(new RefType(null))){
            throw new ExpressionException(expression.toString() + " is not a ref expression");
        }
        int address = ((RefValue) valueExpression).getAddress();
        if(!heap.exists(address)){
            throw new KeyNotFoundException(expression.toString() + " does not exist in the heap");
        }
        IValue value = heap.get(address);
        return value;
    }
    public IType typecheck(IMap<String, IType> typeEnv) throws KeyNotFoundException, ExpressionException {
        IType type;
        type = expression.typecheck(typeEnv);
        if(type instanceof RefType){
            RefType refType = (RefType) type;
            return refType.getInner();
        }
        else throw new ExpressionException("The rH argument is not a RefType");
    }
    @Override
    public String toString(){
        return "rH(" + expression.toString() + ")";
    }
}

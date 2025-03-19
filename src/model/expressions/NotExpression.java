package model.expressions;

import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import exceptions.StatementException;
import model.adt.IHeap;
import model.adt.IMap;
import model.types.BoolType;
import model.types.IType;
import model.values.BoolValue;
import model.values.IValue;

public class NotExpression implements IExpression {
    IExpression expression;
    public NotExpression(IExpression expression) {
        this.expression = expression;
    }
    public IValue evaluate(IMap<String, IValue> symTable, IHeap heap) throws KeyNotFoundException, ExpressionException {
        IValue r = expression.evaluate(symTable, heap);
        return new BoolValue(!((BoolValue) r).getValue());
    }

    @Override
    public IType typecheck(IMap<String, IType> typeEnv) throws KeyNotFoundException, ExpressionException {
        IType type = expression.typecheck(typeEnv);
        if(type.equals(new BoolType())){
            return new BoolType();
        }
        else{
            throw new ExpressionException("Operand is not a boolean");
        }
    }

    @Override
    public IExpression deepCopy() {
        return new NotExpression(expression.deepCopy());
    }

    @Override
    public String toString() {
        return "!("+expression.toString()+")";
    }
}

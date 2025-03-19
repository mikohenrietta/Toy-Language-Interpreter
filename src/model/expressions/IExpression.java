package model.expressions;

import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import model.adt.IHeap;
import model.adt.IMap;
import model.state.ProgramState;
import model.types.IType;
import model.values.IValue;

public interface IExpression {
    IValue evaluate(IMap<String, IValue> symTable, IHeap heap) throws KeyNotFoundException, ExpressionException;
    IType typecheck(IMap<String, IType> typeEnv) throws KeyNotFoundException, ExpressionException;
    IExpression deepCopy();
}

package model.expressions;

import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import exceptions.StatementException;
import model.adt.IHeap;
import model.adt.IMap;
import model.types.BoolType;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.BoolValue;

public class LogicalExpression implements IExpression {
    private IExpression expression1;
    private IExpression expression2;
    private LogicalOperation operation;
    public LogicalExpression(IExpression expression1, IExpression expression2, LogicalOperation operation) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operation = operation;
    }

    @Override
    public String toString() {
        return expression1.toString() + " " + operation.toString().toLowerCase() + " " + expression2.toString();
    }

    public IValue evaluate(IMap<String, IValue> symTable, IHeap heap) throws KeyNotFoundException, ExpressionException {
        var leftExpression = expression1.evaluate(symTable, heap);
        var rightExpression = expression2.evaluate(symTable, heap);

        if(! leftExpression.getType().equals(new BoolType()) || !rightExpression.getType().equals(new BoolType()) ) {
            throw new ExpressionException("Left and right expressions are not the same");
        }
        if(operation == LogicalOperation.AND) {
            return new BoolValue(((BoolValue)leftExpression).getValue() && ((BoolValue)rightExpression).getValue());
        }
        else{
            return new BoolValue(((BoolValue)leftExpression).getValue() || ((BoolValue)rightExpression).getValue());
        }
    }
    public IType typecheck(IMap<String, IType> typeEnv) throws KeyNotFoundException, ExpressionException {
        IType type1, type2;
        type1 = expression1.typecheck(typeEnv);
        type2 = expression2.typecheck(typeEnv);
        if(type1.equals(new BoolType())){
            if(type2.equals(new BoolType())){
                if(operation.equals(LogicalOperation.OR) || operation.equals(LogicalOperation.AND)){
                    return new BoolType();
                }
                else throw new ExpressionException("Operation type is incorrect");
            }
            else throw new ExpressionException("Second operand is not a boolean");
        }
        else throw new ExpressionException("First operand is not a boolean");
    }
    public IExpression deepCopy() {
        return new LogicalExpression(expression1.deepCopy(), expression2.deepCopy(), operation);
    }
}

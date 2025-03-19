package model.expressions;

import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import model.adt.IHeap;
import model.adt.IMap;
import model.types.BoolType;
import model.types.IType;
import model.types.IntType;
import model.values.BoolValue;
import model.values.IValue;
import model.values.IntValue;

public class RelationalExpression implements IExpression {
    private IExpression left;
    private IExpression right;
    private String operator;
    public RelationalExpression(IExpression left, IExpression right, String operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public IValue evaluate(IMap<String, IValue> symTable, IHeap heap) throws KeyNotFoundException, ExpressionException {
        IValue left = this.left.evaluate(symTable, heap);
        IValue right = this.right.evaluate(symTable, heap);
        if(!left.getType().equals(new IntType()) || !right.getType().equals(new IntType()))
            throw new ExpressionException("Type has to be INT");
        switch (operator) {
            case"<":
                return new BoolValue( ((IntValue)left).getValue() < ((IntValue)right).getValue());
            case ">":
                if(((IntValue)left).getValue() > ((IntValue)right).getValue())
                    return new BoolValue(true);
                else
                    return new BoolValue(false);
            case "==":
                return new BoolValue( ((IntValue)left).getValue() == ((IntValue)right).getValue());
            case "!=":
                return new BoolValue( ((IntValue)left).getValue() != ((IntValue)right).getValue());
            case "<=":
                return new BoolValue( ((IntValue)left).getValue() <= ((IntValue)right).getValue());
            case ">=":
                return new BoolValue( ((IntValue)left).getValue() >= ((IntValue)right).getValue());
            default:
                throw new ExpressionException("Unsupported operator: " + operator);
        }
    }
    @Override
    public String toString(){
        return "(" + left.toString() + " " + operator + " " + right.toString() + ")";
    }
    public IType typecheck(IMap<String, IType> typeEnv) throws KeyNotFoundException, ExpressionException {
        IType type1, type2;
        type1 = left.typecheck(typeEnv);
        type2 = right.typecheck(typeEnv);
        if(type1.equals(new IntType())){
            if(type2.equals(new IntType())){
                if(operator.equals("==") || operator.equals("!=") || operator.equals("<=") || operator.equals(">=") || operator.equals("<") || operator.equals(">"))
                    return new BoolType();
                else throw new ExpressionException("Operator type is not valid");
            }
            else throw new ExpressionException("Second operand is not an integer");
        }
        else throw new ExpressionException("First operand is not an integer");
    }
    @Override
    public IExpression deepCopy() {
        return new RelationalExpression(left.deepCopy(), right.deepCopy(), operator);
    }
}

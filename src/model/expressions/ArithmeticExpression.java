package model.expressions;

import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import model.adt.IHeap;
import model.adt.IMap;
import model.state.ProgramState;
import model.statement.IStatement;
import model.types.IType;
import model.types.IntType;
import model.types.StringType;
import model.values.IValue;
import model.values.IntValue;

public class ArithmeticExpression implements IExpression{
    private IExpression e1;
    private IExpression e2;
    private int op;
    public ArithmeticExpression(int op, IExpression e1, IExpression e2) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }
    public IValue evaluate(IMap<String, IValue> symbolTable, IHeap heap) throws KeyNotFoundException, ExpressionException {
        IValue v1,v2;
        v1 = e1.evaluate(symbolTable, heap);
        if(v1.getType().equals(new IntType())){
            v2 = e2.evaluate(symbolTable, heap);
            if(v2.getType().equals(new IntType())){
                IntValue i1 = (IntValue)v1;
                IntValue i2 = (IntValue)v2;
                int n1, n2;
                n1 = i1.getValue();
                n2 = i2.getValue();
                switch(op){
                    case 1:
                        return new IntValue(n1 + n2);
                    case 2:
                        return new IntValue(n1 - n2);
                    case 3:
                        return new IntValue(n1 * n2);
                    case 4:
                        if( n2 == 0 ) throw new ExpressionException("Division by zero");
                        else return new IntValue(n1 / n2);
                }
            }
            else throw new ExpressionException("Second operand is not an integer");
        }
        else throw new ExpressionException("First operand is not an integer");
        return null;
    }
    public IExpression deepCopy(){
        return new ArithmeticExpression(op, e1.deepCopy(), e2.deepCopy());
    }

    @Override
    public IType typecheck(IMap<String, IType> typeEnv) throws KeyNotFoundException, ExpressionException {
        IType type1, type2;
        type1 = e1.typecheck(typeEnv);
        type2 = e2.typecheck(typeEnv);
        if(type1.equals(new IntType())){
            if(type2.equals(new IntType())){
                if(op == 1 || op == 2 || op == 3 || op == 4){
                    return new IntType();
                }
                else throw new ExpressionException("Operation is not of type int");
            }
            else throw new ExpressionException("Second operand is not an integer");
        }
        else throw new ExpressionException("First operand is not an integer");
    }

    @Override
    public String toString(){
        return switch (op) {
            case 1 -> e1.toString() + "+" + e2.toString();
            case 2 -> e1.toString() + "-" + e2.toString();
            case 3 -> e1.toString() + "*" + e2.toString();
            case 4 -> e1.toString() + "/" + e2.toString();
            default -> null;
        };
    }

}

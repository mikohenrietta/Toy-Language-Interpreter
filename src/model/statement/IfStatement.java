package model.statement;

import com.sun.jdi.BooleanType;
import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import exceptions.StatementException;
import model.adt.IMap;
import model.expressions.IExpression;
import model.state.ProgramState;
import model.types.BoolType;
import model.types.IType;
import model.values.BoolValue;
import model.values.IValue;

public class IfStatement implements IStatement {
    private IExpression expression;
    private IStatement thenStatement;
    private IStatement elseStatement;
    public IfStatement(IExpression expression, IStatement thenStatement, IStatement elseStatement) {
        this.expression = expression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }
    @Override
    public String toString(){
        return "IF(" + expression.toString() + ") THEN{" + thenStatement.toString() +"}ELSE{"+ elseStatement.toString() +"}";
    }

    public ProgramState execute(ProgramState state) throws StatementException, KeyNotFoundException, ExpressionException {
        IValue expVal = this.expression.evaluate(state.getSymTable(), state.getHeap());
        if (!(expVal.getType() instanceof BoolType)) {
            throw new StatementException(expVal.toString() + " is not a boolean");
        }
        if(((BoolValue)expVal).getValue()){
            state.getExeStack().push(thenStatement);
        }
        else{
            state.getExeStack().push(elseStatement);
        }
        return null;
    }

    @Override
    public IMap<String, IType> typecheck(IMap<String, IType> typeEnv) throws StatementException, KeyNotFoundException, ExpressionException {
        IType typexp = expression.typecheck(typeEnv);
        if(typexp.equals(new BoolType())){
            thenStatement.typecheck(typeEnv.deepCopy());
            elseStatement.typecheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else throw new StatementException(typexp + " is not of type boolean");
    }

    public IStatement deepCopy() {
        return new IfStatement(expression.deepCopy(), thenStatement.deepCopy(), elseStatement.deepCopy());
    }
}

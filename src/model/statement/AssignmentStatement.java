package model.statement;

import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import exceptions.StatementException;
import model.adt.IMap;
import model.adt.IStack;
import model.expressions.IExpression;
import model.state.ProgramState;
import model.types.IType;
import model.values.IValue;

public class AssignmentStatement implements IStatement {
    private String variable;
    private IExpression expression;
    public AssignmentStatement(String variable, IExpression expression) {
        this.variable = variable;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return variable + " = " + expression.toString();
    }

    public ProgramState execute(ProgramState state) throws StatementException, KeyNotFoundException, ExpressionException {
        if (!state.getSymTable().contains(this.variable)) {
            throw new StatementException("Variable " + variable + " does not exist");
        }
        IValue evalValue = this.expression.evaluate(state.getSymTable(), state.getHeap());
        IType type = state.getSymTable().get(variable).getType();

        if (evalValue.getType().equals(type)) {
            state.getSymTable().insert(this.variable, evalValue);
        } else {
            throw new StatementException("Variable does not match expression");
        }
        return null;
    }

    @Override
    public IMap<String, IType> typecheck(IMap<String, IType> typeEnv) throws StatementException, KeyNotFoundException, ExpressionException {
        IType typevar = typeEnv.get(variable);
        IType typexp = expression.typecheck(typeEnv);
        if(typevar.equals(typexp)) {
            return typeEnv;
        }
        else
        {
            throw new StatementException("Variable " + variable + " does not match expression type");
        }
    }

    public IStatement deepCopy() {
        return new AssignmentStatement(variable, expression.deepCopy());
    }
}

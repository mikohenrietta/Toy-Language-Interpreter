package model.statement;

import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import exceptions.StatementException;
import model.adt.GenericStack;
import model.adt.IMap;
import model.state.ProgramState;
import model.types.IType;

public class ForkStatement implements IStatement {
    private IStatement stmt;
    public ForkStatement(IStatement statement) {
        this.stmt = statement;
    }
    public ProgramState execute(ProgramState state) {
        GenericStack<IStatement> stack = new GenericStack<>();
        ProgramState prg =  new ProgramState(stack, state.getSymTable().deepCopy(), state.getOut(), stmt, state.getFileTable(),state.getHeap());
        return prg;
    }
    @Override
    public String toString() {
        return "fork("+ stmt + ")";
    }

    @Override
    public IMap<String, IType> typecheck(IMap<String, IType> typeEnv) throws StatementException, KeyNotFoundException, ExpressionException {
        return stmt.typecheck(typeEnv.deepCopy());
    }

    @Override
    public IStatement deepCopy() {
        return new ForkStatement(stmt.deepCopy());
    }
}

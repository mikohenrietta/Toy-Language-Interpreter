package model.state;
import exceptions.EmptyStackException;
import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import exceptions.StatementException;
import model.adt.*;
import model.values.IValue;
import model.statement.IStatement;
import model.values.StringValue;

import java.io.BufferedReader;

public class ProgramState {
    private  IStack<IStatement> exeStack;
    private IMap<String, IValue> symTable;
    private IList<IValue> out;
    private IStatement originalProgram;
    private IFileTable fileTable;
    private IHeap heap;
    private static int nextId=0;
    private int id;

    public ProgramState(IStack<IStatement> stk, IMap<String, IValue> symTable, IList<IValue> out, IStatement prg, IFileTable fileTab, IHeap heap) {
        this.exeStack = stk;
        this.symTable = symTable;
        this.out = out;
        this.originalProgram = prg.deepCopy();
        this.fileTable = fileTab;
        this.heap = heap;
        exeStack.push(originalProgram);
        this.id = this.getNextId();
    }
    public IStack<IStatement> getExeStack() {
        return exeStack;
    }
    public IMap<String, IValue> getSymTable() {
        return symTable;
    }
    public IList<IValue> getOut() {
        return out;
    }
    public IHeap getHeap() { return heap; }
    public void setExeStack(IStack<IStatement> stk) {
        this.exeStack = stk;
    }
    public void setSymTable(IMap<String, IValue> symtbl) {
        this.symTable = symtbl;
    }
    public void setOut(IList<IValue> out) {
        this.out = out;
    }
    public IFileTable getFileTable() {
        return fileTable;
    }

    @Override
    public String toString(){
        return "Id: "+this.id +"\n"+ this.exeStack.toString() + this.symTable.toString() + this.out.toString()+this.fileTable.toString()+this.heap.toString()+"\n";
    }
    public boolean isNotCompleted(){
        return !exeStack.isEmpty();
    }
    public synchronized int getNextId(){
        return ++nextId;
    }

    public int getId(){
        return this.id;
    }

    public ProgramState oneStep() throws EmptyStackException, StatementException, KeyNotFoundException, ExpressionException {
        if(exeStack.isEmpty()) {
            throw new EmptyStackException("The stack is empty");
        }
        IStatement statement = exeStack.pop();
        return statement.execute(this);
    }
}

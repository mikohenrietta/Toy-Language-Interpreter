package model.adt;
import exceptions.EmptyStackException;

import java.util.Stack;

public interface IStack<T> {
    T pop() throws EmptyStackException;
    void push(T element);
    boolean isEmpty();
    int size();
    Stack<T> getStack();
}

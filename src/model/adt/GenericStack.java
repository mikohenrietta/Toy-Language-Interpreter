package model.adt;
import exceptions.EmptyStackException;
import java.util.Stack;

public class GenericStack<T> implements IStack<T>{
    private Stack<T> stack;
    public GenericStack() {
        stack = new Stack<T>();
    }

    public T pop() throws EmptyStackException{
        if (stack.isEmpty())
            throw new EmptyStackException("Stack is empty");
        return stack.pop();
    }
    public void push(T element)
    {
        stack.push(element);
    }
    public boolean isEmpty()
    {
        return stack.isEmpty();
    }
    public int size(){
        return stack.size();
    }
    public Stack<T> getStack()
    {
        return stack;
    }

    @Override
    public String toString()
    {
        if(stack.isEmpty())
            return "Generic stack contains:\n";
        StringBuilder str = new StringBuilder();

        for (T item: stack){
            str.append(item);
            str.append("\n");
        }

        return "Generic stack contains:\n" + str.toString();
    }
}

package model.adt;

import java.util.List;
import java.util.ArrayList;

public class GenericList<T> implements IList<T> {
    private List<T> list;

    public GenericList() {
        list = new ArrayList<T>();
    }
    public List<T> getAll(){
        return list;
    }
    public void add(T element){
        list.add(element);
    }
    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString()
    {
        if(list.isEmpty())
            return "Generic list contains:\n";
        StringBuilder str = new StringBuilder();
        for (T element : list){
            str.append(element.toString());
            str.append("\n");
        }

        return "Generic list contains:\n" + str.toString();
    }
}

package model.adt;
import java.util.List;
public interface IList<T> {
    List<T> getAll();
    void add(T element);
}

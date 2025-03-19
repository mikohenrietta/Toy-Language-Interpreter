package model.adt;

import exceptions.KeyNotFoundException;

import java.io.BufferedReader;
import java.util.List;

public interface IFileTable {
    BufferedReader get(String key) throws KeyNotFoundException;
    void insert(String key, BufferedReader value);
    boolean contains(String key);
    void remove(String key) throws KeyNotFoundException;
    List<String> getElements();
}

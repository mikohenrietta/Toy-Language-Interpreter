package model.adt;

import exceptions.KeyNotFoundException;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileTable implements IFileTable {
    private Map<String, BufferedReader> map;

    public FileTable() {
        map = new HashMap<String, BufferedReader>();
    }

    public BufferedReader get(String key) throws exceptions.KeyNotFoundException {
        if (!map.containsKey(key))
            throw new KeyNotFoundException("Key not found");
        return map.get(key);
    }

    public void insert(String key, BufferedReader value) {
        map.put(key, value);
    }

    public boolean contains(String key) {
        return map.containsKey(key);
    }

    public void remove(String key) throws KeyNotFoundException {
        if (!map.containsKey(key))
            throw new KeyNotFoundException("Key not found");
        map.remove(key);
    }
    public List<String> getElements() {
        List<String> list = new ArrayList<String>();
        for (String key : map.keySet()) {
            list.add(key.toString() + " -> " + map.get(key).toString());
        }
        return list;
    }

    @Override
    public String toString() {
        if (map.isEmpty())
            return "File table contains:\n";
        StringBuilder str = new StringBuilder();
        for (String key : map.keySet()) {
            str.append(key.toString() + " -> " + map.get(key).toString() + "\n");
        }
        return "File table contains:\n" + str.toString();
    }
}

package model.adt;

import java.util.HashMap;
import java.util.Map;
import exceptions.KeyNotFoundException;

public class GenericMap<K,V> implements IMap<K,V> {
    private Map<K,V> map;
    public GenericMap() {
        map = new HashMap<K,V>();
    }
    public V get(K key) throws exceptions.KeyNotFoundException
    {
        if(!map.containsKey(key))
            throw new KeyNotFoundException("Key not found");
        return map.get(key);
    }
    public void insert(K key, V value) {
        map.put(key, value);
    }

    public boolean contains(K key) {
        return map.containsKey(key);
    }
    public void remove(K key) throws KeyNotFoundException
    {
        if(!map.containsKey(key))
            throw new KeyNotFoundException("Key not found");
        map.remove(key);
    }
    public GenericMap(Map<K,V> map) {
        this.map = new HashMap<K, V>(map);
    }
    @Override
    public Map<K, V> getContent() {
        return map;
    }

    @Override
    public void update(K key, V value) {
        map.replace(key, value);
    }

    @Override
    public String toString(){
        if(map.isEmpty())
            return "Generic map contains:\n";
        StringBuilder str = new StringBuilder();
        for (K key: map.keySet()){
            str.append(key.toString() + " -> " + map.get(key).toString() + "\n");
        }

        return "Generic map contains:\n" + str.toString();
    }

    @Override
    public GenericMap<K, V> deepCopy() {
        return new GenericMap<>(this.map);
    }
}

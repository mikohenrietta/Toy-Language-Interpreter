package model.adt;

import exceptions.KeyNotFoundException;

import java.util.Map;

public interface IMap<K,V> {
    V get(K key) throws KeyNotFoundException;
    void insert(K key, V value);
    boolean contains(K key);
    void remove(K key) throws KeyNotFoundException;
    void update(K key, V value);
    Map<K,V> getContent();
    IMap<K,V> deepCopy();
}

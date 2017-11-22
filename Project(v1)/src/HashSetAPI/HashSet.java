/*
 * Project for data structure course in university
 * Mindaugas Dirginčius, 2017
 */
package HashSetAPI;

import java.util.LinkedList;

/**
 *
 * @author Mindaugas
 */
public class HashSet<K, E> implements HashSetAPI<K, E> {
    private static final int SIZE = 2147483647;
    private LinkedList<K> keys;
    private LinkedList<E>[] values;
    
    public HashSet(){
        keys = new LinkedList<>();
        values = new LinkedList[SIZE];
    }
    
    @Override
    public void add(K k, E e) {
        keys.add(k);
        
        if(values[k.hashCode()] == null){
            values[k.hashCode()] = new LinkedList<E>();
        }
        
        values[k.hashCode()].add(e);
    }

    @Override
    public void remove(K k, E e) {
        if(keys.contains(k)){
            keys.remove(k);
            
            if(values[k.hashCode()].size() == 1){
                values[k.hashCode()] = null;
            } else {
                values[k.hashCode()].remove(e);
            }
        }
    }

    @Override
    public E get(K k) {
        if(keys.contains(k)){
            return values[k.hashCode()].getFirst();
        } else {
            return null;
        }
    }

    @Override
    public K[] getKeys() {
        return (K[])keys.clone();
    }
    
}

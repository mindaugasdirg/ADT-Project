/*
 * Project for data structure course in university
 * Mindaugas Dirginčius, 2017
 */
package HashSetAPI;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;

/**
 *
 * @author Mindaugas
 */
public class HashSet<K, E> implements HashSetAPI<K, E> {
    private static final int SIZE = 2000000;
    private LinkedList<K> keys;
    private LinkedList<Pair<K, E>>[] values;
    private int size;
    
    public HashSet(){
        keys = new LinkedList<>();
        values = new LinkedList[SIZE];
        size = 0;
    }
    
    @Override
    public void add(K k, E e) {
        keys.add(k);
        
        if(values[k.hashCode()] == null){
            values[k.hashCode()] = new LinkedList<Pair<K, E>>();
        }
        
        Pair<K, E> pair = new Pair<>(k, e);
        values[k.hashCode()].add(pair);
        size++;
    }

    @Override
    public void remove(K k) {
        if(keys.contains(k)){
            keys.remove(k);
            
            if(values[k.hashCode()].size() == 1){
                values[k.hashCode()] = null;
                size--;
            } else {
                values[k.hashCode()].remove(new Pair(k, null));
                size--;
            }
        }
    }

    @Override
    public E get(K k) {
        if(keys.contains(k)){
            if(values[k.hashCode()].size() == 1){
                return values[k.hashCode()].getFirst().getValue();
            } else {
                Iterator<Pair<K, E>> i = values[k.hashCode()].iterator();
                
                while(i.hasNext()){
                    Pair<K, E> item = i.next();
                    
                    if(item.getKey().equals(k)){
                        return item.getValue();
                    }
                }
            }
        }
        
        return null;
    }

    @Override
    public Object[] getKeys() {
        Object[] keyArray = new Object[keys.size()];
        
        for(int i = 0; i < keys.size(); i++){
            keyArray[i] = keys.get(i);
        }
        
        return keyArray;
    }
    
    @Override
    public int size(){
        return size;
    }
    
    private class Pair<K, E>{
        private K key;
        private E value;
        
        public Pair(K k, E e){
            key = k;
            value = e;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public E getValue() {
            return value;
        }

        public void setValue(E value) {
            this.value = value;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 67 * hash + Objects.hashCode(this.key);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Pair<?, ?> other = (Pair<?, ?>) obj;
            if (!Objects.equals(this.key, other.key)) {
                return false;
            }
            return true;
        }
    }
}

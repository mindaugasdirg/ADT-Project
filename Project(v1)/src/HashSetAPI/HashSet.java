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
public class HashSet<K, E> implements HashSetAPI<K, E>{
    private static final int SIZE = 2000000;
    private LinkedList<K> keys;
    private LinkedList<Pair<K, E>>[] values;
    private int size;
    
    public HashSet(){
        keys = new LinkedList<>();
        values = new LinkedList[SIZE];
        size = 0;
    }
    
    private int getIndex(K k){
        return k.hashCode() % SIZE < 0 ? 0 : k.hashCode() % SIZE;
    }
    
    @Override
    public void add(K k, E e) {
        keys.add(k);
        
        if(values[getIndex(k)] == null){
            values[getIndex(k)] = new LinkedList<Pair<K, E>>();
        }
        
        Pair<K, E> pair = new Pair<>(k, e);
        values[getIndex(k)].add(pair);
        size++;
    }

    @Override
    public void remove(K k) {
        if(keys.contains(k)){
            keys.remove(k);
            
            if(values[getIndex(k)].size() == 1){
                values[getIndex(k)] = null;
                size--;
            } else {
                values[getIndex(k)].remove(new Pair(k, null));
                size--;
            }
        }
    }

    @Override
    public E get(K k) {
        if(keys.contains(k)){
            if(values[getIndex(k)].size() == 1){
                return values[getIndex(k)].getFirst().getValue();
            } else {
                Iterator<Pair<K, E>> i = values[getIndex(k)].iterator();
                
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

    @Override
    public Iterator<E> iterator() {
        return new HashSetIterator<E>();
    }
    
    private class HashSetIterator<E> implements Iterator<E>{
        int i;
        int j;
        K key;
        
        public HashSetIterator(){
            i = 0;
            j = 0;
            key = keys.get(i);
        }
        
        @Override
        public boolean hasNext() {
            return i != keys.size();
        }

        @Override
        public E next() {
            if(i != keys.size()){
                E item = (E) values[key.hashCode() % SIZE].get(j).getValue();
                
                j++;
                
                if(j == values[key.hashCode()].size()){
                    i++;
                    j = 0;
                    if(i < keys.size()){
                        key = keys.get(i);
                    } else {
                        key = null;
                    }
                }
                
                return item;
            }
            
            return null;
        }
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

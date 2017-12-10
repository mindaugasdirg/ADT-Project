/*
 * Project for data structure course in university
 * Mindaugas Dirginčius, 2017
 */
package HashSetAPI;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

/**
 *
 * @author Mindaugas
 */
public class HashSet<K, E> implements HashSetAPI<K, E>{
    private static final int SIZE = 2000000;
    //private ArrayList<K> keys;
    private PairNode<K, E>[] values;
    private int size;
    
    public HashSet(){
        //keys = new ArrayList<>();
        values = new PairNode[SIZE];
        size = 0;
    }
    
    private int getIndex(K k){
        return k.hashCode() % SIZE < 0 ? 0 : k.hashCode() % SIZE;
    }
    
    @Override
    public void add(K k, E e) {
        PairNode<K, E> last = values[getIndex(k)];
        
        if(last == null){
            values[getIndex(k)] = new PairNode<>(k ,e);
            size++;
        } else {
            while(last.getNext() != null){
                if(last.getKey().equals(k)){ // no dublicates
                    return;
                }
                
                last = last.getNext();
            }
            
            last.setNext(new PairNode<>(k, e));
            size++;
        }
        /*if(!keys.contains(k)){
            keys.add(k);
            PairNode<K, E> pair = new PairNode<>(k, e);
            pair.setNext(values[getIndex(k)]);
            values[getIndex(k)] = pair;
            size++;
        }*/
    }

    @Override
    public void remove(K k) {
        //if(keys.contains(k)){
            //keys.remove(k);
            
            if(values[getIndex(k)].getKey().equals(k)){
                values[getIndex(k)] = values[getIndex(k)].getNext();
                size--;
                return;
            } else if(values[getIndex(k)] != null){
                PairNode<K, E> last = values[getIndex(k)];
                
                for(PairNode<K, E> i = values[getIndex(k)].getNext(); i.getNext() != null; i = i.getNext()){
                    if(i.getKey().equals(k)){
                        last.setNext(i.getNext());
                        size--;
                        return;
                    }
                    
                    last = i;
                }
            }
        //}
    }

    @Override
    public E get(K k) {
        for(PairNode<K, E> i = values[getIndex(k)]; i != null; i = i.getNext()){
            if(i.getKey().equals(k)){
                return i.getValue();
            }
        }
        
        return null;
    }
    
    @Override
    public void clear(){
        //keys = new ArrayList<>();
        values = new PairNode[SIZE];
        size = 0;
    }

    @Override
    public Object[] getKeys() {
        int count = 0;
        Object[] keys = new Object[size];
        for(PairNode<K ,E> node : values){
            PairNode<K, E> i = node;
            
            while(i != null){
                keys[count] = i.getKey();
                i = i.getNext();
                count++;
            }
        }
        
        return keys;
        /*
        Object[] keyArray = new Object[keys.size()];
        
        for(int i = 0; i < keys.size(); i++){
            keyArray[i] = keys.get(i);
        }
        
        return keyArray;*/
    }
    
    @Override
    public int size(){
        return size;
    }
    
    @Override
    public boolean isEmpty(){
        return size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new HashSetIterator<E>();
    }
    
    private class HashSetIterator<E> implements Iterator<E>{
        int i;
        PairNode<K, E> j;
        //K key;
        
        public HashSetIterator(){
            i = 0;
            //key = keys.get(i);
            j = (PairNode<K, E>) values[i];
            
            while(j == null && i < SIZE - 1){
                i++;
                j = (PairNode<K, E>) values[i];
            }
        }
        
        @Override
        public boolean hasNext() {
            return j != null;
        }

        @Override
        public E next() {
            if(i < SIZE && j != null){
                E item = j.getValue();
                
                j = j.getNext();
                
                while(j == null && i < SIZE - 1){
                    i++;
                    j = (PairNode<K, E>) values[i];
                }
                
                return item;
            }
            
            return null;
        }
    }
    
    private class PairNode<K, E>{
        private K key;
        private E value;
        private PairNode<K, E> next;
        
        public PairNode(K k, E e){
            key = k;
            value = e;
            next = null;
        }
        
        public PairNode<K, E> getNext(){
            return next;
        }
        
        public void setNext(PairNode<K, E> node){
            next = node;
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
            final PairNode<?, ?> other = (PairNode<?, ?>) obj;
            if (!Objects.equals(this.key, other.key)) {
                return false;
            }
            return true;
        }
    }
}

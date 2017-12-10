/*
 * Project for data structure course in university
 * Mindaugas Dirginčius, 2017
 */
package HashSetAPI;

import java.util.Iterator;
import java.util.Objects;

/**
 * Abstract data type with key -> item structure
 * 
 * @author Mindaugas
 */
public class HashSet<K, E> implements HashSetAPI<K, E>{
    private static final int SIZE = 2000000;
    private PairNode<K, E>[] values;
    private int size;
    
    /**
     * Default contructor
     */
    public HashSet(){
        values = new PairNode[SIZE];
        size = 0;
    }
    
    private int getIndex(K k){
        return k.hashCode() % SIZE < 0 ? 0 : k.hashCode() % SIZE;
    }
    
    /**
     * Adds key and item to the list.
     * 
     * @param k Key of the item
     * @param e Item
     */
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
    }

    /**
     * Removes element form the list who has the the key k
     * 
     * @param k Key of the item to be removed
     */
    @Override
    public void remove(K k) {
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
    }

    /**
     * Get item which key is given.
     * 
     * @param k Key of the item
     * @return item which has the key k or null
     */
    @Override
    public E get(K k) {
        for(PairNode<K, E> i = values[getIndex(k)]; i != null; i = i.getNext()){
            if(i.getKey().equals(k)){
                return i.getValue();
            }
        }
        
        return null;
    }
    
    /**
     * Clears the list.
     */
    @Override
    public void clear(){
        values = new PairNode[SIZE];
        size = 0;
    }

    /**
     * Return array of all the keys in the list.
     * 
     * @return Array of keys
     */
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
    }
    
    /**
     * Returns the number of elements in the list.
     * 
     * @return the number of elements in the list
     */
    @Override
    public int size(){
        return size;
    }
    
    /**
     * Checks if list is empty.
     * 
     * @return true if list has 0 items
     */
    @Override
    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * Returns iterator to the list
     * 
     * @return list's iterator
     */
    @Override
    public Iterator<E> iterator() {
        return new HashSetIterator<E>();
    }
    
    private class HashSetIterator<E> implements Iterator<E>{
        int i;
        PairNode<K, E> j;
        
        /**
         * Default constructor
         */
        public HashSetIterator(){
            i = 0;
            j = (PairNode<K, E>) values[i];
            
            while(j == null && i < SIZE - 1){
                i++;
                j = (PairNode<K, E>) values[i];
            }
        }
        
        /**
         * Checks if there is items left in the list.
         * 
         * @return true if not all elements have been returned
         */
        @Override
        public boolean hasNext() {
            return j != null;
        }

        /**
         * Returns the next element in the list and moves pointer to the next 
         * item.
         * 
         * @return the next item in the list of null
         */
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

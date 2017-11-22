/* 
 * Project for data structure course in university
 * Mindaugas DirginÄ�ius, 2017
 */

package HashSetAPI;

/**
 *
 * @author Mindaugas
 */
public interface HashSetAPI<K, E>{
    void add(K k, E e);
    void remove(K k, E e);
    E get(K k);
    K[] getKeys();
}

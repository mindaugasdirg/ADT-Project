/*
 * Project for data structure course in university
 * Mindaugas Dirginčius, 2017
 */
package Tests;

import HashSetAPI.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.TreeMap;
import studijosKTU.Timekeeper;

/**
 *
 * @author Mindaugas
 */
public class SpeedTest {
    private static final int[] AMOUNTS = { 100, 1000, 2000, 5000, 10000, 20000, 50000 };
    
    public void executeTests(){
        addTest();
        removeTest();
        getTest();
    }
    
    private void addTest(){
        Timekeeper tk = new Timekeeper(AMOUNTS);
        Random rand = new Random();
        HashSet<Integer, Integer> hashSet = new HashSet<>();
        LinkedList<Integer> linkedList = new LinkedList<>();
        TreeMap<Integer, Integer> hashMap = new TreeMap<>();
        
        for(int amount : AMOUNTS){
            int[] data = new int[amount];
            hashSet.clear();
            linkedList.clear();
            
            for(int i = 0; i < amount; i++){
                data[i] = rand.nextInt();
            }
            
            tk.start();
            for(int i = 0; i < amount; i++){
                hashSet.add(i, data[i]);
            }
            tk.finish("HashSet.add");
            for(int i = 0; i < amount; i++){
                linkedList.add(data[i]);
            }
            tk.finish("LinkedList.add");
            for(int i = 0; i < amount; i++){
                hashMap.put(i, data[i]);
            }
            tk.finish("TreeMap.add");
            tk.seriesFinish();
        }
    }
    
    private void removeTest(){
        Timekeeper tk = new Timekeeper(AMOUNTS);
        Random rand = new Random();
        HashSet<Integer, Integer> hashSet = new HashSet<>();
        LinkedList<Integer> linkedList = new LinkedList<>();
        TreeMap<Integer, Integer> hashMap = new TreeMap<>();
        
        for(int amount : AMOUNTS){
            int[] data = new int[amount];
            hashSet.clear();
            linkedList.clear();
            
            for(int i = 0; i < amount; i++){
                data[i] = rand.nextInt();
                hashSet.add(i, data[i]);
                linkedList.add(data[i]);
            }
            
            tk.start();
            for(int i = amount / 2; i >= 0; i--){
                hashSet.remove(i);
            }
            tk.finish("HashSet.remove");
            for(int i = amount / 2; i >= 0; i--){
                linkedList.remove(i);
            }
            tk.finish("LinkedList.remove");
            for(int i = amount / 2; i >= 0; i--){
                hashMap.remove(i);
            }
            tk.finish("TreeMap.remove");
            tk.seriesFinish();
        }
    }
    
    private void getTest(){
        Timekeeper tk = new Timekeeper(AMOUNTS);
        Random rand = new Random();
        HashSet<Integer, Integer> hashSet = new HashSet<>();
        LinkedList<Integer> linkedList = new LinkedList<>();
        TreeMap<Integer, Integer> hashMap = new TreeMap<>();
        
        for(int amount : AMOUNTS){
            int[] data = new int[amount];
            hashSet.clear();
            linkedList.clear();
            
            for(int i = 0; i < amount; i++){
                data[i] = rand.nextInt();
                hashSet.add(i, data[i]);
                linkedList.add(data[i]);
                hashMap.put(i, data[i]);
            }
            
            tk.start();
            for(int i = 0; i < amount / 2; i++){
                hashSet.get(i);
            }
            tk.finish("HashSet.get");
            for(int i = 0; i < amount / 2; i++){
                linkedList.get(i);
            }
            tk.finish("LinkedList.get");
            for(int i = 0; i < amount / 2; i++){
                hashMap.get(i);
            }
            tk.finish("TreeMap.get");
            tk.seriesFinish();
        }
    }
    
    public static void main(String ... args){
        new SpeedTest().executeTests();
    }
}

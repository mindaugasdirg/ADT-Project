/*
 * Project for data structure course in university
 * Mindaugas Dirginčius, 2017
 */
package Tests;

import HashSetAPI.HashSet;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Testing class for HashSet data structure.
 * 
 * @author Mindaugas
 */
public class HashSetTests {
    static void runTests(){
        test1();
        test2();
        test3();
    }

    private static void test1() {
        System.out.println("Performing test 1");
        HashSet<Integer, Integer> testSet = new HashSet<>();
        
        testSet.add(1, 1);
        testSet.add(2, 2);
        testSet.add(3, 1);
        testSet.add(4, 2);
        
        int successful = 0;
        
        if(testSet.size() == 4){
            successful++;
        }
        if(testSet.get(1) == 1){
            successful++;
        }
        if(testSet.get(2) == 2){
            successful++;
        }
        if(testSet.get(3) == 1){
            successful++;
        }
        if(testSet.get(4) == 2){
            successful++;
        }
        
        Object[] keys = testSet.getKeys();
        ArrayList<Integer> trueKeys = new ArrayList<>();
        trueKeys.add(1);
        trueKeys.add(2);
        trueKeys.add(3);
        trueKeys.add(4);
        for(Object key : keys){
            trueKeys.remove(key);
        }
        
        if(trueKeys.size() == 0){
            successful++;
        }
        testSet.remove(1);
        if(testSet.size() == 3 && testSet.get(1) == null){
            successful++;
        }
        
        if(successful == 7){
            System.out.println("Test 1 successful");
        } else {
            System.out.println("Test 1 failed");
        }
    }
    
    private static void test2() {
        System.out.println("Performing test 2");
        HashSet<String, Integer> testSet = new HashSet<>();
        
        testSet.add("a", 1);
        testSet.add("A", 2);
        testSet.add("b", 1);
        testSet.add("AAA", 2);
        
        int successful = 0;
        
        if(testSet.size() == 4){
            successful++;
        }
        if(testSet.get("a") == 1){
            successful++;
        }
        if(testSet.get("A") == 2){
            successful++;
        }
        if(testSet.get("b") == 1){
            successful++;
        }
        if(testSet.get("AAA") == 2){
            successful++;
        }
        
        Object[] keys = testSet.getKeys();
        ArrayList<String> trueKeys = new ArrayList<>();
        trueKeys.add("a");
        trueKeys.add("A");
        trueKeys.add("b");
        trueKeys.add("AAA");
        for(Object key : keys){
            trueKeys.remove(key);
        }
        
        if(trueKeys.size() == 0){
            successful++;
        }
        
        testSet.remove("a");
        if(testSet.size() == 3 && testSet.get("a") == null){
            successful++;
        }
        
        if(successful == 7){
            System.out.println("Test 2 successful");
        } else {
            System.out.println("Test 2 failed");
        }
    }
    
    private static void test3(){
        System.out.println("Performing test 3");
        HashSet<String, Integer> testSet = new HashSet<>();
        int successful = 0;
        
        testSet.add("a", 1);
        testSet.add("A", 2);
        testSet.add("b", 3);
        testSet.add("AAA", 5);
        
        Iterator<Integer> i = testSet.iterator();
        int count = 0;
        ArrayList<Integer> values = new ArrayList<>();
        values.add(1);
        values.add(2);
        values.add(3);
        values.add(5);
        
        while(i.hasNext()){
            values.remove(i.next());
            count++;
        }
        
        if(count == 4){
            successful++;
        }
        if(values.size() == 0){
            successful++;
        }
        
        if(successful == 2){
            System.out.println("Test 3 successful");
        } else {
            System.out.println("Test 3 failed");
        }
    }
    
    public static void main(String ... args){
        runTests();
    }
}

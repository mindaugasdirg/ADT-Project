/*
 * Project for data structure course in university
 * Mindaugas Dirginčius, 2017
 */
package Tests;

import HashSetAPI.HashSet;

/**
 *
 * @author Mindaugas
 */
public class HashSetTests {
    static void runTests(){
        test1();
        test2();
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
        if((Integer)keys[0] == 1 && (Integer)keys[1] == 2 && (Integer)keys[2] == 3 && (Integer)keys[3] == 4){
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
        if((String)keys[0] == "a" && (String)keys[1] == "A" && (String)keys[2] == "b" && (String)keys[3] == "AAA"){
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
    
    public static void main(String ... args){
        runTests();
    }
}

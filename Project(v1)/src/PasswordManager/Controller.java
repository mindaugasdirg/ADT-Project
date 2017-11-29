/*
 * Project for data structure course in university
 * Mindaugas Dirginčius, 2017
 */
package PasswordManager;

import HashSetAPI.HashSetAPI;
import java.util.Random;

/**
 *
 * @author Mindaugas
 */
public class Controller {
    HashSetAPI<String, String> data;
    
    public Controller(HashSetAPI dataSource){
        data = dataSource;
    }
    
    public String[][] getAll(){
        String[][] returnedData = new String[data.size()][];
        
        String[] keys = (String[]) data.getKeys();
        for(int i = 0; i < keys.length; i++){
            returnedData[i] = new String[] {keys[i], data.get(keys[i])};
        }
        
        return returnedData;
    }
    
    public String[] search(String key){
        return new String[] {key, data.get(key)};
    }
    
    public void newItem(String page, String username){
        String password = "aaa";
        
        data.add(page, username + ":" + password);
    }
}

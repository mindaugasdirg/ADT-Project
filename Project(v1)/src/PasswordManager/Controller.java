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
    HashSetAPI<String, Password> data;
    
    public Controller(HashSetAPI<String, Password> dataSource){
        data = dataSource;
    }
    
    public Password[] getAll(){
        Password[] returnedData = new Password[data.size()];
        
        Object[] keys = data.getKeys();
        for(int i = 0; i < keys.length; i++){
            returnedData[i] = data.get((String)keys[i]);
        }
        
        return returnedData;
    }
    
    public void remove(String key){
        data.remove(key);
    }
    
    public Password search(String key){
        return data.get(key);
    }
    
    public String newItem(String page, String username, int minBound, int maxBound){
        Password newItem = new Password.Builder()
                .setPage(page)
                .setUsername(username)
                .setMinBound(minBound)
                .setMaxBound(maxBound)
                .build();
        
        data.add(page, newItem);
        
        return newItem.getPassword();
    }
}

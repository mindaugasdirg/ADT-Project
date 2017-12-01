/*
 * Project for data structure course in university
 * Mindaugas Dirginčius, 2017
 */
package PasswordManager;

import HashSetAPI.HashSetAPI;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Mindaugas
 */
public class Controller {
    HashSetAPI<String, Password> data;
    SecretKeySpec key;
    String user;
    String pass;
    
    public Controller(HashSetAPI<String, Password> dataSource){
        data = dataSource;
        
        File auth = new File("auth");
        if(!auth.exists() || !auth.isFile()){
            try {
                auth.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public boolean register(String user, String pass) throws NoSuchAlgorithmException{
        File source = new File(user);
        
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(pass.getBytes(StandardCharsets.UTF_8));
        
        if(source.exists() && !source.isDirectory()){
            return false;
        } else {
            File auth = new File("auth");
            BufferedWriter authWriter;
            try {
                source.createNewFile();
                authWriter = new BufferedWriter(new FileWriter(auth));
                authWriter.append("\n" + user + ":" + new String(hash));
                authWriter.close();
                
                generateKey(pass);
                this.user = user;
                this.pass = pass;

                return true;
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return false;
    }
    
    public boolean login(String user, String pass) throws NoSuchAlgorithmException{
        File source = new File(user);
        
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(pass.getBytes(StandardCharsets.UTF_8));
        
        if(source.exists() && !source.isDirectory()){
            File auth = new File("auth");
            BufferedReader authReader;
            try {
                authReader = new BufferedReader(new FileReader(auth));
                
                String line;
                while((line = authReader.readLine()) != null){
                    String[] logins = line.split(":");
                    
                    if(logins[0].equals(user)){
                        String hashed = new String(hash);
                        
                        if(!hashed.equals(logins[1])){
                            return false;
                        }
                        
                        generateKey(pass);
                        this.user = user;
                        this.pass = pass;
                        data.clear();
                        
                        return true;
                    }
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return false;
    }
    
    private void generateKey(String message){
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] hash = digest.digest(message.getBytes(StandardCharsets.UTF_8));
            key = new SecretKeySpec(hash, "AES");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void saveToFile(){
        try {
            String content = "";
            
            for(Password p : data){
                content = content + p.toString() + "\n";
            }
            
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            
            byte[] encrypted = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
            
            Files.write(Paths.get(user), encrypted);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadFromFile(){
        if(key != null && user != null){
            try {
                byte[] encrypted = Files.readAllBytes(Paths.get(user));
                
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(Cipher.DECRYPT_MODE, key);

                byte[] decrypted = cipher.doFinal(encrypted);

                String content = new String(decrypted);
                String[] logins = content.split("\n");
                
                for(String login : logins){
                    Password item = new Password.Builder().loadFromString(login);
                    data.add(item.getPage(), item);
                }
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidKeyException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchPaddingException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalBlockSizeException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BadPaddingException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
        saveToFile();
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
        saveToFile();
        
        return newItem.getPassword();
    }
}

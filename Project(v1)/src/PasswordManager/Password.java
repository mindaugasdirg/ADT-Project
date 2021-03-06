/*
 * Project for data structure course in university
 * Mindaugas Dirginčius, 2017
 */
package PasswordManager;

import java.util.Random;

/**
 * Holds all the information about login.
 * 
 * @author Mindaugas
 */
public class Password {
    private static String symbols = "!#$%^&*()+,-.0123456789<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz";
    
    private String page;
    private String username;
    private String password;

    /**
     * Empty constructor.
     */
    public Password(){
        page = "";
        username = "";
        password = "";
    }
    
    /**
     * Constructor with defined fields.
     * 
     * @param page
     * @param username
     * @param password 
     */
    public Password(String page, String username, String password) {
        this.page = page;
        this.username = username;
        this.password = password;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return page + ":" + username + ":" + password;
    }
    
    /**
     * Generates new random password.
     * 
     * @param min minimum length of password
     * @param max maximum length of password
     */
    public void generateNewPassword(int min, int max){
        Random rand = new Random();
        int length = rand.nextInt(max - min) + min;
        char[] password = new char[length];
        
        for(int i = 0; i < length; i++){
            password[i] = symbols.charAt(rand.nextInt(symbols.length()));
        }
        
        this.password = new String(password);
    }
    
    /**
     * Password builder.
     */
    public static class Builder{
        private Password password;
        private int min;
        private int max;
        
        public Builder(){
            password = new Password();
            min = 8;
            max = 16;
        }
        
        public Password loadFromString(String data){
            String[] fields = data.split(":");
            
            password.page = fields[0];
            password.username = fields[1];
            password.password = fields[2];
            
            return password;
        }
        
        public Builder setPage(String page){
            password.page = page;
            
            return this;
        }
        
        public Builder setUsername(String username){
            password.username = username;
            
            return this;
        }
        
        public Builder setMinBound(int min){
            this.min = min;
            
            return this;
        }
        
        public Builder setMaxBound(int max){
            this.max = max;
            
            return this;
        }
        
        public Password build(){
            password.generateNewPassword(min, max);
            return password;
        }
    }
}

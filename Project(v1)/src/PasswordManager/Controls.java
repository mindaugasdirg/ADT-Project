/*
 * Project for data structure course in university
 * Mindaugas Dirginčius, 2017
 */
package PasswordManager;

import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *
 * @author Mindaugas
 */
public class Controls {
    private VBox root;
    private TableView table;
    
    public Controls(){
        root = new VBox();
        createTopRow();
        createTable();
        createBottomRow();
    }
    
    public VBox getLayout(){
        return root;
    }
    
    private void createTopRow(){
        HBox row = new HBox();
        
        TextField searchTF = new TextField();
        searchTF.setText("Paieška");
        
        Button searchBtn = new Button("Ieškoti");
        
        row.getChildren().addAll(searchTF, searchBtn);
        
        root.getChildren().add(row);
    }
    
    private void createTable(){
        table = new TableView();
        
        TableColumn page = new TableColumn("Puslapis");
        page.setResizable(false);
        page.setPrefWidth(300);
        TableColumn username = new TableColumn("Prisijungimo vardas");
        username.setResizable(false);
        username.setPrefWidth(300);
        TableColumn password = new TableColumn("Slaptažodis");
        password.setResizable(false);
        password.setPrefWidth(300);
        
        table.getColumns().addAll(page, username, password);
        table.setPrefHeight(450);
        
        root.getChildren().add(table);
    }
    
    private void createBottomRow(){
        HBox row = new HBox();
        
        Text pageLb = new Text();
        pageLb.setText("Puslapis:");
        TextField page = new TextField();
        
        Text usernameLb = new Text();
        usernameLb.setText("Prisijungimo vardas:");
        TextField username = new TextField();
        
        Text passwordLb = new Text();
        passwordLb.setText("Slaptažodis:");
        TextField password = new TextField();
        password.setEditable(false);
        
        Button generate = new Button("Generuoti");
        
        row.getChildren().addAll(pageLb, page, usernameLb, username, passwordLb, password, generate);
        
        root.getChildren().add(row);
    }
}

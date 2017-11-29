/*
 * Project for data structure course in university
 * Mindaugas Dirginčius, 2017
 */
package PasswordManager;

import HashSetAPI.HashSet;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *
 * @author Mindaugas
 */
public class Controls {
    private VBox root;
    private ObservableList<Password> list;
    private Controller controller;
    
    public Controls(){
        list = FXCollections.observableArrayList(new Password());
        controller = new Controller(new HashSet<>());
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
        searchBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Password found = controller.search(searchTF.getText());
                
                list.clear();
                list.add(found);
            }
            
        });
        
        Button showAllBtn = new Button("Visi");
        showAllBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showAll();
            }
            
        });
        
        row.getChildren().addAll(searchTF, searchBtn);
        
        root.getChildren().add(row);
    }
    
    private void createTable(){
        TableView table = new TableView();
        
        TableColumn page = new TableColumn("Puslapis");
        page.setResizable(false);
        page.setPrefWidth(300);
        page.setCellValueFactory(new PropertyValueFactory<>("page"));
        TableColumn username = new TableColumn("Prisijungimo vardas");
        username.setResizable(false);
        username.setPrefWidth(300);
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        TableColumn password = new TableColumn("Slaptažodis");
        password.setResizable(false);
        password.setPrefWidth(300);
        password.setCellValueFactory(new PropertyValueFactory<>("password"));
        
        table.setItems(list);
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
        generate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String passwordStr = controller.newItem(page.getText(), username.getText(), 8, 16);
                /*page.setText("");
                username.setText("");*/
                password.setText(passwordStr);
                
                showAll();
            }
            
        });
        
        row.getChildren().addAll(pageLb, page, usernameLb, username, passwordLb, password, generate);
        
        root.getChildren().add(row);
    }
    
    public void showAll(){
        list.clear();
        Password[] logins = controller.getAll();
        
        for(int i = 0; i < logins.length; i++){
            list.add(logins[i]);
        }
        
        if(list.size() == 0){
            list.add(new Password());
        }
    }
}

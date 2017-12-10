/*
 * Project for data structure course in university
 * Mindaugas Dirginčius, 2017
 */
package PasswordManager;

import HashSetAPI.HashSet;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Pair;

/**
 * View of the GUI.
 * 
 * @author Mindaugas
 */
public class Controls {
    private VBox root;
    private ObservableList<Password> list;
    private Controller controller;
    private TableView table;
    private Button generate;
    private Button removeBtn;
    
    /**
     * Default constructor
     */
    public Controls(){
        list = FXCollections.observableArrayList(new Password());
        controller = new Controller(new HashSet<>());
        root = new VBox();
        root.setSpacing(5);
        root.setPadding(new Insets(5, 0, 5, 0));
        createTopRow();
        createTable();
        createBottomRow();
        login();
    }
    
    /**
     * Returns root node.
     * 
     * @return root element of layout
     */
    public VBox getLayout(){
        return root;
    }
    
    private void createTopRow(){
        HBox row = new HBox();
        row.setSpacing(5);
        row.setPadding(new Insets(0, 0, 0, 5));
        
        TextField searchTF = new TextField();
        searchTF.setPromptText("Paieška");
        
        Button searchBtn = new Button("Ieškoti");
        searchBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Password found = controller.search(searchTF.getText());
                
                list.clear();
                list.add(found);
            }
            
        });
        
 
        Pane spacer = new Pane();
        spacer.setPrefWidth(430);
        
        Button login = new Button("Prisijungti");
        login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                login();
            }
            
        });
        
        Button showAllBtn = new Button("Visi");
        showAllBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showAll();
            }
            
        });
        
        removeBtn = new Button("Pašalinti");
        removeBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Password pass = (Password) table.getSelectionModel().getSelectedItem();
                if(pass != null){
                    controller.remove(pass.getPage());
                    removeBtn.setDisable(true);
                    
                    showAll();
                }
            }
        });
        removeBtn.setDisable(true);
        
        row.getChildren().addAll(searchTF, searchBtn, spacer, login, showAllBtn, removeBtn);
        
        root.getChildren().add(row);
    }
    
    private void createTable(){
        table = new TableView();
        
        TableColumn page = new TableColumn("Puslapis");
        page.setResizable(false);
        page.setPrefWidth(299);
        page.setCellValueFactory(new PropertyValueFactory<>("page"));
        TableColumn username = new TableColumn("Prisijungimo vardas");
        username.setResizable(false);
        username.setPrefWidth(299);
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        TableColumn password = new TableColumn("Slaptažodis");
        password.setResizable(false);
        password.setPrefWidth(299);
        password.setCellValueFactory(new PropertyValueFactory<>("password"));
        
        table.setItems(list);
        table.getColumns().addAll(page, username, password);
        table.setPrefHeight(450);
        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try{
                    Clipboard clipboard = Clipboard.getSystemClipboard();
                    ClipboardContent content = new ClipboardContent();
                    content.putString(((Password)table.getSelectionModel().getSelectedItem()).getPassword());
                    clipboard.setContent(content);
                    
                    removeBtn.setDisable(false);
                } catch (Exception ex){
                    removeBtn.setDisable(true);
                }
            }
        
        });
        
        root.getChildren().add(table);
    }
    
    private void createBottomRow(){
        HBox row = new HBox();
        row.setSpacing(5);
        row.setPadding(new Insets(0, 0, 0, 5));
        row.setAlignment(Pos.BASELINE_LEFT);
        
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
        
        generate = new Button("Generuoti");
        generate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {                
                String passwordStr = controller.newItem(page.getText(), username.getText(), 8, 16);
                password.setText(passwordStr);

                showAll();
            }
        });
        generate.setDisable(true);
        
        row.getChildren().addAll(pageLb, page, usernameLb, username, passwordLb, password, generate);
        
        root.getChildren().add(row);
    }
    
    private void showAll(){
        list.clear();
        Password[] logins = controller.getAll();
        
        for(int i = 0; i < logins.length; i++){
            list.add(logins[i]);
        }
        
        if(list.size() == 0){
            list.add(new Password());
        }
    }
    
    private void login(){
        Dialog<Pair<String, String>> loginScr = new Dialog<>();
        loginScr.setTitle("Prisijungti");
        loginScr.setWidth(500);
        
        VBox main = new VBox();
        main.setSpacing(5);
        main.setAlignment(Pos.CENTER);
        
        HBox user = new HBox();
        user.setPadding(new Insets(5, 0, 5, 0));
        user.setSpacing(5);
        user.setAlignment(Pos.BASELINE_LEFT);
        
        Text userLb = new Text("Vartotojo vardas:");
        TextField userTF = new TextField();
        
        user.getChildren().addAll(userLb, userTF);
        
        HBox pass = new HBox();
        pass.setPadding(new Insets(5, 0, 5, 28));
        pass.setSpacing(5);
        pass.setAlignment(Pos.BASELINE_LEFT);
        
        Text passLb = new Text("Slaptažodis:");
        TextField passTF = new TextField();
        
        pass.getChildren().addAll(passLb, passTF);
        
        main.getChildren().addAll(user, pass);
        
        ButtonType register = new ButtonType("Naujas vartotojas", ButtonData.APPLY);
        ButtonType login = new ButtonType("Prisijungti", ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Atšaukti", ButtonData.CANCEL_CLOSE);
        loginScr.getDialogPane().getButtonTypes().addAll(register, login, cancel);
        loginScr.getDialogPane().setContent(main);
        loginScr.getDialogPane().setPrefWidth(400);
        
        loginScr.setResultConverter(dialogButton -> {
            if(dialogButton == register){
                return new Pair<>((char)10 + userTF.getText(), passTF.getText());
            }
            if(dialogButton == login){
                return new Pair<>(userTF.getText(), passTF.getText());
            }
            return null;
        });
        
        Optional<Pair<String, String>> results = loginScr.showAndWait();
        
        if(results.isPresent()){
            String userStr = results.get().getKey();
            String passStr = results.get().getValue();
            
            while(!validate(userStr, passStr)){
                loginScr.setHeaderText("Neteisingas vartotojas arba slaptažodis");
                results = loginScr.showAndWait();
                
                if(results.isPresent()){
                    userStr = results.get().getKey();
                    passStr = results.get().getValue();
                }
            }
        }
    }
    
    private boolean validate(String user, String pass){
        if(user != null && !user.isEmpty() && pass != null && !pass.isEmpty()){
            try{
                if(user.charAt(0) == (char)10){
                    if(controller.register(user.substring(1), pass)){
                        generate.setDisable(false);
                        
                        return true;
                    }
                } else if(controller.login(user, pass)){
                    controller.loadFromFile();
                    generate.setDisable(false);
                    showAll();
                    
                    return true;
                }
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(Controls.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return false;
    }
}

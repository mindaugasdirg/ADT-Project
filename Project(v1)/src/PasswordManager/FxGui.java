/*
 * Project for data structure course in university
 * Mindaugas Dirginčius, 2017
 */
package PasswordManager;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Mindaugas
 */
public class FxGui extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        Controls ui = new Controls();
        Scene scene = new Scene(ui.getLayout(), 900, 500);
        primaryStage.setTitle("Slaptas");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String ... args){
        launch(args);
    }
}

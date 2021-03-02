package Inventory;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main! Then entrypoint to the application
 *
 * FUTURE ENHANCEMENTS: implement a simple file based database. I lose all my inventory when I exit the app!!!
 */
public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Views/main.fxml"));
        primaryStage.setTitle("Ryan Olson's Awesome Inventory App");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

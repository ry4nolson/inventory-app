package Inventory.Controllers;

import Inventory.Models.InHousePart;
import Inventory.Models.Inventory;
import Inventory.Models.Part;
import Inventory.Models.Product;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * MainController handles the actions of the Main window.
 */
public class MainController {

    @FXML
    private TableView partsTable;
    @FXML
    private TableView productsTable;

    public Inventory inventory;
    private final Stage stage = new Stage();

    public void initialize() {
        System.out.println("initialized");
        inventory = new Inventory();

        inventory.addPart(new InHousePart(2, "Test", 1.0,0,1,2, 2));
        inventory.addProduct(new Product(1, "Product", 2.0, 0, 1,2));

        ObservableList<Part> parts = inventory.getAllParts();
        ObservableList<Product> products = inventory.getAllProducts();

        partsTable.setItems(parts);
        productsTable.setItems(products);

    }
    public void show(){
        this.stage.showAndWait();
    }
    /**
     * exits the application
     * @param actionEvent
     */
    public void exit(ActionEvent actionEvent) {
        Stage window = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        window.close();
        Platform.exit();
    }

    public void addPart(ActionEvent actionEvent) {
        inventory.addPart(new InHousePart(2, "Test", 1.0,0,1,2, 2));
    }

    public void modifyPart(ActionEvent actionEvent) {
    }
}

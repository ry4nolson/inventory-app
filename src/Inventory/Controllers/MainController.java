package Inventory.Controllers;

import Inventory.Models.InHousePart;
import Inventory.Models.Inventory;
import Inventory.Models.Part;
import Inventory.Models.Product;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;

import static java.lang.Integer.parseInt;

/**
 * MainController handles the actions of the Main window.
 */
public class MainController {

    @FXML
    private TableView partsTable;
    @FXML
    private TableView productsTable;

    public Inventory inventory;

    private ObservableList<Part> parts;
    private ObservableList<Product> products;

    /**
     * FUTURE ENHANCEMENT: Add simple database support, I lose all my inventory when I quit the app!
     */
    public void initialize() {
        System.out.println("initialized");
        inventory = new Inventory();

        inventory.addPart(new InHousePart(1, "Test", 1.0,0,1,2, 2));
        inventory.addProduct(new Product(1, "Product", 2.0, 0, 1,2));

        parts = inventory.getAllParts();
        products = inventory.getAllProducts();

        partsTable.setItems(parts);
        productsTable.setItems(products);

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

    /**
     * add new part
     * @param actionEvent
     */
    public void addPart(ActionEvent actionEvent) {
        showPartWindow(null);
    }

    /**
     * modify existing part
     * @param actionEvent
     */
    public void modifyPart(ActionEvent actionEvent) {
        Part part = (Part)partsTable.getSelectionModel().getSelectedItem();

        if (part != null)
            showPartWindow(part);
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "You need to choose a part");
            alert.show();
        }
    }

    /**
     * delete part
     * @param actionEvent
     */
    public void deletePart(ActionEvent actionEvent) {
        Part part = (Part)partsTable.getSelectionModel().getSelectedItem();

        if (part != null)
            inventory.deletePart(part);
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "You need to choose a part");
            alert.show();
        }
    }

    /**
     * add new product
     * @param actionEvent
     */
    public void addProduct(ActionEvent actionEvent) {
        showProductWindow(null);
    }

    /**
     * edit selected product
     * @param actionEvent
     */
    public void modifyProduct(ActionEvent actionEvent) {
        Product product = (Product)productsTable.getSelectionModel().getSelectedItem();

        if (product != null)
            showProductWindow(product);
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "You need to choose a part");
            alert.show();
        }
    }

    /**
     * delete selected product
     * @param actionEvent
     */
    public void deleteProduct(ActionEvent actionEvent) {
        Product product = (Product)productsTable.getSelectionModel().getSelectedItem();

        if (product != null)
            inventory.deleteProduct(product);
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "You need to choose a product");
            alert.show();
        }
    }

    /**
     * show the add/modify part window
     * @param part can be null for a new part
     */
    private void showPartWindow(Part part) {
        Parent root;
        try {
            URL url = getClass().getResource("/Inventory/Views/part.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            root = loader.load();
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(part == null ? "Add Part" : "Modify Part");
            stage.setScene(new Scene(root, 450, 450));

            PartController ctrl = loader.getController();
            ctrl.initData(inventory, part, this);

            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * show the add/modify product window
     * @param product can be null for a new product
     */
    private void showProductWindow(Product product) {
        Parent root;
        try {
            URL url = getClass().getResource("/Inventory/Views/product.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            root = loader.load();
            Stage stage = new Stage(StageStyle.UTILITY);
            stage.setTitle(product == null ? "Add Product" : "Modify Product");
            stage.setScene(new Scene(root, 650, 650));

            ProductController ctrl = loader.getController();
            ctrl.initData(inventory, product, this);

            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * search for parts as you're typing
     * @param actionEvent
     */
    public void searchParts(KeyEvent actionEvent) {
        String search = ((TextField)actionEvent.getSource()).getText().toLowerCase(Locale.ROOT);

        if (search == "") {
            partsTable.setItems(parts);
            return;
        }

        ObservableList<Part> results = FXCollections.observableArrayList();

        try {
            int partId = parseInt(search);
            Part part = inventory.getAllParts().filtered(x -> x.getId() == partId).get(0);
            results.add(part);
        } catch(Exception ex){
            for (Part part:inventory.getAllParts()) {
                if (part.getName().toLowerCase(Locale.ROOT).contains(search)){
                    results.add(part);
                }
            }
        }

        partsTable.setItems(results);

        if (results.size() == 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "No Parts Found");
            alert.show();
        }
        else if (results.size() == 1){
            partsTable.getSelectionModel().selectFirst();
        }
    }

    /**
     * search for products as you're typing
     * @param actionEvent
     */
    public void searchProducts(KeyEvent actionEvent) {
        String search = ((TextField)actionEvent.getSource()).getText().toLowerCase(Locale.ROOT);

        if (search == "") {
            productsTable.setItems(products);
            return;
        }

        ObservableList<Product> results = FXCollections.observableArrayList();

        try {
            int productId = parseInt(search);
            Product product = inventory.getAllProducts().filtered(x -> x.getId() == productId).get(0);
            results.add(product);
        } catch(Exception ex){
            for (Product product:inventory.getAllProducts()) {
                if (product.getName().toLowerCase(Locale.ROOT).contains(search)){
                    results.add(product);
                }
            }
        }

        productsTable.setItems(results);

        if (results.size() == 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "No Products Found");
            alert.show();
        }
        else if (results.size() == 1){
            productsTable.getSelectionModel().selectFirst();
        }
    }

    /**
     * reset the parts table to the full parts inventory
     */
    public void updatePartsTable(){
        this.partsTable.setItems(parts);
    }

    /**
     * reset products table to full product inventory
     */
    public void updateProductsTable(){
        this.productsTable.setItems(products);
    }
}

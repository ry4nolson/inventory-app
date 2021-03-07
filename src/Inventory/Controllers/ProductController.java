package Inventory.Controllers;

import Inventory.Models.Inventory;
import Inventory.Models.Part;
import Inventory.Models.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.Locale;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

/**
 * ProductController handles the actions from the Product window
 */
public class ProductController {
    @FXML
    public Label title;
    @FXML
    public TextField productId;
    @FXML
    public TextField productName;
    @FXML
    public TextField productInventory;
    @FXML
    public TextField productPrice;
    @FXML
    public TextField productMin;
    @FXML
    public TextField productMax;

    @FXML
    public TableView partsTable;
    @FXML
    public TableView associatedPartsTable;

    private ObservableList<Part> partsList;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    private Inventory inventory;
    private Product product;
    private int productIndex = -1;
    private MainController main;

    public void initData(Inventory inventory, Product product, MainController main){
        this.inventory = inventory;
        this.partsList = inventory.getAllParts();
        this.product = product;
        this.main = main;

        this.partsTable.setItems(this.partsList);

        if (this.product != null) {
            initPart();
            this.title.setText("Modify Part");
        } else  {
            this.product = new Product();
            associatedParts = this.product.getAllAssociatedParts();
            this.associatedPartsTable.setItems(associatedParts);
        }
    }

    /**
     * set inputs based on the passed in product
     */
    private void initPart(){
        this.productIndex = inventory.getAllProducts().indexOf(product);
        this.associatedPartsTable.setItems(this.product.getAllAssociatedParts());
        this.productId.setText(String.valueOf(this.product.getId()));
        this.productName.setText(this.product.getName());
        this.productInventory.setText(String.valueOf(this.product.getStock()));
        this.productMin.setText(String.valueOf(this.product.getMin()));
        this.productMax.setText(String.valueOf(this.product.getMax()));
        this.productPrice.setText(String.valueOf(this.product.getPrice()));

        associatedParts = this.product.getAllAssociatedParts();
        this.associatedPartsTable.setItems(associatedParts);
    }

    /**
     * save product
     *
     * RUNTIME ERROR: Catch invalid input for int and double values
     *
     * @param actionEvent the event
     */
    public void save(ActionEvent actionEvent) {
        try {
            int id = 0;
            if (product != null) id = product.getId();

            int inv = parseInt(productInventory.getText());
            int min = parseInt(productMin.getText());
            int max = parseInt(productMax.getText());



            if (inv < min || inv > max)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Inventory must be between min and max");
                alert.show();
                return;
            }

            if (min == max || min > max)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Min must be less than Max");
                alert.show();
                return;
            }

            product = new Product( id != 0 ? id : inventory.getAllProducts().size() + 1,
                    productName.getText(),
                    parseDouble(productPrice.getText()),
                    parseInt(productInventory.getText()),
                    parseInt(productMin.getText()),
                    parseInt(productMax.getText()));

            for(Part part:product.getAllAssociatedParts()){
                product.deleteAssociatedPart(part);
            }

            for (Part part: associatedParts) {
                product.addAssociatedParts(part);
            }


            if (id == 0 ) this.inventory.addProduct(product);
            else this.inventory.updateProduct(productIndex, product);
            closeWindow(actionEvent);
        } catch(Exception ex){
            System.out.println(ex);
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error parsing fields");
            alert.show();
        }
    }

    /**
     * cancel editing
     * @param actionEvent the event
     */
    public void cancel(ActionEvent actionEvent) {
        closeWindow(actionEvent);
    }

    private void closeWindow(ActionEvent actionEvent){
        this.main.updateProductsTable();
        Stage window = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        window.close();
    }

    /**
     * adds the selected part to the products associated parts.
     * @param actionEvent
     */
    public void addPart(ActionEvent actionEvent) {
        Part part = (Part)partsTable.getSelectionModel().getSelectedItem();

        if (associatedParts.contains(part))
            return;

        this.product.addAssociatedParts(part);
    }

    /**
     * removes selected item from product's associated parts.
     * @param actionEvent
     */
    public void removePart(ActionEvent actionEvent) {
        Part part = (Part)associatedPartsTable.getSelectionModel().getSelectedItem();

        if (this.product.getAllAssociatedParts().contains(part))
            this.product.deleteAssociatedPart(part);
    }
    /**
     * search for parts as you're typing
     * @param actionEvent
     */
    public void searchParts(KeyEvent actionEvent) {
        String search = ((TextField)actionEvent.getSource()).getText().toLowerCase(Locale.ROOT);

        if (search == "") {
            partsTable.setItems(inventory.getAllParts());
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
}

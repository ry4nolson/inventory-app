package Inventory.Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/**
 * Main Inventory class. Holds lists of parts and products. It also contains methods to interact with those lists.
 */
public class Inventory {
    private ObservableList<Part> allParts = FXCollections.observableArrayList();
    private ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public Inventory(){
    }

    public void addPart(Part part){
        this.allParts.add(part);
    }

    public void addProduct(Product product){
        this.allProducts.add(product);
    }

    public Part lookupPart(int partId) {
        FilteredList<Part> foundPart = allParts.filtered((part) -> part.getId() == partId);
        if (foundPart.size() == 1)
            return foundPart.get(0);

        return null;
    }

    public Product lookupProduct(int productId) {
        FilteredList<Product> foundProduct = allProducts.filtered((product) -> product.getId() == productId);
        if (foundProduct.size() == 1)
            return foundProduct.get(0);

        return null;
    }

    public ObservableList<Part> lookupPart(String partName){

        FilteredList<Part> results = allParts.filtered((part) -> part.getName().contains(partName));

        return (ObservableList<Part>)results;
    }

    public ObservableList<Product> lookupProduct(String productName){
        FilteredList<Product> results = allProducts.filtered((part) -> part.getName().contains(productName));

        return (ObservableList<Product>)results;
    }

    public void updatePart(int index, Part selectedPart) {
        this.allParts.set(index, selectedPart);
    }

    public void updateProduct(int index, Product selectedProduct){
        this.allProducts.set(index, selectedProduct);
    }

    public boolean deletePart(Part selectedPart){
        return allParts.remove(selectedPart);
    }

    public boolean deleteProduct(Product selectedProduct){
        return allProducts.remove(selectedProduct);
    }

    public ObservableList<Part> getAllParts() {
        return allParts;
    }

    public ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}

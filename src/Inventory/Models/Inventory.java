package Inventory.Models;

import javafx.collections.ObservableList;

public class Inventory {
    private ObservableList<Part> allParts;
    private ObservableList<Product> allProducts;

    public void addPart(Part part){
        this.allParts.add(part);
    }

    public void addProduct(Product product){
        this.allProducts.add(product);
    }
}

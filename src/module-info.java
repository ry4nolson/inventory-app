module Inventory {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;

    opens Inventory;
    opens Inventory.Controllers;
    opens Inventory.Models;
}
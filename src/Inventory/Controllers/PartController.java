package Inventory.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

/**
 * PartController handles the actions for the Part window
 */
public class PartController {

    @FXML
    public HBox inHouseVisible;
    @FXML
    public HBox companyVisible;
    @FXML
    private ToggleGroup partType;
    @FXML
    private RadioButton inHouse;
    @FXML
    private RadioButton outsourced;

    public PartController() {
        inHouse.setSelected(true);
    }

    /**
     * save Part
     * @param actionEvent the event
     */
    public void save(ActionEvent actionEvent) {
    }

    /**
     * cancel editing
     * @param actionEvent the event
     */
    public void cancel(ActionEvent actionEvent) {
    }

    public void showInHouse(ActionEvent actionEvent) {
        this.inHouseVisible.setVisible(true);
        this.companyVisible.setVisible(false);
    }
    public void showOutsourced(ActionEvent actionEvent) {
        this.inHouseVisible.setVisible(false);
        this.companyVisible.setVisible(true);
    }
}

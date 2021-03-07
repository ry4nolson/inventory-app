package Inventory.Controllers;

import Inventory.Models.InHousePart;
import Inventory.Models.Inventory;
import Inventory.Models.OutsourcedPart;
import Inventory.Models.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

/**
 * PartController handles the actions for the Part window
 */
public class PartController {

    @FXML
    public HBox inHouseVisible;
    @FXML
    public HBox companyVisible;
    @FXML
    public TextField partId;
    @FXML
    public TextField partName;
    @FXML
    public TextField partInventory;
    @FXML
    public TextField partPrice;
    @FXML
    public TextField partMin;
    @FXML
    public TextField partMax;
    @FXML
    public TextField partMachineID;
    @FXML
    public TextField partCompany;
    @FXML
    public Label title;
    @FXML
    private ToggleGroup partType;
    @FXML
    private RadioButton inHouse;
    @FXML
    private RadioButton outsourced;

    private Inventory inventory;

    private Part part;
    private int partIndex;
    private MainController main;

    @FXML
    public void initialize() {
        System.out.println("initialize");
        inHouse.fire();
    }

    /**
     * receive data from Main Controller
     * @param inventory
     * @param part
     * @param main
     */
    public void initData(Inventory inventory, Part part, MainController main){
        System.out.println("initData");
        this.inventory = inventory;
        this.part = part;
        this.main = main;

        if (this.part != null){
            initPart();
            title.setText("Modify Part");
        }
    }

    /**
     * load the passed in product into fields
     */
    private void initPart(){
        partIndex = this.inventory.getAllParts().indexOf(part);
        System.out.println(partIndex);

        partId.setText(String.valueOf(part.getId()));
        partName.setText(part.getName());
        partInventory.setText(String.valueOf(part.getStock()));
        partMin.setText(String.valueOf(part.getMin()));
        partMax.setText(String.valueOf(part.getMax()));
        partPrice.setText(String.valueOf(part.getPrice()));

        if (part.getClass() == OutsourcedPart.class) {
            outsourced.fire();
            partCompany.setText(((OutsourcedPart)part).getCompanyName());
        } else {
            inHouse.fire();
            partMachineID.setText(String.valueOf(((InHousePart)part).getMachineId()));
        }
    }

    /**
     * save Part
     *
     * RUNTIME ERROR: Catch invalid input for int and double values
     *
     * @param actionEvent the event
     */
    public void save(ActionEvent actionEvent) {
        try {
            int id = 0;
            if (part != null) id = part.getId();

            int inv = parseInt(partInventory.getText());
            int min = parseInt(partMin.getText());
            int max = parseInt(partMax.getText());



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

            if (outsourced.isSelected()) {
                part = new OutsourcedPart( id != 0 ? id : inventory.getAllParts().size() + 1,
                        partName.getText(),
                        parseDouble(partPrice.getText()),
                        parseInt(partInventory.getText()),
                        parseInt(partMin.getText()),
                        parseInt(partMax.getText()),
                        partCompany.getText());
            } else {
                part = new InHousePart( id != 0 ? id : inventory.getAllParts().size() + 1,
                        partName.getText(),
                        parseDouble(partPrice.getText()),
                        parseInt(partInventory.getText()),
                        parseInt(partMin.getText()),
                        parseInt(partMax.getText()),
                        parseInt(partMachineID.getText()));
            }

            if (id == 0 ) this.inventory.addPart(part);
            else this.inventory.updatePart(partIndex, part);
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

    /**
     * close the window
     * @param actionEvent
     */
    private void closeWindow(ActionEvent actionEvent){
        this.main.updatePartsTable();
        Stage window = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        window.close();
    }

    /**
     * toggle between inhouse and outsourced
     * @param actionEvent
     */
    public void showInHouse(ActionEvent actionEvent) {
        this.inHouseVisible.setVisible(true);
        this.companyVisible.setVisible(false);
        this.inHouseVisible.setManaged(true);
        this.companyVisible.setManaged(false);
    }

    /**
     * toggle between inhouse and outsourced
     * @param actionEvent
     */
    public void showOutsourced(ActionEvent actionEvent) {
        this.inHouseVisible.setVisible(false);
        this.companyVisible.setVisible(true);
        this.inHouseVisible.setManaged(false);
        this.companyVisible.setManaged(true);
    }
}

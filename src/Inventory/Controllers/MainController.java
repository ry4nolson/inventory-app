package Inventory.Controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * MainController handles the actions of the Main window.
 */
public class MainController {
    /**
     * exits the application
     * @param actionEvent
     */
    public void exit(ActionEvent actionEvent) {
        Stage window = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        window.close();
        Platform.exit();
    }
}

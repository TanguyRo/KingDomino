package DomiNations;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class JavaFxGameController {

    @FXML
    private Button startButton;

    @FXML
    private void initialize() {
        // Handle Button event.
        startButton.setOnAction((event) -> {
            System.out.println("Clic sur le bouton");
        });
    }
}
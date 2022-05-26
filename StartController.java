import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class StartController
{
    public StartController(){}

    @FXML
    private void initialize(){}

    @FXML
    void OpenCalculator(ActionEvent event) throws IOException {
        Main.window.setFxml("Calculator.fxml");
        Main.window.setTitle("Calculator");
        Main.window.setScene();
        Main.window.show();
    }

    @FXML
    void OpenCreator(ActionEvent event) throws IOException {
        Main.window.setFxml("Customizer.fxml");
        Main.window.setTitle("Creator");
        Main.window.setScene();
        Main.window.show();
    }
}

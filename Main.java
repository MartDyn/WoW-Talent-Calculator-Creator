import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static WindowControl window = new WindowControl();

    @Override
    public void start(Stage primaryStage) throws Exception{
        window.setFxml("Start.fxml");
        window.setStage(primaryStage);
        window.setScene();
        window.setTitle("Start Menu");
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

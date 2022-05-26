import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class WindowControl
{
    private Stage primaryStage;
    private Parent fxmlParent;

    public WindowControl() {}

    public WindowControl(String fxmlFilePath) throws IOException {
        this.fxmlParent = FXMLLoader.load(getClass().getResource(fxmlFilePath));
    }

    public void setTitle(String newTitle)
    {
        this.primaryStage.setTitle(newTitle);
    }

    public void setFxml(String fxmlFilePath) throws IOException {
        this.fxmlParent = FXMLLoader.load(getClass().getResource(fxmlFilePath));
    }

    public void setStage(Stage newStage)
    {
        this.primaryStage = newStage;
        this.primaryStage.setResizable(false);
    }

    public void setScene()
    {
        this.primaryStage.setScene(new Scene(this.fxmlParent));
    }

    public void setModal(Stage primaryStage)
    {
        this.primaryStage.initOwner(primaryStage);
        this.primaryStage.initModality(Modality.WINDOW_MODAL);
    }

    public void show()
    {
        this.primaryStage.show();
    }

    public Stage getStage()
    {
        return this.primaryStage;
    }
}

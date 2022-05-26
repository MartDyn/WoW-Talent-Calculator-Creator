import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import java.io.File;
import java.util.TreeMap;

public class NewTalentController {
    public NewTalentController() {
    }

    private Talent talentData = new Talent(true);
    private Tree treeData = Singleton.getTreeData();

    @FXML
    private AnchorPane anchor;

    @FXML
    private ImageView icon;

    @FXML
    private TextField name;

    @FXML
    private Label pos;

    @FXML
    private ComboBox prereq;

    @FXML
    private Button browse;

    @FXML
    private Button finish;

    @FXML
    private Button cancel;

    @FXML
    private void initialize() {
        FileChooser browser = new FileChooser();
        TreeMap<Integer, Integer> talentMap = new TreeMap<>();
        browser.setTitle("Choose Icon");
        browser.getExtensionFilters().clear();
        browser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.bmp"));
        browser.setInitialDirectory(new File("C:\\Users\\Martin\\Desktop\\Stuff\\New folder\\Talent Creator\\src\\Abilities"));

        talentData.setPos(Singleton.getTalentData().getPos());
        talentData.setTree(Singleton.getTalentData().getTree());
        icon.setImage(Singleton.getTalentData().getIcon().getImage());
        name.setText(Singleton.getTalentData().getName());
        name.setOnMouseClicked(mouseEvent -> name.clear());

        for (int i = 0; i < Singleton.getTalentData().getRanks().size(); i++) {
            TextArea textArea = (TextArea) anchor.lookup("#rank" + (i + 1));
            textArea.setText(Singleton.getTalentData().getRanks().get(i));
        }

        talentMap.put(-1, 0);
        prereq.getItems().add("<No Prerequisite>");
        for (int i = 0; i < treeData.Talents().size(); i++) {
            if (treeData.Talents().get(i).getName().equals("Empty Slot") != true && talentData.getName().equals(treeData.Talents().get(i).getName()) != true) {
                talentMap.put(i, talentMap.size());
                prereq.getItems().add(treeData.Talents().get(i).getName());
            }
        }
        if (Singleton.getTalentData().getPrereq() == -1)
        {
            prereq.getSelectionModel().selectFirst();
        }
        else
        {
            prereq.getSelectionModel().select(prereq.getItems().get(talentMap.get(Singleton.getTalentData().getPrereq())));
        }
        prereq.valueProperty().addListener((observableValue, oldValue, newValue) -> pos.setText(String.valueOf(talentMap.keySet().toArray()[prereq.getSelectionModel().getSelectedIndex()])));
        cancel.setOnMouseClicked(mouseEvent -> CustomizerController.talentNew.getStage().close());
        browse.setOnMouseClicked(mouseEvent -> BrowseIcon(browser));
        finish.setOnMouseClicked(mouseEvent ->
        {
            talentData.setName(name.getText());
            talentData.setIconImage(icon.getImage().getUrl());
            talentData.setPrereq(-1);
            for (int i = 0; i < 5; i++)
            {
                TextArea textArea = (TextArea) anchor.lookup("#rank" + (i + 1));
                if (textArea.getText().equals("") != true)
                {
                    talentData.setRank(textArea.getText());
                }
            }
            talentData.getIcon().setOnMouseClicked(Singleton.getTalentData().getIcon().getOnMouseClicked());
            talentData.updateTalent();
            Singleton.updateTalent(talentData);
            CustomizerController.talentNew.getStage().close();
        });
    }

    void BrowseIcon(FileChooser browser)
    {
        File selectedFile = browser.showOpenDialog(CustomizerController.talentNew.getStage());

        if (selectedFile != null)
        {
            icon.setImage(new Image("Abilities\\" + selectedFile.getName()));
        }
    }
}


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;

public class CustomizerController
{
    public CustomizerController() {}

    private static Document database;
    private static Tree[] trees = new Tree[3];
    public static WindowControl talentNew = new WindowControl();

    @FXML
    private MenuItem start;

    @FXML
    private AnchorPane main;

    @FXML
    private Button warrior;

    @FXML
    private Button rogue;

    @FXML
    private Button hunter;

    @FXML
    private Button paladin;

    @FXML
    private Button shaman;

    @FXML
    private Button mage;

    @FXML
    private Button warlock;

    @FXML
    private Button priest;

    @FXML
    private Button druid;

    @FXML
    private Button template;

    @FXML
    private TitledPane tree1;

    @FXML
    private TitledPane tree2;

    @FXML
    private TitledPane tree3;

    @FXML
    private GridPane grid1;

    @FXML
    private GridPane grid2;

    @FXML
    private GridPane grid3;

    @FXML
    private void initialize() throws Exception
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        database = builder.parse(new File("src/warrior.xml"));

        warrior.setOnAction(actionEvent ->
        { try { database = builder.parse(new File("src/warrior.xml")); PopulateTrees(); } catch (IOException | SAXException e) {} });
        rogue.setOnAction(actionEvent ->
        { try { database = builder.parse(new File("src/rogue.xml")); PopulateTrees();} catch (IOException | SAXException e) {} });
        hunter.setOnAction(actionEvent ->
        { try { database = builder.parse(new File("src/hunter.xml")); PopulateTrees();} catch (IOException | SAXException e) {} });
        paladin.setOnAction(actionEvent ->
        { try { database = builder.parse(new File("src/paladin.xml")); PopulateTrees();} catch (IOException | SAXException e) {} });
        shaman.setOnAction(actionEvent ->
        { try { database = builder.parse(new File("src/shaman.xml")); PopulateTrees();} catch (IOException | SAXException e) {} });
        mage.setOnAction(actionEvent ->
        { try { database = builder.parse(new File("src/mage.xml")); PopulateTrees();} catch (IOException | SAXException e) {} });
        warlock.setOnAction(actionEvent ->
        { try { database = builder.parse(new File("src/warlock.xml")); PopulateTrees();} catch (IOException | SAXException e) {} });
        priest.setOnAction(actionEvent ->
        { try { database = builder.parse(new File("src/priest.xml")); PopulateTrees();} catch (IOException | SAXException e) {} });
        druid.setOnAction(actionEvent ->
        { try { database = builder.parse(new File("src/druid.xml")); PopulateTrees();} catch (IOException | SAXException e) {} });

        PopulateTrees();
        InitMenuItems();
    }

    void PopulateTrees()
    {
        int talentIterator = 0, arrowIterator = 0;
        TitledPane[] treeContainer = new TitledPane[]{ tree1, tree2, tree3 };
        Element root = database.getDocumentElement();

        Tree.resetPoints();
        grid1.getChildren().clear();
        grid2.getChildren().clear();
        grid3.getChildren().clear();

        int talentIndex = Integer.parseInt(root.getElementsByTagName("talent").item(talentIterator).getAttributes().item(1).getTextContent());
        int arrowIndex = Integer.parseInt(root.getElementsByTagName("arrow").item(talentIterator).getAttributes().item(1).getTextContent());
        for(int t = 0; t < 3; t++)
        {
            Tree tree = new Tree(t, root.getElementsByTagName("tree").item(t).getAttributes().item(1).getTextContent(),
                    treeContainer[t], (GridPane) treeContainer[t].getContent().lookup("#grid" + (t + 1)));
            trees[t] = tree;

            for(int i = 0; i < 28; i++)
            {
                Talent talent = new Talent(true);

                talent.setTree(t);
                talent.setPos(i);

                if(i == talentIndex)
                {
                    try
                    {
                        NodeList nodeList = root.getElementsByTagName("talent").item(talentIterator).getChildNodes();
                        talent.setName(root.getElementsByTagName("talent").item(talentIterator).getAttributes().item(2).getTextContent());
                        talent.setIconImage("Abilities\\" + root.getElementsByTagName("talent").item(talentIterator).getAttributes().item(0).getTextContent());
                        talent.setPrereq(Integer.parseInt(root.getElementsByTagName("talent").item(talentIterator).getAttributes().item(3).getTextContent()));
                        for(int j = 0; j < nodeList.getLength(); j++)
                        {
                            if(nodeList.item(j).getNodeType() == Node.ELEMENT_NODE)
                            {
                                talent.setRank(nodeList.item(j).getTextContent());
                            }
                        }
                    } catch(Exception exception) { talent.setName("Error"); }

                    talentIterator++;

                    try
                    {
                        talentIndex = Integer.parseInt(root.getElementsByTagName("talent").item(talentIterator).getAttributes().item(1).getTextContent());
                    } catch(NullPointerException exception) { talentIndex = 0; }
                }
                else
                {
                    talent.getIcon().setOnMouseEntered(e -> talent.setIconImage("Interface\\create.png"));
                    talent.getIcon().setOnMouseExited(e -> talent.setIconImage("Interface\\empty.png"));
                }

                talent.getIcon().setOnMouseClicked(mouseEvent -> { if(mouseEvent.getButton() == MouseButton.PRIMARY)
                    {
                        try
                        {
                            Singleton.setData(talent, tree);
                            NewTalentWindow();
                        } catch (Exception exception) {}
                    }
                });

                talent.updateTalent();
                tree.addTalent(talent, i);

                if(i == arrowIndex)
                {
                    if(t == Integer.parseInt(root.getElementsByTagName("arrow").item(arrowIterator).getParentNode().getAttributes().item(0).getTextContent()))
                    {
                        Arrow arrow = new Arrow();
                        arrow.setPos(i);
                        try
                        {
                            arrow.setOrigin(Integer.parseInt(root.getElementsByTagName("arrow").item(arrowIterator).getAttributes().item(2).getTextContent()));
                            arrow.setBody(Integer.parseInt(root.getElementsByTagName("arrow").item(arrowIterator).getAttributes().item(3).getTextContent()));
                            arrow.setCap(Integer.parseInt(root.getElementsByTagName("arrow").item(arrowIterator).getAttributes().item(0).getTextContent()));
                        } catch (Exception exception) {}

                        arrowIterator++;

                        try
                        {
                            arrowIndex = Integer.parseInt(root.getElementsByTagName("arrow").item(arrowIterator).getAttributes().item(1).getTextContent());
                        } catch (NullPointerException exception) { arrowIndex = 0; }

                        tree.addArrow(arrow, i);
                    }
                }
            }

            tree.updateTree();
        }
    }

    void NewTalentWindow() throws IOException
    {
        Stage secondStage = new Stage();
        talentNew.setStage(secondStage);
        talentNew.setTitle("Customize");
        talentNew.setModal(Main.window.getStage());
        talentNew.setFxml("NewTalent.fxml");
        talentNew.setScene();
        talentNew.getStage().showAndWait();

        if(Singleton.isModified())
        {
            trees[Singleton.getTalentData().getTree()].updateCustom(Singleton.getTalentData());
            trees[Singleton.getTalentData().getTree()].updateTree();
        }
    }

    void InitMenuItems()
    {
        start.setOnAction(actionEvent ->
        {
            try {
                Main.window.setFxml("Start.fxml");
                Main.window.setTitle("Start Menu");
                Main.window.setScene();
                Main.window.show();
            } catch (IOException exception){}
        });
    }
}


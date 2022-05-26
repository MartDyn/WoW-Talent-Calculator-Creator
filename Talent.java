import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;

public class Talent
{
    private String talentName;
    private int treeIndex = -1, positionIndex = -1, currentRank = 0, prereq = -1;
    private boolean isEnabled = false, isMaxRank = false, isCustom = false;
    private List<String> rankData = new ArrayList();
    private Label rankLabel = new Label();
    private ImageView icon = new ImageView();
    private Tooltip tooltip = new Tooltip();
    private DropShadow border1 = new DropShadow(BlurType.ONE_PASS_BOX, Color.GRAY, 0.0, 1.0, 0.0, 0.0);
    private DropShadow border2 = new DropShadow(BlurType.ONE_PASS_BOX, Color.rgb(37, 107, 181), 0.0, 1.0, 0.0, 0.0);
    private DropShadow border3 = new DropShadow(BlurType.ONE_PASS_BOX, Color.GOLD, 0.0, 1.0, 0.0, 0.0);

    public Talent(boolean isCustomizable)
    {
        border1.setHeight(15.0);
        border2.setHeight(15.0);
        border3.setHeight(15.0);
        border1.setWidth(15.0);
        border2.setWidth(15.0);
        border3.setWidth(15.0);

        if(isCustomizable == true)
        {
            this.isEnabled = true;
            this.isCustom = true;
            this.setIconImage("Interface\\empty.png");
            this.talentName = "Empty Slot";
        }
        else
        {
            this.setIconImage("Interface\\QuestionMark.png");

            this.icon.setOnMouseEntered(e -> {
                if(this.isMaxRank == false && this.isEnabled == true)
                {
                    this.icon.setEffect(border2);
                }
            });
            this.icon.setOnMouseExited(e -> {
                if(this.isMaxRank == false && this.isEnabled == true)
                {
                    this.icon.setEffect(border1);
                }
            });
        }

        this.icon.fitWidthProperty().set(40.0);
        this.icon.fitHeightProperty().set(40.0);
        this.icon.setCursor(Cursor.HAND);
        this.icon.setEffect(border1);
        this.icon.setViewOrder(3);
        this.rankLabel.setMouseTransparent(true);
        this.rankLabel.setBackground(new Background(new BackgroundFill(Color.BLACK, new CornerRadii(5), null)));
        this.rankLabel.setFont(Font.font(12));
        this.rankLabel.setViewOrder(2);
        this.rankLabel.setEffect(border1);
        this.tooltip.setShowDelay(Duration.ZERO);
        this.tooltip.setHideDelay(Duration.ZERO);
        this.tooltip.setShowDuration(Duration.INDEFINITE);
        this.tooltip.setMaxWidth(230);
        this.tooltip.setWrapText(true);
        this.tooltip.setAutoFix(false);
        Tooltip.install(this.icon, this.tooltip);
        GridPane.setHalignment(this.icon, HPos.CENTER);
        GridPane.setHalignment(this.rankLabel, HPos.RIGHT);
        GridPane.setValignment(this.rankLabel, VPos.BOTTOM);
    }

    public void setTree(int index)
    {
        this.treeIndex = index;
    }
    public void setName(String name)
    {
        this.talentName = name;
    }

    public void setPos(int index)
    {
        this.positionIndex = index;
    }

    public void setEnabled(boolean bool)
    {
        if(this.isCustom != true)
        {
            ColorAdjust desaturate = new ColorAdjust(0, 0, 0, 0);
            SepiaTone normalize1 = new SepiaTone(0.6);
            ColorAdjust normalize2 = new ColorAdjust(0,0,0,0);

            if(bool != true)
            {
                desaturate.setSaturation(-1);
                desaturate.setBrightness(-0.33);
                normalize2.setHue(-0.2);
                normalize2.setSaturation(0.1);

                normalize1.setInput(normalize2);
                desaturate.setInput(normalize1);
                border1.setInput(desaturate);

                this.rankLabel.setTextFill(Color.GRAY);
            }
            else
            {
                border1.setInput(desaturate);

                this.rankLabel.setTextFill(Color.LIME);
            }

            this.icon.setEffect(border1);
            this.isEnabled = bool;
        }
    }

    public void setRank(String description)
    {
        this.rankData.add(description);
    }

    public void setIconImage(String imagePath)
    {
        this.icon.setImage(new Image(imagePath));
    }

    public void setPrereq(int index)
    {
        this.prereq = index;
    }

    public void updateCurrentRank(int opIncDec)
    {
        this.currentRank += opIncDec;
    }

    public void updateTalent()
    {
        Text talentName = new Text(this.talentName + "\n");
        Text hasNext = new Text("");
        Text currentRank = new Text("");
        Text nextRank = new Text("");
        talentName.setFill(Color.WHITE);
        talentName.setFont(Font.font(12));
        talentName.setUnderline(true);
        currentRank.setFill(Color.GOLD);
        currentRank.setFont(Font.font(12));
        hasNext.setFill(Color.WHITE);
        hasNext.setFont(Font.font(12));
        nextRank.setFill(Color.GOLD);
        nextRank.setFont(Font.font(12));

        if(this.isCustom != true)
        {
            if(this.currentRank == this.rankData.size())
            {
                this.isMaxRank = true;
                this.rankLabel.setTextFill(Color.GOLD);
                this.rankLabel.setEffect(border3);
                this.icon.setEffect(border3);
            }
            else
            {
                this.isMaxRank = false;
                this.rankLabel.setTextFill(Color.LIME);
                if(this.icon.getEffect().equals(border3))
                {
                    this.icon.setEffect(border2);
                    this.rankLabel.setEffect(border1);
                }
            }

            if (this.currentRank != 0)
            {
                if(isMaxRank == false)
                {
                    currentRank.setText(this.rankData.get(this.currentRank - 1));
                    hasNext.setText("\n\nNext Rank:\n");
                    nextRank.setText(this.rankData.get(this.currentRank));

                }
                else
                {
                    currentRank.setText(this.rankData.get(this.currentRank - 1));
                }
            }
            else if (this.rankData.size() == 1)
            {
                currentRank.setText(this.rankData.get(0));
            }
            else
            {
                currentRank.setText(this.rankData.get(0));
            }

            TextFlow textFlow = new TextFlow(talentName, currentRank, hasNext, nextRank);

            this.tooltip.setGraphic(textFlow);
            this.rankLabel.setText(this.currentRank + "/" + this.rankData.size());
        }
        else
        {
            this.isMaxRank = true;
            this.rankLabel.setTextFill(Color.GOLD);
            if(this.rankData.size() != 0)
            {
                this.rankLabel.setText(" " + this.rankData.size() + " ");
                currentRank.setText(this.rankData.get(this.rankData.size() - 1));
                hasNext.setText("\n<Click to modify talent>");
            }
            else
            {
                hasNext.setText("\n<Click to create new talent>");
            }

            TextFlow textFlow = new TextFlow(talentName, currentRank, hasNext, nextRank);

            this.tooltip.setGraphic(textFlow);;
        }
    }

    public String getName()
    {
        return this.talentName;
    }

    public int getTree()
    {
        return this.treeIndex;
    }

    public int getPos()
    {
        return this.positionIndex;
    }

    public int getPrereq()
    {
        return this.prereq;
    }

    public List<String> getRanks() {
        return rankData;
    }

    public int getCurrentRank()
    {
        return this.currentRank;
    }

    public ImageView getIcon()
    {
        return this.icon;
    }

    public Label getLabel()
    {
        return this.rankLabel;
    }

    public boolean isMaxRank()
    {
        return this.isMaxRank;
    }

    public boolean isEnabled()
    {
        return this.isEnabled;
    }

    public boolean isCustom()
    {
        return this.isCustom;
    }

}

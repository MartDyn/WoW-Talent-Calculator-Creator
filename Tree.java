import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;

public class Tree
{
    private static int totalPoints = 51;
    private IntegerProperty treePoints = new SimpleIntegerProperty(0);
    private HashMap<Integer, Integer> rowPointsCumulative = new HashMap<>();
    private int id = -1;
    private int[] rowTotalPoints = new int[] { 0, 0, 0, 0, 0, 0, 0 };
    private String treeName;
    private ArrayList<Talent> talents = new ArrayList<>();
    private GridPane grid;
    private TitledPane treeMain;

    public Tree(int id, String title, TitledPane titledPane, GridPane gridPane)
    {
        this.id = id;
        this.treeName = title;
        this.treeMain = titledPane;
        this.grid = gridPane;
    }

    public void addTalent(Talent talent, int index)
    {
        if(talent.isCustom() != true)
        {
            talent.getIcon().setOnMouseClicked(e -> {
                if(e.getButton() == MouseButton.PRIMARY && talent.isMaxRank() == false && talent.isEnabled() && totalPoints > 0)
                {
                    talent.updateCurrentRank(1);
                    totalPoints--;
                    this.rowTotalPoints[talent.getPos() / 4]++;
                    this.rowPointsCumulative.merge(talent.getPos() / 4, 1, Integer::sum);
                    this.treePoints.set(this.treePoints.add(1).intValue());
                }
                if(e.getButton() == MouseButton.SECONDARY && this.canDecrease(talent))
                {
                    talent.updateCurrentRank(-1);
                    totalPoints++;
                    this.rowTotalPoints[talent.getPos() / 4]--;
                    this.rowPointsCumulative.merge(talent.getPos() / 4, -1, Integer::sum);
                    this.treePoints.set(this.treePoints.subtract(1).intValue());
                }
                talent.updateTalent();
                this.updateTree();
            });
        }

        this.grid.add(talent.getIcon(), index % 4, index / 4);
        this.grid.add(talent.getLabel(), index % 4, index / 4);
        this.talents.add(talent);
    }

    public void addArrow(Arrow arrow, int index)
    {
        this.grid.add(arrow.getBody(), index % 4, index / 4);
        this.grid.add(arrow.getCap(), index % 4, index / 4);
    }

    public void setListener(ChangeListener<Number> listener)
    {
        this.treePoints.addListener(listener);
    }

    public void updateTree()
    {
        this.treeMain.setText(this.treeName + "(" + this.treePoints.intValue() + ")");

        ListIterator<Talent> talentIterator = this.talents.listIterator();
        while(talentIterator.hasNext())
        {
            Talent talent = talentIterator.next();

            if(talent.getPos() < (this.treePoints.intValue() / 5) * 4 + 4)
            {
                if(talent.getPrereq() == -1 || this.talents.stream().filter(f -> f.getPos() == talent.getPrereq()).findFirst().get().isMaxRank())
                {
                    if(talent.isEnabled() != true)
                    {
                        talent.setEnabled(true);
                    }
                }
                else
                {
                    talent.setEnabled(false);
                }
            }
            else
            {
                talent.setEnabled(false);
            }
        }
    }

    public boolean canDecrease(Talent talent)
    {
        int rowCumulative = this.rowTotalPoints[0], depthIndex = 0;
        for(int i = 6; i >= 0; i--)
        {
            if(this.rowTotalPoints[i] != 0)
            {
                depthIndex = i;
                break;
            }
        }
        for(int i = 1; i <= talent.getPos() / 4; i++)
        {
            rowCumulative += this.rowTotalPoints[i];
        }

        if(talent.getCurrentRank() != 0 && talent.isEnabled())
        {
            if(this.talents.stream().filter(f -> f.getPrereq() == talent.getPos()).findFirst().isEmpty())
            {
                if(depthIndex == talent.getPos() / 4)
                {
                    return true;
                }
                else if(rowCumulative > 5 * (1 + (talent.getPos() / 4)) && this.treePoints.intValue() - depthIndex * 5 > 0)
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else
            {
                if(this.talents.stream().filter(f -> f.getPrereq() == talent.getPos()).findFirst().get().getCurrentRank() == 0)
                {
                    if(depthIndex == talent.getPos() / 4)
                    {
                        return true;
                    }
                    else if(rowCumulative > 5 * (1 + (talent.getPos() / 4)) && this.treePoints.intValue() - depthIndex * 5 > 0)
                    {
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
                else
                {
                    return false;
                }
            }
        }
        else
        {
            return false;
        }

    }

    public static void resetPoints()
    {
        totalPoints = 51;
    }

    public static int getTotalPoints()
    {
        return totalPoints;
    }

    public ArrayList<Talent> Talents()
    {
        return this.talents;
    }

    public void updateCustom(Talent talent)
    {
        this.talents.set(talent.getPos(), talent);
        this.grid.add(talent.getIcon(), talent.getPos() % 4, talent.getPos() / 4);
        this.grid.add(talent.getLabel(), talent.getPos() % 4, talent.getPos() / 4);
    }
}
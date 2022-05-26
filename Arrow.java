import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class Arrow
{
    private int positionIndex = -1, originIndex = -1;
    private boolean isEnabled = false;
    private ImageView arrowBody = new ImageView();
    private ImageView arrowCap = new ImageView();

    public Arrow()
    {
        this.arrowBody.setFitWidth(50.0);
        this.arrowBody.setFitHeight(67.0);
        this.arrowBody.setViewOrder(4);
        this.arrowCap.setFitWidth(64.0);
        this.arrowCap.setFitHeight(87.0);
        this.arrowCap.setViewOrder(0);
        GridPane.setHalignment(this.arrowBody, HPos.CENTER);
        GridPane.setValignment(this.arrowBody, VPos.CENTER);
        GridPane.setHalignment(this.arrowCap, HPos.CENTER);
        GridPane.setValignment(this.arrowCap, VPos.CENTER);
        this.setEnabled(false);
    }

    public void setBody(int bodyId)
    {
        if(this.positionIndex != this.originIndex)
        {
            switch (bodyId) {
                case 0: this.arrowBody.setImage(new Image("Interface\\ArrowVert.png")); break;
                case 1: this.arrowBody.setImage(new Image("Interface\\ArrowHoriz.png")); break;
                case 2: this.arrowBody.setImage(new Image("Interface\\ArrowCorner1.png")); break;
                case 3: this.arrowBody.setImage(new Image("Interface\\ArrowCorner2.png")); break;
                default: break;
            }
        }
    }

    public void setPos(int index)
    {
        this.positionIndex = index;
    }

    public void setOrigin(int index)
    {
        this.originIndex = index;
    }

    public void setCap(int capId)
    {
        this.arrowCap.setImage(new Image("Interface\\ArrowCap.png"));

        switch (capId) {
            case 0: break;
            case 1: this.arrowCap.setRotate(90); break;
            case 2: this.arrowCap.setRotate(-90); break;
            default: this.arrowCap.setVisible(false); break;
        }
    }

    public void setEnabled(boolean bool)
    {
        this.isEnabled = bool;

        if(bool == true)
        {
            this.arrowBody.setEffect(new ColorAdjust(0.0, 0.0, 0.0, 0.0));
        }
        else
        {
            this.arrowBody.setEffect(new ColorAdjust(0.0, -1.0, 0.0, 0.0));
        }
        this.arrowCap.setEffect(this.arrowBody.getEffect());
    }

    public ImageView getBody()
    {
        return this.arrowBody;
    }

    public ImageView getCap()
    {
        return this.arrowCap;
    }

    public int getOrigin()
    {
        return this.originIndex;
    }
}

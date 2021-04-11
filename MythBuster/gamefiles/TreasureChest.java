package gamefiles;

import controller.Controller;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public abstract class TreasureChest implements Touchable {

    protected double positionX;
    protected double positionY;
    protected double width = 100;
    protected double height = 100;
    private ImageView imageView;
    private Group treasureGroup;

    protected int cost = 10;
    protected boolean opened;

    public TreasureChest(double positionX, double positionY, int cost, String spritePath) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.cost = cost;

        Label costText = new Label(Integer.toString(cost));
        imageView = new ImageView(spritePath);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        costText.setPrefSize(100, 100);
        costText.setStyle("-fx-font-size: 30; -fx-font-weight: bold; -fx-text-fill: white;"
                + "-fx-alignment:CENTER;");
        costText.setTranslateY(-80);
        Node image = imageView;
        treasureGroup = new Group();
        treasureGroup.setLayoutX(positionX);
        treasureGroup.setLayoutY(positionY);
        treasureGroup.getChildren().addAll(image, costText);
    }

    @Override
    public Rectangle2D getBoundary() {
        return new Rectangle2D(positionX, positionY, width, height);
    }
    @Override
    public boolean intersects(Touchable other) {
        return other.getBoundary().intersects(this.getBoundary());
    }

    public boolean canOpen() {
        if (!opened && intersects(Controller.getPlayer()) && Controller.getPlayer().getCoins() >= cost) {
            return true;
        }
        return false;
    }
    public abstract void open();

    public boolean isOpened() {
        return opened;
    }

    public Group getGroup() {
        return treasureGroup;
    }

}

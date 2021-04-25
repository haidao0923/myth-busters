package gamefiles;

import controller.Controller;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
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

    private static int chestsOpened;

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
        if (!opened && intersects(Controller.getPlayer())
            && Controller.getPlayer().getCoins() >= cost) {
            return true;
        }
        return false;
    }
    public abstract void open();

    protected void dropCoins(int newCoins, int num, double positionX, double positionY) {
        DropMethods.dropCoins(newCoins, num, positionX, positionY);
    }

    public void displayReward(String text) {
        chestsOpened++;
        Label display = new Label(text);
        display.setPrefWidth(Controller.getW());
        display.setStyle("-fx-font-size: 30; -fx-font-weight: bold; -fx-text-fill: white;"
                + "-fx-alignment:CENTER;");
        display.setLayoutY(200);
        Platform.runLater(() -> {
            Controller.getGameScreen().getBoard().getChildren().add(display);
        });

        new AnimationTimer() {
            private int timer = 60;
            @Override
            public void handle(long currentNanoTime) {
                timer--;
                if (timer <= 0) {
                    Controller.getGameScreen().getBoard().getChildren().remove(display);
                    this.stop();
                }
            }
        }.start();
    }


    public boolean isOpened() {
        return opened;
    }

    public Group getGroup() {
        return treasureGroup;
    }

    public static int getChestsOpened() {
        return chestsOpened;
    }
    public static void setChestsOpened(int amount) {
        chestsOpened = amount;
    }
}

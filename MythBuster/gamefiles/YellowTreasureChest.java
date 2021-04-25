package gamefiles;

import controller.Controller;
import javafx.animation.AnimationTimer;

public class YellowTreasureChest extends TreasureChest {
    public YellowTreasureChest(double positionX, double positionY, int cost) {
        super(positionX, positionY, cost, "sprites/ChestYellow.png");
    }

    @Override
    public void open() {
        Controller.getPlayer().subtractCoins(cost);

        int coinAmount = (int) (Math.random() * 9 + 2) * 5;
        if (!atQueue.isEmpty()) {
            AnimationTimer e = atQueue.remove();
            e.stop();
        }
        dropCoins(coinAmount, 12, positionX, positionY);
        displayReward("You found " + Integer.toString(coinAmount) + " coins!");

        opened = true;
    }
}

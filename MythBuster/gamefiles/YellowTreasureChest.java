package gamefiles;

import controller.Controller;

public class YellowTreasureChest extends TreasureChest {
    public YellowTreasureChest(double positionX, double positionY, int cost) {
        super(positionX, positionY, cost, "sprites/ChestYellow.png");
    }

    @Override
    public void open() {
        Controller.getPlayer().subtractCoins(cost);

        int coinAmount = (int) (Math.random() * 9 + 2) * 5;
        dropCoins(coinAmount, 12, positionX, positionY);
        displayReward("You found " + Integer.toString(coinAmount) + " coins!");

        opened = true;
    }
}

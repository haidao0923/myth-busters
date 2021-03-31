package gamefiles.characters;

import controller.Controller;
import controller.GameLoop;
import gamefiles.Touchable;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Monster implements Touchable {
    protected String name;
    protected double maxHealth;
    protected double currentHealth;
    protected double percentageHealth;
    protected boolean isDead;
    protected double movementSpeed;
    protected double positionX;
    protected double positionY;
    protected double width;
    protected double height;
    protected ImageView imageView;

    protected Node image;
    protected Rectangle healthBar;
    protected Rectangle healthBarBacking;
    protected double healthBarWidth;

    protected Group monsterGroup;
    public Monster(String name, double health, double movementSpeed, String spritePath,
        double width, double height) {

        this.name = name;
        this.maxHealth = health;
        currentHealth = maxHealth;
        this.movementSpeed = ((Math.random() * 0.5) + 0.5) * movementSpeed; //Somewhat randomize movespeed to prevent stacking.
        this.width = width;
        this.height = height;
        this.healthBarWidth = width;

        isDead = false;

        imageView = new ImageView(spritePath);
        imageView.setFitWidth(this.width);
        imageView.setFitHeight(this.height);
        image = imageView;
        healthBar = new Rectangle(positionX, positionY - 30, healthBarWidth, 10);
        healthBar.setFill(Color.GREEN);
        healthBarBacking = new Rectangle(positionX, positionY - 30, healthBarWidth, 10);
        healthBarBacking.setFill(Color.RED);
        monsterGroup = new Group();
        monsterGroup.getChildren().addAll(image, healthBarBacking, healthBar);

    }


    public abstract void update();

    public Group getGroup() {
        return monsterGroup;
    }

    public void redrawHealthBar() {
        percentageHealth = currentHealth / maxHealth;
        healthBar.setWidth(healthBarWidth * percentageHealth);
    }

    public void checkDeath() {
        if (currentHealth <= 0) {
            isDead = true;
            Platform.runLater(() -> {
                monsterGroup.getChildren().removeAll(image, healthBar, healthBarBacking);
            });
            GameLoop.monsters.remove(this);
        }
    }


    public void addHealth(double value) {
        this.currentHealth += value;
    }
    public void takeDamage(double value) {
        addHealth(-value);
    }
    public void loseAllHealth() {
        takeDamage(maxHealth);
        checkDeath();
    }

    public void moveAbsolute(double x, double y) {
        positionX = x;
        positionY = y;
        monsterGroup.relocate(positionX, positionY);
    }

    public void moveRelative(double x, double y) {
        positionX += x;
        positionY += y;
        monsterGroup.relocate(positionX, positionY);
    }



    public Rectangle2D getBoundary() {
        return new Rectangle2D(positionX, positionY, width, height);
    }
    public boolean intersects(Touchable other) {
        return other.getBoundary().intersects(this.getBoundary());
    }

    public double getWidth() {
        return width;
    }
    public double getHeight() {
        return height;
    }

    public double getPositionX() {
        return positionX;
    }
    public double getPositionY() {
        return positionY;
    }

    public String getName() {
        return this.name;
    }

    public double getCurrentHealth() {
        return currentHealth;
    }

}

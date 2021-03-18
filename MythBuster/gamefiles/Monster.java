package gamefiles;

import controller.Controller;
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
    protected double attackSpeed;

    protected double positionX;
    protected double positionY;
    protected double width;
    protected double height;


    private Node image;
    private Rectangle healthBar;
    private double healthBarWidth = 120;

    protected Group monsterGroup;
    public Monster(String name, double health, double movementSpeed, double attackSpeed, String spritePath,
        double width, double height) {
        this.name = name;
        this.maxHealth = health;
        currentHealth = maxHealth;
        this.movementSpeed = movementSpeed;
        this.attackSpeed = attackSpeed;
        this.width = width;
        this.height = height;

        Controller.monsters.add(this);
        isDead = false;

        ImageView imageView = new ImageView(spritePath);
        imageView.setFitWidth(this.width);
        imageView.setFitHeight(this.height);
        image = imageView;
        healthBar = new Rectangle(positionX, positionY - 30, healthBarWidth, 10);
        healthBar.setFill(Color.RED);
        monsterGroup = new Group();
        monsterGroup.getChildren().addAll(image, healthBar);
        act();

    }

    public abstract void act();

    public Group getGroup() {
        return monsterGroup;
    }

    public void redrawHealthBar() {
        percentageHealth = currentHealth / maxHealth;
        healthBar.setWidth(healthBarWidth * percentageHealth);
    }

    public void checkDeath() {
        if (currentHealth < 0) {
            isDead = true;
            monsterGroup.getChildren().removeAll(image, healthBar);
            Controller.monsters.remove(this);
        }
    }

    public void addHealth(double value) {
        this.currentHealth += value;
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
}
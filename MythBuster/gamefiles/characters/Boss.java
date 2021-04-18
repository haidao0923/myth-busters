package gamefiles.characters;

import controller.Controller;
import controller.SpriteAnimation;
import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Boss extends Monster {
    Image idleImage = new Image("sprites/Boss/idle.png");
    Image attackImage = new Image("sprites/Boss/attacking.png");
    SpriteAnimation idleAnimation = new SpriteAnimation(imageView, Duration.millis(750), 4, 4,0, 0, 100, 100);
    SpriteAnimation attackAnimation = new SpriteAnimation(imageView, Duration.millis(750), 13, 6, 0, 0, 100, 100);
    private int damage = 150;
    private int damageCooldown = 60;
    public Boss() {
        super("The Boss", 500, 3, "sprites/Boss/idle.png", 250, 250);
        imageView.setViewport(new Rectangle2D(0, 0, 100, 100));
        healthBarBacking.setY(positionY - 10);
        healthBar.setY(positionY - 10);
        idle();
    }

    public void update() {
        double targetPositionX = Controller.getPlayer().getPositionX();
        double targetPositionY = Controller.getPlayer().getPositionY() - 30;
        checkDeath();
        redrawHealthBar();
        // flip sprite if needed
        double offsetX = targetPositionX - (positionX + width / 2);
        double offsetY = targetPositionY - positionY;
        if (offsetX > 0) {
            if (Math.abs(offsetX) > 10) {
                imageView.setScaleX(1);
            }
        } else {
            if (Math.abs(offsetX) > 10) {
                imageView.setScaleX(-1);
            }
        }

        double magnitude = Math.sqrt(offsetX * offsetX + offsetY * offsetY);
        if (Controller.getPlayer().intersects(this)) {
            if (damageCooldown > 0) {
                damageCooldown--;
            } else {
                damageCooldown = 60;
                idleAnimation.pause();
                attack();
                Controller.getPlayer().takeDamage(damage);
                //System.out.println(Controller.getPlayer().getCurrentHealth());
            }
        } else if (Math.abs(offsetX) > 10 || Math.abs(offsetY) > 10) {
            damageCooldown = 60;
            moveRelative(movementSpeed * offsetX / magnitude, movementSpeed * offsetY / magnitude);
        }
    }
    public void idle() {
        imageView.setImage(idleImage);
        idleAnimation.setCycleCount(500);
        idleAnimation.play();
    }
    public void attack() {
        imageView.setImage(attackImage);
        attackAnimation.setCycleCount(1);
        attackAnimation.play();
        attackAnimation.setOnFinished(actionEvent -> {
            attackAnimation.stop();
            imageView.setImage(idleImage);
            imageView.setViewport(new Rectangle2D(0, 0, 100, 100));
            idleAnimation.play();
        });
    }
}

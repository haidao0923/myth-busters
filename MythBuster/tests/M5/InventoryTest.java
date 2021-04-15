package tests.M5;

import controller.Controller;
import controller.GameLoop;
import gamefiles.Inventory;

import gamefiles.items.ItemDatabase;

import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.assertj.core.internal.bytebuddy.pool.TypePool;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.service.query.EmptyNodeQueryException;
import org.testfx.service.query.NodeQuery;
import org.testfx.util.NodeQueryUtils;

import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.testfx.api.FxAssert.verifyThat;
import static org.junit.Assert.assertTrue;

public class InventoryTest extends ApplicationTest {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Controller controller = new Controller();
        controller.start(primaryStage);
    }

    @Test
    public void testInventoryScreen() {
        clickOn("Start Game");
        clickOn("#HeroNameTextField");
        write("test123");
        clickOn("#startingWeaponSelector");
        clickOn("SPEAR");
        clickOn("Begin!");
        push(KeyCode.I);

        verifyThat("Press I to go back.", NodeMatchers.isNotNull());

    }

    @Test(expected = EmptyNodeQueryException.class)
    public void testInventoryGoBack() {
        clickOn("Start Game");
        clickOn("#HeroNameTextField");
        write("test123");
        clickOn("#startingWeaponSelector");
        clickOn("SPEAR");
        clickOn("Begin!");
        push(KeyCode.I);
        push(KeyCode.I);//Go back.
        verifyThat("Press I to go back.", NodeMatchers.isNotNull());


    }

    @Test
    public void testInventorySize() {
        Inventory.addToInventory(ItemDatabase.getItem(0));
        Inventory.addToInventory(ItemDatabase.getItem(1));
        Inventory.addToInventory(ItemDatabase.getItem(2));

        clickOn("Start Game");
        clickOn("#HeroNameTextField");
        write("test123");
        clickOn("#startingWeaponSelector");
        clickOn("SPEAR");
        clickOn("Begin!");
        push(KeyCode.I);

        assertEquals(3, Inventory.getInventory().size());


        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Inventory.clearInventory();
                assertEquals(0, Inventory.getInventory().size());
            }
        });
    }



}

package unsw.dungeon;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.File;

/**
 * A DungeonLoader that also creates the necessary ImageViews for the UI,
 * connects them via listeners to the model, and creates a controller.
 * @author Robert Clifton-Everest
 *
 */
public class DungeonControllerLoader extends DungeonLoader {

    private List<ImageView> entities; //this list is the same as initialEntities in dungeoncontroller
    private DungeonController dungeonController;

    //Images
    private Image playerImage;
    private Image wallImage;
    private Image exitImage;
    private Image treasureImage;
    private Image doorImage;
    private Image keyImage;
    private Image boulderImage;
    private Image switchImage;
    private Image portalImage;
    private Image enemyImage;
    private Image archerImage;
    private Image swordImage;
    private Image potionImage;
    private Image openDoorImage;
    private Image fireImage;
    private Image wandImage;
    private Image bossImage;

    public DungeonControllerLoader(String filename)
            throws FileNotFoundException {
        super(filename);
        entities = new ArrayList<>();
        // TODO change files to src/resources/images
        playerImage = new Image((new File("src/resources/images/human_new.png")).toURI().toString());
        wallImage = new Image((new File("src/resources/images/brick_brown_0.png")).toURI().toString());
        exitImage = new Image((new File("src/resources/images/exit.png")).toURI().toString());
        treasureImage = new Image((new File("src/resources/images/gold_pile.png")).toURI().toString());
        doorImage = new Image((new File("src/resources/images/closed_door.png")).toURI().toString());
        keyImage = new Image((new File("src/resources/images/key.png")).toURI().toString());
        boulderImage = new Image((new File("src/resources/images/boulder.png")).toURI().toString());
        switchImage = new Image((new File("src/resources/images/pressure_plate.png")).toURI().toString());
        portalImage = new Image((new File("src/resources/images/portal.png")).toURI().toString());
        enemyImage = new Image((new File("src/resources/images/deep_elf_master_archer.png")).toURI().toString());
        archerImage = new Image((new File("src/resources/images/gnome.png")).toURI().toString());
        wandImage = new Image((new File("src/resources/images/FireWand.png")).toURI().toString());
        fireImage = new Image((new File("src/resources/images/fire.gif")).toURI().toString());
        swordImage = new Image((new File("src/resources/images/greatsword_1_new.png")).toURI().toString());
        potionImage = new Image((new File("src/resources/images/bubbly.png")).toURI().toString());
        openDoorImage = new Image((new File("src/resources/images/open_door.png")).toURI().toString());
        bossImage = new Image((new File("src/resources/images/impDemon.png")).toURI().toString());
        // dungeoncontrollerloader now instantiates dungeoncontroller within itself
        dungeonController = new DungeonController(load(), entities,this);

    }

    @Override
    public void onLoad(Entity player) {
        ImageView view = new ImageView(playerImage);
        addEntity(player, view);
    }

    @Override
    public void onLoad(Wall wall) {
        ImageView view = new ImageView(wallImage);
        addEntity(wall, view);
    }

    @Override
    public void onLoad(Exit exit) {
        ImageView view = new ImageView(exitImage);
        addEntity(exit, view);
    }

    @Override
    public void onLoad(Treasure treasure) {
        ImageView view = new ImageView(treasureImage);
        addEntity(treasure, view);
    }
    @Override
    public void onLoad(Door door) {
        ImageView view = new ImageView(doorImage);
        addEntity(door, view);
    }
    @Override
    public void onLoad(Key key) {
        ImageView view = new ImageView(keyImage);
        addEntity(key, view);
    }
    @Override
    public void onLoad(Boulder boulder) {
        ImageView view = new ImageView(boulderImage);
        addEntity(boulder, view);
    }
    @Override
    public void onLoad(FloorSwitch floorswitch) {
        ImageView view = new ImageView(switchImage);
        addEntity(floorswitch, view);
    }
    @Override
    public void onLoad(Portal portal) {
        ImageView view = new ImageView(portalImage);
        addEntity(portal, view);
    }
    @Override
    public void onLoad(Enemy enemy) {
        ImageView view = new ImageView(enemyImage);
        addEntity(enemy, view);
    }
    @Override
    public void onLoad(Archer archer) {
        ImageView view = new ImageView(archerImage);
        addEntity(archer, view);
    }
    @Override
    public void onLoad(Boss boss) {
        ImageView view = new ImageView(bossImage);
        addEntity(boss, view);
    }
    @Override
    public void onLoad(Arrow arrow) {
        ImageView view = new ImageView(fireImage);
        addEntity(arrow, view);
    }
    @Override
    public void onLoad(Sword sword) {
        ImageView view = new ImageView(swordImage);
        addEntity(sword, view);
    }
    @Override
    public void onLoad(Wand wand) {
        ImageView view = new ImageView(wandImage);
        addEntity(wand, view);
    }
    @Override
    public void onLoad(InvincibilityPotion potion) {
        ImageView view = new ImageView(potionImage);
        addEntity(potion, view);
    }

    private void addEntity(Entity entity, ImageView view) {
        trackPosition(entity, view);
        entities.add(view);
        //entity.setView(view);
    }

    private void removeEntity(ImageView view){
        entities.remove(view);
    }

    /**
     * Set a node in a GridPane to have its position track the position of an
     * entity in the dungeon.
     *
     * By connecting the model with the view in this way, the model requires no
     * knowledge of the view and changes to the position of entities in the
     * model will automatically be reflected in the view.
     * @param entity
     * @param node
     */
    private void trackPosition(Entity entity, Node node) {
        GridPane.setColumnIndex(node, entity.getX());
        GridPane.setRowIndex(node, entity.getY());
        entity.x().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setColumnIndex(node, newValue.intValue());
                //Should move demon to front
                if (entity instanceof Boss){
                    node.toFront();
                }
            }
        });
        entity.y().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setRowIndex(node, newValue.intValue());
                //Should move demon to front
                if (entity instanceof Boss){
                    node.toFront();
                }
            }
        });
        ///////////////////////////////////////////////////////
        entity.exists().addListener((observable, oldValue, newValue) -> {
            if (newValue == false ){
                dungeonController.getSquares().getChildren().remove(node);
                removeEntity(((ImageView)node));
            }
                
        });
        if (entity instanceof Door){
            ((Door)entity).open().addListener((observable, oldValue, newValue) -> {
                if (newValue == true ){
                    dungeonController.getSquares().getChildren().remove(node);
                    removeEntity(((ImageView)node));
                    dungeonController.getSquares().add(new ImageView(openDoorImage),entity.getX(),entity.getY());
                }
            });
        }
        ///////////////////////////////////////////////////////
    }

    /**
     * Create a controller that can be attached to the DungeonView with all the
     * loaded entities.
     * @return
     * @throws FileNotFoundException
     */
    public DungeonController loadController() throws FileNotFoundException {
        //return new DungeonController(load(), entities);
        return dungeonController;
    }


}

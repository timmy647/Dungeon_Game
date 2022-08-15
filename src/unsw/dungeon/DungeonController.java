package unsw.dungeon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

//import org.hamcrest.core.IsInstanceOf;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.converter.IntegerStringConverter;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

//import static org.junit.Assume.assumeNoException;

import java.io.File;

/**
 * A JavaFX controller for the dungeon.
 * @author Robert Clifton-Everest
 *
 */
public class DungeonController {

    @FXML
    private GridPane squares;

    @FXML
    private Pane pane;

    @FXML
    private Text treasureNum;

    @FXML
    private Text swordNum;

    @FXML
    private Text fireNum;

    @FXML
    private ImageView potionImg;

    @FXML
    private Text potionTime;

    private Timeline timelineEnemy,timelinePotion,timelineGoal,timelineArcher,timelineArrow,timelineBoss;
    @FXML
    private ImageView swordImg;

    @FXML
    private ImageView wandImg;

    @FXML
    private Text goalsText;

    @FXML
    private GridPane bossHP;

    @FXML
    private Text bossHPText;

    private List<ImageView> initialEntities;

    private Player player;

    private Dungeon dungeon;

    private DungeonControllerLoader dungeonControllerLoader;

    public DungeonController(Dungeon dungeon, List<ImageView> initialEntities,DungeonControllerLoader dungeonControllerLoader) {
        this.dungeon = dungeon;
        this.player = dungeon.getPlayer();
        this.initialEntities = initialEntities;
        this.dungeonControllerLoader = dungeonControllerLoader;

        timelineEnemy = new Timeline(new KeyFrame(Duration.millis(333), e -> dungeon.moveEnemies()));
        timelineEnemy.setCycleCount(Timeline.INDEFINITE);
        timelineEnemy.play();

        timelineArcher = new Timeline(new KeyFrame(Duration.millis(333), e -> dungeon.moveArchers(this)));
        timelineArcher.setCycleCount(Timeline.INDEFINITE);
        timelineArcher.play();

        timelineBoss = new Timeline(new KeyFrame(Duration.millis(333), e -> dungeon.moveBosses(this)));
        timelineBoss.setCycleCount(Timeline.INDEFINITE);
        timelineBoss.play();

        timelineArrow = new Timeline(new KeyFrame(Duration.millis(111), e -> dungeon.moveArrows()));
        timelineArrow.setCycleCount(Timeline.INDEFINITE);
        timelineArrow.play();

        timelinePotion = new Timeline(new KeyFrame(Duration.millis(25), e -> player.tickDuration(25)));
        timelinePotion.setCycleCount(Timeline.INDEFINITE);
        timelinePotion.play();

        EventHandler<ActionEvent> event =  
        new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) { 
                if (dungeon.getGoal().check()) {
                    Loader loader = new Loader();
                    loader.setNormalScene("Win.fxml");
                    timelineEnemy.stop();
                    timelinePotion.stop();
                    timelineGoal.stop();
                    timelineArcher.stop();
                    timelineArrow.stop();
                    timelineBoss.stop();
                } else if (dungeon.getGameOver().check()) {
                    Loader loader = new Loader();
                    loader.setNormalScene("GameOver.fxml");
                    timelineEnemy.stop();
                    timelinePotion.stop();
                    timelineGoal.stop();
                    timelineArcher.stop();
                    timelineArrow.stop();
                    timelineBoss.stop();
                }
            } 
        }; 
        timelineGoal = new Timeline(new KeyFrame(Duration.millis(100), event));
        timelineGoal.setCycleCount(Timeline.INDEFINITE);
        timelineGoal.play();
    }

    @FXML
    public void initialize() {
        squares.setAlignment(Pos.BOTTOM_CENTER);
        Image ground = new Image((new File("images/dirt_0_new.png")).toURI().toString());
        
        // Add the ground first so it is below all other entities
        for (int x = 0; x < dungeon.getWidth(); x++) {
            for (int y = 0; y < dungeon.getHeight(); y++) {
                squares.add(new ImageView(ground), x, y);
            }
        }

        for (ImageView entity : initialEntities){
            squares.getChildren().add(entity);
        }
        
        IntegerProperty intTreasure = player.treasure();
        intTreasure.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                treasureNum.setText(Integer.toString(intTreasure.get()));
            }
        });

        IntegerProperty intSword = player.sword();
        intSword.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                swordNum.setText(Integer.toString(intSword.get()));
            }
        });

        IntegerProperty intWand = player.wand();
        intWand.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                fireNum.setText(Integer.toString(intWand.get()));
            }
        });

        IntegerProperty potionDuration = player.duration();
        potionTime.setVisible(false);
        potionTime.managedProperty().bind(potionImg.visibleProperty());
        potionDuration.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                potionTime.setText(Integer.toString(potionDuration.get()/1000));
            }
        });

        potionImg.setVisible(false);
        potionImg.managedProperty().bind(potionImg.visibleProperty());
        BooleanProperty potion = player.invincible();
        potion.addListener((observable, oldValue, newValue) -> {
            if (potion.get()) {
                potionImg.setVisible(true);
                potionTime.setVisible(true);
            } else {
                potionImg.setVisible(false);
                potionTime.setVisible(false);
            }
        });

        swordImg.setVisible(true);
        wandImg.setVisible(false);
        StringProperty weapon = player.weapon();
        weapon.addListener((observable, oldValue, newValue) -> {
            if (weapon.get().equals("wand")) {
                wandImg.setVisible(true);
                swordImg.setVisible(false);
            } else if (weapon.get().equals("sword")) {
                swordImg.setVisible(true);
                wandImg.setVisible(false);
            }
        });



        Goal goal = dungeon.getGoal();
        String text = "Goals: ";
        text = getGoalText(goal, text);
        goalsText.setText(text);

        //setup boss health
        bossHPText.setVisible(false);
        if (!dungeon.findBoss().isEmpty()) {
            bossHPText.setVisible(true);
            setBossHealth();
        }
    }

    public void setBossHealth() {
        ArrayList<Image> images = new ArrayList<Image>();
        ArrayList<ImageView> hp = new ArrayList<ImageView>();
        for (int i=0; i<10; i++) {
            images.add(new Image("/resources/images/red_health.png"));
            hp.add(new ImageView());
            hp.get(i).setFitWidth(50);
            hp.get(i).setFitHeight(30);
            hp.get(i).setImage(images.get(i));
            bossHP.add(hp.get(i), i, 0);
        }
        Boss boss = dungeon.findBoss().get(0);
        IntegerProperty health = boss.health();
        health.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                if (health.get() < 10)
                    hp.get(9).setVisible(false);
                if (health.get() < 9)
                    hp.get(8).setVisible(false);
                if (health.get() < 8)
                    hp.get(7).setVisible(false);
                if (health.get() < 7)
                    hp.get(6).setVisible(false);
                if (health.get() < 6)
                    hp.get(5).setVisible(false);
                if (health.get() < 5)
                    hp.get(4).setVisible(false);
                if (health.get() < 4)
                    hp.get(3).setVisible(false);
                if (health.get() < 3)
                    hp.get(2).setVisible(false);
                if (health.get() < 2)
                    hp.get(1).setVisible(false);
                if (health.get() < 1)
                    hp.get(0).setVisible(false);
            }
        }); 

    }

    public String getGoalText(Goal goal, String text) {
        if (goal instanceof GoalAnd) {
            GoalAnd goaland = (GoalAnd) goal;
            text = text + "(";
            for (int i=0; i<goaland.getGoals().size(); i++) {
                Goal g = goaland.getGoals().get(i);
                text = getGoalText(g, text);
                if (i < goaland.getGoals().size()-1) 
                    text = text + " and ";
            }
            text = text + ")";
        } else if (goal instanceof GoalOr) {
            GoalOr goalor = (GoalOr) goal;
            text = text + "(";
            for (int i=0; i<goalor.getGoals().size(); i++) {
                Goal g = goalor.getGoals().get(i);
                text = getGoalText(g, text);
                if (i < goalor.getGoals().size()-1) 
                    text = text + " or ";
            }
            text = text + ")";
        } else if (goal instanceof GoalExit) {
            text = text + "go to exit";
        } else if (goal instanceof GoalBoulder) {
            text = text + "boulders on switches";
        } else if (goal instanceof GoalEnemy) {
            text = text + "kill all enemies";
        } else if (goal instanceof GoalTreasure) {
            text = text + "collect all treasures";
        }
        return text;
    }

    public void DynamicAddArrow(Dungeon dungeon, int x, int y, String direction){
        if(x >= 0 && x < dungeon.getWidth() && y >= 0 && y < dungeon.getHeight()){
            dungeonControllerLoader.loadArrow(dungeon, x, y, direction);
            for (ImageView entity : initialEntities){
                if(squares.getChildren().contains(entity) == false){
                    squares.getChildren().add(entity);
                }
            }
        }
        
    }

    @FXML 
    public void backtoMenu(ActionEvent event) {
        Loader loader = new Loader();
        timelineEnemy.stop();
        timelinePotion.stop();
        timelineGoal.stop();
        timelineArcher.stop();
        timelineArrow.stop();
        timelineBoss.stop();
        loader.setNormalScene("Start.fxml");
    }

    @FXML
    public void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
        case UP:
        {
            player.moveUp();
            break;
        }
        case DOWN:
        {
            player.moveDown();
            break;
        }
        case LEFT:
        {
            player.moveLeft();
            break;
        }
        case RIGHT:
        {
            player.moveRight();
            break;
        }
        case W:
        {
            player.attack(this, "up");
            break;
        }
        case S:
        {
            player.attack(this, "down");
            break;
        }
        case A:
        {
            player.attack(this, "left");
            break;
        }
        case D:
        {
            player.attack(this, "right");
            break;
        }
        case SPACE:
        {
            player.changeWeapon();
            break;
        }
        default:
            break;
        }
    }

    public GridPane getSquares() {
        return squares;
    }

}


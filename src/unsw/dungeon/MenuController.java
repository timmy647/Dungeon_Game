package unsw.dungeon;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class MenuController {
    @FXML
    private Button Start;
    @FXML
    private TextField inputMap;
    @FXML
    private Text invalidInput;

    private Loader loader;

    public MenuController() {
        this.loader = new Loader();
    }

    @FXML
    public void handleStart(ActionEvent event) throws IOException {
        loader.setNormalScene("selectLevel.fxml");
    }

    @FXML
    public void selectMaze(MouseEvent event) {
        loader.setDungeonScene("maze.json");
    }

    @FXML
    public void selectBoulder(MouseEvent event) {
        loader.setDungeonScene("boulders.json");
    }

    @FXML
    public void selectAdvanced(MouseEvent event) {
        loader.setDungeonScene("advanced.json");
    }

    @FXML 
    public void backtoMenu(ActionEvent event) {
        loader.setNormalScene("Start.fxml");
    }

    @FXML
    public void playAgain(ActionEvent event) {
        String input = loader.getPrevStage();
        if (input != null) {
            loader.setDungeonScene(input);
        }
    }

    @FXML
    public void nextLevel(ActionEvent event) {
        String input = loader.getNextStage();
        if (input != null) {
            loader.setDungeonScene(input);
        } else {
            int position[] = {300, 300, 300, 80, 130, 60};
            Popup popup = createPopup("You have finished all level games!", position);
            popup.show(loader.getStage());
        }
    }

    @FXML
    public void loadMap(ActionEvent event) {
        String input = inputMap.getText();
        if (input != null) {
            Scene s = loader.setDungeonScene(input);
            if (s == null) {
                invalidInput.setText("Invalid Input");
            } else {
                invalidInput.setText("");
            }
        }
    }

    @FXML
    public void showInstructions(ActionEvent event) {
        String up = "UP KEY: player moves upward";
        String down = "DOWN KEY: player moves downward";
        String left = "LEFT KEY: player moves left";
        String right = "RIGHT KEY: player moves right";
        String space = "SPACE: swap weapons";
        String w = "W KEY: attack upward";
        String s = "S KEY: attack downward";
        String a = "A KEY: attack left";
        String d = "D KEY: attack right";
        String instruction = up+"\n"+down+"\n"+left+"\n"+right+"\n"+space+"\n"+w+"\n"+s+"\n"+a+"\n"+d;
        int position[] = {300, 250, 300, 230, 130, 200};
        Popup popup = createPopup(instruction, position);
        popup.show(loader.getStage());
    }

    public Popup createPopup(String text, int position[]) {
        Popup popup = new Popup();
        Label label = new Label(text);
        Button button = new Button("back");
        label.setStyle(" -fx-background-color: white;");
        button.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                if (popup.isShowing()) {
                    popup.hide();
                }
            }
        });
        popup.getContent().add(label);
        popup.getContent().add(button);
        popup.setWidth(position[0]);
        popup.setHeight(position[1]);
        label.setAlignment(Pos.CENTER);
        button.setAlignment(Pos.CENTER);
        label.setPrefWidth(position[2]);
        label.setPrefHeight(position[3]);
        button.setPrefWidth(50); 
        button.setPrefHeight(25);
        button.setLayoutX(position[4]);
        button.setLayoutY(position[5]);
        popup.setAutoHide(true);
        popup.setAutoFix(true);
        return popup;
    }
}
package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Loader {
    // current stage
    private static Stage stage;
    private static String prevStage;
    private static List<String> stages = Arrays.asList("boulders.json", "maze.json", "advanced.json","firefight.json");

    public Loader() {
        if (stage == null) {
            stage = new Stage();
        }
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public String getPrevStage() {
        return prevStage;
    }
    public String getNextStage() {
        for (int i=0; i<stages.size()-1; i++) {
            if (prevStage.equals(stages.get(i))) {
                return stages.get(i+1);
            }
        }
        return null;
    }

    public Scene loadNormal(String name) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(name));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            root.requestFocus();
            return scene;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Scene loadDungeon(String input) throws IOException {
        DungeonControllerLoader Dloader;
        prevStage = input;
        try {
            Dloader = new DungeonControllerLoader(input);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DungeonView.fxml"));
            loader.setController(Dloader.loadController());
            Parent root = loader.load();
            Scene scene = new Scene(root);
            root.requestFocus();
            return scene;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Scene setDungeonScene(String input) {
        stage.setTitle("Dungeon");
        try {
            Scene scene = loadDungeon(input);
            if (scene == null) {
                return null;
            }
            stage.close();
            stage.setScene(scene);
            stage.show();
            return scene;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public void setNormalScene(String input) {
        stage.setTitle("Menu");
        try {
            Scene scene = loadNormal(input);
            stage.close();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
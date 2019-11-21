package clipboard;

import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        // display user interface
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/Login.fxml"));
        Parent root = loader.load();
        LoginController UI = loader.getController();

        // append to scene
        Scene scene = new Scene(root,600,550);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

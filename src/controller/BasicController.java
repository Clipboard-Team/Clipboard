package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Admin;
import model.Lead;
import model.Member;
import model.Project;


public class BasicController {

    public BasicController changeScreen(String path, ActionEvent event, BasicController controller){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent parent = loader.load();
            controller = loader.getController();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return controller;
    }
    void start(){

    }
    void start(Project project){

    }
    void start(Project project, Member member){

    }

    void start(Project project, Admin admin){

    }

    void start(Project project, Lead lead){

    }
}

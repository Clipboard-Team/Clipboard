package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class LoginController extends BasicController{
    @FXML Button loginButton, createButton;

    public void handleLoginButton(ActionEvent event){
        BasicController controller = changeScreen("../view/Home.fxml", event, new HomeController());
        controller.start();
    }
    public void handleCreateButton(ActionEvent event){
        BasicController controller = changeScreen("../view/NewProject.fxml", event, new NewProjectController());
        controller.start();
    }
}

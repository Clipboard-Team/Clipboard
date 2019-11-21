package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Project;

public class NewProjectController extends BasicController{
    @FXML TextField projectName;
    @FXML Button cancelButton, nextButton;

    public void handleCancelButton(ActionEvent event){
        BasicController controller = changeScreen("../view/Login.fxml", event, new LoginController());
        controller.start();
    }
    public void handleNextButton(ActionEvent event){
        if(!projectName.getText().isEmpty()){
            Project project = new Project(projectName.getText());
            BasicController controller = changeScreen("../view/NewTeam.fxml", event, new NewTeamController());
            controller.start(project);
        }
    }
}

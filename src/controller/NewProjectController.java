package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Project;

public class NewProjectController extends BasicController{

    // Functionality
    @FXML TextField projectName;
    @FXML Button cancelButton, nextButton;

    public void handleNextButton(ActionEvent event){
        if(!projectName.getText().isEmpty()){
            // initialize data
            Project project = new Project(projectName.getText());

            // pass data
            BasicController controller = changeScreen("../view/NewTeam.fxml", event, new NewTeamController());
            controller.start(project);
        }
    }

    public void handleCancelButton(ActionEvent event){
        BasicController controller = changeScreen("../view/Login.fxml", event, new LoginController());
        controller.start();
    }
}

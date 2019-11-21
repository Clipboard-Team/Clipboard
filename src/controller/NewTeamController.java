package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Project;
import model.Team;

public class NewTeamController extends BasicController{
    Project project = null;
    @FXML TextField teamName;
    @FXML Button backButton, nextButton;

    public void handleBackButton(ActionEvent event){
        BasicController controller = changeScreen("../view/NewProject.fxml", event, new NewProjectController());
        controller.start();
    }
    public void handleNextButton(ActionEvent event){
        if(!teamName.getText().isEmpty()){
            project.setTeam(new Team(teamName.getText()));
            BasicController controller = changeScreen("../view/NewAdmin.fxml", event, new NewAdminController());
            controller.start(project);
        }
    }
    public void start(Project project){
        this.project = project;
    }
}

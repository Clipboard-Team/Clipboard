package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Project;
import model.Team;

public class NewTeamController extends BasicController{
    // Data
    private Project project = null;

    // Functionality
    @FXML TextField teamName;
    @FXML Button backButton, nextButton;

    public void start(Project project){
        // receive data
        this.project = project;
    }

    public void handleNextButton(ActionEvent event){
        if(!teamName.getText().isEmpty()){
            // add data
            project.setTeam(new Team(teamName.getText()));

            // pass data
            BasicController controller = changeScreen("../view/NewAdmin.fxml", event, new NewAdminController());
            controller.start(project);
        }
    }

    public void handleBackButton(ActionEvent event){
        BasicController controller = changeScreen("../view/NewProject.fxml", event, new NewProjectController());
        controller.start();
    }
}

package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Member;
import model.Project;

public class NewAdminController extends BasicController {

    // Data
    private Project project;

    // Functionality
    @FXML TextField username;
    @FXML Button backButton, nextButton;

    public void start(Project project){
        // receive data
        this.project = project;
    }

    public void handleNextButton(ActionEvent event){
        if(!username.getText().isEmpty()){
            // add data
            project.getTeam().addMember(new Member(username.getText(), project.getTeam(), "Admin"));

            // pass data
            BasicController controller = changeScreen("../view/NewMember.fxml", event, new NewMemberController());
            controller.start(project);
        }
    }

    public void handleBackButton(ActionEvent event){
        BasicController controller = changeScreen("../view/NewTeam.fxml", event, new NewTeamController());
        controller.start();
    }
}
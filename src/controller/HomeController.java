package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import model.Member;
import model.Project;
import model.Task;

public class HomeController extends BasicController{
    Project project = null;
    Member member = null;
    @FXML Label projectTitle, username, role;
    @FXML Button logoutButton, createButton, editButton
            , deleteButton, leftButton, rightButton, manageButton;
    @FXML ListView<Task> assignedTasks;
    @FXML ListView<Task> completedTasks;

    void start(Project project, Member member){
        this.project = project;
        this.member = member;
        projectTitle.setText(project.getTitle());
        username.setText(member.getName());
        //role.setText(member.getRole());
    }

    public void handleLogoutButton(ActionEvent event){

    }
    public void handleCreateButton(ActionEvent event){

    }
    public void handleEditButton(ActionEvent event){

    }
    public void handleDeleteButton(ActionEvent event){

    }
    public void handleLeftButton(ActionEvent event){

    }
    public void handleRightButton(ActionEvent event){

    }
    public void handleManageButton(ActionEvent event){

    }
}

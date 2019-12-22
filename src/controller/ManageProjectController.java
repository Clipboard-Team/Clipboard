package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.Member;
import model.Project;
import model.Task;

public class ManageProjectController extends BasicController {
    @FXML Button renameProjectButton, renameTeamButton, membersButton,
            viewAnalyticsButton, updateButton, clearButton, logoutButton, homeButton;
    @FXML TextField searchTextField;
    @FXML Text projectText, teamText;
    @FXML DatePicker startDatePicker, endDatePicker;
    private Project project = null;
    private Member member = null;

    void start(Project project, Member member){
        this.project = project;
        this.member = member;
        printCurrentState();
        refreshScreen();
    }


    private void printCurrentState(){
        System.out.println("Printing Current State of ManageProjectController: ");
        System.out.println("\tCurrent Member: "+member.getName());
        System.out.println("\tProject Name: "+project.getTitle());
        System.out.println("\tTeam name: "+project.getTeam().getTitle());
        for(Member m : project.getTeam().getMembers()){
            System.out.println("\tMember: "+m.getName());
        }
        for(Task t : project.getTeam().getTasks()){
            System.out.println("\tTask: "+t.toString());
        }
    }
    private void refreshScreen(){
        this.projectText.setText(project.getTitle());
        this.teamText.setText(project.getTeam().getTitle());
    }
    public void handleRenameProjectButton(ActionEvent event){
        System.out.println("Rename Project Tapped");
    }
    public void handleRenameTeamButton(ActionEvent event){
        System.out.println("Rename Team Tapped");
    }
    public void handleMembersButton(ActionEvent event){
        System.out.println("Members Tapped");
    }
    public void handleViewAnalyticsButton(ActionEvent event){
        System.out.println("View Analytics Tapped");
    }
    public void handleUpdateButton(ActionEvent event){
        System.out.println("Update Tapped");
    }
    public void handleClearButton(ActionEvent event){
        System.out.println("Clear Tapped");
    }
    public void handleLogoutButton(ActionEvent event){
        System.out.println("Logout Tapped");
    }
    public void handleHomeButton(ActionEvent event){
        System.out.println("Home Tapped");
    }
}

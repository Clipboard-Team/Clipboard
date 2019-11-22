package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.Comment;
import model.Member;
import model.Project;
import model.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeController extends BasicController{
    Project project = null;
    Member member = null;
    @FXML Label projectTitle, username, role;
    @FXML Button logoutButton, createButton, editButton
            , deleteButton, leftButton, rightButton, manageButton;
    @FXML ListView<Task> assignedTasks, completedTasks;
    @FXML VBox mainWindow;

    @FXML ListView<Task> toDoListView, inProgressListView, haltedListView, doneListView;
    ObservableList<Task> observableListToDo;
    ObservableList<Task> observableListInProgress;
    ObservableList<Task> observableListHalted;
    ObservableList<Task> observableListDone;

    @FXML TextField taskTitle, commentTextField;
    @FXML TextArea descriptionTextArea;
    @FXML ComboBox statusComboBox, assignedComboBox, difficultyComboBox;
    @FXML Button confirmButton, cancelButton;
    @FXML DialogPane createTaskPane;
    List<String> taskTypes = new ArrayList<>();
    ObservableList<String> observableListTaskTypes;
    List<String> difficulties = new ArrayList<>();
    ObservableList<String> observableListDifficulties;
    List<Member> members = new ArrayList<>();
    ObservableList<Member> observableListMembers;

    void start(Project project, Member member){
        taskTypes.clear();
        taskTypes.add("To Do");
        taskTypes.add("In Progress");
        taskTypes.add("Halted");
        taskTypes.add("Done");
        difficulties.clear();
        difficulties.add("Easy");
        difficulties.add("Medium");
        difficulties.add("Hard");
        difficulties.add("Unknown");
        members.clear();
        for(Member mem : project.getTeam().getMembers()){
            members.add(mem);
        }

        // TODO:
        /*
        *   clear all status observable lists, traverse through project tasks and use
        *   switch cases to add to the correct observable list, set list views for each
        * */

        this.project = project;
        this.member = member;
        projectTitle.setText(project.getTitle());
        username.setText(member.getName());
        //role.setText(member.getRole());
    }

    public void handleLogoutButton(ActionEvent event){

    }
    public void handleCreateButton(ActionEvent event){
        try{
            observableListTaskTypes = FXCollections.observableArrayList(taskTypes);
            observableListDifficulties = FXCollections.observableArrayList(difficulties);
            observableListMembers = FXCollections.observableArrayList(members);
            statusComboBox.setItems(observableListTaskTypes);
            difficultyComboBox.setItems(observableListDifficulties);
            assignedComboBox.setItems(observableListMembers);

            createTaskPane.setVisible(true);
            mainWindow.setDisable(true);
        }catch(Exception e){
            e.printStackTrace();
        }
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
    public void handleCancelButton(ActionEvent event){
        try{
            createTaskPane.setVisible(false);
            mainWindow.setDisable(false);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void handleConfirmButton(ActionEvent event){
        try{
            if(!statusComboBox.getSelectionModel().isEmpty() && !taskTitle.getText().isEmpty() && !difficultyComboBox.getSelectionModel().isEmpty()){
                System.out.println("Valid input");
                String title = taskTitle.getText();
                String status = statusComboBox.getValue().toString();
                String difficulty = difficultyComboBox.getValue().toString();
                Task t = new Task(title, status, difficulty);
                if(!descriptionTextArea.getText().isEmpty()){
                    t.setDescription(descriptionTextArea.getText());
                }
                if(!assignedComboBox.getSelectionModel().isEmpty()){
                    Member member = null;
                    for(Member mem : members){
                        if(mem.getName().equals(assignedComboBox.getValue().toString())){
                            member = mem;
                            break;
                        }
                    }
                    t.setAssignedTo(member);
                }
                if(!commentTextField.getText().isEmpty()){
                    t.addComment(new Comment(commentTextField.getText(), new Date()));
                }
                //project.addTask(t);
                System.out.println("Adding task to project");
                System.out.println("Task: "+t.getTitle()+" "+t.getStatus()+" "+t.getDifficulty()
                        +"\n\t"+t.getDescription()+"\n\t"+t.getComments()+"\n\t"+t.getAssignedTo());
            } else{
                System.out.println("Invalid input");
            }
            createTaskPane.setVisible(false);
            mainWindow.setDisable(false);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

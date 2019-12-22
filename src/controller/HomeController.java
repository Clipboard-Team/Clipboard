package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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
    int index;
    Task selectedTask;

    List<Task> toDoList = new ArrayList<>();
    List<Task> inProgressList = new ArrayList<>();
    List<Task> haltedList = new ArrayList<>();
    List<Task> doneList = new ArrayList<>();
    ObservableList<Task> toDoObservableList = FXCollections.observableArrayList(toDoList);
    ObservableList<Task> inProgressObservableList = FXCollections.observableArrayList(inProgressList);
    ObservableList<Task> haltedObservableList = FXCollections.observableArrayList(haltedList);
    ObservableList<Task> doneObservableList = FXCollections.observableArrayList(doneList);


    @FXML TextField taskTitle, commentTextField;
    @FXML TextArea descriptionTextArea;
    @FXML ComboBox statusComboBox, assignedComboBox, difficultyComboBox;
    @FXML Button confirmButton, cancelButton;
    @FXML DialogPane createTaskPane;
    @FXML DatePicker dueDatePicker;
    List<String> taskTypes = new ArrayList<>();
    ObservableList<String> taskTypesObservableList;
    List<String> difficulties = new ArrayList<>();
    ObservableList<String> difficultiesTypesObservableList;
    List<Member> members = new ArrayList<>();
    ObservableList<Member> membersObservableList;

    void start(Project project, Member member){
        makeListSelectable(toDoListView);
        makeListSelectable(inProgressListView);
        makeListSelectable(haltedListView);
        makeListSelectable(doneListView);
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

        this.project = project;
        this.member = member;
        projectTitle.setText(project.getTitle());
        username.setText(member.getName());
        role.setText(member.getRole());
        renderTasks();
        //role.setText(member.getRole());
    }

    public void renderTasks(){
        toDoList.clear();
        inProgressList.clear();
        haltedList.clear();
        doneList.clear();
            for(Task t : project.getTeam().getTasks()){
                switch(t.getStatus()){
                    case "To Do":
                        toDoList.add(t);
                        break;
                    case "In Progress":
                        inProgressList.add(t);
                        break;
                    case "Halted":
                        haltedList.add(t);
                        break;
                    case "Done":
                        doneList.add(t);
                        break;
                }
            }

        toDoObservableList = FXCollections.observableArrayList(toDoList);
        inProgressObservableList = FXCollections.observableArrayList(inProgressList);
        haltedObservableList = FXCollections.observableArrayList(haltedList);
        doneObservableList = FXCollections.observableArrayList(doneList);
        toDoListView.setItems(toDoObservableList);
        inProgressListView.setItems(inProgressObservableList);
        haltedListView.setItems(haltedObservableList);
        doneListView.setItems(doneObservableList);
    }
    public void handleLogoutButton(ActionEvent event){
        changeScreen("../view/login.fxml", event, new LoginController());
    }
    public void handleCreateButton(ActionEvent event){
        try{
            taskTypesObservableList = FXCollections.observableArrayList(taskTypes);
            difficultiesTypesObservableList = FXCollections.observableArrayList(difficulties);
            membersObservableList = FXCollections.observableArrayList(members);
            statusComboBox.setItems(taskTypesObservableList);
            difficultyComboBox.setItems(difficultiesTypesObservableList);
            assignedComboBox.setItems(membersObservableList);

            createTaskPane.setVisible(true);
            mainWindow.setDisable(true);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void handleDeleteButton(ActionEvent event){
        if(selectedTask != null){
            project.getTeam().getTasks().remove(selectedTask);

            switch(selectedTask.getStatus()){
                case "To Do":
                    toDoList.remove(selectedTask);
                    toDoObservableList = FXCollections.observableArrayList(toDoList);
                    toDoListView.setItems(toDoObservableList);
                    break;
                case "In Progress":
                    inProgressList.remove(selectedTask);
                    inProgressObservableList = FXCollections.observableArrayList(inProgressList);
                    inProgressListView.setItems(inProgressObservableList);
                    break;
                case "Halted":
                    haltedList.remove(selectedTask);
                    haltedObservableList = FXCollections.observableArrayList(haltedList);
                    haltedListView.setItems(haltedObservableList);
                    break;
                case "Done":
                    doneList.remove(selectedTask);
                    doneObservableList = FXCollections.observableArrayList(doneList);
                    doneListView.setItems(doneObservableList);
                    break;
            }
            printAllTasks();
        }
    }
    public void handleLeftButton(ActionEvent event){
        if(selectedTask != null){
            switch(selectedTask.getStatus()){
                case "To Do":
                    System.out.println("Cannot move left any further.");
                    break;
                case "In Progress":
                    selectedTask.setStatus("To Do");
                    toDoList.add(selectedTask);
                    toDoObservableList = FXCollections.observableArrayList(toDoList);
                    toDoListView.setItems(toDoObservableList);

                    inProgressList.remove(selectedTask);
                    inProgressObservableList = FXCollections.observableArrayList(inProgressList);
                    inProgressListView.setItems(inProgressObservableList);
                    break;
                case "Halted":
                    selectedTask.setStatus("In Progress");
                    inProgressList.add(selectedTask);
                    inProgressObservableList = FXCollections.observableArrayList(inProgressList);
                    inProgressListView.setItems(inProgressObservableList);

                    haltedList.remove(selectedTask);
                    haltedObservableList = FXCollections.observableArrayList(haltedList);
                    haltedListView.setItems(haltedObservableList);
                    break;
                case "Done":
                    selectedTask.setStatus("Halted");
                    haltedList.add(selectedTask);
                    haltedObservableList = FXCollections.observableArrayList(haltedList);
                    haltedListView.setItems(haltedObservableList);

                    doneList.remove(selectedTask);
                    doneObservableList = FXCollections.observableArrayList(doneList);
                    doneListView.setItems(doneObservableList);
                    break;
            }
        }
    }
    public void handleRightButton(ActionEvent event){
        if(selectedTask != null){
            switch(selectedTask.getStatus()){
                case "To Do":
                    selectedTask.setStatus("In Progress");
                    inProgressList.add(selectedTask);
                    inProgressObservableList = FXCollections.observableArrayList(inProgressList);
                    inProgressListView.setItems(inProgressObservableList);

                    toDoList.remove(selectedTask);
                    toDoObservableList = FXCollections.observableArrayList(toDoList);
                    toDoListView.setItems(toDoObservableList);
                    break;
                case "In Progress":
                    selectedTask.setStatus("Halted");
                    haltedList.add(selectedTask);
                    haltedObservableList = FXCollections.observableArrayList(haltedList);
                    haltedListView.setItems(haltedObservableList);

                    inProgressList.remove(selectedTask);
                    inProgressObservableList = FXCollections.observableArrayList(inProgressList);
                    inProgressListView.setItems(inProgressObservableList);
                    break;
                case "Halted":
                    selectedTask.setStatus("Done");
                    doneList.add(selectedTask);
                    doneObservableList = FXCollections.observableArrayList(doneList);
                    doneListView.setItems(doneObservableList);

                    haltedList.remove(selectedTask);
                    haltedObservableList = FXCollections.observableArrayList(haltedList);
                    haltedListView.setItems(haltedObservableList);
                    break;
                case "Done":
                    System.out.println("Cannot move right any further.");
                    break;
            }
        }
    }
    public void handleManageButton(ActionEvent event){
        BasicController controller = changeScreen("../view/ManageProject.fxml", event, new ManageProjectController());
        controller.start(project, member);
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
                String title = taskTitle.getText();
                String status = statusComboBox.getValue().toString();
                String difficulty = difficultyComboBox.getValue().toString();
                Task t = new Task(title, status, difficulty);
                t.setTitle(title);
                if(!project.getTeam().taskExists(t)){
                    System.out.println("Valid input");
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
                    if(dueDatePicker.getValue() != null){
                        System.out.println("has date value: "+dueDatePicker.getValue());
                        t.setDueDate(dueDatePicker.getValue());
                    }
                    project.getTeam().addTask(t);
                    System.out.println("Adding task to project");
                    System.out.println("Task: "+t.getTitle()+" "+t.getStatus()+" "+t.getDifficulty()
                            +"\n\t"+t.getDescription()+"\n\t"+t.getComments()+"\n\t"+t.getAssignedTo());
                    createTaskPane.setVisible(false);
                    mainWindow.setDisable(false);
                    renderTasks();
                }
            } else{
                System.out.println("Invalid input");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void makeListSelectable(ListView<Task> list){
        list.getSelectionModel().selectedItemProperty().addListener((listObservable,oldValues,newValues) -> cellWasSelected(list));
        list.getSelectionModel().select(0);
    }
    public void cellWasSelected(ListView<Task> listView){ // functions provided when a cell is selected in list
        try
        {
            index = listView.getSelectionModel().getSelectedIndex();
            selectedTask = listView.getSelectionModel().getSelectedItem();
            try{
                if(selectedTask != null){
                    if(!selectedTask.getStatus().equalsIgnoreCase("To Do")){
                        toDoListView.getSelectionModel().clearSelection();
                    }
                    if(!selectedTask.getStatus().equalsIgnoreCase("In Progress")){
                        inProgressListView.getSelectionModel().clearSelection();
                    }
                    if(!selectedTask.getStatus().equalsIgnoreCase("Halted")){
                        haltedListView.getSelectionModel().clearSelection();
                    }
                    if(!selectedTask.getStatus().equalsIgnoreCase("Done")){
                        doneListView.getSelectionModel().clearSelection();
                    }
                }
                System.out.println(selectedTask.getStatus()+": "+index+", "+selectedTask.getTitle());
            } catch(Exception e){
                System.out.println("Caught");
                //e.printStackTrace();
            }
        }
        catch(Exception e){
            System.out.println("cellWasSelected() -> Caught");
            //e.printStackTrace();
        }
    }

    public void printAllTasks(){
        System.out.println("Current total tasks: "+project.getTeam().getTasks().size()+"\n\t"+project.getTeam().toString());
        System.out.println("To Do column: "+toDoList.size()+"\n\t"+toDoList.toString());
        System.out.println("In Progress column: "+inProgressList.size()+"\n\t"+inProgressList.toString());
        System.out.println("Halted column: "+haltedList.size()+"\n\t"+haltedList.toString());
        System.out.println("Done column: "+doneList.size()+"\n\t"+doneList.toString());
    }


    @FXML DialogPane viewTaskPane;
    @FXML Text statusViewText, titleViewText, difficultyViewText, assignedViewText, startDateText;
    @FXML TextField commentViewTextField;
    @FXML Button goToEditViewButton, saveViewButton, assignViewButton, addCommentViewButton;
    @FXML ListView<Comment> commentsListView;
    ObservableList<Comment> commentsObservableList;
    public void handleViewButton(ActionEvent event){
        try{
            if(selectedTask != null){
                statusViewText.setText(selectedTask.getStatus());
                titleViewText.setText(selectedTask.getTitle());
                difficultyViewText.setText(selectedTask.getDifficulty());
                commentsObservableList = FXCollections.observableArrayList(selectedTask.getComments());
                commentsListView.setItems(commentsObservableList);
                startDateText.setText(Project.dateFormatter.format(selectedTask.getStartDate()));
                if(selectedTask.getAssignedTo() != null){
                    assignedViewText.setText(selectedTask.getAssignedTo().getName());
                }

                viewTaskPane.setVisible(true);
                mainWindow.setDisable(true);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void handleGoToEditViewButton(ActionEvent event){
        System.out.println("Edit button tapped");
    }
    public void handleSaveViewButton(ActionEvent event){
        System.out.println("Edit button tapped");
        try{
            viewTaskPane.setVisible(false);
            mainWindow.setDisable(false);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void handleAssignViewButton(ActionEvent event){
        System.out.println("Edit button tapped");
        if(selectedTask.getAssignedTo() == null){
            selectedTask.setAssignedTo(member);
            selectedTask.addComment(new Comment("Task now assigned to "+selectedTask.getAssignedTo().getName(), new Date()));
        } else if(!selectedTask.getAssignedTo().getName().equalsIgnoreCase(member.getName())){
            selectedTask.setAssignedTo(member);
            selectedTask.addComment(new Comment("Task now assigned to "+selectedTask.getAssignedTo().getName(), new Date()));
        }
        commentsObservableList = FXCollections.observableArrayList(selectedTask.getComments());
        commentsListView.setItems(commentsObservableList);
        assignedViewText.setText(selectedTask.getAssignedTo().getName());
    }
    public void handleAddCommentViewButton(ActionEvent event){
        System.out.println("Edit button tapped");
        if(commentViewTextField.getText() != null){
            selectedTask.addComment(new Comment(commentViewTextField.getText(), new Date()));
            commentsObservableList = FXCollections.observableArrayList(selectedTask.getComments());
            commentsListView.setItems(commentsObservableList);
        }
    }

    @FXML DialogPane editTaskPane;
    @FXML TextField taskEditTitle, commentEditTextField;
    @FXML TextArea descriptionEditTextArea;
    @FXML ComboBox statusEditComboBox, assignedEditComboBox, difficultyEditComboBox;
    @FXML Button confirmEditButton, cancelEditButton;
    @FXML DatePicker dueEditDatePicker;

    public void handleEditButton(ActionEvent event){
        try{
            if(selectedTask != null){
                taskTypesObservableList = FXCollections.observableArrayList(taskTypes);
                difficultiesTypesObservableList = FXCollections.observableArrayList(difficulties);
                membersObservableList = FXCollections.observableArrayList(members);

                statusEditComboBox.setItems(taskTypesObservableList);
                difficultyEditComboBox.setItems(difficultiesTypesObservableList);
                assignedEditComboBox.setItems(membersObservableList);

                taskEditTitle.setText(selectedTask.getTitle());
                statusEditComboBox.setValue(selectedTask.getStatus());
                difficultyEditComboBox.setValue(selectedTask.getDifficulty());
                if(selectedTask.getAssignedTo() != null){
                    assignedEditComboBox.setValue(selectedTask.getAssignedTo());
                }
                if(selectedTask.getDescription() != null){
                    descriptionEditTextArea.setText(selectedTask.getDescription());
                }
                if(selectedTask.getDueDate() != null){
                    dueEditDatePicker.setValue(new java.sql.Date(selectedTask.getDueDate().getTime()).toLocalDate());
                } else{
                    System.out.println("reset");
                    dueEditDatePicker.setValue(null);
                }

                editTaskPane.setVisible(true);
                mainWindow.setDisable(true);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void handleConfirmEditButton(ActionEvent event){
        try{
            if(!statusEditComboBox.getSelectionModel().isEmpty() && !taskEditTitle.getText().isEmpty() && !difficultyEditComboBox.getSelectionModel().isEmpty()) {
                String title = taskEditTitle.getText();
                String status = statusEditComboBox.getValue().toString();
                String difficulty = difficultyEditComboBox.getValue().toString();
                int index = project.getTeam().getTasks().indexOf(selectedTask);

                String currTitle = selectedTask.getTitle();
                Boolean titleIsValid = false;

                if(selectedTask.getTitle().equalsIgnoreCase(title)){
                    titleIsValid = true;
                } else{
                    for (Task t : project.getTeam().getTasks()) {
                        if (t.getTitle().equalsIgnoreCase(title)) {
                            titleIsValid = false;
                        } else{
                            titleIsValid = true;
                        }
                    }
                }
                if (titleIsValid == true) {
                    selectedTask.setTitle(title);
                    selectedTask.setStatus(status);
                    selectedTask.setDifficulty(difficulty);
                    if (!descriptionEditTextArea.getText().isEmpty()) {
                        selectedTask.setDescription(descriptionEditTextArea.getText());
                    }
                    if (!assignedEditComboBox.getSelectionModel().isEmpty()) {
                        Member member = null;
                        for (Member mem : members) {
                            if (mem.getName().equalsIgnoreCase(assignedEditComboBox.getValue().toString())) {
                                member = mem;
                                break;
                            }
                        }
                        selectedTask.setAssignedTo(member);
                    } else {
                        selectedTask.setAssignedTo(null);
                    }
                    if (!commentEditTextField.getText().isEmpty()) {
                        selectedTask.addComment(new Comment(commentEditTextField.getText(), new Date()));
                    }
                    if(dueEditDatePicker.getValue() != null){
                        System.out.println("has edit date value: "+dueEditDatePicker.getValue());
                        selectedTask.setDueDate(dueEditDatePicker.getValue());
                    } else{
                        System.out.println("setting back to null due date");
                        selectedTask.setDueDate(null);
                    }
                    project.getTeam().getTasks().set(index, selectedTask);
                    editTaskPane.setVisible(false);
                    mainWindow.setDisable(false);
                    renderTasks();
                } else {

                }
            } else{

            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void handleCancelEditButton(ActionEvent event){
        try{
            editTaskPane.setVisible(false);
            mainWindow.setDisable(false);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

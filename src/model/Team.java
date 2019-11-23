package model;
import java.util.ArrayList;
import java.util.List;

public class Team {
    private List<Member> members = new ArrayList<>();
    private String title;
    private List<Task> tasks = new ArrayList<>();

    public Team(String title){
        this.title = title;
    }

    public void addMember(Member member){
        this.members.add(member);
    }
    public List<Member> getMembers(){
        return this.members;
    }

    public void addTask(Task task){
        tasks.add(task);
    }
    public List<Task> getTasks(){
        if(tasks.isEmpty()){
            System.out.println("empty");
            return new ArrayList<Task>();
        }else {
            return this.tasks;
        }
    }
}


//package controller;
//
//        import javafx.collections.FXCollections;
//        import javafx.collections.ObservableList;
//        import javafx.event.ActionEvent;
//        import javafx.fxml.FXML;
//        import javafx.scene.control.*;
//        import javafx.scene.layout.VBox;
//        import model.Comment;
//        import model.Member;
//        import model.Project;
//        import model.Task;
//
//        import java.util.ArrayList;
//        import java.util.Date;
//        import java.util.List;
//
//public class HomeController extends BasicController{
//    Project project = null;
//    Member member = null;
//    @FXML Label projectTitle, username, role;
//    @FXML Button logoutButton, createButton, editButton
//            , deleteButton, leftButton, rightButton, manageButton;
//    @FXML ListView<Task> assignedTasks, completedTasks;
//    @FXML VBox mainWindow;
//
//    @FXML ListView<Task> toDoListView, inProgressListView, haltedListView, doneListView;
//    List<Task> toDoList = new ArrayList<>();
//    List<Task> inProgressList = new ArrayList<>();
//    List<Task> haltedList = new ArrayList<>();
//    List<Task> doneList = new ArrayList<>();
//    ObservableList<Task> toDoObservableList;
//    ObservableList<Task> inProgressObservableList;
//    ObservableList<Task> haltedObservableList;
//    ObservableList<Task> doneObservableList;
//
//
//    @FXML TextField taskTitle, commentTextField;
//    @FXML TextArea descriptionTextArea;
//    @FXML ComboBox statusComboBox, assignedComboBox, difficultyComboBox;
//    @FXML Button confirmButton, cancelButton;
//    @FXML DialogPane createTaskPane;
//    List<String> taskTypes = new ArrayList<>();
//    ObservableList<String> taskTypesObservableList;
//    List<String> difficulties = new ArrayList<>();
//    ObservableList<String> difficultiesTypesObservableList;
//    List<Member> members = new ArrayList<>();
//    ObservableList<Member> membersObservableList;
//
//    void start(Project project, Member member){
//        taskTypes.clear();
//        taskTypes.add("To Do");
//        taskTypes.add("In Progress");
//        taskTypes.add("Halted");
//        taskTypes.add("Done");
//        difficulties.clear();
//        difficulties.add("Easy");
//        difficulties.add("Medium");
//        difficulties.add("Hard");
//        difficulties.add("Unknown");
//        members.clear();
//        for(Member mem : project.getTeam().getMembers()){
//            members.add(mem);
//        }
//
//        this.project = project;
//        this.member = member;
//        projectTitle.setText(project.getTitle());
//        username.setText(member.getName());
//        renderTasks();
//        //role.setText(member.getRole());
//    }
//
//    public void renderTasks(){
//        for(Task t : project.getTeam().getTasks()){
//            switch(t.getStatus()){
//                case "To Do":
//                    toDoList.add(t);
//                    break;
//                case "In Progress":
//                    inProgressList.add(t);
//                    break;
//                case "Halted":
//                    haltedList.add(t);
//                    break;
//                case "Done":
//                    doneList.add(t);
//                    break;
//            }
//        }
//
//        initObserveList(toDoObservableList, toDoList);
//        initObserveList(inProgressObservableList, inProgressList);
//        initObserveList(haltedObservableList, haltedList);
//        initObserveList(doneObservableList, doneObservableList);
//        toDoListView.setItems(toDoObservableList);
//        inProgressListView.setItems(inProgressObservableList);
//        haltedListView.setItems(haltedObservableList);
//        doneListView.setItems(doneObservableList);
//    }
//    public void initObserveList(ObservableList ob, List s){
//        ob = FXCollections.observableArrayList(s);
//    }
//    public void handleLogoutButton(ActionEvent event){
//
//    }
//    public void handleCreateButton(ActionEvent event){
//        try{
//            initObserveList(taskTypesObservableList, taskTypes);
//            initObserveList(difficultiesTypesObservableList, difficulties);
//            initObserveList(membersObservableList, members);
//            statusComboBox.setItems(taskTypesObservableList);
//            difficultyComboBox.setItems(difficultiesTypesObservableList);
//            assignedComboBox.setItems(membersObservableList);
//
//            createTaskPane.setVisible(true);
//            mainWindow.setDisable(true);
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }
//    public void handleEditButton(ActionEvent event){
//
//    }
//    public void handleDeleteButton(ActionEvent event){
//
//    }
//    public void handleLeftButton(ActionEvent event){
//
//    }
//    public void handleRightButton(ActionEvent event){
//
//    }
//    public void handleManageButton(ActionEvent event){
//
//    }
//    public void handleCancelButton(ActionEvent event){
//        try{
//            createTaskPane.setVisible(false);
//            mainWindow.setDisable(false);
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }
//    public void handleConfirmButton(ActionEvent event){
//        try{
//            if(!statusComboBox.getSelectionModel().isEmpty() && !taskTitle.getText().isEmpty() && !difficultyComboBox.getSelectionModel().isEmpty()){
//                System.out.println("Valid input");
//                String title = taskTitle.getText();
//                String status = statusComboBox.getValue().toString();
//                String difficulty = difficultyComboBox.getValue().toString();
//                Task t = new Task(title, status, difficulty);
//                if(!descriptionTextArea.getText().isEmpty()){
//                    t.setDescription(descriptionTextArea.getText());
//                }
//                if(!assignedComboBox.getSelectionModel().isEmpty()){
//                    Member member = null;
//                    for(Member mem : members){
//                        if(mem.getName().equals(assignedComboBox.getValue().toString())){
//                            member = mem;
//                            break;
//                        }
//                    }
//                    t.setAssignedTo(member);
//                }
//                if(!commentTextField.getText().isEmpty()){
//                    t.addComment(new Comment(commentTextField.getText(), new Date()));
//                }
//                project.getTeam().addTask(t);
//                System.out.println("Adding task to project");
//                System.out.println("Task: "+t.getTitle()+" "+t.getStatus()+" "+t.getDifficulty()
//                        +"\n\t"+t.getDescription()+"\n\t"+t.getComments()+"\n\t"+t.getAssignedTo());
//            } else{
//                System.out.println("Invalid input");
//            }
//            createTaskPane.setVisible(false);
//            mainWindow.setDisable(false);
//            renderTasks();
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }
//}

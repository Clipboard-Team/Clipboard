package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import model.Member;
import model.Project;
import model.Task;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ManageProjectController extends BasicController {
    @FXML Button renameProjectButton, renameTeamButton, membersButton,
            viewAnalyticsButton, updateButton, clearButton, logoutButton, homeButton;
    @FXML TextField searchTextField;
    @FXML Text projectText, teamText;
    @FXML DatePicker startDatePicker, endDatePicker;
    @FXML CheckBox statusToDoCheckBox, statusInProgressCheckBox, statusHaltedCheckBox,
            statusDoneCheckBox, diffEasyCheckBox, diffMediumCheckBox, diffHardCheckBox,
            diffUnknownCheckBox;
    @FXML ComboBox typeComboBox, directionComboBox;
    private Project project = null;
    private Member member = null;

    void start(Project project, Member member){
        this.project = project;
        this.member = member;
        printCurrentState();
        refreshScreen();

        addActionToCheckBox(statusToDoCheckBox);
        addActionToCheckBox(statusInProgressCheckBox);
        addActionToCheckBox(statusHaltedCheckBox);
        addActionToCheckBox(statusDoneCheckBox);
        addActionToCheckBox(diffEasyCheckBox);
        addActionToCheckBox(diffMediumCheckBox);
        addActionToCheckBox(diffHardCheckBox);
        addActionToCheckBox(diffUnknownCheckBox);
    }

    void addActionToCheckBox(CheckBox ch){
        ch.setOnAction(i->updateFilter(ch));
    }

    List<CheckBox> boxesChecked = new ArrayList<>();
    void updateFilter(CheckBox ch) {
        System.out.println("method called");
        if(ch.isSelected()) {
            // if does not exist in checked list, add to checked list
            if(!boxesChecked.contains(ch)){
                boxesChecked.add(ch);
            }
        } else {
            // if exists in checked list, delete
            if(boxesChecked.contains(ch)){
                boxesChecked.remove(ch);
            }
        }
        System.out.println("New List of Checked Boxes: "+boxesChecked);
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
        ObservableList<Task> originalTasks = FXCollections.observableArrayList(project.getTeam().getTasks());
        FilteredList<Task> tasks = new FilteredList<>(originalTasks);
        tasks.setPredicate(null); //used to reset filter
        //tableView.setItems(tasks);

        List<Task> filteredList = new ArrayList<>();
        if(boxesChecked.size()!=0){
            System.out.println("Filtering with checkboxes");
            List<Predicate<Task>> filtersCollection = new ArrayList<>();
            for(CheckBox ch : boxesChecked){
                filtersCollection.add(i -> i.getStatus().equalsIgnoreCase(ch.getText()) || i.getDifficulty().equalsIgnoreCase(ch.getText()));
            }
            List<Task> originalList = project.getTeam().getTasks();
            originalList.stream().filter(
                    filtersCollection.stream().reduce(Predicate::or).orElse(t->true)
            )
            .forEach(filteredList::add);
            System.out.println("Semi Filtered List: "+filteredList.toString());
        }
        if(startDatePicker.getValue() != null){
            System.out.println("Filtering with start date: ");
            if(filteredList.size() == 0 && boxesChecked.size() == 0){
                System.out.println("\tNo checkbox filters were used, initializing filterList for start");
                filteredList.addAll(project.getTeam().getTasks());
            }
            Iterator<Task> i = filteredList.iterator();
            Date currFilter = java.sql.Date.valueOf(startDatePicker.getValue());
            while (i.hasNext()) {
                Task t = i.next();
                System.out.println("Comparing: "+t.getStartDate()+", with: "+currFilter);
                if(t.getStartDate().before(currFilter)){
                    // delete
                    System.out.println("Removing");
                    i.remove();
                } else{
                    // keep
                    System.out.println("Keeping");
                }
            }
        }
        if(endDatePicker.getValue() != null){
            System.out.println("Filtering with end date: ");
            if(filteredList.size() == 0 && startDatePicker.getValue() == null){
                System.out.println("\tNo checkbox filters were used, initializing filterList for end");
                filteredList.addAll(project.getTeam().getTasks());
            }
            Iterator<Task> i = filteredList.iterator();
            Date currFilter = java.sql.Date.valueOf(endDatePicker.getValue());
            while (i.hasNext()) {
                Task t = i.next();
                System.out.println("Comparing: "+t.getDueDate()+", with: "+currFilter);
                if(t.getDueDate() != null){
                    if(t.getDueDate().after(currFilter)){
                        // delete
                        System.out.println("Removing");
                        i.remove();
                    } else{
                        // keep
                        System.out.println("Keeping");
                    }
                } else{
                    System.out.println("Keeping, this one doesnt have due date");
                }
            }
        }
        if(typeComboBox.getValue() != null && directionComboBox.getValue() != null){
            System.out.println("Sorting with type: "+typeComboBox.getValue()+", and: "+directionComboBox.getValue());

        }
    }
    public void handleClearButton(ActionEvent event){
        System.out.println("Clear Tapped");
    }
    public void handleLogoutButton(ActionEvent event){
        System.out.println("Logout Tapped");
        changeScreen("../view/login.fxml", event, new LoginController());
    }
    public void handleHomeButton(ActionEvent event){
        System.out.println("Home Tapped");
        BasicController controller = changeScreen("../view/Home.fxml", event, new HomeController());
        controller.start(project, member);
    }
}

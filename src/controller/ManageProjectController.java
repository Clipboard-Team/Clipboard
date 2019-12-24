package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import model.Member;
import model.Project;
import model.Task;

import javax.swing.*;
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
    @FXML ComboBox<String> typeComboBox, directionComboBox;
    ObservableList<String> typeComboBoxObservableList, directionComboBoxObservableList;
    ObservableList<Task> tasksObservableList;
    @FXML TableView<Task> tasksTableView;
    @FXML TableColumn<Task, Date> startCol, dueCol;
    @FXML TableColumn<Task, String> statusCol, difficultyCol, assignedCol, titleCol,
            descriptionCol;
    @FXML TableColumn<Task, Integer> totalCommentsCol;
    private Project project = null;
    private Member member = null;

    void start(Project project, Member member){
        this.project = project;
        this.member = member;
        printCurrentState();
        refreshScreen();

        searchTextField.setOnKeyTyped((event) ->{
            try{
                System.out.println("detected key press");
                if(searchTextField.getText().isEmpty()){
                    if(filterExists()){

                    } else{
                        displayOriginalTasks();
                    }
                    // TODO: create a method that checks if any filter exists, if not use above code, if so, save observable list
                } else{
                    searchTasks(searchTextField.getText());
                }
            } catch(Exception e){
                e.printStackTrace();
            }
        });

        addActionToCheckBox(statusToDoCheckBox);
        addActionToCheckBox(statusInProgressCheckBox);
        addActionToCheckBox(statusHaltedCheckBox);
        addActionToCheckBox(statusDoneCheckBox);
        addActionToCheckBox(diffEasyCheckBox);
        addActionToCheckBox(diffMediumCheckBox);
        addActionToCheckBox(diffHardCheckBox);
        addActionToCheckBox(diffUnknownCheckBox);

        List<String> types = new ArrayList<>();
        types.add("Total Comments");
        types.add("Alphabetical Task Title");
        types.add("Start Date");
        typeComboBoxObservableList = FXCollections.observableList(types);
        typeComboBox.setItems(typeComboBoxObservableList);
        List<String> directions = new ArrayList<>();
        directions.add("Ascending");
        directions.add("Descending");
        directionComboBoxObservableList = FXCollections.observableList(directions);
        directionComboBox.setItems(directionComboBoxObservableList);

        startCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        dueCol.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        difficultyCol.setCellValueFactory(new PropertyValueFactory<>("difficulty"));
        assignedCol.setCellValueFactory(new PropertyValueFactory<>("assignedTo"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        totalCommentsCol.setCellValueFactory(new PropertyValueFactory<>("totalComments"));
        tasksTableView.getColumns().setAll(startCol, dueCol, statusCol, difficultyCol, assignedCol, titleCol, descriptionCol, totalCommentsCol);
        displayOriginalTasks();
    }

    private boolean filterExists(){
        List<CheckBox> checkBoxes = new ArrayList<>();
        checkBoxes.add(statusToDoCheckBox);
        checkBoxes.add(statusInProgressCheckBox);
        checkBoxes.add(statusHaltedCheckBox);
        checkBoxes.add(statusDoneCheckBox);
        checkBoxes.add(diffEasyCheckBox);
        checkBoxes.add(diffMediumCheckBox);
        checkBoxes.add(diffHardCheckBox);
        checkBoxes.add(diffUnknownCheckBox);
        for(CheckBox ch : checkBoxes){
            if(ch.isSelected()){
                return true;
            }
        }

        List<DatePicker> datePickers = new ArrayList<>();
        datePickers.add(startDatePicker);
        datePickers.add(endDatePicker);
        for(DatePicker da : datePickers){
            if(da.getValue() != null){
                return true;
            }
        }

        return false;
    }

    private void searchTasks(String keyword){
        List<Task> filterList = tasksObservableList.stream().
                filter(s -> s.getTitle().toUpperCase().//convert to uppercase for checking
                        contains(keyword.toUpperCase()) || (s.getDescription() != null && s.getDescription().toUpperCase().
                        contains(keyword.toUpperCase()))).//filter values containing black
                collect(Collectors.toList());//collect as list
        tasksTableView.setItems(FXCollections.observableList(filterList));
    }
    private void displayOriginalTasks(){
        tasksObservableList = FXCollections.observableList(project.getTeam().getTasks());
        tasksTableView.setItems(tasksObservableList);
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
        if(boxesChecked.size()!=0 || startDatePicker.getValue() != null
                || endDatePicker.getValue() != null
                || (typeComboBox.getValue() != null && directionComboBox.getValue() != null)){
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
                switch(typeComboBox.getSelectionModel().getSelectedItem()){
                    case "Total Comments":
                        break;
                    case "Alphabetical Task Title":
                        break;
                    case "Start Date":
                        break;
                }
                switch(directionComboBox.getSelectionModel().getSelectedItem()){
                    case "Ascending":
                        break;
                    case "Descending":
                        break;
                }
            }
            System.out.println("Displaying filtered list onto UI");
            tasksObservableList = FXCollections.observableList(filteredList);
            tasksTableView.setItems(tasksObservableList);
        } else{ // no filter or sort options were made
            System.out.println("No filter or sort options detected, displaying original list");
            displayOriginalTasks();
        }

    }
    public void handleClearButton(ActionEvent event){
        System.out.println("Clear Tapped");
        displayOriginalTasks();
        boxesChecked.clear();
        statusToDoCheckBox.setSelected(false);
        statusInProgressCheckBox.setSelected(false);
        statusHaltedCheckBox.setSelected(false);
        statusDoneCheckBox.setSelected(false);
        diffEasyCheckBox.setSelected(false);
        diffMediumCheckBox.setSelected(false);
        diffHardCheckBox.setSelected(false);
        diffUnknownCheckBox.setSelected(false);
        startDatePicker.setValue(null);
        startDatePicker.getEditor().clear();
        endDatePicker.setValue(null);
        endDatePicker.getEditor().clear();
        typeComboBox.getSelectionModel().clearSelection();
        typeComboBox.setPromptText("Type");
        directionComboBox.getSelectionModel().clearSelection();
        directionComboBox.setPromptText("Direction");
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

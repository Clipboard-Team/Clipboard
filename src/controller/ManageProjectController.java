package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import model.Member;
import model.Project;
import model.Task;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ManageProjectController extends BasicController {

    // Data
    private Project project = null;
    private Member member = null;

    // Display UI
    @FXML Text projectText, teamText;

    // Button features
    @FXML Button renameProjectButton, renameTeamButton, membersButton,
            viewAnalyticsButton, updateButton, clearButton, logoutButton, homeButton;

    // Search tasks
    @FXML TextField searchTextField;

    // Filter tasks
    private List<CheckBox> boxesChecked = new ArrayList<>();
    @FXML DatePicker startDatePicker, endDatePicker;
    @FXML CheckBox statusToDoCheckBox, statusInProgressCheckBox, statusHaltedCheckBox,
            statusDoneCheckBox, diffEasyCheckBox, diffMediumCheckBox, diffHardCheckBox,
            diffUnknownCheckBox;

    // TableView
    private List<Task> filteredList = null;
    private ObservableList<Task> tasksObservableList;
    @FXML TableView<Task> tasksTableView;
    @FXML TableColumn<Task, Date> startCol, dueCol;
    @FXML TableColumn<Task, String> statusCol, difficultyCol, assignedCol, titleCol, descriptionCol;
    @FXML TableColumn<Task, Integer> totalCommentsCol;

    void start(Project project, Member member){
        // set data
        this.project = project;
        this.member = member;
        printCurrentState();
        refreshScreen();

        // add functions to checkboxes
        addActionToCheckBox(statusToDoCheckBox);
        addActionToCheckBox(statusInProgressCheckBox);
        addActionToCheckBox(statusHaltedCheckBox);
        addActionToCheckBox(statusDoneCheckBox);
        addActionToCheckBox(diffEasyCheckBox);
        addActionToCheckBox(diffMediumCheckBox);
        addActionToCheckBox(diffHardCheckBox);
        addActionToCheckBox(diffUnknownCheckBox);

        // create TableView
        startCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        dueCol.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        difficultyCol.setCellValueFactory(new PropertyValueFactory<>("difficulty"));
        assignedCol.setCellValueFactory(new PropertyValueFactory<>("assignedTo"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        totalCommentsCol.setCellValueFactory(new PropertyValueFactory<>("totalComments"));
        tasksTableView.getColumns().setAll(startCol, dueCol, statusCol, difficultyCol, assignedCol, titleCol, descriptionCol, totalCommentsCol);
        displayTasks(project.getTeam().getTasks());

        // give search bar function
        searchTextField.setOnKeyTyped((event) ->{
            try{
                System.out.println("detected key press");
                if(searchTextField.getText().isEmpty()){ // empty search field
                    if(filterExists()){
                        displayTasks(filteredList);
                    } else{
                        displayTasks(project.getTeam().getTasks());
                    }
                } else{ // not empty, check tasks for subset
                    List<Task> filterList = tasksObservableList.stream().
                            filter(s -> s.getTitle().toUpperCase().
                                    contains(searchTextField.getText().toUpperCase()) || (s.getDescription() != null && s.getDescription().toUpperCase().
                                    contains(searchTextField.getText().toUpperCase()))).
                            collect(Collectors.toList());

                    displayTasks(filterList);
                }
            } catch(Exception e){
                e.printStackTrace();
            }
        });
    }

    public void handleUpdateButton(ActionEvent event){ // update table view with filtered list
        System.out.println("Update Tapped");

        filteredList = new ArrayList<>();
        if(filterExists()){ // if filter exists
            if(!boxesChecked.isEmpty()){ // filter through checkboxes
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
            if(startDatePicker.getValue() != null){ // filter through start date
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
            if(endDatePicker.getValue() != null){ // filter through due date
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

            // show official filtered list
            displayTasks(filteredList);
        } else{ // no filters exist, show original list of tasks
            System.out.println("No filter or sort options detected, displaying original list");
            displayTasks(project.getTeam().getTasks());
        }
    }

    public void handleClearButton(ActionEvent event){ // clear filter options
        System.out.println("Clear Tapped");
        displayTasks(project.getTeam().getTasks());

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
    }

    public void handleRenameProjectButton(ActionEvent event){ // rename project
        System.out.println("Rename Project Tapped");
    }

    public void handleRenameTeamButton(ActionEvent event){ // rename team
        System.out.println("Rename Team Tapped");
    }

    public void handleMembersButton(ActionEvent event){ // add delete or edit members
        System.out.println("Members Tapped");
    }

    public void handleLogoutButton(ActionEvent event){ // logout
        System.out.println("Logout Tapped");
        changeScreen("../view/login.fxml", event, new LoginController());
    }

    public void handleHomeButton(ActionEvent event){ // go to home page
        System.out.println("Home Tapped");
        BasicController controller = changeScreen("../view/Home.fxml", event, new HomeController());
        controller.start(project, member);
    }

    public void handleViewAnalyticsButton(ActionEvent event){ // go to analytics page
        System.out.println("View Analytics Tapped");
    }

    private void displayTasks(List<Task> list){ // choose which list to display
        tasksObservableList = FXCollections.observableList(list);
        tasksTableView.setItems(tasksObservableList);
    }

    private void printCurrentState(){ // check date is passed correctly
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

    private void refreshScreen(){ // update UI
        this.projectText.setText(project.getTitle());
        this.teamText.setText(project.getTeam().getTitle());
    }

    private boolean filterExists(){ // check if any filter option is checked or date filter exists
        List<CheckBox> checkBoxes = new ArrayList<>();
        checkBoxes.add(statusToDoCheckBox);
        checkBoxes.add(statusInProgressCheckBox);
        checkBoxes.add(statusHaltedCheckBox);
        checkBoxes.add(statusDoneCheckBox);
        checkBoxes.add(diffEasyCheckBox);
        checkBoxes.add(diffMediumCheckBox);
        checkBoxes.add(diffHardCheckBox);
        checkBoxes.add(diffUnknownCheckBox);
        List<DatePicker> datePickers = new ArrayList<>();
        datePickers.add(startDatePicker);
        datePickers.add(endDatePicker);
        return checkBoxes.stream().anyMatch(CheckBox::isSelected) ||
                datePickers.stream().anyMatch(s -> s.getValue()!=null);
    }

    private void addActionToCheckBox(CheckBox ch){ // give each checkbox a function
        ch.setOnAction(i->updateFilter(ch));
    }

    private void updateFilter(CheckBox ch) { // update list of checked checkboxes
        System.out.println("update filter method called");
        if(ch.isSelected()) {
            if(!boxesChecked.contains(ch)){
                boxesChecked.add(ch);
            }
        } else {
            if(boxesChecked.contains(ch)){
                boxesChecked.remove(ch);
            }
        }
    }
}

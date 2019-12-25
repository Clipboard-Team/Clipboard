package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Member;
import model.Project;

public class NewMemberController extends BasicController{

    // Data
    private Project project = null;
    private ObservableList<Member> observableList;


    // Functionality
    @FXML TextField username;
    @FXML ListView<Member> listView;
    @FXML Button backButton, addButton, finalizeButton;
    @FXML private RadioButton chk, adminRadioButton, leadRadioButton;

    // TODO: add delete functionality for members except current first member (admin)
    private int index;
    private Member selectedMem;

    public void start(Project project){
        // receive data
        this.project = project;

        observableList = FXCollections.observableArrayList(project.getTeam().getMembers());
        listView.setItems(observableList);
        listView.getSelectionModel().selectedItemProperty().addListener((listObservable,oldValues,newValues) -> cellWasSelected());
        listView.getSelectionModel().select(0);

        final ToggleGroup group = new ToggleGroup();
        adminRadioButton.setSelected(true);
        adminRadioButton.setToggleGroup(group);
        leadRadioButton.setToggleGroup(group);
        chk = adminRadioButton;

        group.selectedToggleProperty().addListener((ov, t, t1) -> {

            chk = (RadioButton)t1.getToggleGroup().getSelectedToggle(); // Cast object to radio button
            System.out.println("Selected Radio Button - "+chk.getText());

        });
    }

    public void handleAddButton(ActionEvent event){
        Member mem = new Member(username.getText(), project.getTeam(), chk.getText());

        if(!username.getText().isEmpty() && !project.getTeam().memberExists(mem)){
            // add data
            project.getTeam().addMember(mem);

            observableList.add(mem);
            listView.setItems(observableList);
        }
    }

    public void handleFinalizeButton(ActionEvent event){
        // pass data
        BasicController controller = changeScreen("../view/Home.fxml", event, new HomeController());
        controller.start(project, project.getTeam().getMembers().get(0));
    }

    private void cellWasSelected(){ // functions provided when a cell is selected in list
        try
        {
            index = listView.getSelectionModel().getSelectedIndex();
            selectedMem = listView.getSelectionModel().getSelectedItem();
        }
        catch(Exception e){
            System.out.println("cellWasSelected() -> Caught");
        }
    }

    public void handleBackButton(ActionEvent event){
        BasicController controller = changeScreen("../view/NewAdmin.fxml", event, new NewAdminController());
        controller.start();
    }
}

package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Member;
import model.Project;

public class NewMemberController extends BasicController{
    Project project = null;
    private ObservableList<Member> observableList;
    int index;
    Member selectedMem;
    @FXML TextField username;
    @FXML ListView<Member> listView;
    @FXML Button backButton, addButton, finalizeButton;
    @FXML RadioButton chk, adminRadioButton, leadRadioButton;

    public void handleBackButton(ActionEvent event){
        BasicController controller = changeScreen("../view/NewAdmin.fxml", event, new NewAdminController());
        controller.start();
    }
    public void handleFinalizeButton(ActionEvent event){
        BasicController controller = changeScreen("../view/Home.fxml", event, new HomeController());
        controller.start(project, project.getTeam().getMembers().get(0));
    }
    public void handleAddButton(ActionEvent event){
        if(!username.getText().isEmpty() &&
                !project.getTeam().memberExists(
                        new Member(username.getText(), project.getTeam(), chk.getText()))){
            Member mem = new Member(username.getText(), project.getTeam(), chk.getText());
            project.getTeam().addMember(mem);
            observableList.add(mem);
            listView.setItems(observableList);
        }
    }
    public void handleAdminRadioButton(ActionEvent event){
        //leadRadioButton.setSelected(false);
    }
    public void handleLeadRadioButton(ActionEvent event){
        //adminRadioButton.setSelected(false);
    }
    public void start(Project project){
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

        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {

                chk = (RadioButton)t1.getToggleGroup().getSelectedToggle(); // Cast object to radio button
                System.out.println("Selected Radio Button - "+chk.getText());

            }
        });
    }
    public void cellWasSelected(){ // functions provided when a cell is selected in list
        try
        {
            index = listView.getSelectionModel().getSelectedIndex();
            selectedMem = listView.getSelectionModel().getSelectedItem();
        }
        catch(Exception e){
            System.out.println("cellWasSelected() -> Caught");
        }
    }
}

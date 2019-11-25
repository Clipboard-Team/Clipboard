package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Lead;
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
                        new Member(username.getText(), project.getTeam()))){
            Member mem = new Member(username.getText(), project.getTeam());
            project.getTeam().addMember(mem);
            observableList.add(mem);
            listView.setItems(observableList);
        }
    }
    public void start(Project project){
        this.project = project;
        observableList = FXCollections.observableArrayList(project.getTeam().getMembers());
        listView.setItems(observableList);
        listView.getSelectionModel().selectedItemProperty().addListener((listObservable,oldValues,newValues) -> cellWasSelected());
        listView.getSelectionModel().select(0);
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

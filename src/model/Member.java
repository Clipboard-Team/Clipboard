package model;
import java.util.ArrayList;
import java.util.List;

public class Member {
    String name;
    Team team;
    String role;

    public Member(String name, Team team, String role){
        this.name = name;
        this.team = team;
        this.role = role;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }

    public void setRole(String role){
        this.role = role;
    }
    public String getRole(){
        return this.role;
    }

    void assignTaskToMe(Task task){

    }
    void addCommentToTask(Task task){

    }
    public String toString(){
        return this.name;
    }
}

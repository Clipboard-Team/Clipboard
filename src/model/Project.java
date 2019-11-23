package model;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Project {
    private Team team;
    private String title;
    private String description;
    private Date startDate;
    private Date latestDate;

    public Project(String title){
        this.title = title;
        this.startDate = new Date();
        this.latestDate = new Date();
    }

    public void setTeam(Team team){
        this.team = team;
    }
    public Team getTeam(){
        return this.team;
    }

    public void setTitle(String title){

    }
    public String getTitle(){
        return this.title;
    }

    public void setDescription(String description){
        this.description = description;
    }
    public String getDescription(){
        return this.description;
    }

    public void setStartDate(Date startDate){
        this.startDate = startDate;
    }
    public Date getStartDate(){
        return this.startDate;
    }

    public void setLatestDate(Date latestDate){
        this.latestDate = latestDate;
    }
    public Date getLatestDate(){
        return this.latestDate;
    }
}

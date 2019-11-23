package model;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Task {
    String title;
    String status;
    String difficulty;
    String description;
    List<Comment> comments = new ArrayList<>();
    Date startDate;
    Date latestUpdateDate;
    Member assignedTo;

    void assign_to_member(Member member){

    }

    public Task(String title, String status, String difficulty){
        this.title = title;
        this.status = status;
        this.difficulty = difficulty;
    }

    public void addComment(Comment comment){
        this.comments.add(comment);
    }

    public List<Comment> getComments(){
        return this.comments;
    }

    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }

    public void setStatus(String status){
        this.status = status;
    }
    public String getStatus(){
        return this.status;
    }

    public void setDifficulty(String difficulty){
        this.difficulty = difficulty;
    }
    public String getDifficulty(){
        return this.difficulty;
    }

    public void setDescription(String description){
        this.description = description;
    }
    public String getDescription(){
        return this.description;
    }

    public void setAssignedTo(Member member){
        this.assignedTo = member;
    }

    public Member getAssignedTo(){
        return this.assignedTo;
    }

    public String toString(){
        if(this.assignedTo != null){
            return this.getTitle()+" ("+this.getDifficulty()+") : "+this.getAssignedTo();
        }else{
            return this.getTitle()+" ("+this.getDifficulty()+")";
        }
    }

}

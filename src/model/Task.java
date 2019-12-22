package model;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static java.time.temporal.ChronoUnit.DAYS;

public class Task {
    private String title;
    private String status;
    private String difficulty;
    private String description;
    private List<Comment> comments = new ArrayList<>();
    private Date startDate;
    Date latestUpdateDate;
    private Member assignedTo;
    private Date dueDate;

    public Task(String title, String status, String difficulty){
        this.title = title;
        this.status = status;
        this.difficulty = difficulty;
        this.startDate = java.sql.Date.valueOf(LocalDate.now());
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

    public void setStartDate(LocalDate startDate){
        this.startDate = java.sql.Date.valueOf(startDate);
    }
    public Date getStartDate(){
        return this.startDate;
    }

    public void setDueDate(LocalDate dueDate){
        this.dueDate = java.sql.Date.valueOf(dueDate);
    }
    public Date getDueDate(){
        return this.dueDate;
    }

    public String toString(){
        if(this.assignedTo != null){
            return this.getTitle()+" ("+this.getDifficulty()+") : "+this.getAssignedTo();
        }else if(dueDate != null){
                long daysLeft = DAYS.between(LocalDate.now(), new java.sql.Date(dueDate.getTime()).toLocalDate());
                return this.getTitle()+" ("+this.getDifficulty()+") "+daysLeft+" days left";

        }

        return this.getTitle()+" ("+this.getDifficulty()+")";
    }

    public boolean equals(Task t){
        if(t.getTitle().equalsIgnoreCase(this.getTitle())){
            return true;
        }
        return false;
    }

}

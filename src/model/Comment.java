package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Comment {

    private String comment;
    private Date date;
    public Comment(String comment, Date date){
        this.comment = comment;
        this.date = date;
    }
    public String toString(){
        return Project.dateFormatter.format(date) + ": "+this.comment;
    }
    public Date getDate(){
        return this.date;
    }
    public String getComment(){
        return this.comment;
    }
}

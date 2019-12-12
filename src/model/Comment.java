package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Comment {
    SimpleDateFormat format = new SimpleDateFormat("dd-M-yyyy hh:mm");

    String comment;
    Date date;
    public Comment(String comment, Date date){
        this.comment = comment;
        this.date = date;
    }
    public String toString(){
        return format.format(date) + ": "+this.comment;
    }
    public Date getDate(){
        return this.date;
    }
    public String getComment(){
        return this.comment;
    }
}

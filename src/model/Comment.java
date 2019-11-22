package model;

import java.util.Date;

public class Comment {
    String comment;
    Date date;
    public Comment(String comment, Date date){
        this.comment = comment;
        this.date = date;
    }
    public String toString(){
        return this.comment;
    }
}

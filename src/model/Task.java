package model;
import java.util.Date;

public class Task {
    String task_title;
    String task_description;
    String[] task_comments;
    Date task_start_date;
    Date task_latest_update_date;
    Member task_assigned_to;
    String task_status;

    void assign_to_memebr(Member member){}

    

}

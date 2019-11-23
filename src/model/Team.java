package model;
import java.util.ArrayList;
import java.util.List;

public class Team {
    private List<Member> members = new ArrayList<>();
    private String title;
    private List<Task> tasks = new ArrayList<>();

    public Team(String title){
        this.title = title;
    }

    public void addMember(Member member){
        this.members.add(member);
    }
    public List<Member> getMembers(){
        return this.members;
    }

    public void addTask(Task task){
        tasks.add(task);
    }
    public List<Task> getTasks(){
        if(tasks.isEmpty()){
            System.out.println("empty");
            return new ArrayList<Task>();
        }else {
            return this.tasks;
        }
    }
    public boolean taskExists(Task task){
        for(Task t : tasks){
            if(t.getTitle().equalsIgnoreCase(task.getTitle())){
                return true;
            }
        }
        return false;
    }
}
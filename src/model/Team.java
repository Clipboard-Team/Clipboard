package model;
import java.util.ArrayList;
import java.util.List;

public class Team {
    private List<Member> members = new ArrayList<>();
    private String title;

    public Team(String title){
        this.title = title;
    }

    public void addMember(Member member){
        this.members.add(member);
    }
    public List<Member> getMembers(){
        return this.members;
    }
}

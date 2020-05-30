package coreContext;

public class Skill{
    private String type, name;
    
    public Skill(String t, String n){
        type = t;
        name = n;
    }
    
    public String getType(){
        return type;
    }
    
    public String getName(){
        return name;
    }
}
package coreContext;

public class Person{
    private String id, name, surname, brief, bg;
    private Skill[] skills;
    
    public Person(String id_, String n, String snm, String br, String b, Skill[] s){
        id = id_;
        name = n;
        surname = snm;
        brief = br;
        bg = b;
        skills = s;
    }
    
    public Skill[] getSkills(){
        return skills;
    }
    
    public String getName(){
        return name;
    }
    
    public String getSurname(){
        return surname;
    }
    
    public String getBriefInfo(){
        return brief;
    }
    
    public String getBG(){
        return bg;
    }
    
    public String getID(){
        return id;
    }
    
}
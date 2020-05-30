package coreContext;

import java.util.*;
import java.sql.*;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

@Component

public class DataCache{
    
    private HashMap<String, Person> persons = new HashMap<>();
    
    @PostConstruct
    public void init(){
         try{
             Class.forName("org.postgresql.Driver"); // почему-то сам не регистрируется
         }catch(Exception e){e.printStackTrace(System.out);}
         Properties connection_props = new Properties();
         connection_props.put("user", "postgres");
         connection_props.put("password", "password_here");
         int personsCounter = 0;
         try(Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", connection_props)){
             String query = "SELECT * FROM persons WHERE id = ?";
             PreparedStatement dataQuery = con.prepareStatement(query);
             query = "SELECT * FROM skills WHERE id = ?";
             PreparedStatement skillsQuery = con.prepareStatement(query);
             dataQuery.setInt(1, personsCounter);
             ResultSet rs = dataQuery.executeQuery();
             System.out.println("Connected");
             Person person = null;
             while(rs.isBeforeFirst()){
                 while(rs.next()){
                     skillsQuery.setInt(1, personsCounter);
                     ResultSet skillsData = skillsQuery.executeQuery();
                     ArrayList<Skill> skills = new ArrayList<>();
                     while(skillsData.next()){
                         skills.add(new Skill(skillsData.getString("type"), skillsData.getString("skill")));
                     }
                     person = new Person(
                         String.valueOf(rs.getInt("id")),
                         rs.getString("name"),
                         rs.getString("surname"),
                         rs.getString("brief"),
                         rs.getString("bg"),
                         skills.toArray(new Skill[skills.size()])
                     );
                     persons.put(person.getID(), person);
                     System.out.println(rs.getString("name") + " " + rs.getString("surname"));
                 }
                 personsCounter ++;
                 dataQuery.setInt(1, personsCounter);
                 rs = dataQuery.executeQuery();
             }
         }catch(Exception e){
             e.printStackTrace(System.out);
         }
         System.out.println();
    }
    
    public Person getPersonByID(String id){
        return persons.get(id);
    }
    
}
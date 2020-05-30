package coreContext;

import java.util.*;
import java.sql.*;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

@Component

public class DataCache{
    
    private HashMap<String, Person> persons = new HashMap<>(); // можно ретривить людей по ID
    
    @PostConstruct // инициализация кэша (загрузка данных из БД)
    public void init(){
         try{
             Class.forName("org.postgresql.Driver"); // почему-то сам не регистрируется
         }catch(Exception e){
             e.printStackTrace(System.out);
         }
         Properties connection_props = new Properties();
         connection_props.put("user", "postgres");
         connection_props.put("password", "password_here");
         int personsCounter = 0; // делаем перебором, с возможностью добавлять новых пользователей
         try(Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", connection_props)){
             String query = "SELECT * FROM persons WHERE id = ?";
             PreparedStatement dataQuery = con.prepareStatement(query);
             query = "SELECT * FROM skills WHERE id = ?";
             PreparedStatement skillsQuery = con.prepareStatement(query);
             dataQuery.setInt(1, personsCounter);
             ResultSet personData = dataQuery.executeQuery();
             Person person = null;
             while(personData.isBeforeFirst()){ // перебор людей в базе
                 while(personData.next()){
                     skillsQuery.setInt(1, personsCounter);
                     ResultSet skillsData = skillsQuery.executeQuery();
                     ArrayList<Skill> skills = new ArrayList<>();
                     while(skillsData.next()){ // наполнение массива навыков
                         skills.add(new Skill(skillsData.getString("type"), skillsData.getString("skill")));
                     }
                     person = new Person( // вот и новый объект в кэше
                         String.valueOf(personData.getInt("id")),
                         personData.getString("name"),
                         personData.getString("surname"),
                         personData.getString("brief"),
                         personData.getString("bg"),
                         skills.toArray(new Skill[skills.size()])
                     );
                     persons.put(person.getID(), person); // в карту его
                 }
                 personsCounter ++; // следующий индекс
                 dataQuery.setInt(1, personsCounter);
                 personData = dataQuery.executeQuery();
             }
         }catch(Exception e){
             e.printStackTrace(System.out); // куда-то делось соединение с базой
         } // можно было бы в таком случае каждый раз делать запрос отдельно, в принципе
    }      // но это слишком затратно, нужен пулинг соединений. А я не уверен, что успел бы с ним
    
    public Person getPersonByID(String id){
        return persons.get(id);
    }
    
}
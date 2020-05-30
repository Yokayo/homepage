package ajax;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.io.StringWriter;
import javax.inject.Inject;
import javax.json.*;
import coreContext.*;

@Controller
public class AjaxController{
    
    @Inject private DataCache cache;
    // передача данных по ID и типу
    @RequestMapping(value = "persons", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> passInfo(HttpServletRequest request){
        String infoType = request.getParameter("type");
        JsonObjectBuilder builder = Json.createObjectBuilder();
        if(infoType == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        StringWriter result = new StringWriter();
        JsonWriter writer = Json.createWriter(result);
        String personID = request.getParameter("userid");
        if(personID == null)
            personID = "0";
        Person person = cache.getPersonByID(personID);
        if(person == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        switch(infoType){
            case "brief":
                builder.add("Description", person.getBriefInfo());
                writer.write(builder.build());
                break;
            case "bg":
                builder.add("BG", person.getBG());
                writer.write(builder.build());
                break;
            case "fullname":
                builder.add("Name", person.getName() + " " + person.getSurname());
                writer.write(builder.build());
                break;
            case "skills":
                JsonArrayBuilder skillsBuilder = Json.createArrayBuilder();
                Skill[] personsSkills = person.getSkills();
                for(int a = 0; a < personsSkills.length; a++){
                    JsonObjectBuilder currentSkill = Json.createObjectBuilder();
                    currentSkill.add("type", personsSkills[a].getType())
                        .add("name", personsSkills[a].getName());
                    skillsBuilder.add(currentSkill.build());
                }
                builder.add("Skills", skillsBuilder.build());
                writer.write(builder.build());
                break;
        }
        return new ResponseEntity<>(result.toString(), HttpStatus.OK);
    }
    
}
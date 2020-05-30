import org.springframework.web.*;
import org.springframework.web.context.support.*;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.springframework.context.annotation.*;
import org.springframework.web.context.*;
import org.springframework.web.servlet.DispatcherServlet;

public class Init implements WebApplicationInitializer{
    
    @Override
    public void onStartup(ServletContext container) throws ServletException{
        
        AnnotationConfigWebApplicationContext coreContext = new AnnotationConfigWebApplicationContext();
        coreContext.scan("coreContext");
        container.addListener(new ContextLoaderListener(coreContext)); // root context
        
        AnnotationConfigWebApplicationContext ajaxContext = new AnnotationConfigWebApplicationContext();
        ajaxContext.scan("ajax"); // AJAX servlet context
        ServletRegistration.Dynamic ajaxServlet = container.addServlet("ajaxServlet", new DispatcherServlet(ajaxContext));
        ajaxServlet.setLoadOnStartup(1);
        ajaxServlet.addMapping("/info/*"); // AJAX servlet
    }
}
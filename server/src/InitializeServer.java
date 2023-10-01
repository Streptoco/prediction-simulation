import engine.general.object.Engine;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class InitializeServer implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
//        Engine engine = new Engine();
        System.out.println("Starting Predictions Server");
        //sce.getServletContext().setAttribute("engine", new Engine());
    }
}
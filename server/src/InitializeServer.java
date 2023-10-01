import engine.general.object.Engine;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class InitializeServer implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
//        Engine engine = new Engine();
        System.out.println("Starting Predictions Server");
        sce.getServletContext().setAttribute("engine", new Engine());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        // hey lol
    }
}
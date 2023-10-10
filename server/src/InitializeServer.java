import engine.general.object.Engine;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import request.impl.AllocationRequest;

import java.util.PriorityQueue;

@WebListener
public class InitializeServer implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Starting Predictions Server");
        sce.getServletContext().setAttribute("engine", new Engine());
        sce.getServletContext().setAttribute("requestQueue", new PriorityQueue<AllocationRequest>());
        sce.getServletContext().setAttribute("requestID", 1);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        // hey lol
    }
}
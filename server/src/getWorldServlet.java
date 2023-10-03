import com.google.gson.Gson;
import engine.general.object.Engine;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "Get World Servlet", urlPatterns = "/get-world")
public class getWorldServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("hey lol");
        Engine engine = (Engine)this.getServletContext().getAttribute("engine");
        System.out.println(engine);
        Gson gson = new Gson();
        String json = gson.toJson(engine.getLastWorld());
        // Set the response content type to JSON
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        // Send the JSON response
        System.out.println(json);
        resp.getWriter().write(json);
    }
}

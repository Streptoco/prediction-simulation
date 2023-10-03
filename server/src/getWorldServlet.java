import com.google.gson.Gson;
import engine.general.object.Engine;
import enginetoui.dto.basic.impl.WorldDTO;
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
        Gson gson = new Gson();
        WorldDTO currentWorld = engine.getLastWorld();
        String json = gson.toJson(currentWorld);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }
}

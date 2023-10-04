import com.google.gson.Gson;
import engine.general.object.Engine;
import enginetoui.dto.basic.impl.WorldDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "Get All Worlds Servlet", urlPatterns = "/get-worlds-list")
public class getAllWorldsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Engine engine = (Engine)this.getServletContext().getAttribute("engine");
        Gson gson = new Gson();
        List<WorldDTO> resultList = engine.getAllWorlds();
        String json = gson.toJson(resultList);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }
}

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import request.impl.AllocationRequest;

import java.io.IOException;
import java.util.PriorityQueue;

@WebServlet(name = "Get Requests", urlPatterns = "/get-requests")
public class getRequestsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PriorityQueue<AllocationRequest> requests = (PriorityQueue<AllocationRequest>) this.getServletContext().getAttribute("requestQueue");
        Gson gson = new Gson();
        AllocationRequest latestRequest = requests.poll();
        String jsonObject = gson.toJson(latestRequest);
        System.out.println("[getRequestServlet] - [doGet]: " + jsonObject);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(jsonObject);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PriorityQueue<AllocationRequest> requests = (PriorityQueue<AllocationRequest>) this.getServletContext().getAttribute("requestQueue");
        Gson gson = new Gson();
        String jsonObject = gson.toJson(requests.toArray());
        System.out.println("[getRequestServlet] - [doPost]: " + jsonObject);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(jsonObject);

    }
}

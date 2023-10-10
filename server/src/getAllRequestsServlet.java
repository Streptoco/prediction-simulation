import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import request.impl.AllocationRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

@WebServlet(name = "Get All Requests", urlPatterns = "/get-all-requests")
public class getAllRequestsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PriorityQueue<AllocationRequest> requests = (PriorityQueue<AllocationRequest>) this.getServletContext().getAttribute("requestQueue");
        Gson gson = new Gson();
        List<AllocationRequest> resultList = new ArrayList<>();
        PriorityQueue<AllocationRequest> copiedQueue = new PriorityQueue<>(requests);
        while(!(copiedQueue.isEmpty())) {
            resultList.add(copiedQueue.poll());
        }
        String jsonObject = gson.toJson(resultList);
        System.out.println("[getAllRequestsServlet] - [deGet]: " + jsonObject);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(jsonObject);

    }
}

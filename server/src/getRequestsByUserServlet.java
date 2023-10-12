import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import request.impl.AllocationRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

@WebServlet(name = "Get Requests by user", urlPatterns = "/get-requests")
public class getRequestsByUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        List<Integer> idList = (List<Integer>) session.getAttribute("userRequestsID");
        PriorityQueue<AllocationRequest> requests = (PriorityQueue<AllocationRequest>) this.getServletContext().getAttribute("requestQueue");
        List<AllocationRequest> resultList = new ArrayList<>();
        PriorityQueue<AllocationRequest> copiedQueue = new PriorityQueue<>(requests);
        while(!(copiedQueue.isEmpty())) {
            for (Integer i : idList) {
                if(copiedQueue.peek().getRequestID() == i) {
                    resultList.add(copiedQueue.poll());
                    break;
                }
            }
        }
        Gson gson = new Gson();
        String jsonObject = gson.toJson(resultList);
        System.out.println("[getRequestsByUserServlet] - [deGet]: " + jsonObject);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(jsonObject);
    }
}

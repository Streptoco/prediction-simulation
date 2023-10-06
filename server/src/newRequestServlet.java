import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import request.impl.AllocationRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

@WebServlet(name = "New Request", urlPatterns = "/new-request")
public class newRequestServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PriorityQueue<AllocationRequest> requests = (PriorityQueue<AllocationRequest>) this.getServletContext().getAttribute("requestQueue");
        Gson gson = new Gson();
        InputStream stream = req.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder requestBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }
        String jsonData = requestBody.toString();
        AllocationRequest newRequest = gson.fromJson(jsonData, AllocationRequest.class);
        requests.add(newRequest);

        System.out.println(jsonData);
        resp.getWriter().println(jsonData);
    }
}

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import enginetoui.dto.basic.DeserializeAllocationRequest;
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
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

@WebServlet(name = "New Request", urlPatterns = "/new-request")
public class newRequestServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PriorityQueue<AllocationRequest> requests = (PriorityQueue<AllocationRequest>) this.getServletContext().getAttribute("requestQueue");
        Integer requestID = (Integer) this.getServletContext().getAttribute("requestID");
        req.getSession(true).setAttribute("userRequestsID", new ArrayList<>());
        ((List<Integer>)(req.getSession().getAttribute("userRequestsID"))).add(requestID);
        this.getServletContext().setAttribute("requestID", requestID + 1);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(AllocationRequest.class, new DeserializeAllocationRequest())
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
        InputStream stream = req.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder requestBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }
        String jsonData = requestBody.toString();
        AllocationRequest newRequest = gson.fromJson(jsonData, AllocationRequest.class);
        newRequest.setRequestID(requestID);
        requests.add(newRequest);
        System.out.println("[newRequestServlet] - [doPost]: " + newRequest);
        resp.getWriter().println(jsonData);
    }
}

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
import java.util.PriorityQueue;

@WebServlet(name = "Change request status", urlPatterns = "/approve-deny-status")
public class changeRequestStatusServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PriorityQueue<AllocationRequest> requests = (PriorityQueue<AllocationRequest>) this.getServletContext().getAttribute("requestQueue");
        PriorityQueue<AllocationRequest> copyQueue = new PriorityQueue<>(requests);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(AllocationRequest.class, new DeserializeAllocationRequest())
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
        String jsonData = getJSONData(req.getInputStream());
        AllocationRequest requestToChange = gson.fromJson(jsonData, AllocationRequest.class);
        setRequestID(jsonData, requestToChange);
        for (AllocationRequest currentRequest : copyQueue) {
            if (currentRequest.getRequestID() == requestToChange.getRequestID()) {
                System.out.println("[" + Thread.currentThread().getName() + "] [changeRequestStatusServlet] - [doPost]: Status of requestID: " + currentRequest.getRequestID()
                        + " is changed from: " + currentRequest.getStatus()
                        + " to: " + requestToChange.getStatus());
                if (req.getParameter("status").equalsIgnoreCase("approved")) {
                    currentRequest.approveRequest();
                } else {
                    currentRequest.denyRequest();
                }
                return;
            }
        }
    }

    private String getJSONData(InputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder requestBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }
        return requestBody.toString();
    }
    private void setRequestID(String jsonData, AllocationRequest requestToChange) {
        int indexBegin = jsonData.indexOf("D") + 3;
        int indexEnd = jsonData.indexOf(",\"s");
        if (indexEnd != -1) {
            String requestIDAsString = jsonData.substring(indexBegin, indexEnd);
            int requestID = Integer.parseInt(requestIDAsString);
            requestToChange.setRequestID(requestID);
        }
    }
}

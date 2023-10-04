import engine.general.object.Engine;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.InputStream;

@WebServlet(name = "Test Servlet", urlPatterns = "/upload-file")
@MultipartConfig
public class testServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part filePart = req.getPart("file");
        String fileName = getSubmittedFileName(filePart);
        resp.getWriter().println(fileName);
        System.out.println("New Engine created");
        Engine engine = (Engine)this.getServletContext().getAttribute("engine");
        InputStream fileContent = filePart.getInputStream();
        try {
            engine.setupSimulation(fileContent);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    private String getSubmittedFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return fileName.substring(fileName.lastIndexOf('/') + 1)
                        .substring(fileName.lastIndexOf('\\') + 1);
            }
        }
        return null;
    }
}

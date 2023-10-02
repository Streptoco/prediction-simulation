import engine.general.object.Engine;
import enginetoui.dto.basic.impl.WorldDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;

@WebServlet(name = "Test Servlet", urlPatterns = "/upload-file")
@MultipartConfig
public class testServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("hey lol");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part filePart = req.getPart("file");
        String fileName = getSubmittedFileName(filePart);
        resp.getWriter().println(fileName);
        System.out.println("New Engine created");
        Engine engine = (Engine)this.getServletContext().getAttribute("engine");
        try {
            engine.loadWorld(filePart);
            int j = engine.setupSimulation();
            System.out.println(j);
            WorldDTO blyat = engine.getWorldDTO(0);
            System.out.println(blyat);
            System.out.println();
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

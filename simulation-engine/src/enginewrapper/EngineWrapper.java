package enginewrapper;

import engine.exception.XMLException;
import engine.general.object.Engine;
import engine.general.object.World;
import engine.worldbuilder.prdobjects.PRDWorld;
import engine.xml.NewXMLReader;
import engine.xml.XmlReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class EngineWrapper {
    public static void main(String[] args) {
        NewXMLReader xmlReader = new NewXMLReader();
        try {
            xmlReader.ReadXML("D:\\MISC\\תואר\\Java\\ex1\\predictions-1\\tests\\ex2\\ex2-virus.xml");
        } catch (JAXBException e) {
            System.out.println(e.getMessage());
        }
    }
}

package mybeans.xml;

import mybeans.Data;
import mybeans.DataSheet;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class DataSheetToXML {

    public static void saveXMLDoc(Document doc, String fileName) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(fileName);
            transformer.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public static Document createDataSheetDOM(DataSheet dataSheet) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("datasheet");
            doc.appendChild(rootElement);

            int dataSize = dataSheet.size();
            for (int i = 0; i < dataSize; i++) {
                Data data = dataSheet.getDataItem(i);
                Element dataElement = doc.createElement("data");
                rootElement.appendChild(dataElement);

                dataElement.setAttribute("date", data.getDate());
                dataElement.setAttribute("x", String.valueOf(data.getX()));
                dataElement.setAttribute("y", String.valueOf(data.getY()));
            }

            return doc;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return null;
        }
    }
}

package mybeans.xml;

import mybeans.Data;
import mybeans.DataSheet;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class DataHandler extends DefaultHandler {
    private List<Data> dataList;
    private Data currentData;
    private StringBuilder dataBuilder;

    @Override
    public void startDocument() throws SAXException {
        dataList = new ArrayList<>();
        dataBuilder = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("data")) {
            currentData = new Data("", 0.0, 0.0);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        dataBuilder.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("data")) {
            dataList.add(currentData);
        } else if (qName.equalsIgnoreCase("date")) {
            currentData.setDate(dataBuilder.toString().trim());
        } else if (qName.equalsIgnoreCase("x")) {
            currentData.setX(Double.parseDouble(dataBuilder.toString().trim()));
        } else if (qName.equalsIgnoreCase("y")) {
            currentData.setY(Double.parseDouble(dataBuilder.toString().trim()));
        }
        dataBuilder.setLength(0);
    }

    public List<Data> getDataList() {
        return dataList;
    }

    public DataSheet getDataSheet() {
        DataSheet dataSheet = new DataSheet();
        for (Data data : dataList) {
            dataSheet.addDataItem(data);
        }
        return dataSheet;
    }
}

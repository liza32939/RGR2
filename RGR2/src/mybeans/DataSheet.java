package mybeans;

import mybeans.Data;

import java.util.ArrayList;
import java.util.List;

public class DataSheet {
    private List<Data> dataItems;

    public DataSheet() {
        dataItems = new ArrayList<>();
    }

    public void addDataItem(Data data) {
        dataItems.add(data);
    }

    public void removeDataItem(int index) {
        if (index >= 0 && index < dataItems.size()) {
            dataItems.remove(index);
        }
    }

    public Data getDataItem(int index) {
        return dataItems.get(index);
    }

    public int size() {
        return dataItems.size();
    }
}

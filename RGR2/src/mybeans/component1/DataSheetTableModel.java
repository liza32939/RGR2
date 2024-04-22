package mybeans.component1;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Iterator;
import mybeans.DataSheet;

public class DataSheetTableModel extends AbstractTableModel {
    private static final long serialVersionUID = 1L;
    private int columnCount = 3;
    private int rowCount = 1;
    private DataSheet dataSheet = null;
    private ArrayList<DataSheetChangeListener> listenerList = new ArrayList<DataSheetChangeListener>();
    private DataSheetChangeEvent event = new DataSheetChangeEvent(this);

    String[] columnNames = {"Date", "X Value", "Y Value"};

    public DataSheet getDataSheet() {
        return dataSheet;
    }

    public void addDataSheetChangeListener(DataSheetChangeListener l) {
        listenerList.add(l);
    }

    public void removeDataSheetChangeListener(DataSheetChangeListener l) {
        listenerList.remove(l);
    }

    protected void fireDataSheetChange() {
        Iterator<DataSheetChangeListener> i = listenerList.iterator();
        while (i.hasNext()) {
            i.next().dataChanged(event);
        }
    }

    public void setDataSheet(DataSheet dataSheet) {
        this.dataSheet = dataSheet;
        rowCount = this.dataSheet.size();
        fireDataSheetChange();
    }

    @Override
    public int getColumnCount() {
        return columnCount;
    }

    @Override
    public int getRowCount() {
        return rowCount;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex >= 0;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        try {
            double d;
            if (dataSheet != null) {
                if (columnIndex == 0) {
                    dataSheet.getDataItem(rowIndex).setDate((String) value);
                } else if (columnIndex == 1) {
                    d = Double.parseDouble((String) value);
                    dataSheet.getDataItem(rowIndex).setX(d);
                } else if (columnIndex == 2) {
                    d = Double.parseDouble((String) value);
                    dataSheet.getDataItem(rowIndex).setY(d);
                }
            }
            fireDataSheetChange();
        } catch (Exception ex) {}
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (dataSheet != null) {
            if (columnIndex == 0)
                return dataSheet.getDataItem(rowIndex).getDate();
            else if (columnIndex == 1)
                return dataSheet.getDataItem(rowIndex).getX();
            else if (columnIndex == 2)
                return dataSheet.getDataItem(rowIndex).getY();
        }
        return null;
    }

    public void setRowCount(int rowCount) {
        if (rowCount > 0)
            this.rowCount = rowCount;
    }

}


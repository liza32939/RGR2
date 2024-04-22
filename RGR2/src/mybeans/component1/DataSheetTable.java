package mybeans.component1;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import mybeans.Data;
import mybeans.DataSheet;

public class DataSheetTable extends JPanel {
    private JTable table;
    private DataSheetTableModel tableModel;
    private JButton addButton;
    private JButton delButton;

    public DataSheetTable() {
        setLayout(new BorderLayout());

        // Таблиця
        table = new JTable();
        tableModel = new DataSheetTableModel();
        table.setModel(tableModel);

        // Панель прокрутки для таблиці
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);

        // Панель для кнопок
        JPanel panelButtons = new JPanel(new FlowLayout());
        addButton = new JButton("Add");
        delButton = new JButton("Delete");
        panelButtons.add(addButton);
        panelButtons.add(delButton);
        add(panelButtons, BorderLayout.SOUTH);

        // Обробник події для кнопки додавання
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tableModel.setRowCount(tableModel.getRowCount() + 1);
                tableModel.getDataSheet().addDataItem(new Data("", 0.0, 0.0));
                table.revalidate();
                tableModel.fireDataSheetChange();
            }
        });

        // Обробник події для кнопки видалення
        delButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (tableModel.getRowCount() > 1) {
                    tableModel.setRowCount(tableModel.getRowCount() - 1);
                    tableModel.getDataSheet().removeDataItem(tableModel.getDataSheet().size() - 1);
                    table.revalidate();
                    tableModel.fireDataSheetChange();
                } else {
                    tableModel.getDataSheet().getDataItem(0).setDate("");
                    tableModel.getDataSheet().getDataItem(0).setX(0);
                    tableModel.getDataSheet().getDataItem(0).setY(0);
                    table.revalidate();
                    table.repaint();
                    tableModel.fireDataSheetChange();
                }
            }
        });
    }

    public DataSheetTableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(DataSheetTableModel tableModel) {
        this.tableModel = tableModel;
        table.setModel(tableModel);
        table.revalidate();
    }

    public void revalidate() {
        if (table != null) table.revalidate();
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getDelButton() {
        return delButton;
    }
}


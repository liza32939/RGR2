package myApplication;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import mybeans.Data;
import mybeans.DataSheet;
import mybeans.component1.DataSheetChangeListener;
import mybeans.component1.DataSheetChangeEvent;
import mybeans.component2.DataSheetGraph;
import mybeans.component1.DataSheetTable;
import mybeans.xml.DataSheetToXML;
import mybeans.xml.SAXRead;

public class Test extends JFrame {
    private DataSheet dataSheet;
    private DataSheetGraph dataSheetGraph = null;
    private DataSheetTable dataSheetTable = null;
    private final JFileChooser fileChooser = new JFileChooser();

    public Test() {
        super("Приклад JavaBeans");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize data sheet
        dataSheet = new DataSheet();
        dataSheet.addDataItem(new Data("", 0.0, 0.0));

        // Create data sheet graph component
        dataSheetGraph = new DataSheetGraph();
        dataSheetGraph.setDataSheet(dataSheet);
        add(dataSheetGraph, BorderLayout.EAST);

        // Create data sheet table component
        dataSheetTable = new DataSheetTable();
        dataSheetTable.getTableModel().setDataSheet(dataSheet);
        dataSheetTable.getTableModel().addDataSheetChangeListener(new DataSheetChangeListener() {
            public void dataChanged(DataSheetChangeEvent e) {
                dataSheetGraph.revalidate();
                dataSheetGraph.repaint();
            }
        });
        add(dataSheetTable, BorderLayout.WEST);

        // Create panel for control buttons
        JPanel panel = new JPanel();
        add(panel, BorderLayout.SOUTH);

        // Initialize file chooser
        fileChooser.setCurrentDirectory(new java.io.File("."));

        // Add buttons
        JButton readButton = new JButton("Read");
        JButton saveButton = new JButton("Save");
        JButton clearButton = new JButton("Clear");
        JButton exitButton = new JButton("Exit");

        panel.add(readButton);
        panel.add(saveButton);
        panel.add(clearButton);
        panel.add(exitButton);

        // Button actions
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                dispose();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dataSheet = new DataSheet();
                dataSheet.addDataItem(new Data("", 0.0, 0.0));
                dataSheetTable.getTableModel().setDataSheet(dataSheet);
                dataSheetTable.revalidate();
                dataSheetGraph.setDataSheet(dataSheet);
            }
        });

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (JFileChooser.APPROVE_OPTION == fileChooser.showSaveDialog(null)) {
                    String fileName = fileChooser.getSelectedFile().getPath();
                    DataSheetToXML.saveXMLDoc(DataSheetToXML.createDataSheetDOM(dataSheet), fileName);
                    JOptionPane.showMessageDialog(null, "File " + fileName.trim() + " saved!",
                            "Results saved", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        readButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(null)) {
                    String fileName = fileChooser.getSelectedFile().getPath();
                    dataSheet = SAXRead.XMLReadData(fileName);
                    dataSheetTable.getTableModel().setDataSheet(dataSheet);
                    dataSheetTable.revalidate();
                    dataSheetGraph.setDataSheet(dataSheet);
                }
            }
        });

        // Set preferred sizes
        dataSheetGraph.setPreferredSize(new Dimension(300, 400));
        dataSheetTable.setPreferredSize(new Dimension(200, 300));

        pack();
        setLocationRelativeTo(null); // Center the frame on screen
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Test().setVisible(true);
            }
        });
    }
}

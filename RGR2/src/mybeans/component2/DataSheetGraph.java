package mybeans.component2;

import mybeans.DataSheet;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class DataSheetGraph extends JPanel {
    private static final long serialVersionUID = 1L;
    private DataSheet dataSheet = null;
    private boolean isConnected;
    private int deltaX;
    private int deltaY;
    transient private Color color;

    public DataSheetGraph() {
        super();
        initialize();
    }

    private void initialize() {
        isConnected = false;
        deltaX = 5;
        deltaY = 5;
        color = Color.red;
        this.setSize(300, 400);
    }

    public DataSheet getDataSheet() {
        return dataSheet;
    }

    public void setDataSheet(DataSheet dataSheet) {
        this.dataSheet = dataSheet;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean isConnected) {
        this.isConnected = isConnected;
        repaint();
    }

    public int getDeltaX() {
        return deltaX;
    }

    public int getDeltaY() {
        return deltaY;
    }

    public void setDeltaX(int deltaX) {
        this.deltaX = deltaX;
        repaint();
    }

    public void setDeltaY(int deltaY) {
        this.deltaY = deltaY;
        repaint();
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
        repaint();
    }

    private double minX() {
        double result = 0;
        if (dataSheet != null) {
            int size = dataSheet.size();
            for (int i = 0; i < size; i++)
                if (dataSheet.getDataItem(i).getX() < result)
                    result = dataSheet.getDataItem(i).getX();
        }
        return result;
    }

    private double maxX() {
        double result = 0;
        if (dataSheet != null) {
            int size = dataSheet.size();
            for (int i = 0; i < size; i++)
                if (dataSheet.getDataItem(i).getX() > result)
                    result = dataSheet.getDataItem(i).getX();
        }
        return result;
    }

    private double minY() {
        double result = 0;
        if (dataSheet != null) {
            int size = dataSheet.size();
            for (int i = 0; i < size; i++)
                if (dataSheet.getDataItem(i).getY() < result)
                    result = dataSheet.getDataItem(i).getY();
        }
        return result;
    }

    private double maxY() {
        double result = 0;
        if (dataSheet != null) {
            int size = dataSheet.size();
            for (int i = 0; i < size; i++)
                if (dataSheet.getDataItem(i).getY() > result)
                    result = dataSheet.getDataItem(i).getY();
        }
        return result;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        showGraph(g2);
    }

    public void showGraph(Graphics2D gr) {
        double xMin, xMax, yMin, yMax;
        double width = getWidth();
        double height = getHeight();
        xMin = minX() - deltaX;
        xMax = maxX() + deltaX;
        yMin = minY() - deltaY;
        yMax = maxY() + deltaY;

        double xScale = width / (xMax - xMin);
        double yScale = height / (yMax - yMin);
        double x0 = -xMin * xScale;
        double y0 = yMax * xScale;

        gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gr.setPaint(Color.WHITE);
        gr.fill(new Rectangle2D.Double(0.0, 0.0, width, height));

        double xStep = 1;
        for (double dx = xStep; dx < xMax; dx += xStep) {
            double x = x0 + dx * xScale;
            gr.setPaint(Color.LIGHT_GRAY);
            gr.draw(new Line2D.Double(x, 0, x, height));
            gr.setPaint(Color.BLACK);
            gr.drawString(Math.round(dx / xStep) * xStep + "", (int) x + 2, 10);
        }

        for (double dx = -xStep; dx >= xMin; dx -= xStep) {
            double x = x0 + dx * xScale;
            gr.setPaint(Color.LIGHT_GRAY);
            gr.draw(new Line2D.Double(x, 0, x, height));
            gr.setPaint(Color.BLACK);
            gr.drawString(Math.round(dx / xStep) * xStep + "", (int) x + 2, 10);
        }

        double yStep = 1;
        for (double dy = yStep; dy < yMax; dy += yStep) {
            double y = y0 - dy * yScale;
            gr.setPaint(Color.LIGHT_GRAY);
            gr.draw(new Line2D.Double(0, y, width, y));
            gr.setPaint(Color.BLACK);
            gr.drawString(Math.round(dy / yStep) * yStep + "", 2, (int) y - 2);
        }

        for (double dy = -yStep; dy >= yMin; dy -= yStep) {
            double y = y0 - dy * yScale;
            gr.setPaint(Color.LIGHT_GRAY);
            gr.draw(new Line2D.Double(0, y, width, y));
            gr.setPaint(Color.BLACK);
            gr.drawString(Math.round(dy / yStep) * yStep + "", 2, (int) y - 2);
        }

        gr.setPaint(Color.BLACK);
        gr.setStroke(new BasicStroke(3.0f));
        gr.draw(new Line2D.Double(x0, 0, x0, height));
        gr.draw(new Line2D.Double(0, y0, width, y0));
        gr.drawString("X", (int) width - 10, (int) y0 - 2);
        gr.drawString("Y", (int) x0 + 2, 10);

        if (dataSheet != null) {
            if (!isConnected) {
                for (int i = 0; i < dataSheet.size(); i++) {
                    double x = x0 + (dataSheet.getDataItem(i).getX() * xScale);
                    double y = y0 - (dataSheet.getDataItem(i).getY() * yScale);
                    gr.setColor(Color.white);
                    gr.fillOval((int) (x - 5 / 2), (int) (y - 5 / 2), 5, 5);
                    gr.setColor(color);
                    gr.drawOval((int) (x - 5 / 2), (int) (y - 5 / 2), 5, 5);
                }
            } else {
                gr.setPaint(color);
                gr.setStroke(new BasicStroke(2.0f));
                double xOld = x0 + dataSheet.getDataItem(0).getX() * xScale;
                double yOld = y0 - dataSheet.getDataItem(0).getY() * yScale;
                for (int i = 1; i < dataSheet.size(); i++) {
                    double x = x0 + dataSheet.getDataItem(i).getX() * xScale;
                    double y = y0 - dataSheet.getDataItem(i).getY() * yScale;
                    gr.draw(new Line2D.Double(xOld, yOld, (double) x, y));
                    xOld = x;
                    yOld = y;
                }
            }
        }
    }
}
package entity;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import java.time.LocalDate;
import javax.swing.*;
import java.time.Month;
import java.util.*;

public class LineChart extends JFrame {
    public LineChart(){
    }

    public LineChart(String title, Map<String, Integer> monthlyRevenue) {
        super(title);

        CategoryDataset dataset = createDataset(monthlyRevenue);
        JFreeChart chart = createChart(dataset);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(600, 400));
        setContentPane(chartPanel);
    }

    private CategoryDataset createDataset(Map<String, Integer> monthlyRevenue) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Map.Entry<String, Integer> entry : monthlyRevenue.entrySet()) {
            dataset.addValue(entry.getValue(), "Monthly Revenue", entry.getKey());
        }
        return dataset;
    }

    private JFreeChart createChart(CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createLineChart(
                "Monthly Revenue Chart",
                "Month",
                "Total Revenue",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        CategoryAxis xAxis = plot.getDomainAxis();

        xAxis.setCategoryMargin(0.5);
        xAxis.setLowerMargin(0.02);
        xAxis.setUpperMargin(0.02);

        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        return chart;
    }

    public Map<String, Integer> calculateMonthlyRevenue(ArrayList<Reservation> reservations) {
        Map<String, Integer> monthlyRevenue = new LinkedHashMap<>();

        for (Reservation reservation : reservations) {
            String monthKey = reservation.getExit_date().getMonth().toString();
            int totalRevenue = monthlyRevenue.getOrDefault(monthKey, 0);
            totalRevenue += reservation.getTotal_price();
            monthlyRevenue.put(monthKey, totalRevenue);
        }

        return monthlyRevenue;
    }
}
package gfl.tdd_web.beans;

import gfl.tdd_web.data.CalculatorData;
import gfl.tdd_web.models.Calculator;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.SneakyThrows;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.Base64;

@Named
@RequestScoped
@Getter
public class CalculationResultBean implements Serializable {

    @Inject
    private CalculatorBean calculatorBean;

    @Getter(AccessLevel.NONE)
    private double[][] arrays;

    private int minIndex;
    private int maxIndex;
    private double minValue;
    private double maxValue;
    private double minValueArgument;
    private double maxValueArgument;
    private double sum;
    private double average;

    private String chartData;

    @SneakyThrows
    @PostConstruct
    public void init() {
        CalculatorData calculatorData = calculatorBean.getCalculatorData();

        arrays = Calculator.calculateYForRangeX(calculatorData.getA(), calculatorData.getStartInterval(),
                calculatorData.getEndInterval(), calculatorData.getStep());
        minIndex = Calculator.findMinValueIndex(arrays[1]);
        maxIndex = Calculator.findMaxValueIndex(arrays[1]);

        minValue = arrays[1][minIndex];
        minValueArgument = arrays[0][minIndex];

        maxValue = arrays[1][maxIndex];
        maxValueArgument = arrays[0][maxIndex];

        sum = Calculator.calculateSum(arrays[1]);
        average = Calculator.calculateAverage(arrays[1]);


        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(createBufferedImageChart(arrays), "png", os);
        byte[] imageData = os.toByteArray();
        chartData = "data:image/png;base64," + Base64.getEncoder().encodeToString(imageData);


    }

    private static BufferedImage createBufferedImageChart(double[][] points) {

        XYSeries series = new XYSeries("Data");

        for (int i = 0; i < points[0].length; i++) {
            series.add(points[0][i], points[1][i]);
        }
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        JFreeChart chart = ChartFactory.createScatterPlot(
                "Point Chart",
                "X",
                "Y",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = (XYPlot) chart.getPlot();
        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        domainAxis.setAutoRangeIncludesZero(false);

        XYItemRenderer renderer = plot.getRenderer();
        renderer.setSeriesPaint(0, Color.blue);
        double size = 1.0;
        double delta = size / 2.0;
        Shape shape1 = new Rectangle2D.Double(-delta, -delta, size, size);
        renderer.setSeriesShape(0, shape1);

        return chart.createBufferedImage(600, 400);
    }

    public String goBack(){
        return "calculator";
    }
}

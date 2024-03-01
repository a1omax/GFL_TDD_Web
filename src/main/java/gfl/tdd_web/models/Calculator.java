package gfl.tdd_web.models;


import java.util.Arrays;
import java.util.OptionalDouble;
import java.util.Scanner;


public class Calculator {

    static final double E = 1e10;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input a (2,4): ");
        double a = scanner.nextDouble();

        System.out.println("Input interval (1 5 0,01)");
        System.out.print("start: ");
        double start = scanner.nextDouble();
        System.out.print("end: ");
        double end = scanner.nextDouble();
        System.out.print("step: ");
        double step = scanner.nextDouble();
        System.out.println();

        double[][] arrays = calculateYForRangeX(a, start, end, step);

        int minIndex = findMinValueIndex(arrays[1]);
        int maxIndex = findMaxValueIndex(arrays[1]);


        printMinValue(minIndex, arrays[1][minIndex], arrays[0][minIndex]);
        System.out.println();
        printMaxValue(maxIndex, arrays[1][maxIndex], arrays[0][maxIndex]);
        System.out.println();


        double sum = calculateSum(arrays[1]);
        double average = calculateAverage(arrays[1]);
        System.out.println("Sum = " + sum);
        System.out.println("Average = " + average);
    }

    public static double function(double a, double x) {

        if (x > a) {
            return x * Math.sqrt(x - a);
        } else if (x == a) {
            return x * Math.sin(a * x);
        } else return Math.exp(-a * x) * Math.cos(a * x);
    }

    public static int countSteps(double start, double end, double step) throws IllegalArgumentException {
        if (step == 0) {
            throw new IllegalArgumentException("Step can not equal to 0");
        }
        return (int) (Math.abs(end - start) / step)+1;
    }


    public static double[] createArrayForArgumentX(double start, double end, double step) {
        int nSteps = countSteps(start, end, step);

        if (nSteps < 1) {
            return new double[0];
        }

        double[] arrayOfArgumentX = new double[nSteps];

        arrayOfArgumentX[0] = start;

        for (int i = 1; i < arrayOfArgumentX.length; i++) {
            arrayOfArgumentX[i] =  Math.round((start + step * i) *E)/E;
        }

        return arrayOfArgumentX;
    }

    public static double[] calculateArrayYForArrayOfX(double a, double[] arrayOfArgumentX) {
        double[] arrayOfY = new double[arrayOfArgumentX.length];

        for (int i = 0; i < arrayOfY.length; i++) {
            arrayOfY[i] = function(a, arrayOfArgumentX[i]);
        }
        return arrayOfY;
    }

    public static double[][] calculateYForRangeX(double a, double start, double end, double step){
        double [][] arrays = new double[2][];

        arrays[0] = createArrayForArgumentX(start,end,step);
        arrays[1] = calculateArrayYForArrayOfX(a, arrays[0]);

        return arrays;
    }

    public static int findMaxValueIndex(double[] values){
        if (values.length == 0){
            throw new IllegalArgumentException("Max value index can't be found, array length equals to 0");
        }

        int maxIndex = 0;
        for (int i = 1; i < values.length; i++) {
            if (values[maxIndex] < values[i]){
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    public static int findMinValueIndex(double[] values){
        if (values.length == 0){
            throw new IllegalArgumentException("Min value index can't be found, array length equals to 0");
        }

        int minIndex = 0;
        for (int i = 1; i < values.length; i++) {
            if (values[minIndex] > values[i]){
                minIndex = i;
            }
        }
        return minIndex;
    }

    public static double calculateSum(double[] values){
        return Arrays.stream(values).sum();
    }

    public static double calculateAverage(double[] values){
        OptionalDouble average = Arrays.stream(values).average();
        if (average.isPresent()){
            return average.getAsDouble();
        }
        else {
            throw new RuntimeException("Average can't be calculated");
        }
    }


    public static void printMaxValue(int maxIndex,double maxValue,  double argument){
        System.out.println("Max value index = " + maxIndex);
        System.out.println("Max value = " + maxValue);
        System.out.println("Argument = " + argument);
    }


    public static void printMinValue(int minIndex, double minValue, double argument){
        System.out.println("Min value index = " + minIndex);
        System.out.println("Min value = " + minValue);
        System.out.println("Argument = " + argument);
    }
}
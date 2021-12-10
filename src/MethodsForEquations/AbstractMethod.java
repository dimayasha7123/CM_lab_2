/**
 * |_|>
 * |_|>    Created by Dimyasha on 10.12.2021
 * |_|>
 */

package MethodsForEquations;

import static MethodsForEquations.Utils.matrixToString;
import static MethodsForEquations.Utils.unboxDoubleTwoDemArray;

public abstract class AbstractMethod {
    //размерность
    protected int n;

    //матрица А коэф. для вычислений и исходная
    protected double[][] aSrc;
    protected double[][] a;

    //массив B свободных членов для вычислений и исходная
    protected double[] bSrc;
    protected double[] b;

    //массив X неизвестных
    protected double[] x;


    //точность вывода
    protected int digits = 6;
    public int getDigits() {
        return digits;
    }

    public void setDigits(int digits) {
        this.digits = digits;
    }


    public AbstractMethod(Double[][] a) {
        this(unboxDoubleTwoDemArray(a));
    }

    public AbstractMethod(double[][] a) {
        n = a.length;

        this.a = new double[n][n];
        this.aSrc = new double[n][n];

        this.b = new double[n];
        this.bSrc = new double[n];

        for (int i = 0; i < n; ++i) {
            b[i] = a[i][n];
            bSrc[i] = a[i][n];

            System.arraycopy(a[i], 0, this.a[i], 0, n);
            System.arraycopy(a[i], 0, this.aSrc[i], 0, n);
        }
    }

    public double[] Invoke() {
        return Invoke(false);
    }

    public abstract double[] Invoke(boolean print);

    public double getErrorRate() {
        return getErrorRate(false);
    }

    public double getErrorRate(boolean print) {
        //проверяем не null ли x
        if (x == null) throw new NullPointerException("А метод инвокать кто будет?");

        //находим макс. по модулю из b
        double maxB = 0;
        for (double i : bSrc) if (Math.abs(i) > maxB) maxB = Math.abs(i);

        //находим bWithError
        double[] bWithError = new double[n];
        for (int i = 0; i < n; ++i) {
            bWithError[i] = 0;
            for (int j = 0; j < n; ++j) {
                bWithError[i] += aSrc[i][j] * x[j];
            }
        }

        //находим максимальную deltaBmax
        double[] deltaB = new double[n];
        double deltaBmax = 0;
        for (int i = 0; i < n; ++i) {
            deltaB[i] = bSrc[i] - bWithError[i];
            double curr = Math.abs(bSrc[i] - bWithError[i]);
            if (curr > deltaBmax) deltaBmax = curr;
        }

        if (print) {
            char leftUp = '\u239b';
            char leftMid = '\u239c';
            char leftDown = '\u239d';
            char rightUp = '\u239e';
            char rightMid = '\u239f';
            char rightDown = '\u23a0';

            int digits = 3;

            System.out.println("DeltaB:");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n; ++i) {

                if (i == 0) sb.append(leftUp);
                else if (i == n - 1) sb.append(leftDown);
                else sb.append(leftMid);

                sb.append(String.format("%" + (digits + 7) + "." + digits + "e ", deltaB[i]));

                if (i == 0) sb.append(rightUp);
                else if (i == n - 1) sb.append(rightDown);
                else sb.append(rightMid);
                sb.append('\n');
            }
            System.out.println(sb);
            System.out.println("DeltaBmax = " + deltaBmax);
            System.out.println("MaxB = " + maxB);
            System.out.println("Error = " + deltaBmax / maxB);
        }
        return deltaBmax / maxB;
    }

    @Override
    public String toString(){
        return "Matrix A (source):\n" +
                matrixToString(a, digits) +
                "\nVector B (source):\n" +
                matrixToString(b, digits);
    }

    public abstract void printInfo();
}
/**
 * |_|>
 * |_|>    Created by Dimyasha on 10.12.2021
 * |_|>
 */

package MethodsForEquations;

import java.util.function.Function;

import static MethodsForEquations.Utils.matrixToString;

public class TridiagRunMethod extends AbstractMethod {

    public TridiagRunMethod(Double[][] a) {
        super(a);
    }

    public TridiagRunMethod(double[][] a) {
        super(a);
    }

    protected double getA(int i) {
        return i == 0 ? 0 : a[i][i - 1];
    }

    protected double getB(int i) {
        return a[i][i];
    }

    protected double getC(int i) {
        return i == n - 1 ? 0 : a[i][i + 1];
    }

    protected double getD(int i) {
        return b[i];
    }

    protected double[] makeMatrix(Function<Integer, Double> func) {
        double[] matr = new double[n];
        for (int i = 0; i < n; ++i) matr[i] = func.apply(i);
        return matr;
    }

    @Override
    public double[] Invoke(boolean print) {
        x = new double[n];
        double[] alpha = new double[n];
        double[] beta = new double[n];

        alpha[0] = (-1) * getC(0) / getB(0);
        beta[0] = getD(0) / getB(0);

        for (int i = 1; i < n; ++i) {
            alpha[i] = (-1) * getC(i) / (getA(i) * alpha[i - 1] + getB(i));
            beta[i] = (getD(i) - getA(i) * beta[i - 1]) / (getA(i) * alpha[i - 1] + getB(i));
        }

        x[n - 1] = (getD(n - 1) - getA(n - 1) * beta[n - 2])
                / (getA(n - 1) * alpha[n - 2] + getB(n - 1));

        for (int i = n - 2; i >= 0; --i) {
            x[i] = alpha[i] * x[i + 1] + beta[i];
        }

        if (print) {
            System.out.println(this);

            System.out.println("A:");
            System.out.println(matrixToString(makeMatrix(this::getA), digits));
            System.out.println("B:");
            System.out.println(matrixToString(makeMatrix(this::getB), digits));
            System.out.println("C:");
            System.out.println(matrixToString(makeMatrix(this::getC), digits));
            System.out.println("D:");
            System.out.println(matrixToString(makeMatrix(this::getD), digits));

            System.out.println("Alpha:");
            System.out.println(matrixToString(alpha, digits));
            System.out.println("Beta:");
            System.out.println(matrixToString(beta, digits));

            System.out.println("X:");
            System.out.println(matrixToString(x, digits));
        }

        return x;
    }

    @Override
    public void printInfo() {
        Invoke(true);
        getErrorRate(true);
    }

}

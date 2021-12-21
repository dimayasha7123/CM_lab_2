/**
 * |_|>
 * |_|>    Created by Dimyasha on 21.12.2021
 * |_|>
 */

package MethodsForEquations.IterativeMethods;

import MethodsForEquations.AbstractMethod;
import MethodsForEquations.IterativeMethods.IType;
import UTFE.TableOutput.Table;

import java.util.ArrayList;
import java.util.Arrays;

public class IterativeMethod extends AbstractMethod {

    protected IType type = IType.JACOBI;
    protected double eps = Math.pow(10, -2);

    public IterativeMethod(Double[][] a, IType type, double eps) {
        super(a);
        this.type = type;
        this.eps = eps;
    }

    public IterativeMethod(double[][] a, IType type, double eps) {
        super(a);
        this.type = type;
        this.eps = eps;
    }

    public IterativeMethod(Double[][] a) {
        super(a);
    }

    public IterativeMethod(double[][] a) {
        super(a);
    }

    @Override
    public double[] Invoke(boolean print) {
        double[] prevX = new double[n];
        x = new double[n];
        System.arraycopy(b, 0, prevX, 0, n);

        System.out.println("prevX = " + Arrays.toString(prevX));

        ArrayList<Object[]> table = new ArrayList<>();
        Object[] header = new Object[n + 2];
        header[0] = "n";
        header[n + 1] = "max|X(k)i - X(k-1)i|";
        for (int i = 1; i <= n; ++i) header[i] = "X" + (i + 1);
        table.add(header);

        Object[] firstRow = new Object[n + 2];
        firstRow[0] = 0;
        firstRow[n + 1] = "-";
        for (int i = 1; i <= n; ++i) firstRow[i] = prevX[i - 1];
        table.add(firstRow);

        double delta = 100;
        int iter = 1;
        while (delta >= eps) {
            delta = 0;
            for (int i = 0; i < n; ++i) {

                x[i] = b[i] / a[i][i];
                for (int j = 0; j <= i - 1; ++j) {
                    if (type == IType.JACOBI) x[i] -= prevX[j] * a[i][j] / a[i][i];
                    if (type == IType.GAUSS_SEIDEL) x[i] -= x[j] * a[i][j] / a[i][i];

                }
                for (int j = i + 1; j < n; ++j) {
                    x[i] -= prevX[j] * a[i][j] / a[i][i];
                }

                double currDelta = Math.abs(x[i] - prevX[i]);
                if (currDelta > delta) delta = currDelta;
            }

            Object[] row = new Object[n + 2];
            row[0] = iter++;
            row[n + 1] = delta;
            for (int i = 1; i <= n; ++i) row[i] = x[i - 1];
            table.add(row);

            System.arraycopy(x, 0, prevX, 0, n);
        }

        if (print) {
            System.out.println("eps = " + eps);
            System.out.println(Table.TableToString(table.toArray(Object[][]::new)));
        }

        return x;
    }

    @Override
    public void printInfo() {
        Invoke(true);
    }
}

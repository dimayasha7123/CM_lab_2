/**
 * |_|>
 * |_|>    Created by Dimyasha on 03.12.2021
 * |_|>
 */

package LUMethods;

import java.text.DecimalFormat;

public class LUMethod {
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
    protected int[] xPos;

    //матрицы BC из LU разложения
    protected double[][] Blu;
    protected double[][] Clu;

    //вектор Y неизвестных
    protected double[] y;

    protected static double[][] unboxDoubleTwoDemArray(Double[][] a) {
        int n = a.length;
        int m = a[0].length;
        double[][] unboxed = new double[n][m];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                unboxed[i][j] = a[i][j];
            }
        }
        return unboxed;
    }

    public LUMethod(Double[][] a) {
        this(unboxDoubleTwoDemArray(a));
    }

    public LUMethod(double[][] a) {
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

    protected static int getLen(double x, int digits) {
        DecimalFormat df = new DecimalFormat("#." + "#".repeat(digits));
        return df.format(x).length();
    }

    protected static int getLen(int x) {
        return ((Integer) x).toString().length();
    }

    protected static String matrixToString(double[] a, int digits) {
        double[][] b = new double[a.length][1];
        for (int i = 0; i < b.length; ++i) {
            b[i][0] = a[i];
        }
        return matrixToString(b, digits);
    }

    protected static String matrixToString(double[][] a, int digits) {
        char leftUp = '\u239b';
        char leftMid = '\u239c';
        char leftDown = '\u239d';
        char rightUp = '\u239e';
        char rightMid = '\u239f';
        char rightDown = '\u23a0';

        StringBuilder sb = new StringBuilder();

        int N = a.length;
        int M = a[0].length;

        int[] wides = new int[M];

        for (int i = 0; i < M; ++i) {
            for (int j = 0; j < N; ++j) {
                wides[i] = Math.max(wides[i], getLen(a[j][i], digits));
            }
        }

        for (int i = 0; i < N; ++i) {

            if (i == 0) sb.append(leftUp);
            else if (i == N - 1) sb.append(leftDown);
            else sb.append(leftMid);
            sb.append(" ");

            for (int j = 0; j < M; ++j) {
                String frmStr = "%" + wides[j] + "s ";
                DecimalFormat df = new DecimalFormat("#." + "#".repeat(digits));
                sb.append(String.format(frmStr, df.format(a[i][j])));
            }

            if (i == 0) sb.append(rightUp);
            else if (i == N - 1) sb.append(rightDown);
            else sb.append(rightMid);
            sb.append('\n');
        }
        return sb.toString();
    }

    public double[] Invoke() {
        return Invoke(false);
    }

    public double[] Invoke(boolean print) {
        y = new double[n];
        Blu = new double[n][n];
        Clu = new double[n][n];
        for (int i = 0; i < n; ++i) {
            Clu[i][i] = 1;
            for (int j = i; j < n; ++j) {
                Blu[j][i] = a[j][i];
                for (int k = 0; k < i; ++k) {
                    Blu[j][i] -= Blu[j][k] * Clu[k][i];
                }
            }
            for (int j = i + 1; j < n; ++j) {
                Clu[i][j] = a[i][j];
                for (int k = 0; k < i; ++k) {
                    Clu[i][j] -= Blu[i][k] * Clu[k][j];
                }
                Clu[i][j] /= Blu[i][i];
            }
        }

        for (int i = 0; i < n; ++i) {
            y[i] = b[i];
            for (int j = 0; j < i; ++j) {
                y[i] -= Blu[i][j] * y[j];
            }
            y[i] /= Blu[i][i];
        }

        //обратный ход
        x = new double[n];
        for (int i = n - 1; i >= 0; --i) {
            x[i] = y[i];
            for (int j = n - 1; j > i; --j) {
                x[i] -= Clu[i][j] * x[j];
            }
        }
        
        double det = 1;
        for (int i = 0; i < n; ++i) det *= Blu[i][i];
        System.out.println("det = " + det);

        System.out.println(matrixToString(Blu, 3));
        System.out.println(matrixToString(Clu, 3));
        System.out.println(matrixToString(y, 3));
        System.out.println(matrixToString(x, 3));
        return new double[1];
    }
}

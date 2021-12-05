/**
 * |_|>
 * |_|>    Created by Dimyasha on 30.11.2021
 * |_|>
 */

package GaussMethods;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.function.DoubleToIntFunction;

public class GaussMethod {
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

    //тип метода
    protected GType type = GType.BY_FIRST;

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

    public GaussMethod(Double[][] a, GType type) {
        this(a);
        this.type = type;
    }

    public GaussMethod(double[][] a, GType type) {
        this(a);
        this.type = type;
    }

    public GaussMethod(Double[][] a) {
        this(unboxDoubleTwoDemArray(a));
    }

    public GaussMethod(double[][] a) {
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
        DecimalFormat df = new DecimalFormat("#." +"#".repeat(digits));
        return df.format(x).length();
    }

    protected static int getLen(int x){
        return ((Integer) x).toString().length();
    }

    protected static String xMatrix(int[] pos){
        char leftUp = '\u239b';
        char leftMid = '\u239c';
        char leftDown = '\u239d';
        char rightUp = '\u239e';
        char rightMid = '\u239f';
        char rightDown = '\u23a0';

        StringBuilder sb = new StringBuilder();

        int N = pos.length;
        int M = 1;

        int wide = 0;

        for (int i = 0; i < M; ++i) {
            wide = Math.max(wide, getLen(pos[i]));
        }

        for (int i = 0; i < N; ++i) {

            if (i == 0) sb.append(leftUp);
            else if (i == N - 1) sb.append(leftDown);
            else sb.append(leftMid);
            sb.append(" ");

            sb.append(String.format("%" + wide + "s ", "x" + (pos[i] + 1)));

            if (i == 0) sb.append(rightUp);
            else if (i == N - 1) sb.append(rightDown);
            else sb.append(rightMid);
            sb.append('\n');
        }
        return sb.toString();
    }

    protected static String matrixToString(double[] a, int digits){
        double[][] b = new double[a.length][1];
        for (int i = 0; i < b.length; ++i){
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

    protected void replaceLines(int i, int j) {
        double[] t = a[i];
        a[i] = a[j];
        a[j] = t;

        double t2 = b[i];
        b[i] = b[j];
        b[j] = t2;
    }

    protected void replaceColumns(int i, int j) {
        for (int k = 0; k < n; ++k) {
            double t = a[k][i];
            a[k][i] = a[k][j];
            a[k][j] = t;
        }
        int t = xPos[i];
        xPos[i] = xPos[j];
        xPos[j] = t;
    }

    public double[] Invoke() {
        return Invoke(false);
    }

    public double[] Invoke(boolean print) {
        int dig = 3;

        xPos = new int[n];
        for (int i = 0; i < n; i++) {
            xPos[i] = i;
        }

        if (print) {
            System.out.println(matrixToString(a, dig));
            System.out.println(matrixToString(b, dig));
            System.out.println(xMatrix(xPos));
        }

        for (int i = 0; i < n; ++i) {
            //выбор опорного элемента
            switch (type) {
                case BY_LINE -> {
                    double max = -1;
                    int maxIndex = -1;
                    for (int j = 0; j < n; ++j) {
                        double curr = Math.abs(a[i][j]);
                        if (curr > max) {
                            max = curr;
                            maxIndex = j;
                        }
                    }
                    replaceColumns(i, maxIndex);
                }
                case BY_COLUMN -> {
                    double max = -1;
                    int maxIndex = -1;
                    for (int j = i; j < n; ++j) {
                        double curr = Math.abs(a[j][i]);
                        if (curr > max) {
                            max = curr;
                            maxIndex = j;
                        }
                    }
                    replaceLines(i, maxIndex);
                }
                case BY_ALL -> {
                    double max = -1;
                    int maxJ = -1;
                    int maxK = -1;
                    for (int j = i; j < n; ++j) {
                        for (int k = i; k < n; ++k) {
                            double curr = Math.abs(a[j][k]);
                            if (curr > max) {
                                max = curr;
                                maxJ = j;
                                maxK = k;
                            }
                        }
                    }
                    replaceLines(i, maxJ);
                    replaceColumns(i, maxK);
                }
            }
            double support = a[i][i];

            //делим строку на опорный элемент, чтобы опорный стал единицей
            for (int j = i; j < n; ++j) {
                a[i][j] /= support;
            }
            b[i] /= support;

            //делаем нули в i-столбце ниже нашего элемента
            for (int j = i + 1; j < n; ++j) {
                double ratio = a[j][i];
                for (int k = 0; k < n; ++k) {
                    a[j][k] -= a[i][k] * ratio;
                }
                b[j] -= b[i] * ratio;
            }


            if (print) {
                System.out.println(matrixToString(a, dig));
                System.out.println(matrixToString(b, dig));
                System.out.println(xMatrix(xPos));
            }
        }

        if (print) {
            System.out.println("a = " + Arrays.deepToString(a).replaceAll("], ", "],\n\t"));
            System.out.println("b = " + Arrays.toString(b));
        }

        //обратный ход
        x = new double[n];
        for (int i = n - 1; i >= 0; --i) {
            x[i] = b[i];
            for (int j = n - 1; j > i; --j) {
                x[i] -= a[i][j] * x[j];
            }
        }

        //восстанавливаем порядок x
        double[] xReplaced = new double[n];
        for (int i = 0; i < n; ++i) {
            xReplaced[xPos[i]] = x[i];
        }
        x = xReplaced;

        if (print) System.out.println("ans = " + Arrays.toString(x));
        return x;
    }

    public double getErrorRate() {
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

        System.out.println("DeltaB:");
        for (int i = 0; i < n; ++i){
            System.out.printf("%.3e\n", deltaB[i]);
        }

        System.out.println("deltaBmax = " + deltaBmax);
        System.out.println("maxB = " + maxB);
        return deltaBmax / maxB;
    }

    public void printInfo() {
        if (x == null) this.Invoke(true);
        System.out.println("Error = " + this.getErrorRate());
    }
}
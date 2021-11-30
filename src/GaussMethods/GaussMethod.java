/**
 * |_|>
 * |_|>    Created by Dimyasha on 30.11.2021
 * |_|>
 */

package GaussMethods;

import java.util.Arrays;

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

        xPos = new int[n];
        for (int i = 0; i < n; i++) {
            xPos[i] = i;
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
            ;
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

        //находим максимальную deltaB
        double deltaB = 0;
        for (int i = 0; i < n; ++i) {
            double curr = Math.abs(bSrc[i] - bWithError[i]);
            if (curr > deltaB) deltaB = curr;
        }
        return deltaB / maxB;
    }

    public void printInfo() {
        if (x == null) this.Invoke(true);
        System.out.println("Error = " + this.getErrorRate());
    }
}
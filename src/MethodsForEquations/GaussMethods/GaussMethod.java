/**
 * |_|>
 * |_|>    Created by Dimyasha on 30.11.2021
 * |_|>
 */

package MethodsForEquations.GaussMethods;


import java.util.Arrays;
import MethodsForEquations.AbstractMethod;
import static MethodsForEquations.utils.*;


public class GaussMethod extends AbstractMethod {
    //тип метода
    protected int[] xPos;
    protected GType type = GType.BY_FIRST;

    public GaussMethod(Double[][] a, MethodsForEquations.GaussMethods.GType type) {
        this(a);
        this.type = type;
    }

    public GaussMethod(double[][] a, MethodsForEquations.GaussMethods.GType type) {
        this(a);
        this.type = type;
    }

    public GaussMethod(Double[][] a) {
        super(a);
    }

    public GaussMethod(double[][] a) {
        super(a);
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

    public void printInfo(){
        if (x == null) this.Invoke(true);
        getErrorRate(true);
    }
}
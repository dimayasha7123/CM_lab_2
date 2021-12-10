/**
 * |_|>
 * |_|>    Created by Dimyasha on 03.12.2021
 * |_|>
 */

package MethodsForEquations.LUMethods;

import MethodsForEquations.AbstractMethod;
import static MethodsForEquations.utils.matrixToString;

public class LUMethod extends AbstractMethod {
    //матрицы BC из LU разложения
    protected double[][] Blu;
    protected double[][] Clu;

    //вектор Y неизвестных
    protected double[] y;

    public LUMethod(Double[][] a) {
        super(a);
    }

    public LUMethod(double[][] a) {
        super(a);
    }

    public double[] Invoke(boolean print) {
        y = new double[n];
        Blu = new double[n][n];
        Clu = new double[n][n];

        //считаем матрицы B и C
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

        //считаем y
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

        int digits = 3;
        if (print) {
            System.out.println(matrixToString(Blu, digits));
            System.out.println(matrixToString(Clu, digits));
            System.out.println(matrixToString(y, digits));
            System.out.println(matrixToString(x, digits));
        }
        return x;
    }

    public double determinant(){
        if (x == null) this.Invoke(true);
        double det = 1;
        for (int i = 0; i < n; ++i) det *= Blu[i][i];
        return det;
    }

    public double[][] inverseMatrix(boolean print) {
        if (x == null) this.Invoke(true);

        int digits = 6;

        double[][] E = new double[n][n];
        double[][] inv = new double[n][n];
        y = new double[n];
        for (int k = 0; k < n; ++k) {
            E[k][k] = 1.0;

            for (int i = 0; i < n; ++i) {
                y[i] = E[k][i];
                for (int j = 0; j < i; ++j) {
                    y[i] -= Blu[i][j] * y[j];
                }
                y[i] /= Blu[i][i];
            }

            if (print) {
                System.out.println("E" + k);
                System.out.println(matrixToString(y, digits));
            }

            //обратный ход
            x = new double[n];
            for (int i = n - 1; i >= 0; --i) {
                x[i] = y[i];
                for (int j = n - 1; j > i; --j) {
                    x[i] -= Clu[i][j] * x[j];
                }
            }

            System.arraycopy(x, 0, inv[k], 0, n);
        }

        double[][] tmp = new double[n][n];

        if (print) {
            for (int i = 0; i < n; ++i) {
                System.out.println("X" + i);
                System.out.println(matrixToString(inv[i], digits));
            }
        }

        //транспонируем матрицу
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                tmp[i][j] = inv[j][i];
            }
        }
        inv = tmp;

        if (print) {
            System.out.println("Inverse matrix");
            System.out.println(matrixToString(inv, digits));
            double[][] error = new double[n][n];
            System.out.println("Error");
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < n; ++j) {
                    error[i][j] = 0;
                    for (int k = 0; k < n; ++k) {
                        error[i][j] += aSrc[i][k] * inv[k][j];
                    }
                    System.out.printf("%10.3e ", error[i][j]);
                }
                System.out.println();
            }
        }
        return inv;
    }

    public void printInfo(){
        if (x == null) this.Invoke(true);
        getErrorRate(true);
        inverseMatrix(true);
    }
}

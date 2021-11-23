/**
 * |_|>
 * |_|>    Created by Dimyasha on 13.11.2021
 * |_|>
 */

package GaussMethods;

import GaussMethods.Abstract.AbstractGauss;

import java.util.Arrays;

public class GaussClassic extends AbstractGauss {

    public GaussClassic(double[][] a) {
        super(a);
    }

    @Override
    public double[] Invoke(boolean print) {
        for (int i = 0; i < n; ++i) {
            //опорный элемент
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

        if (print) System.out.println("ans = " + Arrays.toString(x));

        return x;
    }
}

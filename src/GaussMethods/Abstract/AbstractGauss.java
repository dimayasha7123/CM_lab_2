/**
 * |_|>
 * |_|>    Created by Dimyasha on 12.11.2021
 * |_|>
 */

package GaussMethods.Abstract;


public abstract class AbstractGauss {
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

    public AbstractGauss(double[][] a) {
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
}

/**
 * |_|>
 * |_|>    Created by Dimyasha on 10.12.2021
 * |_|>
 */

package MethodsForEquations;

import java.text.DecimalFormat;

public class utils {
    public static double[][] unboxDoubleTwoDemArray(Double[][] a) {
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

    protected static int getLen(double x, int digits) {
        DecimalFormat df = new DecimalFormat("#." + "#".repeat(digits));
        return df.format(x).length();
    }

    protected static int getLen(int x) {
        return ((Integer) x).toString().length();
    }

    public static String xMatrix(int[] pos) {
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

    public static String matrixToString(double[] a, int digits) {
        double[][] b = new double[a.length][1];
        for (int i = 0; i < b.length; ++i) {
            b[i][0] = a[i];
        }
        return matrixToString(b, digits);
    }

    public static String matrixToString(double[][] a, int digits) {
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

}

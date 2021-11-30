package CM_lab_2;


import GaussMethods.GaussMethod;

public class Main {

    public static void  main(String[] args) {
        double[][] a = {
                {2, 1, -1, 1, 2.7},
                {0.4, 0.5, 4, -8.5, 21.9},
                {0.3, -1, 1, 5.2, -3.9},
                {1, 0.2, 2.5, -1, 9.9}
        };

        double[][] b = {
                {2.34, -4.21, -11.61, 14.41},
                {8.04, 5.22, 0.27, -6.44},
                {3.92, -7.99, 8.37, 55.56}
        };

        double[][] c = {
                {1, 10, -5, 1},
                {1.01, 9.99, -5.01, 2},
                {0.99, 1.01, -4.99, 3}
        };
        new GaussMethod(a).printInfo();
    }
}

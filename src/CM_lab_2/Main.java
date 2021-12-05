package CM_lab_2;


import GaussMethods.GType;
import GaussMethods.GaussMethod;
import LUMethods.LUMethod;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        double[][] data = new double[][]{
                {0.12, -1.0, 0.32, -0.18, 0.72},
                {0.08, -0.12, -0.77, 0.32, 0.58},
                {0.25, 0.02, 0.14, -1.0, -1.56},
                {-0.77, -0.14, 0.06, -0.12, -1.21}
        };
        new LUMethod(data).Invoke();
        System.out.println();
        System.out.println();
        new GaussMethod(data, GType.BY_FIRST).printInfo();
        System.out.println("======================================================================");
        new GaussMethod(data, GType.BY_LINE).printInfo();
        System.out.println("======================================================================");
        new GaussMethod(data, GType.BY_COLUMN).printInfo();
        System.out.println("======================================================================");
        new GaussMethod(data, GType.BY_ALL).printInfo();

    }
}

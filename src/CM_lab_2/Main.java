package CM_lab_2;


import MethodsForEquations.GaussMethods.GType;
import MethodsForEquations.GaussMethods.GaussMethod;
import MethodsForEquations.LUMethods.LUMethod;

public class Main {

    public static void main(String[] args) {
        double[][] data = new double[][]{
                {0.12, -1.0, 0.32, -0.18, 0.72},
                {0.08, -0.12, -0.77, 0.32, 0.58},
                {0.25, 0.02, 0.14, -1.0, -1.56},
                {-0.77, -0.14, 0.06, -0.12, -1.21}
        };

        double[][] denData = new double[][]{
                {-83.0, 27.0, -13.0, -11.0, 142.0},
                {5.0, -68.0, 13.0, 24.0, 26.0},
                {9.0, 54.0, 127.0, 36.0, 23.0},
                {13.0, 27.0, 34.0, 156.0, 49.0}
        };

        new LUMethod(data).printInfo();

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

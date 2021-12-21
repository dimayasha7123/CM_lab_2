package CM_lab_2;


import MethodsForEquations.GaussMethods.GType;
import MethodsForEquations.GaussMethods.GaussMethod;
import MethodsForEquations.IterativeMethods.IType;
import MethodsForEquations.IterativeMethods.IterativeMethod;
import MethodsForEquations.LUMethods.LUMethod;
import MethodsForEquations.TridiagRunMethod;
import UTFE.TableOutput.Table;

public class Main {

    public static void main(String[] args) {
        double[][] data = new double[][]{
                {0.12, -1.0, 0.32, -0.18, 0.72},
                {0.08, -0.12, -0.77, 0.32, 0.58},
                {0.25, 0.02, 0.14, -1.0, -1.56},
                {-0.77, -0.14, 0.06, -0.12, -1.21}
        };

        double[][] testData = new double[][]{
                {2, 1, 0, 0, 0, 3},
                {3, 7, 3, 0, 0, 13},
                {0, 2, 6, 3, 0, 11},
                {0, 0, 1, 2, 1, 4},
                {0, 0, 0, 1, 2, 3}
        };

        Table.SetDecimalPlaces(15);
        double[][] herData = new double[][]{
                {2.0, 0.2, -1.0, 0.0, 2.7},
                {0.4, -8.5, 0.5, 4.0, 21.9},
                {0.3, -1.0, 5.2, 1.0, -3.9},
                {1.0, 0.2, -1.0, 2.5, 9.9}
        };
        new IterativeMethod(herData, IType.GAUSS_SEIDEL, Math.pow(10, -6)).printInfo();


        /*new LUMethod(data).printInfo();

        System.out.println();
        System.out.println();
        new GaussMethod(data, GType.BY_FIRST).printInfo();
        System.out.println("======================================================================");
        new GaussMethod(data, GType.BY_LINE).printInfo();
        System.out.println("======================================================================");
        new GaussMethod(data, GType.BY_COLUMN).printInfo();
        System.out.println("======================================================================");
        new GaussMethod(data, GType.BY_ALL).printInfo();*/


/*
        //метод трехдиагольной прогонки
        double[][] tridiagData = new double[][]{
                {0.809, 0.588, 0, 0, 0, 4.398},
                {0.228, 0.309, 0.351, 0, 0, 5.027},
                {0, 0.095, -2.309, 0.58, 0, 5.655},
                {0, 0, 1.885, -0.809, 0.253, 6.283},
                {0, 0, 0, -0.4, -1, 3.142}
        };
        new TridiagRunMethod(tridiagData).printInfo();*/
    }
}

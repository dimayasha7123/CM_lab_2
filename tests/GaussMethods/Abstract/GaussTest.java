package GaussMethods.Abstract;


import GaussMethods.GType;
import GaussMethods.GaussMethod;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GaussTest {

    @Test
    void invoke() {
        ArrayList<Double[][]> data = new ArrayList<>();
        data.add(new Double[][] {
                {2.0, 1.0, -1.0, 1.0, 2.7},
                {0.4, 0.5, 4.0, -8.5, 21.9},
                {0.3, -1.0, 1.0, 5.2, -3.9},
                {1.0, 0.2, 2.5, -1.0, 9.9}
        });
        data.add(new Double[][] {
                {2.34, -4.21, -11.61, 14.41},
                {8.04, 5.22, 0.27, -6.44},
                {3.92, -7.99, 8.37, 55.56}
        });
        data.add(new Double[][] {
                {1.0, 10.0, -5.0, 1.0},
                {1.01, 9.99, -5.01, 2.0},
                {0.99, 1.01, -4.99, 3.0}
        });

        data.forEach((x) -> {
            new GaussMethod(x).printInfo();
            System.out.println("======================================================================");
        });


        assertEquals(1, 1);
    }

    @Test
    void secTest() {
        Double[][] data = new Double[][] {
                {2.0, 1.0, -1.0, 1.0, 2.7},
                {0.4, 0.5, 4.0, -8.5, 21.9},
                {0.3, -1.0, 1.0, 5.2, -3.9},
                {1.0, 0.2, 2.5, -1.0, 9.9}
        };
        new GaussMethod(data, GType.BY_FIRST).printInfo();
        System.out.println("======================================================================");
        new GaussMethod(data, GType.BY_LINE).printInfo();
        System.out.println("======================================================================");
        new GaussMethod(data, GType.BY_COLUMN).printInfo();
        System.out.println("======================================================================");
        new GaussMethod(data, GType.BY_ALL).printInfo();


        assertEquals(1, 1);
    }
}
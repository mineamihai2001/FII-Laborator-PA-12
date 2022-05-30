import Classes.Test1;
import Solver.Solver;

import java.io.File;
import java.net.MalformedURLException;

public class Main {

    public static void main(String[] args) throws MalformedURLException {
        Solver solver = new Solver();
        ClassLoader classLoader = Test1.class.getClassLoader();
        File file = new File("D:\\Informatica\\Anul_2\\semestrul_2\\Laboratoare\\PA\\Laborator-PA-12\\src\\main\\java\\Classes");
        try {
            Class aClass = classLoader.loadClass("Classes.Test1");
            solver.info(aClass);
            solver.invoke(aClass);
            solver.invoke2(aClass);
            System.out.println("-----EXPLORE-----");
            solver.explore(file);
            solver.statistics();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
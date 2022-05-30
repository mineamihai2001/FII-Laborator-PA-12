package Solver;

import Test.Test;

import java.io.File;
import java.lang.reflect.*;
import java.util.Objects;


public class Solver {

    private int failed, passed, found;

    public Solver(){
        this.failed = this.passed = this.found = 0;
    }

    // Compulsory (var, methods, constructors)
    public void info(Class<?> clasa){
        System.out.println("\n------INFO------");
        if(clasa.getPackage() == null) System.out.println("\nClasa nu are pachet");
        else System.out.println("1.Package -> " + clasa.getPackage().getName());
        if(clasa.getDeclaredFields().length != 0){
            System.out.println("2.Variabile: ");
            for(Field field : clasa.getDeclaredFields())
                System.out.println(field.toString());
        }
        if(clasa.getDeclaredConstructors().length != 0){
            System.out.println("3.Constructori: ");
            for(Constructor<?> constructor : clasa.getDeclaredConstructors())
                System.out.print(constructor.toString());
            System.out.println();
        }
        if(clasa.getDeclaredMethods().length != 0){
            System.out.println("4.Metode: ");
            for(Method method : clasa.getDeclaredMethods())
                System.out.println(method.toString());
            System.out.println();
        }
    }

    // Static methods with no arguments
    public void invoke(Class<?> clasa){
        System.out.println("\n-----INVOKE-----");
        for(Method method : clasa.getDeclaredMethods()){
            if(method.isAnnotationPresent(Test.class)){
                int modifiers = method.getModifiers();
                if(Modifier.isStatic(modifiers)){
                    Parameter[] parameters = method.getParameters();
                    if(parameters.length == 0){
                        try{
                            this.found++;
                            System.out.println(method.getName());
                            method.invoke(null);
                            this.passed++;
                        }
                        catch (IllegalAccessException illegalAccessException){
                            illegalAccessException.printStackTrace();
                        }
                        catch (InvocationTargetException invocationTargetException){
                            this.failed++;
                            System.out.println("Error invoke " + invocationTargetException.getCause());
                        }
                    }
                }
            }
        }
    }

    // Methods (static/ not-static) with @Test
    public void invoke2(Class<?> clasa){
        System.out.println("-----INVOKE2-----");
        for(Method method : clasa.getDeclaredMethods()){
            if(method.isAnnotationPresent(Test.class)){
                Object[] objects = new Object[1];
                objects[0] = (int) 99;
                try{
                    this.found++;
                    System.out.println(method.getName());
                    method.invoke(clasa.getDeclaredConstructor().newInstance(), objects);
                    this.passed++;
                }
                catch (IllegalAccessException | InstantiationException | NoSuchMethodException illegalAccessException){
                    illegalAccessException.printStackTrace();
                }
                catch (InvocationTargetException invocationTargetException){
                    System.out.println("Error invoke " + invocationTargetException.getCause());
                    this.failed++;
                }
            }
        }
    }

    // Explore the folder recursively
    public void explore(File file) throws ClassNotFoundException {
        if(file.exists()){
            if(file.isDirectory()){
                for(File file1 : Objects.requireNonNull(file.listFiles()))
                    explore(file1);
            }
            else{
                if(file.getName().endsWith(".java")){
                    Class<?> newInstance = Class.forName("Classes.Test1");
                    if(newInstance.isAnnotationPresent(Test.class)){
                        int modifiers = newInstance.getModifiers();
                        if(Modifier.isPublic(modifiers)){
                            invoke2(newInstance);
                        }
                    }
                }
            }
        }
    }

    // Statistics
    public void statistics(){
        System.out.println("\n-----STATISTICS-----");
        System.out.println("Total tests: " + found);
        System.out.println("Passed tests: " + passed);
        System.out.println("Failed tests: " + failed);
    }

}
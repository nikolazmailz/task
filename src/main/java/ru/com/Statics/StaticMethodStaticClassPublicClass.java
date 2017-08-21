package ru.com.Statics;

/**
 * Created by nikk on 19.07.2017.
 */
public class StaticMethodStaticClassPublicClass {

    private static String s;

    StaticMethodStaticClassPublicClass(){
        System.out.println("empty constructor");
    }

    StaticMethodStaticClassPublicClass(String s){
        this.s = s;
        System.out.println(s);
    }

    public String getS(){
        return s;
    }


    public static class StaticClass{

        public String StaticMethod(){
            //StaticMethodStaticClassPublicClass sm = new StaticMethodStaticClassPublicClass();
            return s;
        }
    }
}

package ru.com.Statics;

/**
 * Created by nikk on 19.07.2017.
 */
public class Main {

    public static void main(String[] args) {

        StaticMethodStaticClassPublicClass.StaticClass staticMethodStaticClassPublicClass = new StaticMethodStaticClassPublicClass.StaticClass();

//        System.out.println(staticMethodStaticClassPublicClass.StaticMethod());
//
//        System.out.println(StaticMethodStaticClassPublicClass.StaticClass.StaticMethod());

        System.out.println(staticMethodStaticClassPublicClass.StaticMethod());
        System.out.println(StaticMethodStaticClassPublicClass.StaticClass.class.getClass());
        StaticMethodStaticClassPublicClass sm = new StaticMethodStaticClassPublicClass("text");
        StaticMethodStaticClassPublicClass sm2 = new StaticMethodStaticClassPublicClass();
        //sm.setS("sss");
        System.out.println(sm2.getS());
        System.out.println(sm.getS());
    }

}

package ru.com.Statics;

/**
 * Created by nikk on 19.07.2017.
 */
public class Sttc {

    static class X{
        static String str="String static";
    }
    public static void main(String args[])
    {
        X.str = "Inside Class Example1";
        System.out.println(X.str);
    }

}

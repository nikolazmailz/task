package ru.com.AbstractClassImplementsInterface;

/**
 * Created by nikk on 19.07.2017.
 */
public class Realiz extends AbstrctAndImpl {


    @Override
    public String walk() {
        return "Meow";
    }

    @Override
    String MakeNoise() {
        return "cat is walking";
    }


}

package ru.com.AbstractClassImplementsInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikk on 19.07.2017.
 */
public class MainClass {

    public static void main(String[] args) {

        AbstrctAndImpl realz[] = new AbstrctAndImpl[2]; // при объявлении массива всегда нужно указывать размер

        List<AbstrctAndImpl> realzList = new ArrayList<>();

        realzList.add(new Realiz());
        realzList.add(new RealizTwo());

        for(AbstrctAndImpl abstrctAnd : realzList){
            System.out.println(abstrctAnd.walk());
            System.out.println(abstrctAnd.MakeNoise());
        }
    }

}

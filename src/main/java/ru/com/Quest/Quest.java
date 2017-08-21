package ru.com.Quest;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Created by nikk on 06.08.2017.
 */
public class Quest {

    public static void main(String[] args) {

        String s = " сапог с ф а сарай сапог арбуз сарай арбуз болт бокс биржа";

        String[] str = s.trim().split(" ");
        Arrays.sort(str);
        TreeMap<String, ArrayList<String>> tMap = new TreeMap<>();

        for (String retval : str) {

            if(tMap.get(retval.substring(0, 1)) == null) {
                tMap.put(retval.substring(0, 1), new ArrayList<String>());
            }
            tMap.get(retval.substring(0, 1)).add(retval);
        }

        System.out.println(tMap);


        File[]fList;
        File F = new File("F:\\WebServer_\\medical");

        fList = F.listFiles();

        for(int i=0; i<fList.length; i++)
        {
            //Нужны только папки в место isFile() пишим isDirectory()
            if(fList[i].isFile())
                System.out.println("<a href='"+fList[i].getName()+"' target='_blank'>"+fList[i].getName()+"</a><br><br>");
        }

    }
}

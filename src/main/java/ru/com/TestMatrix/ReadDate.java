package ru.com.TestMatrix;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by nikk on 20.07.2017.
 */
public class ReadDate {

    static private java.io.BufferedReader jin = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));

    static Calendar ReadData() {
        Calendar cal = new GregorianCalendar();
        try {

            String string = jin.readLine();
            DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            Date date = format.parse(string);
            cal.setTime(date);

        } catch (ParseException e) {
            System.out.print("Введены некорректные данные, повторите ввод!\n");
            ReadData();
        } catch (IOException e) {
            System.out.print("Введены некорректные данные, повторите ввод!\n");
            ReadData();
        }
        return cal;
    }

}

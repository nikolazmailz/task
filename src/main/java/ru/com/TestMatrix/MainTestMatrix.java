package ru.com.TestMatrix;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by nikk on 20.07.2017.
 */
public class MainTestMatrix {

    public static void main(String[] args) {

        DateFormat ddMMyyyy = new SimpleDateFormat("dd.MM.yyyy");

        //получаю список часов в сутках
        ArrayList<String> hoursInDay = InitialDate.getHoursInDay();
        //получаю исходные данные
        ArrayList<String> initialDate = InitialDate.getInitialDate();
        //после чего преорбразаую данные в map
        Map<Date, Date> mapDate = getMapInitialDate(initialDate);

        //забираю введенный период
        System.out.println("Введить начало периода");
        Calendar start = ReadDate.ReadData();
        System.out.println("Введить конец периода");
        Calendar finish = ReadDate.ReadData();

        //расчитываю количество дней
        long days = TimeUnit.MILLISECONDS.toDays(finish.getTimeInMillis() - start.getTimeInMillis());

        //заполняю данные для вывода оси Y
        Date dateStart = start.getTime();
        ArrayList<Date> period = new ArrayList<>();
        for (int i = 0; i <= days; i++) {
            period.add(addDays(dateStart, i));
        }

        // получаю размер матрицы и создаю пустую матрицу
        int sizeY = (int) days + 1;
        int sizeX = hoursInDay.size();
        String[][] emptyMatrix = new String[sizeY][sizeX];

        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                emptyMatrix[i][j] = " -";
            }
        }

        //получаю даты которые попали в веденный период
        Map<Date, Date> periodIsTrue = new LinkedHashMap<>();

        for (Map.Entry<Date, Date> entry : mapDate.entrySet()) {
            Date key = entry.getKey();
            Date value = entry.getValue();
            if (key.after(start.getTime()) && key.before(finish.getTime())) {
                periodIsTrue.put(key, value);
            }
        }


        Calendar calKey = new GregorianCalendar();
        Calendar calValue = new GregorianCalendar();

        for (Map.Entry<Date, Date> entry : periodIsTrue.entrySet()) {

            Date key = entry.getKey();
            Date value = entry.getValue();

            calKey.setTime(key);
            calValue.setTime(value);

            //ищу координты матрицы
            int x = key.getHours();
            int y = -1;

            for (int i = 0; i < period.size(); i++) {
                String axisY = ddMMyyyy.format(period.get(i));
                String axisY2 = ddMMyyyy.format(key);

                if (axisY.equals(axisY2)) {
                    y = i;
                }
            }

            //если найденна координата Y
            if (y != -1) {

                //вычисляю количество часов
                long differenceHours = TimeUnit.MILLISECONDS.toHours(calValue.getTimeInMillis() - calKey.getTimeInMillis());

                //заполняю пустую матрицу данными
                if (" -".equals(emptyMatrix[y][x])) {
                    if (differenceHours < 10) {
                        emptyMatrix[y][x] = " " + differenceHours;
                    } else {
                        emptyMatrix[y][x] = "" + differenceHours;
                    }
                } else {

                    Integer after = Integer.parseInt(emptyMatrix[y][x]);
                    Integer before = Integer.parseInt(String.valueOf(differenceHours));

                    if (before > after) {
                        if (differenceHours < 10) {
                            emptyMatrix[y][x] = " " + differenceHours;
                        } else {
                            emptyMatrix[y][x] = "" + differenceHours;
                        }
                    }
                }
            }
        }


        //вывожу Матрицу на экран
        System.out.print("----------");
        for (String hours : hoursInDay) {
            System.out.print(" :: " + hours);
        }
        for (int i = 0; i < sizeY; i++) {
            String axisY = ddMMyyyy.format(period.get(i));
            System.out.print("\n" + axisY);
            for (int j = 0; j < sizeX; j++) {
                System.out.print(" :: " + emptyMatrix[i][j]);
            }
        }

        // 1.01.2017
        // 1.02.2017
    }


    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    private static Map<Date, Date> getMapInitialDate(ArrayList<String> initialDate) {

        DateFormat format = new SimpleDateFormat("dd.MM.yyyy HH");

        Map<Date, Date> mapID = new LinkedHashMap<>();

        for (String obj : initialDate) {

            String[] str = obj.split("::");

            try {
                Date dateS = format.parse(str[0]);
                Date dateF = format.parse(str[1]);
                mapID.put(dateS, dateF);
            } catch (ParseException e) {
                System.out.println("Даты не корректны");
            }

        }

        return mapID;
    }

}

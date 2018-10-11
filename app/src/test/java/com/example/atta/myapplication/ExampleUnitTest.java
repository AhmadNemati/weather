package com.example.atta.myapplication;

import com.example.atta.myapplication.calendar.PersianCalendar;

import org.junit.Test;

import java.util.Calendar;
import java.util.TimeZone;

import static org.junit.Assert.*;

/**
 * WeatherModel local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    PersianCalendar persianCalendar;
    @Test
    public void addition_isCorrect() {
        persianCalendar=new PersianCalendar();
        persianCalendar.setTimeZone(TimeZone.getDefault());
        persianCalendar.setTimeInMillis(1539195268218L);


        System.out.println(persianCalendar.getPersianLongDate());

      //  persianCalendar.set(persianCalendar.get(Calendar.YEAR),persianCalendar.get(Calendar.MONTH),persianCalendar.get(Calendar.DAY_OF_MONTH));
       // System.out.printf(String.valueOf(persianCalendar.getPersianYear()));
      //  assertEquals(4, 2 + 2);
    }
}
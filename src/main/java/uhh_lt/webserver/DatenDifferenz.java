package uhh_lt.webserver;

import java.util.GregorianCalendar;

public class DatenDifferenz {

    public static void main(String[] args) {

        GregorianCalendar adate = new GregorianCalendar();
        GregorianCalendar qdate = new GregorianCalendar();
        printDifference(adate, qdate);
    }

    private static void printDifference(GregorianCalendar adate, GregorianCalendar qdate)
    {

        long difference = adate.getTimeInMillis() - qdate.getTimeInMillis();


        /**int days = (int)(difference / (1000 * 60 * 60 * 24));
         int hours = (int)(difference / (1000 * 60 * 60) % 24);
         int minutes = (int)(difference / (1000 * 60) % 60);
         int seconds = (int)(difference / 1000 % 60);
         int millis = (int)(difference % 1000);
         System.out.println("Difference: " +
         days + " days, " +
         hours + " hours, " +
         minutes + " minutes, " +
         seconds + " seconds and " +
         millis + " milliseonds");*/
    }
}

package uhh_lt.webserver;

import java.text.ParseException;
import java.util.GregorianCalendar;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DatenDifferenz {



    public static void main(String[] args) {

        GregorianCalendar adate = new GregorianCalendar();
        GregorianCalendar qdate = new GregorianCalendar();
        printDifference(adate, qdate);

        Differenz("2019-02-20 16:29:03", "2019-02-20 17:23:31");
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

    public static void Differenz(String aDate, String tDate)
    {
        try {
            //String sDate1 = "31/12/1998";
            //String sDate2 = "31/12/1999";
            Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(aDate);  //2019-02-20 16:29:03
            Date date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(tDate);

            long diffInMillies = Math.abs(date2.getTime() - date1.getTime());
            long diff = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);

            //System.out.println(sDate1 + "\t" + date1);
            System.out.println(diff);
        }

        catch (ParseException e)
        {
            e.printStackTrace();
        }


    }
}

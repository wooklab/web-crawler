package dev.wooklab.crawl.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateHandler {
    private static final Logger logger = LoggerFactory.getLogger(DateHandler.class);

    private DateHandler() {
        throw new AssertionError();
    }

    /**
     * Method to get the current time.<br/>
     *
     * @param dateFormat - e.g. yyyy, yyyyMM, yyyyMMdd, yyyyMMddHHmmss
     * @return Current Time
     * @author InWook Hwang
     */
    public static String getRealTime(String dateFormat) {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();

        return new SimpleDateFormat(dateFormat).format(date);
    }


    /**
     * Method to get the number of days difference between two date.<br/>
     *
     * @param beginDate
     * @param endDate
     * @param inputDateFormat
     * @return days difference between two date
     */
    public static int getDiffDate(String beginDate, String endDate, String inputDateFormat) {
        Date begin  = strDateToDate(beginDate, inputDateFormat);
        Date end    = strDateToDate(endDate, inputDateFormat);

        long diff       = end.getTime() - begin.getTime();
        long diffDays   = diff / (24 *  60 * 60 * 1000);

        return (int) diffDays;
    }

    /**
     * Method to convert String Date to Date.<br/>
     *
     * @param strDate
     * @param inputDateFormat
     * @return Date
     */
    public static Date strDateToDate(String strDate, String inputDateFormat) {
        DateFormat  dateFormat  = new SimpleDateFormat(inputDateFormat);
        Date        date = null;
        try {
            date = dateFormat.parse(strDate);
        } catch (ParseException parseException) {
            logger.error("Parse Error in strDateToDate method", parseException);
        }
        return date;
    }

    /**
     * Method to convert Date to String Date<br/>
     * @param date
     * @param outputDateFormat
     * @return
     */
    public static String dateToStrDate(Date date, String outputDateFormat) {
        DateFormat dateFormat = new SimpleDateFormat(outputDateFormat);
        return dateFormat.format(date);
    }
}


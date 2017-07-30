package com.universe.softplat.scheduler.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class EAPDataFormat {
    public static final String PATTERN_DATE = "yyyy-MM-dd";
    public static final String PATTERN_TIME = "HH:mm:ss";
    public static final String PATTERN_TIMESTAMP = "yyyy-MM-dd HH:mm:ss";

    private static SimpleDateFormat dateFormat = new SimpleDateFormat();
    
    private static DecimalFormat decFormat = new DecimalFormat("#####################.#########");


    public static boolean isEmpty(String value) {
        return value==null || "".equals(value);
    }

    public static Integer toInt(String value) {
        if(isEmpty(value))
            return null;
        return new Integer(value);
    }

    public static Long toLong(String value) {
        if(isEmpty(value))
            return null;
        return new Long(value);
    }

    public static Float toFloat(String value) {
        if(isEmpty(value))
            return null;
        return new Float(value);
    }

    public static Double toDouble(String value) {
        if(isEmpty(value))
            return null;
        return new Double(value);
    }


    public static Date toDate(String value) {
        return toDate(PATTERN_DATE, value);
    }

    public static Date toTime(String value) {
        return toDate(PATTERN_TIME, value);
    }

    public static Timestamp toTimestamp(String value) {
        return toTimestamp(PATTERN_TIMESTAMP, value);
    }
    
    public static Timestamp toTimestamp(String pattern, String value) {
        if(isEmpty(value))
            return null;
        dateFormat.applyPattern(pattern);
        java.util.Date date = null;
        try {
            date = dateFormat.parse(value);
        } catch(ParseException ex) {
            return null;
        }
        return new Timestamp(date.getTime());
    }

    public static Date toDate(String pattern, String value) {
        if(isEmpty(value))
            return null;
        dateFormat.applyPattern(pattern);
        java.util.Date date = null;
        try {
            date = dateFormat.parse(value);
        } catch(ParseException ex) {
            return null;
        }
        return new Date(date.getTime());
    }




    public static String toString(String value) {
        return isEmpty(value)?"":value;
    }

    public static String toString(int value) {
        return String.valueOf(value);
    }

    public static String toString(long value) {
        return String.valueOf(value);
    }

    public static String toString(float value) {
        return String.valueOf(value);
    }

    public static String toString(double value) {
    	return decFormat.format(value);
        //return String.valueOf(value);
    }

    public static String toString(Date value) {
        if(value == null)
            return null;
        dateFormat.applyPattern(PATTERN_DATE);
        return dateFormat.format(value);
    }
    
    public static String toString(Timestamp value) {
        if(value == null)
            return null;
        dateFormat.applyPattern(PATTERN_TIMESTAMP);
        return dateFormat.format(value);
    }




    public static String parseString(String value, String defaultValue) {
        if(!isEmpty(value))
            return value;
        if(!isEmpty(defaultValue))
            return defaultValue;
        return "";
    }

    public static int parseString(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch(Exception e) {
        }
        return defaultValue;
    }

    public static long parseString(String value, long defaultValue) {
        try {
            return Long.parseLong(value);
        } catch(Exception e) {
        }
        return defaultValue;
    }

    public static float parseString(String value, float defaultValue) {
        try {
            return Float.parseFloat(value);
        } catch(Exception e) {
        }
        return defaultValue;
    }

    public static double parseString(String value, double defaultValue) {
        try {
           return Double.parseDouble(value);
        
        } catch(Exception e) {
        }
        return defaultValue;
    }


    public static Date parseString(String value, Date defaultValue) {
        try {
            return toDate(value);
        } catch(Exception e) {
        }
        return defaultValue;
    }
    
    public static Timestamp parseString(String value, Timestamp defaultValue) {
        try {
            return toTimestamp(value);
        } catch(Exception e) {
        }
        return defaultValue;
    }

    public static int getStringRealLength(String str) {
        if(str == null)
            return 0;
        char c[] = str.toCharArray();
        int length = 0;
        for(int i=0; i<c.length; i++) {
            if(isHalf(c[i]))
              length += 1;
            else
              length += 2;
        }
        return length;
    }

    public static boolean isHalf(char c) {
        if (!(('\uFF61'<=c) && (c<='\uFF9F')) && !(('\u0020'<=c) && (c<='\u007E')))
            return false;
        return true;
    }



}

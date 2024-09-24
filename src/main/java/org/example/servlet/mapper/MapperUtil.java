package org.example.servlet.mapper;

import java.sql.Date;

public class MapperUtil {
    public static int parseInteger(String input) {

        Integer id = 0;
        if (input != null) {
            try {
                id = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                return id;
            }
        }
        return id;
    }

    public static float parseFloat(String input) {

        float id = 0;
        if (input != null) {
            try {
                id = Float.parseFloat(input);
            } catch (NumberFormatException e) {
                return id;
            }
        }
        return id;
    }

    public static Date parseDate(String input) {

        Date date  = null;
        if (input != null) {
            try {
                date = Date.valueOf(input);
            } catch (IllegalArgumentException e) {
                return date;
            }
        }
        return date;
    }
}

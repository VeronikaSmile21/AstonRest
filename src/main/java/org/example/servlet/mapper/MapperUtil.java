package org.example.servlet.mapper;

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
}

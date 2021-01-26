package Resources;

import HotelSystem.BookingsManager.Room;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validate {
    public static boolean isAlpha(String s) {
        return s != null && s.matches("^[a-zA-Z]*$");
    }

    public static boolean isJob(String job) {
        return job.equals("Manager") || job.equals("Receptionist") || job.equals("Engineer") || job.equals("HR") || job.equals("Cleaner") || job.equals("Waiter");
    }

    public static boolean isEmail(String email) {
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isNumber(String numS, String expected) {
        try {
            if (expected.equals("Integer")) {
                Integer.parseInt(numS);
            } else {
                Double.parseDouble(numS);
            }
            return true;
        } catch (NumberFormatException fe) {
            return false;
        }
    }

    public static boolean isAlphaNum(String s) {
        return s.matches("[A-Za-z0-9]+");
    }

    public static boolean isDate(String date) {
        String regex = "^\\d{4}/(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(date);
        return matcher.matches();
    }

    public static boolean isRoomType(String roomType) {
        return roomType.equals("Standard") || roomType.equals("Suite") || roomType.equals("Presidential");
    }

    public static boolean existsIn(Room[] rooms, int roomNum, int loop) {
        for (int i = 0; i < loop; i++) {
            if (rooms[i].getRoomNum() == roomNum) {
                return true;
            }
        }
        return false;
    }

    public static boolean isTime(String time) {
        String regex = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(time);
        return matcher.matches();
    }

    public static boolean isServiceType(String type) {
        return type.equals("Spa") || type.equals("Pool") || type.equals("Restaurant") || type.equals("Room Service");
    }
}

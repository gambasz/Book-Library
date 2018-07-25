package com.mbox;

import com.sun.istack.internal.NotNull;
import frontend.data.Course;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class controller {

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    public static List<String> getAllEdition() {
        String temp = "First;Second;Third;Fourth;Fifth;Sixth;Seventh;Eighth;Ninth;Tenth;" +
                "Eleventh;Twelfth;Thirteenth;Fourteenth;Fifteenth";
        String[] tempArray = temp.split(";");

        return Arrays.asList(tempArray);
    }

    public static List<String> getAllTypes() {
        String temp = "Book;Online Service;Website;";
        String[] tempArray = temp.split(";");

        return Arrays.asList(tempArray);
    }

    public static String stringAdjustment(String main, String modify) {

        int difference = main.length() - modify.length();

        if (difference >= 0) {
            StringBuilder modifyBuilder = new StringBuilder(modify);
            for (int i = 0; i < difference; i++)
                modifyBuilder.append(" ");
            modify = modifyBuilder.toString();


        }

        return modify;
    }

    public static String multipyStr(String string, int times) {
        String returned = "";
        for (int i = 0; i < times; i++) {
            returned += string;
        }
        return returned;
    }


    @NotNull
    public static String resourcesFormat(ArrayList<frontend.data.Resource> allResources, int max) {

        StringBuilder resFormated = new StringBuilder();
        ArrayList<Integer> ids = new ArrayList<Integer>();
//        ArrayList<String> editions = new ArrayList<String>();

        int counter = 0;
        for (frontend.data.Resource tempResource : allResources) {
            if (counter < max) {
                if (!ids.contains(tempResource.getID())) {
                    resFormated.append(tempResource.toString() + "\n");
                    ids.add(tempResource.getID());
                    if (counter < max - 1)
                        resFormated.append(multipyStr("-", tempResource.toString().length()) + "\n");
                }
            } else {
//                resFormated.deleteCharAt(resFormated.length()-1);
                break;
            }
            counter++;
        }
        resFormated.append("\n");

        return resFormated.toString();
    }


    @NotNull
    public static Semester findDefaultSemester() {
        Calendar c = Calendar.getInstance();
        String season;
        int year;

        season = getSeason(c.get(Calendar.MONTH));
        year = (c.get(Calendar.YEAR));
        System.out.println("Semesterhey: " + season + "year: " + year);
        Semester semester1 = new Semester(season, year, 0);
        semester1.setId(DBManager.getSemesterIDByName(season, Integer.toString(year)));

        System.out.println("Sem: " + semester1.getSeason() + " year:  " + semester1.getYear());
        return semester1;
    }

    private static String getSeason(int monthNumber) {
        String monthName = getMonthName(monthNumber);
        switch (monthName) {
            case "January":

            case "February":

            case "December":
                return ("Winter");

            case "March":

            case "April":

            case "May":
                return ("Spring");
            case "June":
                return ("Summer 1");

            case "July":
            case "August":
                return ("Summer 2");

            case "September":

            case "October":

            case "November":
                return ("Fall");

            default:
                return ("Invalid argument");
        }
    }

    private static String getMonthName(int monthNumber) {


        switch (monthNumber) {
            case 0:
                return "January";

            case 1:
                return "February";

            case 2:
                return "March";

            case 3:
                return "April";

            case 4:
                return "May";

            case 5:
                return "June";

            case 6:
                return "July";

            case 7:
                return "August";

            case 8:
                return "September";


            case 9:
                return "October";

            case 10:
                return "November";

            case 11:
                return "December";

            default:
                return ("Invalid argument");
        }


    }


    public static Course searchForCourse(Course course, ArrayList<Course> courseList) {
        Course result = new Course();
        int lastCourseID = 0;
        for (Course item : courseList) {

            if (item.getID() != lastCourseID) {

                if (course.getID() == item.getID()) {

                    if (course.getCommonID() == item.getCommonID()) {
                        return item;
                    }
                } else
                    lastCourseID = item.getID();

            }


        }


        return result;
    }

    public static String convertSeasonGUItoDB(String season) {
        season = season.toLowerCase();
        season = season.substring(0, 1).toUpperCase() + season.substring(1);
        if (season.equals("Summer_1")) {
            season = "Summer 1";
        }
        if (season.equals("Summer_2")) {
            season = "Summer 2";
        }
        return season;
    }

    public static String convertSeasonDBtoGUI(String season) {
        season = season.toUpperCase();
        if (season.equals("SUMMER 1") || season.equals("SUMMER 2"))
            season = season.replace(' ', '_');

        return season;
    }
}


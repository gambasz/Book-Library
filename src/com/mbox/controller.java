package com.mbox;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class controller {

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    public static List<String> getAllEdition(){
        String temp = "First;Second;Third;Fourth;Fifth;Sixth;Seventh;Eighth;Ninth;Tenth;" +
                "Eleventh;Twelfth;Thirteenth;Fourteenth;Fifteenth";
        String[] tempArray = temp.split(";");

        return Arrays.asList(tempArray);
    }

    public static List<String> getAllTypes(){
        String temp = "Book;Online Service;Website;";
        String[] tempArray = temp.split(";");

        return Arrays.asList(tempArray);
    }

    public static String stringAdjustment(String main, String modify){

        int difference = main.length() - modify.length();

        if(difference>=0){
            for(int i=0; i<difference; i++){
                modify+=" ";
            }

        }

        return modify;
    }

    public static String multipyStr(String string, int times){
        String returned="";
        for(int i=0; i<times; i++){
            returned+=string;
        }
        return returned;
    }



    @NotNull
    public static String resourcesFormat(ArrayList<frontend.data.Resource> allResources, int max){

        StringBuilder resFormated = new StringBuilder();
        ArrayList<Integer> ids = new ArrayList<Integer>();
//        ArrayList<String> editions = new ArrayList<String>();

        int counter=0;
        for (frontend.data.Resource tempResource : allResources){
            if(counter<max){
                if(!ids.contains(tempResource.getID()) ){
                    resFormated.append(tempResource.toString()+"\n");
                    ids.add(tempResource.getID());
                    if(counter<max-1)
                        resFormated.append(multipyStr("-",tempResource.toString().length()) + "\n");
                }
            }
            else{
//                resFormated.deleteCharAt(resFormated.length()-1);
                break;
            }
            counter++;
        }
        resFormated.append("\n");

        return resFormated.toString();
    }


    @NotNull
    public static String findDefaultSemester(){
        String semester="";
        semester = getSeason(Calendar.getInstance().MONTH);
        semester = semester.toUpperCase();
        return semester;
    }
        public static String getSeason(int monthNumber) {
            String monthName = getMonthName(monthNumber);
            switch (monthName) {
                case "January":
                    return "Winter";

                case "February":
                    return "Winter";

                case "December":
                    return ("Winter");

                case "March":
                    return ("Spring");

                case "April":
                    return ("Spring");

                case "May":
                    return ("Spring");

                case "June":
                    return ("Summer");

                case "July":
                    return ("Summer");

                case "August":
                    return ("Summer");

                case "September":
                    return ("Fall");

                case "October":
                    return ("Fall");

                case "November":
                    return ("Fall");

                default:
                    return ("Invalid argument");
            }
        }

        public static String getMonthName(int monthNumber){


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
    }


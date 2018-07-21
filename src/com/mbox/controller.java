package com.mbox;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
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
                    resFormated.append(tempResource.toString()+"\n"+
                            multipyStr("-",tempResource.toString().length()) + "\n");
                    ids.add(tempResource.getID());
//                    editions.add(tempResource.getEdition());
                }
            }
            else{
                resFormated.deleteCharAt(resFormated.length()-1);
                break;
            }
            counter++;
        }
//        resFormated.append("\n");

        return resFormated.toString();
    }

}

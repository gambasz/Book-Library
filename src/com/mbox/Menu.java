// CLI Menu to test DB functions.

package com.mbox;

import java.text.NumberFormat;
import java.util.Scanner;

public class Menu {

    Scanner kb = new Scanner(System.in);
    private boolean byebye;

    private void printHeader(){

        System.out.println("=========================\n");
        System.out.println("         Welcome         \n");
        System.out.println(" Please select an option \n");
        System.out.println("=========================\n");
    }

    private void printChoices(){

        System.out.println("1) Get a whole table");
        System.out.println("2) Find by table and ID");
        System.out.println("3) Does nothing");
        System.out.println("0) Exit");
    }

    public void runMenu(){

        try{

            DBManager.openConnection();

        }catch(Exception e){

            System.out.println("Something went wrong when trying to connect to the database.");
        }

        printHeader();

        while(!byebye){

            printChoices();
            
            int choice = getInput();
            performAction(choice);
        }

    }

    // Fix me
    private void performAction(int choice) {

        String table;

        switch(choice){

            case 1:
                System.out.print("Please enter a table: ");
                table = kb.nextLine();
                System.out.println();
                DBManager.getTable(table);
                break;

            case 2:
                System.out.print("Please enter a table: ");
                table = kb.nextLine();
                System.out.println();
                System.out.print("Please enter an ID: ");

                try{

                    int id = Integer.parseInt(kb.nextLine());
                    System.out.println(DBManager.findByID(table, id));

                } catch(Exception e){

                }
                break;
            case 3:
                System.out.println("You chose option 3");
                System.exit(0);
                break;
            case 0:
                System.out.println("Bye bye");
                DBManager.closeConnection();
                System.exit(0);
                break;
            default:
                System.out.println("Nani?");
                System.exit(0);
                break;
        }

    }

    private int getInput() {

        int choice = -1;

        do{
            System.out.print("Enter your choice: ");

            try{

                choice = Integer.parseInt(kb.nextLine());


            } catch (NumberFormatException e){

                System.out.println("Not a number, try again.");

            }

            if(choice < 0 || choice > 3){

                System.out.println("Out of range");
            }

        } while(choice < 0 || choice > 3);

        return choice;
    }

}

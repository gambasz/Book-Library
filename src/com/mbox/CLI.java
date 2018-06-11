package com.mbox;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.lang.String;
public class CLI {


    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        DBManager DB = new DBManager();
        DB.openConnection();


        System.out.println("Enter Help to know more.");

        while (true) {
            System.out.print(">> ");
            String input = scan.nextLine();

            String[] values = input.split(" -");
            if(input == null){
                continue;
            }

            String command = values[0];


            switch (command) {
                case "help":
                case "HELP":
                case "Help": {

                    showHelp();
                    break;
                }
                case "Insert":
                case "INSERT":
                case "insert": {

                    String table = values[1];
                    switch (table) {
                        case "PERSON":
                        case "Person":
                        case "person": {
                            try {

                                Person p = new Person(values[2],values[3],values[4]);
                                DB.executeNoReturnQuery(DB.qiPerson(p));
                                System.out.println("Added Person");
                            }catch(Exception e){
                                System.out.println("It must be Firstname Lastname Type");
                            }
                            break;
                        }
                        case "Resource":
                        case "RESOURCE":
                        case "resource": {
                            try {
                                Resource r = new Resource(values[2], values[3], values[4], values[5], Integer.valueOf(values[6]),
                                        Integer.valueOf(values[7]), values[8]);
                                DB.executeNoReturnQuery(DB.qiResource(r));
                                System.out.println("Added Resource");
                            }catch(Exception e){
                                System.out.println("It must be type title author isbn total current description");
                            }
                            break;
                        }
                        case "Course":
                        case "COURSE":
                        case "course": {
                            try {
                                Course c = new Course(values[2], values[3], values[4], values[5]);
                                DB.executeNoReturnQuery(DB.qiCourse(c));
                                System.out.println("Added Course");
                            }catch(Exception e){
                                System.out.println("It must be title crn description department");
                            }
                            break;
                        }
                        default:
                            System.out.println("There is no table like that.");
                    }
                    break;
                }
                case "SHOW":
                case "show":
                case "Show": {
                    String table = values[1];
                    switch (table) {
                        case "PERSON":
                        case "Person":
                        case "person": {

                            DBManager.getTablePerson();
                            break;
                        }

                        case "Resource":
                        case "RESOURCE":
                        case "resource": {
                            DBManager.getTableResources();
                            break;
                        }

                        case "Publisher":
                        case "PUBLISHER":
                        case "publisher": {
                            DBManager.getTablePublishers();
                            break;
                        }

                        default:
                            System.out.println("There is no table like that.");
                    }
                    break;
                }
                default:
                    System.out.println("Enter Help to know more");
            }

        }



    }
    public static void showHelp(){
        System.out.println("-------Instruction--------");
        System.out.println("To add to person: INSERT -Person -<Firstname> -<Lastname> -<Type>");
        System.out.println("To add to resource: INSERT -Resource -<Type> -<Title> -<Author> -<ISBN> -<totalAmount>"+
                "-<currentAmount> -<Description>");
        System.out.println("1. To add to publisher: INSERT -Publisher -<Title> -<contactInfo> -<description>");
        System.out.println("2. To see data in table: SHOW -<Name of table> ");
    }
}


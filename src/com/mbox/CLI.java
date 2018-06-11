/*package com.mbox;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.lang.String;
public class CLI {
    public static Statement st;
    public static Connection con;

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        DBManager.openConnection();
        System.out.println("Connection to the database has been established.");

        System.out.println("Enter Help to know more.");

        while (true) {
            System.out.print(">> ");
            String input = scan.nextLine();

            String[] values = input.split("\\s+");
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
                                //This line is testing if data is available
                                System.out.println(values[2]+ " " + values[3] + " " +values[4]);
                                Person p = new Person(values[2],values[3],values[4]);
                                p.addToDB();
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
                                r.addToDB();
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
                                c.addToDB();
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
                            try{
                                st = con.createStatement();
                                ResultSet rs = st.executeQuery("SELECT * FROM PERSON");
                                while (rs.next()) {
                                    System.out.println(String.valueOf(rs.getInt(1)) + " " + rs.getString(2)
                                            + " " + rs.getString(3) + " " + rs.getString(4)
                                            + " " + String.valueOf(rs.getInt(5)));
                                }
                            } catch(Exception e){
                                System.out.println("Wrong connection");
                            }
                            break;
                        }
                        case "Resource":
                        case "RESOURCE":
                        case "resource": {
                            try{
                                ResultSet rs = st.executeQuery("SELECT * FROM RESOURCES");
                                while (rs.next()) {
                                    System.out.println(String.valueOf(rs.getInt(1)) + "--" + rs.getString(2) +
                                            "--" + rs.getString(3) + "--" + rs.getString(4) + "--" + rs.getString(5) + "--" + String.valueOf(rs.getInt(6)) + " " +
                                            String.valueOf(rs.getInt(7)) + " " + rs.getString(8));
                                }}
                            catch(Exception e){
                                System.out.println("Wrong connection");
                            }
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
        System.out.println("1. To add: INSERT <Name of table> data seprated by space");
        System.out.println("2. To see data in table: SHOW <Name of table> ");
    }
}

*/
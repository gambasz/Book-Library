
package com.mbox;
import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;
import java.util.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.lang.String;
public class CLI {


    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        DBManager DB = new DBManager();
        DB.openConnection();
        System.out.println("MBox CLI Version 1.0");
        System.out.println("Enter help for a list of commends.\n");

        while (true) {
            System.out.print(">> ");
            String input = scan.nextLine();

            String[] values = input.split(" -");
            if (input == null) {
                continue;
            }

            String command = values[0];


            switch (command.toLowerCase()) {
                case "help": {

                    showHelp();
                    break;
                }
                case "insert": {

                        String table = values[1];
                        insertTable(table, values, DB);
                        break;
                }
                case "show": {
                        String table = values[1];
                        showTable(table);
                        break;
                }
                case "search": {
                        String table = values[1];
                        searchInTable(table, values);
                        break;
                }
                case "delete": {
                        String table = values[1];
                        deleteFromTable(table, values);
                        break;
                }
                case "clear": {
                        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                        break;
                }
                case "exit": {
                        System.exit(0);
                        break;
                }



                default:
                    System.out.println("Invalid input");
            }
        }
    }






    public static void showHelp(){
        System.out.println("-------------------------------------------------------------------Instruction"+
        "-------------------------------------------------------------");
        System.out.printf("%-100s%-50s\n","SYNTAX","EXPLANATION");
        System.out.printf("%-100s%-50s\n","INSERT -Person -<Firstname> -<lastname> -<type>","[Add to person]");
        System.out.printf("%-100s%-50s\n","INSERT -Resource -<Type> -<Title> -<Author> -<ISBN> -<totalAmount>"+
                " -<currentAmount> -<Description>","[Add to resource]");
        System.out.printf("%-100s%-50s\n","INSERT -Publisher -<Title> -<ContactInfo> -<Description>","[Add to publisher]");
        System.out.printf("%-100s%-50s\n","INSERT -Course -<Title> -<CRN> -<Description> -<Department>","[Add to course]");
        System.out.printf("%-100s%-50s\n","Show -<NameOfTable>","[Show a table]");
        System.out.printf("%-100s%-50s\n","Search -Person -<Firstname>","[Search by Firstname in Person table]");
        System.out.printf("%-100s%-50s\n","Search -Publisher -<Title>","[Search by Title in Publisher table]");
        System.out.printf("%-100s%-50s\n","Search -Resource -<Title>","[Search by Title in Resources table]");
        System.out.printf("%-100s%-50s\n","Search -Course -<Title>","[Search by Title in Courses table]");
        System.out.printf("%-100s%-50s\n","Delete -<Table>","[Delete by ID from that table]");
    }

    public static void showTable(String table) {
        switch (table.toLowerCase()) {
            case "person": {

                DBManager.printTablePerson();
                break;
            }

            case "resource": {
                DBManager.printTableResources();
                break;
            }

            case "publisher": {
                DBManager.printTablePublishers();
                break;
            }
            case "course": {
                DBManager.printTableCourses();
                break;
            }
            default: System.out.println("There is no table like that.");
        }
    }


    public static void insertTable(String table, String[] values,DBManager DB){
        switch(table.toLowerCase()){
            case"person":{
                try{

                     Person p=new Person(1, values[2],values[3],values[4]);
                     DB.executeNoReturnQuery(DB.insertPersonQuery(p));
                     System.out.println("Added Person");
                }catch(Exception e){
                    System.out.println("It must be Firstname Lastname Type");
                 }
                break;
            }

            case"resource":{
                try{
                    Resource r = new Resource(0,values[2],values[3],values[4],values[5],Integer.valueOf(values[6]),
                    Integer.valueOf(values[7]),values[8]);
                    DB.executeNoReturnQuery(DB.insertResourceQuery(r));
                    System.out.println("Added Resource");
                }catch(Exception e){
                    System.out.println("It must be type title author isbn total current description");
            }
                break;
        }

            case"course":{
                try{
                    Course c=new Course(0,values[2],values[3],values[4],values[5]);
                    DB.executeNoReturnQuery(DB.insertCourseQuery(c));
                    System.out.println("Added Course");
                }catch(Exception e){
                    System.out.println("It must be title crn description department");
            }
                break;
        }
            case"publisher":{
                try{
                    Publisher  pub =new Publisher(values[2],values[3],values[4]);
                    DB.executeNoReturnQuery(DB.insertPublisherQuery(pub));
                    System.out.println("Added Publisher");
                }catch(Exception e){
                    System.out.println("It must be title crn description department");
                }
                break;
            }
            default: System.out.println("There is no table like that.");


        }
    }

    //Search method
    public static void deleteFromTable(String table, String[] values){
        DBManager DB = new DBManager();
        Scanner scan = new Scanner(System.in);
        switch(table.toLowerCase()){
            case "person":{
                    DBManager.printTablePerson();
                    System.out.print("ID: ");
                    try{
                        int ID = scan.nextInt();
                        DB.executeNoReturnQuery("DELETE FROM PERSON WHERE ID = " + ID);
                        System.out.println("Deleted");

                    }catch(Exception e){
                        System.out.println("Invalid input");
                    }

                break;
            }
            case "publisher":{
                    DBManager.printTablePublishers();
                    System.out.print("ID: ");
                    try{
                        int ID = scan.nextInt();
                        DB.executeNoReturnQuery("DELETE FROM PUBLISHERS WHERE ID = " + ID);
                        System.out.println("Deleted");

                    }catch(Exception e){
                        System.out.println("Invalid input");
                    }

                break;
            }
            case "course": {
                    DBManager.printTableCourses();
                    System.out.print("ID: ");
                    try{
                        int ID = scan.nextInt();
                        DB.executeNoReturnQuery("DELETE FROM COURSECT WHERE ID = " + ID);
                        System.out.println("Deleted");

                    }catch(Exception e){
                        System.out.println("Invalid input");
                    }

                break;
            }
            case "resource":{
                    DBManager.printTableResources();
                    System.out.print("ID: ");
                    try{
                        int ID = scan.nextInt();
                        DB.executeNoReturnQuery("DELETE FROM RESOURCES WHERE ID = " + ID);
                        System.out.println("Deleted");

                    }catch(Exception e){
                        System.out.println("Invalid input");
                    }

                break;
            }
        }
    }


    //delete method use ID based on search method
    public static void searchInTable(String table, String[] values){
        DBManager DB = new DBManager();
        switch(table.toLowerCase()){
            case "person":{
                try {


                    String query = String.format("SELECT * FROM PERSON WHERE FIRSTNAME='%s'", values[2]);
                    ResultSet rs = DB.st.executeQuery(query);

                    while (rs.next()) {
                        System.out.println(rs.getInt(1) + " | " + rs.getString(2) + " | " +
                                rs.getString(3) + " | " + rs.getString(4));
                    }
                }catch(Exception e){
                    System.out.println("DATA not found");
                }
                break;
            }
            case "publisher":{
                try {
                    String query = String.format("SELECT * FROM PUBLISHERS WHERE TITLE='%s'", values[2]);
                    ResultSet rs = DB.st.executeQuery(query);

                    while (rs.next()) {
                        System.out.println(rs.getInt(1) + " | " + rs.getString(2) + " | " +
                                rs.getString(3)+" | "+rs.getString(4));
                    }
                }catch(Exception e){
                    System.out.println("DATA not found");
                }
                break;
            }
            case "course": {
                try {


                    String query = String.format("SELECT * FROM COURSECT WHERE TITLE='%s'", values[2]);
                    ResultSet rs = DB.st.executeQuery(query);

                    while (rs.next()) {
                        System.out.println(rs.getInt(1) + " | " + rs.getString(2) + " | " +
                                rs.getString(3) + " | " + rs.getString(4)+ " | "+
                                rs.getString(5));
                    }
                }catch(Exception e){
                    System.out.println("DATA not found");
                }
                break;
            }
            case "resource":{
                try {


                    String query = String.format("SELECT * FROM RESOURCES WHERE TITLE='%s'", values[2]);
                    ResultSet rs = DB.st.executeQuery(query);

                    while (rs.next()) {
                        System.out.println(rs.getInt(1) + " | " + rs.getString(2) + " | " +
                                rs.getString(3) + " | " + rs.getString(4) + " | " +
                                rs.getInt(5) + " | " + rs.getInt(6) +" | "+ rs.getString(7));
                    }
                }catch(Exception e){
                    System.out.println("DATA not found");
                }
                break;
            }
        }
    }


    public static void insertID(){
        Scanner scan = new Scanner(System.in);
        DBManager DB = new DBManager();
        System.out.println("Format: <CourseID> <PersonID> <ResourceID> <PublisherID> <SemesterID>");
        System.out.println("Enter <exit> to exit.");

        while(true) {
            System.out.println("Enter PersonID, CourseID, ResourceID, PublisherID: ");
            String input = scan.nextLine();
            if(!input.contains("exit")){
            String[] values = input.split(" ");

                DB.executeNoReturnQuery(String.format("INSERT INTO RELATION_COURSE_PERSON" +
                        " (COURSEID, PERSONID) VALUES ('%d', '%d')",Integer.parseInt(values[0]),Integer.parseInt(values[1])));
                DB.executeNoReturnQuery(String.format("INSERT INTO RELATION_COURSE_RESOURCES" +
                        " (COURSEID, RESOURCEID) VALUES ('%d', '%d')",Integer.parseInt(values[0]),Integer.parseInt(values[2])));
                DB.executeNoReturnQuery(String.format("INSERT INTO RELATION_COURSE_SEMESTER" +
                        " (COURSEID, SEMESTERID) VALUES ('%d', '%d')",Integer.parseInt(values[0]),Integer.parseInt(values[4])));
                DB.executeNoReturnQuery(String.format("INSERT INTO RELATION_PERSON_RESOURCES" +
                        " (PERSONID, RESOURCEID) VALUES ('%d', '%d')",Integer.parseInt(values[1]),Integer.parseInt(values[2])));
                DB.executeNoReturnQuery(String.format("INSERT INTO RELATION_PUBLISHER_RESOURCE" +
                        " (PUBLISHERID, RESOURCEID) VALUES ('%d', '%d')",Integer.parseInt(values[3]),Integer.parseInt(values[2])));
                System.out.println("Added ID");
            } else { break;}
        }
    }


    public static void readFromTable() {

        try {
            Scanner scan = new Scanner(System.in);
            DBManager DB = new DBManager();
            System.out.println("Enter Course ID: ");
            int courseID = scan.nextInt();
            int personID = 0;

            int[] cr = new int[20];
            int[] pr = new int[20];
            //get personID
            ResultSet rs;

            rs = DB.st.executeQuery("SELECT * FROM RELATION_COURSE_PERSON WHERE COURSEID = " + courseID);

            while (rs.next()) {
                personID = rs.getInt(2);
            }


            //get resourceID
            rs = DB.st.executeQuery("SELECT * FROM RELATION_COURSE_RESOURCES WHERE COURSEID = " + courseID);
            int i = 0;
            while (rs.next()) {
                cr[i] = rs.getInt(2);
                i++;
            }



            //from person get resource

            rs = DB.st.executeQuery("SELECT * FROM RELATION_PERSON_RESOURCES WHERE PERSONID = " + personID);
            int a = 0;
            while (rs.next()) {
                pr[a] = rs.getInt(2);
                a++;
            }


            int[] comm = new int[20];
            for(int k=0;i<cr.length;i++){
                for(int j=0;j<pr.length;j++){
                    if(cr[i]==pr[j]){
                        comm[k] = cr[k];
                    }
                }
            }






            String fullName = "";
            String course = "";
            String resource = "";

            rs = DB.st.executeQuery(DBManager.getPersonInTableQuery(personID));
            while(rs.next()) {
                fullName = rs.getString(3) + " " + rs.getString(4);
            }


            rs = DB.st.executeQuery(DBManager.getCourseInTableQuery(courseID));
            while(rs.next()) {
                course = rs.getString(2) + rs.getString(3);
            }

            rs = DB.st.executeQuery(DBManager.getResourceInTableQuery(comm[0]));
            while(rs.next()) {
                resource = rs.getString(3);
            }


            System.out.println("Name :" + fullName);
            System.out.println("Course :" + course);
            System.out.println("Resource :" + resource);
        }catch(Exception e){
            System.out.println("Error");
        }
    }
}



//delete

package hospitalManagement;

import com.mysql.cj.jdbc.ha.MultiHostMySQLConnection;

import java.sql.Connection;
import java.util.Properties;
import java.io.*;
import java.sql.DriverManager;
import java.util.Scanner;

public class Main {

    private static String db_url ;
    private static String db_username;
    private static  String db_password;



    static{
        loadDatabaseProperties();
    }






    private static void loadDatabaseProperties() {
        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("hospitalManagement/env.properties")) {
            Properties properties = new Properties();
            if (input != null) {
                properties.load(input);
                db_url = properties.getProperty("db_url");
                db_username = properties.getProperty("db_username");
                db_password = properties.getProperty("db_password");
                System.out.println("credentials fetch successfully....");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {

        try{
            Class.forName("java.com.cj.jdbc.Driver");
            System.out.println("Driver load successfully.....");
        }
        catch(ClassNotFoundException e)
        {
            System.out.println(e.getMessage());
        }



        try{
            Scanner scanner=new Scanner(System.in);
            Connection connection=  DriverManager.getConnection(db_url,db_username,db_password);
            System.out.println("connected successfully");

            Doctor doctor=new Doctor(connection,scanner);
            Patient patient=new Patient(connection,scanner);

            while(true)
            {
                System.out.println("************WELCOME TO HOSPITAL MANAGEMENT SYSTEM********");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patients");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                System.out.println("Enter your choice: ");
                int choice = scanner.nextInt();


                switch(choice){
                    case 1:
                        System.out.println("Add patient");
                        break;
                    case 2:
                        System.out.println("view patients");
                        break;
                    case 3:
                        System.out.println("view Doctors");
                        break;
                    case 4:
                        System.out.println("book Appointment");
                        break;
                    case 5:
                        System.out.println("THANK YOU! FOR USING HOSPITAL MANAGEMENT SYSTEM!!");
                        return;
                    default:
                        System.out.println("enter valid choice ");
                        break;
                }
            }


        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }



    }
}
package hospitalManagement;
import java.sql.*;
import java.util.*;


public class Patient {


    private Connection connection;
    private Scanner scanner;

    public Patient(Connection connection,Scanner scanner)
    {
        this.connection=connection;
        this.scanner=scanner;
    }


    public void Add_Patient()
    {
        String query="insert into patients(name,age,gender) values (?,?,?)";
        System.out.println("********Add Patient******");
        scanner.nextLine();
        System.out.println("Enter Patient Name:");
        String p_name=scanner.nextLine();

        System.out.println("enter age:");
        int p_age=scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter gender of patient");
        String p_gender=scanner.nextLine();

        try{
            PreparedStatement pst= connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1,p_name);
            pst.setInt(2,p_age);
            pst.setString(3,p_gender);
            int rowAffected=pst.executeUpdate();
            if(rowAffected>0) {
                ResultSet generatedKeys = pst.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int userId = generatedKeys.getInt(1);
                    System.out.println("Patient added successfully. UserId: " + userId);
                } else {
                    System.out.println("Error retrieving userid after adding patient.");
                }
            }
            else {
                System.out.println("error to add patient....");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public void view_Patients()
    {

        String query="select * from patients";
        System.out.println("*********Patient List**********");

        try {
            PreparedStatement pst=connection.prepareStatement(query);
            ResultSet result=pst.executeQuery();
            System.out.println("Patients: ");
            System.out.println("+------------+--------------------+----------+------------+");
            System.out.println("| Patient Id | Name               | Age      | Gender     |");
            System.out.println("+------------+--------------------+----------+------------+");

                while (result.next()) {
                    int id = result.getInt("id");
                    String name = result.getString("name");
                    int age = result.getInt("age");
                    String gender = result.getString("gender");
                    System.out.printf("| %-10s | %-18s | %-8s | %-10s |\n", id, name, age, gender);
                    System.out.println("+------------+--------------------+----------+------------+");
                }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}

package hospitalManagement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLOutput;
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
        System.out.println("Enter gender of patient");
        String p_gender=scanner.nextLine();

        try{
            PreparedStatement pst= connection.prepareStatement(query);
            pst.setString(1,p_name);
            pst.setInt(2,p_age);
            pst.setString(3,p_gender);
            int rowAffected=pst.executeUpdate();
            if(rowAffected>0)
            {
                System.out.println("Patients added successfully......");
            }
            else {
                System.out.println("error to add patient....");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}

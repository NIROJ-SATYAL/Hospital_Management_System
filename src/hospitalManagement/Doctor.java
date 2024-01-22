package hospitalManagement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {


    private Connection connection;
    private Scanner scanner;


    public Doctor(Connection connection,Scanner scanner)
    {
        this.connection=connection;
        this.scanner=scanner;
    }


    public void show_doctor()
    {

        System.out.println("**********Doctor Details***********");
        String query="select * from Doctor ";
        try {
            PreparedStatement pst=connection.prepareStatement(query);
            ResultSet result=pst.executeQuery();

            System.out.println("+------------+--------------------+------------------+");
            System.out.println("| Doctor Id  | Name               | Specialization   |");
            System.out.println("+------------+--------------------+------------------+");
            while(result.next()){
                int id=result.getInt("doctor_id");
                String name=result.getString("name");
                String spec=result.getString("specialization");
                System.out.printf("| %-10s | %-18s | %-16s |\n", id, name, spec);
                System.out.println("+------------+--------------------+------------------+");

            }

        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }


    public boolean getDoctorById(int id)
    {
        String query="select * from doctors where doctor_id=?";

        try{
            PreparedStatement PST=connection.prepareStatement(query);
            PST.setInt(1,id);
            ResultSet result=PST.executeQuery();
            if(result.next())
            {
                return true;
            }
            else {
                return false;
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return false;
    }
}

package hospitalManagement;
import java.sql.Connection;
import java.util.Scanner;

public class Doctor {


    private Connection connection;
    private Scanner scanner;


    public Doctor(Connection connection,Scanner scanner)
    {
        this.connection=connection;
        this.scanner=scanner;
    }
}

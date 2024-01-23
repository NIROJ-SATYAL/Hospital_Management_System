package hospitalManagement;
import java.sql.*;
import java.util.Scanner;

public class Appointments {



    private Connection connection;
    private Scanner scanner;

    public Appointments(Connection connection , Scanner scanner)
    {
        this.connection=connection;
        this.scanner=scanner;

    }


    public void  Book_Appointment( Patient patient,Doctor doctor)
    {
        System.out.println("****************Book Now**************");
        System.out.println("Enter UserId");
        int user_id=scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter UserName");
        String user_name=scanner.nextLine();


        if(patient.getPatientById(user_id))
        {
            System.out.println("Enter doctor id:");
            int doctor_id=scanner.nextInt();
            scanner.nextLine();
            System.out.println("enter appointment date:");
            String a_date=scanner.nextLine();


            if(doctor.getDoctorById(doctor_id))
            {

                if(checkDoctorAvailability(doctor_id,a_date))
                {
                    String query ="insert into appointments(Patient_id,Doctor_id,appointment_date) values (?,?,?) ";

                    try{
                        PreparedStatement pst=connection.prepareStatement(query);
                        pst.setInt(1,user_id);
                        pst.setInt(2,doctor_id);
                        pst.setString(3,a_date);
                        int RowAffected=pst.executeUpdate();
                        if(RowAffected>0)
                        {
                            System.out.println("Appointment successfully");

                        }
                        else {
                            System.out.println("problemoccur during make appointment...");
                        }

                    }
                    catch (SQLException e)
                    {
                        e.printStackTrace();
                    }

                }
                else {
                    System.out.println("Date not available.....");
                }

            }
            else {
                System.out.println("cannot find doctor.....");
            }
        }
        else {
            System.out.println("Register patient first.......");
        }





    }



    public boolean checkDoctorAvailability(int id ,String AppointmentDate){
        String query="select count(*) as total_appointments from appointments where doctor_id=? and appointment_date=?";
        try{
            PreparedStatement pst=connection.prepareStatement(query);
            pst.setInt(1,id);
            pst.setString(2,AppointmentDate);
            ResultSet result= pst.executeQuery();

            if(result.next())
            {
                int appointment_id =result.getInt("total_appointments");
                if(appointment_id==0)
                {
                    return true;
                }
                else {
                    return false;
                }
            }

        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }
}

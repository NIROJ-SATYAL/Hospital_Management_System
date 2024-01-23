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
                        PreparedStatement pst=connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
                        pst.setInt(1,user_id);
                        pst.setInt(2,doctor_id);
                        pst.setString(3,a_date);
                        int RowAffected=pst.executeUpdate();
                        if(RowAffected>0)
                        {
                            System.out.println("Appointment successfully");

                            ResultSet  generatedKeys=pst.getGeneratedKeys();
                            if(generatedKeys.next())
                            {
                                int appointment_id = generatedKeys.getInt(1);
                                String fetch_query="SELECT\n" +
                                        "    \n" +
                                        "    appointments.appointment_date,\n" +
                                        "    \n" +
                                        "    patients.name,\n" +
                                        "    patients.age,\n" +
                                        "    patients.gender,\n" +
                                        "   \n" +
                                        "    doctors.name AS doctor_name,\n" +
                                        "    doctors.specialization\n" +
                                        "FROM\n" +
                                        "    appointments\n" +
                                        "JOIN\n" +
                                        "    patients ON appointments.Patient_id = patients.id\n" +
                                        "JOIN\n" +
                                        "    doctors ON appointments.Doctor_id = doctors.doctor_id \n" +
                                        "    where appointment_id=?;";

                                try{
                                    PreparedStatement fetch_pst=connection.prepareStatement(fetch_query);
                                    fetch_pst.setInt(1,appointment_id);
                                    ResultSet result=fetch_pst.executeQuery();
                                    if(result.next()){
                                        String appointmentDate=result.getString("appointment_date");
                                        String patient_name=result.getString("name");
                                        int age=result.getInt("age");
                                        String gender=result.getString("gender");
                                        String d_name=result.getString("doctor_name");
                                        String d_specialization =result.getString("specialization");

                                        System.out.println("+------------+--------------------+----------+--------------------------------------------------------------------------+");
                                        System.out.println("| Appointment-Date| Patient-Name| Patient-Age| Patient-Gender | Doctor_Name |  Specialization");
                                        System.out.println("+------------+--------------------+----------+----------------------------------------------------------------------------+");
                                        System.out.printf("| %-10s | %-18s | %-8s | %-10s | %-10s | %-10s\n", appointmentDate, patient_name, age, gender,d_name,d_specialization);
                                        System.out.println("+------------+--------------------+----------+--------------------------------------------------------------------------------+");
                                    }

                                }
                                catch(SQLException e)
                                {
                                   e.printStackTrace();
                                }

                            }



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
        String query="select count(*) as total_appointments from appointments where Doctor_id=? and appointment_date=?";
        try{
            PreparedStatement pst=connection.prepareStatement(query);
            pst.setInt(1,id);
            pst.setString(2,AppointmentDate);
            ResultSet result= pst.executeQuery();

            if(result.next())
            {
                int appointment_id =result.getInt("total_appointments");

                if(appointment_id<=3)
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



    public void show_appointment()
    {
        String fetch_query="SELECT\n" +
                "    \n" +
                "    appointments.appointment_date,\n" +
                "    \n" +
                "    patients.name,\n" +
                "    patients.age,\n" +
                "    patients.gender,\n" +
                "   \n" +
                "    doctors.name AS doctor_name,\n" +
                "    doctors.specialization\n" +
                "FROM\n" +
                "    appointments\n" +
                "JOIN\n" +
                "    patients ON appointments.Patient_id = patients.id\n" +
                "JOIN\n" +
                "    doctors ON appointments.Doctor_id = doctors.doctor_id";

        System.out.println("*****************Appointment List**********");


        try{
            PreparedStatement fetch_pst=connection.prepareStatement(fetch_query);
//            fetch_pst.setInt(1,appointment_id);

            System.out.println("+------------+--------------------+----------+--------------------------------------------------------------------------+");
            System.out.println("| Appointment-Date| Patient-Name| Patient-Age| Patient-Gender | Doctor_Name |  Specialization");
            System.out.println("+------------+--------------------+----------+----------------------------------------------------------------------------+");
            ResultSet result=fetch_pst.executeQuery();
            while(result.next()){
                String appointmentDate=result.getString("appointment_date");
                String patient_name=result.getString("name");
                int age=result.getInt("age");
                String gender=result.getString("gender");
                String d_name=result.getString("doctor_name");
                String d_specialization =result.getString("specialization");


                System.out.printf("| %-10s | %-18s | %-8s | %-10s | %-10s | %-10s\n", appointmentDate, patient_name, age, gender,d_name,d_specialization);

            }
            System.out.println("+------------+--------------------+----------+--------------------------------------------------------------------------------+");

        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }


    }
}

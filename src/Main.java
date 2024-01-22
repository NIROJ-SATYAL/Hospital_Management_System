
import java.util.Properties;
import java.io.*;

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

    }
}
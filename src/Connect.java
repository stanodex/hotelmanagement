import java.sql.Connection;
import java.sql.DriverManager;

public class Connect {
    private static final String Db_Url = "jdbc:mysql://localhost:3306/DATABASE NAME";
    private static final String Db_User = "root";
    private static final String Db_Pass = "DATABASE PASSWORD";
    private static Connection connection;

    private Connect() {
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(Db_Url, Db_User, Db_Pass);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
            return connection;
        }


    public static void CloseConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            } finally {
                connection = null;
            }
        }
    }
}
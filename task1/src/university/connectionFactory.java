package university;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class connectionFactory {

  public static Connection getHardCodedConnection() throws SQLException {
    return getConnection("user", "password", "localhost", "3306", "grades");
  }

  public static Connection getConnection(String user, String password,
      String server, String port, String db) throws SQLException {
    Connection connection = null;
    Properties properties = new Properties();
    properties.put("user", user);
    properties.put("password", password);

    try {
      Class.forName("com.mysql.jdbc.Driver").newInstance();
      connection = DriverManager.getConnection("jdbc:mysql://" + server + ":"
          + port + "/" + db, properties);
    } catch (InstantiationException | IllegalAccessException
        | ClassNotFoundException e) {
      System.err.println("There is a problem with the MySQL JDBC driver.");
      e.printStackTrace();
    }

    return connection;
  }

}

package university;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
  public static final String defaultPropertiesFileName = "db.properties";

  public static Connection getConnection() throws IOException, SQLException {
    return getPropertiesFileConnection(defaultPropertiesFileName);
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

  public static Connection getPropertiesFileConnection(String fileName)
      throws IOException, SQLException {

    Properties properties = new Properties();
    InputStream inputStream = new FileInputStream(fileName);
    properties.load(inputStream);

    String user = properties.getProperty("user");
    String password = properties.getProperty("password");
    String server = properties.getProperty("server");
    String port = properties.getProperty("port");
    String database = properties.getProperty("database");

    return getConnection(user, password, server, port, database);
  }
}

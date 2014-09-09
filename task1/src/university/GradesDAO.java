package university;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class GradesDAO {
  private Connection connection;
  private String db = "grades";

  public GradesDAO() throws SQLException {
    connection = connectionFactory.getHardCodedConnection();

    createTable("ITC313");
  }

  public void createTable(String tableName) throws SQLException {
    String query = "create table " + this.db + "." + tableName + " "
        + "(id integer not null," + "name varchar(40) not null,"
        + "grade1 integer not null," + "grade2 integer not null,"
        + "grade3 integer not null," + "gradeExam integer not null"
        + "primary key (id))";

    Statement s = null;
    try {
      s = connection.createStatement();
      s.executeUpdate(query);
    } catch (SQLException ex) {
      ex.printStackTrace();
    } finally {
      if (s != null) {
        s.close();
      }
    }
  }

}

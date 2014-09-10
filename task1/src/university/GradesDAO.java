package university;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GradesDAO {
  private Connection connection;
  private String db = "grades";

  public GradesDAO() throws SQLException {
    connection = ConnectionFactory.getHardCodedConnection();

    String t = "a";

    dropTable(t);
    createTable(t);
    insertStudent(t, "Ben", 50, 50, 50, 50);
    System.out.println(selectStudent(t, 1));
  }

  public void insertStudent(String tableName, String name, int grade1,
      int grade2, int grade3, int gradeExam) throws SQLException {

    PreparedStatement s = null;
    try {
      s = connection.prepareStatement("insert into " + tableName
        + " (name, grade1, grade2, grade3, gradeExam) "
        + "values (?, ?, ?, ?, ?)");

      s.setString(1, name);
      s.setInt(2, grade1);
      s.setInt(3, grade2);
      s.setInt(4, grade3);
      s.setInt(5, gradeExam);
      s.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (s != null) {
        s.close();
      }
    }
  }

  public void dropTable(String tableName) throws SQLException {
    String query = "drop table if exists " + this.db + "." + tableName;
    runUpdate(query);
  }

  public Student selectStudent(String tableName, int id) throws SQLException {
    String query = "select id, name, grade1, grade2, grade3, gradeExam from "
      + tableName + " where id=?";

    System.out.println(query);

    PreparedStatement s = null;
    ResultSet r = null;
    Student student = null;
    try {
      s = connection.prepareStatement(query);
      s.setInt(1, id);
      r = s.executeQuery();

      r.next();

      String name = r.getString("name");
      int grade1 = r.getInt("grade1");
      int grade2 = r.getInt("grade2");
      int grade3 = r.getInt("grade3");
      int gradeExam = r.getInt("gradeExam");

      student = new Student(id, name, grade1, grade2, grade3, gradeExam);
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (s != null) {
        s.close();
      }
    }
    return student;
  }

  public void runUpdate(String query) throws SQLException {
    System.out.println(query);

    Statement s = null;
    try {
      s = connection.createStatement();
      s.executeUpdate(query);
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (s != null) {
        s.close();
      }
    }
  }

  public void createTable(String tableName) throws SQLException {
    String query = "create table " + this.db + "." + tableName
      + " (id integer not null auto_increment, "
      + "name varchar(40) not null, " + "grade1 integer not null, "
      + "grade2 integer not null, " + "grade3 integer not null,"
      + "gradeExam integer not null, " + "primary key (id))";
    runUpdate(query);

    query = "alter table " + tableName + " auto_increment = 1";
    runUpdate(query);
  }
}

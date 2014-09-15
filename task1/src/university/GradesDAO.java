// TODO:
// move column names to Student object

package university;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GradesDAO {
  private Connection connection;
  private String db = "grades";

  public GradesDAO() throws SQLException, IOException {
    connection = ConnectionFactory.getConnection();
  }

  public GradesDAO(String fileName) throws SQLException, IOException {
    connection = ConnectionFactory.getPropertiesFileConnection(fileName);
  }

  public void insertStudent(String tableName, Student student)
      throws SQLException {
    insertStudent(tableName, student.name, student.grade1, student.grade2,
        student.grade3, student.gradeExam);
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

  public Student[] selectStudentById(String tableName, int id)
      throws SQLException {
    return selectStudent(tableName, "id", id, java.sql.Types.INTEGER);
  }

  public Student[] selectStudentByName(String tableName, String name)
      throws SQLException {
    return selectStudent(tableName, "name", name, java.sql.Types.VARCHAR);
  }

  public Student[] selectStudentByGrade(String tableName, int grade, int value)
      throws SQLException {
    if (grade >= 1 && grade <= 4) {
      String which = null;
      switch (grade) {
        case 1:
          which = "grade1";
          break;
        case 2:
          which = "grade2";
          break;
        case 3:
          which = "grade3";
          break;
        case 4:
          which = "gradeExam";
          break;
      }
      return selectStudent(tableName, which, value, java.sql.Types.INTEGER);
    } else {
      return null;
    }
  }

  public Student[] selectStudent(String tableName, String column, Object value,
      int type) throws SQLException {
    String query = "select id, name, grade1, grade2, grade3, gradeExam from "
      + tableName + " where " + column + "=?";

    System.out.println(query);

    PreparedStatement s = null;
    ResultSet r = null;
    List<Student> students = new ArrayList<Student>();
    try {
      s = connection.prepareStatement(query);
      s.setObject(1, value, type);
      r = s.executeQuery();

      while (r.next()) {
        int id = r.getInt("id");
        String name = r.getString("name");
        int grade1 = r.getInt("grade1");
        int grade2 = r.getInt("grade2");
        int grade3 = r.getInt("grade3");
        int gradeExam = r.getInt("gradeExam");

        students.add(new Student(id, name, grade1, grade2, grade3, gradeExam));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (s != null) {
        s.close();
      }
    }
    return students.toArray(new Student[students.size()]);
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

package university;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GradesDAO {
  public static final String defaultPropertiesFileName = "db.properties";
  private Connection connection;
  private String db;

  /*
   * Create a new GradesDAO using the default properties file.
   */
  public GradesDAO() throws SQLException, IOException, URISyntaxException {
    this(defaultPropertiesFileName);
  }

  /*
   * Create a new GradesDAO given the filename of a properties file
   */
  public GradesDAO(String fileName) throws SQLException, IOException {
    connection = ConnectionFactory.getPropertiesFileConnection(fileName);
    db = connection.getMetaData().getURL().split("/")[3];
  }

  /*
   * Insert the given Student to the given Table
   */
  public void insertStudent(String tableName, Student student)
      throws SQLException {
    insertStudent(tableName, student.name, student.grade1, student.grade2,
        student.grade3, student.gradeExam);
  }

  /*
   * Insert a new Student to the given table
   */
  public void insertStudent(String tableName, String name, int grade1,
      int grade2, int grade3, int gradeExam) throws SQLException {
    PreparedStatement s = null;
    try {
      s = connection.prepareStatement("insert into " + tableName + " ("
        + Student.columnName + "," + Student.columnGrade1 + ","
        + Student.columnGrade2 + "," + Student.columnGrade3 + ","
        + Student.columnGradeExam + ") " + "values (?, ?, ?, ?, ?)");

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

  public String[] getTables() throws SQLException {
    List<String> tables = new ArrayList<String>();

    DatabaseMetaData md = connection.getMetaData();
    ResultSet r = md.getTables(null, null, "%", null);
    while (r.next()) {
      tables.add(r.getString(3));
    }

    return tables.toArray(new String[tables.size()]);
  }

  /*
   * Drop the given table
   */
  public void dropTable(String tableName) throws SQLException {
    String query = "drop table if exists " + this.db + "." + tableName;
    runUpdate(query);
  }

  /*
   * Get the Student with the given id from the given table Returns null if no
   * student has that id
   */
  public Student selectStudentById(String tableName, int id)
      throws SQLException {
    Student student = null;
    try {
      student = selectStudent(tableName, "id", id, java.sql.Types.INTEGER)[0];
    } catch (ArrayIndexOutOfBoundsException ex) {
      System.err.println("No student with ID " + id + " found in table "
        + tableName);
    }
    return student;
  }

  /*
   * Get Students with the given name
   */
  public Student[] selectStudentByName(String tableName, String name)
      throws SQLException {
    return selectStudent(tableName, Student.columnName, name,
        java.sql.Types.VARCHAR);
  }

  /*
   * Get Students with matching name
   */
  public Student[] selectStudentLikeName(String tableName, String name)
      throws SQLException {
    return selectStudent(tableName, Student.columnName, name,
        java.sql.Types.VARCHAR, "like");
  }

  /*
   * Select students with grade = value grade is the number of the grade: 1:
   * grade1 2: grade2 3: grade3 4: gradeExam
   */
  public Student[] selectStudentByGrade(String tableName, int grade, int value,
      String operator) throws SQLException {
    if (grade >= 1 && grade <= 4) {
      String which = null;
      switch (grade) {
        case 1:
          which = Student.columnGrade1;
          break;
        case 2:
          which = Student.columnGrade2;
          break;
        case 3:
          which = Student.columnGrade3;
          break;
        case 4:
          which = Student.columnGradeExam;
          break;
      }
      return selectStudent(tableName, which, value, java.sql.Types.INTEGER,
          operator);
    } else {
      return null;
    }
  }

  /*
   * selectStudent using "="
   */
  public Student[] selectStudent(String tableName, String column, Object value,
      int type) throws SQLException {
    return selectStudent(tableName, column, value, type, "=");
  }

  /*
   * Select from the given grades table all students where column = value
   * Returns an array of Students, and an empty array if no results.
   */
  public Student[] selectStudent(String tableName, String column, Object value,
      int type, String operator) throws SQLException {
    String query = "select " + Student.columnId + "," + Student.columnName
      + "," + Student.columnGrade1 + "," + Student.columnGrade2 + ","
      + Student.columnGrade3 + "," + Student.columnGradeExam + " from "
      + tableName + " where " + column + " " + operator + " ?";

    System.out.println(query);

    PreparedStatement s = null;
    ResultSet r = null;
    List<Student> students = new ArrayList<Student>();
    try {
      s = connection.prepareStatement(query);
      s.setObject(1, value, type);
      r = s.executeQuery();

      while (r.next()) {
        int id = r.getInt(Student.columnId);
        String name = r.getString(Student.columnName);
        int grade1 = r.getInt(Student.columnGrade1);
        int grade2 = r.getInt(Student.columnGrade2);
        int grade3 = r.getInt(Student.columnGrade3);
        int gradeExam = r.getInt(Student.columnGradeExam);

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

  /*
   * Run an update query (i.e. no return value) with the given query string
   */
  public void runUpdate(String query) throws SQLException {
    // System.out.println(query);

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

  /*
   * Create table for student grades with a given name.
   */
  public void createTable(String tableName) throws SQLException {
    String query = "create table " + this.db + "." + tableName + " ("
      + Student.columnId + " integer not null auto_increment, "
      + Student.columnName + " varchar(40) not null, " + Student.columnGrade1
      + " integer not null, " + Student.columnGrade2 + " integer not null, "
      + Student.columnGrade3 + " integer not null," + Student.columnGradeExam
      + " integer not null, " + "primary key (" + Student.columnId + "))";
    runUpdate(query);

    query = "alter table " + tableName + " auto_increment = 1";
    runUpdate(query);
  }
}

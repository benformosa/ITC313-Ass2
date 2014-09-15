package university;

import java.io.IOException;
import java.sql.SQLException;

/*
 * Database Actions: Create table Drop table New student Search by field
 * Application Actions: Calculate final score Objects: Database connection
 * Student
 */

public class GradingTool {

  public GradingTool() {
  }

  public static void main(String[] args) {
    try {
      GradesDAO g;
      try {
        g = new GradesDAO();

        String t = "a";

        g.dropTable(t);
        g.createTable(t);
        g.insertStudent(t, "Ben", 38, 50, 50, 50);

        System.out.println(g.selectStudentById(t, 1)[0]);

      } catch (IOException e) {
        e.printStackTrace();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}

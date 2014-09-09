package university;

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
      GradesDAO g = new GradesDAO();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}

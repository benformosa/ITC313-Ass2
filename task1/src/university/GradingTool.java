package university;


/*
 * Database Actions: Create table Drop table New student Search by field
 * Application Actions: Calculate final score Objects: Database connection
 * Student
 */

public class GradingTool {

  public GradingTool() {
  }

  public static void main(String[] args) {
    GradesDAO g;
    try {
      g = new GradesDAO();
      String t = "a";

      g.dropTable(t);
      g.createTable(t);
      g.insertStudent(t, "Ben", 38, 50, 50, 50);

      System.out.println(g.selectStudentById(t, 1));
      System.out.println(g.selectStudentById(t, 9));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

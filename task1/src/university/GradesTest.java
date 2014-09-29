package university;

// Database Actions:
// Create table
// Drop table
// New student
// Search by field
// Application Actions:
// Calculate final score
// Objects:
// Database connection
// Student

public class GradesTest {
  public static void main(String[] args) {
    GradesDAO g;
    try {
      g = new GradesDAO();
      String t = "a";

      g.dropTable(t);
      g.createTable(t);
      g.insertStudent(t, "Ben", 38, 21, 89, 65);
      g.insertStudent(t, "Frank", 70, 75, 53, 87);
      g.insertStudent(t, "Benjamin", 99, 30, 57, 44);
      g.insertStudent(t, "Kath", 87, 60, 62, 21);

      System.out.println("* by id");
      System.out.println(g.selectStudentById(t, 1));
      System.out.println(g.selectStudentById(t, 9));

      System.out.println();
      System.out.println("* by grade");
      for (Student s : g.selectStudentByGrade(t, 1, 60, ">")) {
        System.out.println(s);
      }

      System.out.println();
      System.out.println("* like name");
      for (Student s : g.selectStudentLikeName(t, "Ben%")) {
        System.out.println(s);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public GradesTest() {
  }
}

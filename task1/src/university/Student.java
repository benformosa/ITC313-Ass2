package university;

public class Student {
  public static final String columnGrade1 = "grade1";
  public static final String columnGrade2 = "grade2";
  public static final String columnGrade3 = "grade3";
  public static final String columnGradeExam = "gradeExam";
  public static final String columnId = "id";

  public static final String columnName = "name";
  private static final int grade1Weight = 10;
  private static final int grade2Weight = 20;
  private static final int grade3Weight = 20;
  private static final int gradeExamWeight = 50;
  private static final int max = 100;

  public static final String printGrade1 = "Assignment 1";
  public static final String printGrade2 = "Assignment 2";
  public static final String printGrade3 = "Assignment 3";
  public static final String printGradeExam = "Final";
  public static final String printGradeFinal = "Final score";
  public static final String printId = "ID";
  public static final String printName = "Name";

  public static String[] getColumns() {
    return new String[] { columnName, columnId, columnGrade1, columnGrade2,
        columnGrade3, columnGradeExam };
  }

  public static String[] getPrintColumns() {
    String[] columns = { printId, printName, printGrade1, printGrade2,
        printGrade3, printGradeExam, printGradeFinal };
    return columns;
  }

  public int grade1;
  public int grade2;
  public int grade3;
  public int gradeExam;

  public int id;

  public String name;

  public Student(int id, String name, int grade1, int grade2, int grade3,
      int gradeExam) {
    this.id = id;
    this.name = name;
    this.grade1 = grade1;
    this.grade2 = grade2;
    this.grade3 = grade3;
    this.gradeExam = gradeExam;
  }

  public int getFinalGrade() {
    return (grade1 * grade1Weight + grade2 * grade2Weight + grade3
      * grade3Weight + gradeExam * gradeExamWeight)
      / (grade1Weight + grade2Weight + grade3Weight + gradeExamWeight);
  }

  public Object[] toArray() {
    Object[] array = { id, name, grade1, grade2, grade3, gradeExam,
        getFinalGrade() };
    return array;
  }

  @Override
  public String toString() {
    return String.format("%d %s - %d, %d, %d, %d : %d", id, name, grade1,
        grade2, grade3, gradeExam, getFinalGrade());
  }
}

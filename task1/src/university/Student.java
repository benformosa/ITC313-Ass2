package university;

public class Student {
  private static final int grade1Weight = 10;
  private static final int grade2Weight = 20;
  private static final int grade3Weight = 20;
  private static final int gradeExamWeight = 50;

  int id;
  String name;
  int grade1;
  int grade2;
  int grade3;
  int gradeExam;

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
    return (grade1 / 100 * grade1Weight) + (grade2 / 100 * grade2Weight)
        + (grade3 / 100 * grade3Weight) + (gradeExam / 100 * gradeExamWeight);
  }
}

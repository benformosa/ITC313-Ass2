package university;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class StudentPanel extends JPanel {
  JButton dropStudentButton;
  Student student;

  public StudentPanel(Student student) {
    super();
    this.student = student;

    this.add(new JLabel("ID:"));
    this.add(new JLabel(Integer.toString(student.id)));
    this.add(new JLabel("Name:"));
    this.add(new JLabel(student.name));
    this.add(new JLabel("Grades:"));
    this.add(new JLabel(Integer.toString(student.grade1)));
    this.add(new JLabel(Integer.toString(student.grade2)));
    this.add(new JLabel(Integer.toString(student.grade3)));
    this.add(new JLabel(Integer.toString(student.gradeExam)));
    this.add(new JLabel("Final Grade:"));
    this.add(new JLabel(Integer.toString(student.getFinalGrade())));
  }
}

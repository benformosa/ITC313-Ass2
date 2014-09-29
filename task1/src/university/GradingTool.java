package university;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;

public class GradingTool implements ActionListener {
  public static void main(String[] args) throws SQLException, IOException,
      URISyntaxException {
    @SuppressWarnings("unused")
    GradingTool gt = new GradingTool();
  }

  private JButton changeTableButton;
  private JComboBox<String> changeTableCBox;
  private DefaultComboBoxModel<String> changeTableCBoxModel;

  private String currentTable;

  private JLabel currentTableLabel;
  private JButton dropTableButton;
  private JFrame frame;
  private GradesDAO g;
  private JButton insertButton;
  private JTextField insertGrade1Text;
  private JTextField insertGrade2Text;

  private JTextField insertGrade3Text;
  private JTextField insertGradeExamText;
  private JTextField insertNameText;
  private JButton newTableButton;
  private JTextField newTableText;
  private String[] operators = new String[] { "<", "<=", "=", ">=", ">", "like" };
  private JPanel panel;
  private JButton selectButton;
  private JComboBox<String> selectColumnCBox;
  private DefaultComboBoxModel<String> selectColumnCBoxModel;
  private JComboBox<String> selectOperatorCBox;
  private DefaultComboBoxModel<String> selectOperatorCBoxModel;
  private JPanel selectOutput;

  private JTextField selectValueText;

  public GradingTool() throws SQLException, IOException, URISyntaxException {
    g = new GradesDAO();

    frame = new JFrame();
    frame.setTitle("Grading Tool");
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

    // *** Create tablePanel ***

    JPanel tablePanel = new JPanel();

    currentTableLabel = new JLabel("Using table " + currentTable);

    changeTableCBoxModel = new DefaultComboBoxModel<String>(g.getTables());
    changeTableCBox = new JComboBox<String>(changeTableCBoxModel);

    changeTableButton = new JButton("Change table");
    changeTableButton.addActionListener(this);
    changeTableButton.doClick(); // this initialises currentTable

    dropTableButton = new JButton("Drop table");
    dropTableButton.addActionListener(this);

    newTableText = new JTextField(10);
    newTableButton = new JButton("Create table");
    newTableButton.addActionListener(this);

    tablePanel.add(new JLabel("Tables"));
    tablePanel.add(currentTableLabel);
    tablePanel.add(changeTableCBox);
    tablePanel.add(changeTableButton);
    tablePanel.add(dropTableButton);
    tablePanel.add(new JLabel("Create:"));
    tablePanel.add(newTableText);
    tablePanel.add(newTableButton);

    // *** Create insertPanel ***

    JPanel insertPanel = new JPanel();

    insertNameText = new JTextField(15);
    insertGrade1Text = new JTextField(4);
    insertGrade2Text = new JTextField(4);
    insertGrade3Text = new JTextField(4);
    insertGradeExamText = new JTextField(4);
    insertButton = new JButton("Add Student");
    insertButton.addActionListener(this);

    insertPanel.add(new JLabel("Insert"));
    insertPanel.add(new JLabel(Student.printName));
    insertPanel.add(insertNameText);
    insertPanel.add(new JLabel(Student.printGrade1));
    insertPanel.add(insertGrade1Text);
    insertPanel.add(new JLabel(Student.printGrade2));
    insertPanel.add(insertGrade2Text);
    insertPanel.add(new JLabel(Student.printGrade3));
    insertPanel.add(insertGrade3Text);
    insertPanel.add(new JLabel(Student.printGradeExam));
    insertPanel.add(insertGradeExamText);
    insertPanel.add(insertButton);

    // *** Create selectPanel ***

    JPanel selectPanel = new JPanel();

    selectColumnCBoxModel = new DefaultComboBoxModel<String>(
        Student.getColumns());
    selectColumnCBox = new JComboBox<String>(selectColumnCBoxModel);
    selectColumnCBox.setSelectedItem(Student.columnId);

    selectOperatorCBoxModel = new DefaultComboBoxModel<String>(operators);
    selectOperatorCBox = new JComboBox<String>(selectOperatorCBoxModel);
    selectOperatorCBox.setSelectedItem(">");

    selectValueText = new JTextField(15);
    selectValueText.setText("0");

    selectButton = new JButton("Select");
    selectButton.addActionListener(this);

    selectOutput = new JPanel();
    selectOutput.setLayout(new BoxLayout(selectOutput, BoxLayout.PAGE_AXIS));
    updateSelected(g.selectAll(currentTable));

    selectPanel.add(new JLabel("Select"));
    selectPanel.add(new JLabel("Column:"));
    selectPanel.add(selectColumnCBox);
    selectPanel.add(new JLabel("Operator:"));
    selectPanel.add(selectOperatorCBox);
    selectPanel.add(new JLabel("Value:"));
    selectPanel.add(selectValueText);
    selectPanel.add(selectButton);
    selectPanel.add(selectOutput);

    // *** Add panels to frame ***

    panel.add(tablePanel);
    panel.add(new JSeparator());
    panel.add(insertPanel);
    panel.add(new JSeparator());
    panel.add(selectPanel);

    frame.add(panel);
    frame.pack();
    frame.setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == changeTableButton) {
      this.currentTable = (String) changeTableCBox.getSelectedItem();
      currentTableLabel.setText("Using table " + currentTable);
      try {
        updateSelected(g.selectAll(currentTable));
      } catch (SQLException ex) {
        System.err.println("Unable to select");
      }
      System.out.println("Now using table " + currentTable);

    } else if (e.getSource() == dropTableButton) {
      String table = (String) changeTableCBox.getSelectedItem();
      try {
        g.dropTable(table);
        System.out.println("Dropped table " + table);
        changeTableCBoxModel.removeElement(table);
      } catch (SQLException ex) {
        System.err.println("Failed to drop table " + table);
        ex.printStackTrace();
      }
      System.out.println(this.currentTable);

    } else if (e.getSource() == newTableButton) {
      String table = newTableText.getText();
      if (!(table == null || table.isEmpty())) {
        try {
          g.createTable(table);
          changeTableCBoxModel.addElement(table);
        } catch (SQLException ex) {
          System.err
              .println("Failed to create table " + newTableText.getText());
          ex.printStackTrace();
        }
      } else {
        System.err.println("Cannot create table with empty name");
      }

    } else if (e.getSource() == insertButton) {
      String name = insertNameText.getText();
      int grade1 = Integer.parseInt(insertGrade1Text.getText());
      int grade2 = Integer.parseInt(insertGrade2Text.getText());
      int grade3 = Integer.parseInt(insertGrade3Text.getText());
      int gradeExam = Integer.parseInt(insertGradeExamText.getText());

      if (grade1 > 0 && grade1 <= 100 && grade2 > 0 && grade2 <= 100
        && grade3 > 0 && grade3 <= 100 && gradeExam > 0 && gradeExam <= 100) {
        try {
          g.insertStudent(currentTable, name, grade1, grade2, grade3, gradeExam);
          System.out.println("Added new Student " + insertNameText.getText());

          insertNameText.setText("");
          insertGrade1Text.setText("");
          insertGrade2Text.setText("");
          insertGrade3Text.setText("");
          insertGradeExamText.setText("");
        } catch (NumberFormatException ex) {
          System.err.println("Incorrect number format");
        } catch (SQLException ex) {
          System.err.println("Failed to insert student");
          ex.printStackTrace();
        }
      } else {
        System.err.println("Enter grade between 0 and 100");
      }

    } else if (e.getSource() == selectButton) {
      int type;
      switch ((String) selectColumnCBox.getSelectedItem()) {
        case Student.columnId:
        case Student.columnGrade1:
        case Student.columnGrade2:
        case Student.columnGrade3:
        case Student.columnGradeExam:
          type = java.sql.Types.INTEGER;
          break;
        case Student.columnName:
        default:
          type = java.sql.Types.VARCHAR;
          break;
      }

      Student[] selected = new Student[] {};
      try {
        if (type == java.sql.Types.INTEGER) {
          selected = g.selectStudent(currentTable,
              (String) selectColumnCBox.getSelectedItem(),
              Integer.parseInt(selectValueText.getText()), type,
              (String) selectOperatorCBox.getSelectedItem());
        } else if (type == java.sql.Types.VARCHAR) {
          selected = g.selectStudent(currentTable,
              (String) selectColumnCBox.getSelectedItem(),
              selectValueText.getText(), type,
              (String) selectOperatorCBox.getSelectedItem());
        }
        updateSelected(selected);
      } catch (SQLException ex) {
        System.err.println("Unable to select");
        ex.printStackTrace();
      }
    }
  }

  public void updateSelected(Student[] selected) {
    try {
      selectOutput.removeAll();

      // convert array of students to [][] of student data
      List<Object[]> list = new ArrayList<Object[]>();
      for (Student s : selected) {
        list.add(s.toArray());
      }

      // display data in a JTable
      JTable table = new JTable(list.toArray(new Object[list.size()][]),
          Student.getPrintColumns());

      table.setAutoCreateRowSorter(true);
      table.setPreferredScrollableViewportSize(table.getPreferredSize());
      table.setFillsViewportHeight(true);
      JScrollPane scrollPane = new JScrollPane(table);
      selectOutput.add(scrollPane);
      selectOutput.validate();
    } catch (NullPointerException ex) {
    }
  }
}
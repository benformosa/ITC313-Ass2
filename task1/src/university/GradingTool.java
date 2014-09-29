package university;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

public class GradingTool implements ActionListener {
  public static void main(String[] args) throws SQLException, IOException,
      URISyntaxException {
    @SuppressWarnings("unused")
    GradingTool gt = new GradingTool();
  }

  private GradesDAO g;
  private String currentTable;
  private JFrame frame;
  private JPanel panel;
  private JLabel statusLabel;

  private JButton changeTableButton;
  private JComboBox<String> changeTableCBox;
  private DefaultComboBoxModel<String> changeTableCBoxModel;
  private JLabel currentTableLabel;
  private JButton dropTableButton;
  private JButton newTableButton;
  private JTextField newTableText;

  private JButton insertButton;
  private JTextField insertGrade1Text;
  private JTextField insertGrade2Text;
  private JTextField insertGrade3Text;
  private JTextField insertGradeExamText;
  private JTextField insertNameText;

  private String[] operators = new String[] { "<", "<=", "=", ">=", ">", "like" };
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
    frame.setPreferredSize(new Dimension(600, 600));
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    panel = new JPanel(new BorderLayout());
    panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

    statusLabel = new JLabel("Loaded database " + g.db);
    statusLabel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

    // *** Create tablePanel ***

    JPanel tablePanel = new JPanel();
    tablePanel.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createEtchedBorder(), "Tables"));

    currentTableLabel = new JLabel("Using table " + currentTable);

    changeTableCBoxModel = new DefaultComboBoxModel<String>(g.getTables());
    changeTableCBox = new JComboBox<String>(changeTableCBoxModel);

    changeTableButton = new JButton("Change table");
    changeTableButton.addActionListener(this);

    dropTableButton = new JButton("Drop table");
    dropTableButton.addActionListener(this);

    newTableText = new JTextField(10);
    newTableButton = new JButton("Create table");
    newTableButton.addActionListener(this);

    JPanel tablePanel1 = new JPanel();
    JPanel tablePanel2 = new JPanel();
    JPanel tablePanel3 = new JPanel();

    tablePanel1.add(currentTableLabel);
    tablePanel2.add(changeTableCBox);
    tablePanel2.add(changeTableButton);
    tablePanel2.add(dropTableButton);
    tablePanel3.add(new JLabel("Create:"));
    tablePanel3.add(newTableText);
    tablePanel3.add(newTableButton);

    tablePanel.add(tablePanel1);
    tablePanel.add(tablePanel2);
    tablePanel.add(tablePanel3);

    // *** Create insertPanel ***

    JPanel insertPanel = new JPanel();
    insertPanel.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createEtchedBorder(), "Insert"));

    insertNameText = new JTextField(15);
    insertGrade1Text = new JTextField(4);
    insertGrade2Text = new JTextField(4);
    insertGrade3Text = new JTextField(4);
    insertGradeExamText = new JTextField(4);
    insertButton = new JButton("Add Student");
    insertButton.addActionListener(this);

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
    selectPanel.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createEtchedBorder(), "Select"));
    selectPanel.setPreferredSize(new Dimension(panel.getWidth(), 300));

    selectColumnCBoxModel = new DefaultComboBoxModel<String>(
        Student.getPrintColumns());
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

    setCurrentTable();

    JPanel selectPanel1 = new JPanel();
    JPanel selectPanel2 = new JPanel();

    selectPanel1.add(new JLabel("Column:"));
    selectPanel1.add(selectColumnCBox);
    selectPanel1.add(new JLabel("Operator:"));
    selectPanel1.add(selectOperatorCBox);
    selectPanel2.add(new JLabel("Value:"));
    selectPanel2.add(selectValueText);
    selectPanel2.add(selectButton);

    selectPanel.add(selectPanel1);
    selectPanel.add(selectPanel2);
    selectPanel.add(selectOutput);

    // *** Add panels to frame ***

    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

    mainPanel.add(tablePanel);
    mainPanel.add(selectPanel);
    mainPanel.add(insertPanel);

    panel.add(mainPanel);
    panel.add(statusLabel, BorderLayout.PAGE_END);

    statusLabel.setText("Loaded database " + g.db);

    frame.add(panel);
    frame.pack();
    frame.setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == changeTableButton) {
      setCurrentTable((String) changeTableCBox.getSelectedItem());

    } else if (e.getSource() == dropTableButton) {
      String table = (String) changeTableCBox.getSelectedItem();
      try {
        g.dropTable(table);
        statusLabel.setText("Dropped table " + table);
        changeTableCBoxModel.removeElement(table);
        if (table == currentTable) {
          setCurrentTable();
        }
      } catch (SQLException ex) {
        statusLabel.setText("Failed to drop table " + table);
      }

    } else if (e.getSource() == newTableButton) {
      try {
        String table = newTableText.getText();
        if (Arrays.asList(g.getTables()).contains(table)) {
          statusLabel.setText("Table with name " + table + " already exists");
        } else if (!(table == null || table.isEmpty())) {

          g.createTable(table);
          statusLabel.setText("Created new table " + table);
          changeTableCBoxModel.addElement(table);

        } else {
          statusLabel.setText("Cannot create table with empty name");
        }
      } catch (SQLException ex) {
        statusLabel.setText("Failed to create table " + newTableText.getText());
      }

    } else if (e.getSource() == insertButton) {
      try {
        String name = insertNameText.getText();

        if (name.isEmpty()) {
          statusLabel.setText("Enter a name");
          return;
        }

        int grade1 = Integer.parseInt(insertGrade1Text.getText());
        int grade2 = Integer.parseInt(insertGrade2Text.getText());
        int grade3 = Integer.parseInt(insertGrade3Text.getText());
        int gradeExam = Integer.parseInt(insertGradeExamText.getText());

        if (grade1 > 0 && grade1 <= Student.maxGrade && grade2 > 0
          && grade2 <= Student.maxGrade && grade3 > 0
          && grade3 <= Student.maxGrade && gradeExam > 0
          && gradeExam <= Student.maxGrade) {

          g.insertStudent(currentTable, name, grade1, grade2, grade3, gradeExam);
          statusLabel.setText("Added new Student " + insertNameText.getText());

          insertNameText.setText("");
          insertGrade1Text.setText("");
          insertGrade2Text.setText("");
          insertGrade3Text.setText("");
          insertGradeExamText.setText("");

        } else {
          statusLabel.setText("Enter grade between 0 and " + Student.maxGrade);
        }
      } catch (NumberFormatException ex) {
        statusLabel.setText("Incorrect number format");
      } catch (SQLException ex) {
        statusLabel.setText("Failed to insert student");
      }

    } else if (e.getSource() == selectButton) {
      String dbColumn = Student
          .getColumnFromPrintColumn((String) selectColumnCBox.getSelectedItem());

      int type = Student.getSQLType(dbColumn);

      Student[] selected = new Student[] {};
      try {
        if (type == java.sql.Types.INTEGER) {
          selected = g.selectStudent(currentTable, dbColumn,
              Integer.parseInt(selectValueText.getText()), type,
              (String) selectOperatorCBox.getSelectedItem());
        } else if (type == java.sql.Types.VARCHAR) {
          selected = g.selectStudent(currentTable, dbColumn,
              selectValueText.getText(), type,
              (String) selectOperatorCBox.getSelectedItem());
        }
        updateSelected(selected);
      } catch (SQLException ex) {
        statusLabel.setText("Unable to select");
      }
    }
  }

  public void setCurrentTable() {
    try {
      setCurrentTable(changeTableCBoxModel.getElementAt(0));
    } catch (NullPointerException ex) {
      clearCurrentTable();
    }
  }

  public void setCurrentTable(String newTable) {
    currentTable = newTable;
    try {
      updateSelected(g.selectAll(currentTable));
    } catch (SQLException e) {
      clearSelected();
    }
    currentTableLabel.setText("Using table " + currentTable);
    statusLabel.setText("Now using table " + currentTable);
  }

  public void clearCurrentTable() {
    clearSelected();
    currentTableLabel.setText("No table");
    statusLabel.setText("No table");
  }

  public void clearSelected() {
    selectOutput.removeAll();
    selectOutput.validate();
  }

  public void updateSelected(Student[] selected) {
    try {
      clearSelected();

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
    } catch (NullPointerException ex) {
    }
    selectOutput.validate();
  }
}
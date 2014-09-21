package university;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GradingTool implements ActionListener {
  private String currentTable;
  private GradesDAO g;

  private String[] operators = new String[] { "<", "<=", "=", ">=", ">", "like" };

  private JLabel currentTableLabel;
  private JButton changeTableButton;
  private JComboBox<String> changeTableCBox;
  private DefaultComboBoxModel<String> changeTableCBoxModel;
  private JButton dropTableButton;
  private JTextField newTableText;
  private JButton newTableButton;

  private JTextField insertNameText;
  private JTextField insertGrade1Text;
  private JTextField insertGrade2Text;
  private JTextField insertGrade3Text;
  private JTextField insertGradeExamText;
  private JButton insertButton;
  private DefaultComboBoxModel<String> selectColumnCBoxModel;
  private JComboBox<String> selectColumnCBox;
  private DefaultComboBoxModel<String> selectOperatorCBoxModel;
  private JComboBox<String> selectOperatorCBox;
  private JTextField selectValueText;
  private JButton selectButton;
  private JLabel selectOutput;

  public static void main(String[] args) throws SQLException, IOException,
      URISyntaxException {
    GradingTool gt = new GradingTool();
  }

  public GradingTool() throws SQLException, IOException, URISyntaxException {
    g = new GradesDAO();

    JFrame frame = new JFrame();
    frame.setTitle("Grading Tool");
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // frame.setSize(600, 600);

    JPanel panel = new JPanel();
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
    insertPanel.add(new JLabel("Name"));
    insertPanel.add(insertNameText);
    insertPanel.add(new JLabel("Grade 1"));
    insertPanel.add(insertGrade1Text);
    insertPanel.add(new JLabel("Grade 2"));
    insertPanel.add(insertGrade2Text);
    insertPanel.add(new JLabel("Grade 3"));
    insertPanel.add(insertGrade3Text);
    insertPanel.add(new JLabel("Exam Grade"));
    insertPanel.add(insertGradeExamText);
    insertPanel.add(insertButton);

    // *** Create selectPanel ***

    JPanel selectPanel = new JPanel();

    selectColumnCBoxModel = new DefaultComboBoxModel<String>(
        Student.getColumns());
    selectColumnCBox = new JComboBox<String>(changeTableCBoxModel);

    selectOperatorCBoxModel = new DefaultComboBoxModel<String>(operators);
    selectOperatorCBox = new JComboBox<String>(changeTableCBoxModel);

    selectValueText = new JTextField(15);
    selectButton = new JButton("Select");
    selectButton.addActionListener(this);

    selectOutput = new JLabel();

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
    panel.add(insertPanel);
    frame.add(selectPanel);

    frame.add(panel);
    frame.pack();
    frame.setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == changeTableButton) {
      this.currentTable = (String) changeTableCBox.getSelectedItem();
      currentTableLabel.setText("Using table " + currentTable);
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
      // validate fields
      try {
        g.insertStudent(currentTable, insertNameText.getText(),
            Integer.parseInt(insertGrade1Text.getText()),
            Integer.parseInt(insertGrade2Text.getText()),
            Integer.parseInt(insertGrade3Text.getText()),
            Integer.parseInt(insertGradeExamText.getText()));
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
    } else if (e.getSource() == selectButton) {
      // validate fields
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

      try {

        g.selectStudent(currentTable,
            (String) selectColumnCBox.getSelectedItem(), selectValueText, type,
            (String) selectOperatorCBox.getSelectedItem());
      } catch (SQLException ex) {
        System.err.println("Unable to select");
        ex.printStackTrace();
      }
    }
  }
}

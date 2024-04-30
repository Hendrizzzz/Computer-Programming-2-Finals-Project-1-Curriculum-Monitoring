import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class CurriculumGui extends JFrame {

    private JPanel menuPanel, comboBoxPanel;
    private JPanel displayCurriculum;
    private JTextArea displayArea;

    private JTable courseTable;

    private ActionListener dropDownHandler1, dropDownHandler2;
    private JComboBox<String> oneDropdown, twoDropdown;


    private static final Font font1 = new Font("Arial", Font.BOLD, 20);
    private static final Font font2 = new Font("Times New Roman", Font.BOLD, 15);

    public static void main(String[] args) {
        new CurriculumGui().CurriculumGUI();
    }

    public void CurriculumGUI(){

        courseTable = new JTable();

        setTitle("Curriculum Monitoring");
        setSize(400,400);
        setResizable(false);


        menuPanel = new JPanel();
        setMenuPanel();


        add(menuPanel, BorderLayout.NORTH);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void setMenuPanel() {
        menuPanel = new JPanel(new GridBagLayout());
        GridBagConstraints menuLayout = new GridBagConstraints();
        menuLayout.gridx = 0;
        menuLayout.gridy =0;
        menuLayout.anchor = GridBagConstraints.CENTER;

        JLabel menuHeadLabel = new JLabel("<html><div style='text-align: center;'>Personal Checklist Monitoring App</div></html>");
        menuHeadLabel.setFont(font1);
        menuPanel.add(menuHeadLabel, menuLayout);


        menuLayout.gridy = 1;
        menuLayout.insets = new Insets(10,10,10,10);
        JLabel menuChoiceLabel = new JLabel("<html><br>1. Show subjects for each school term<br><br>" +
                "2. Show subjects with grades for each term<br><br>" +
                "3. Enter grades for subjects recently finished<br><br>" +
                "4. Edit a course<br><br>" +
                "5. Quit<br><br></html>");
        menuChoiceLabel.setFont(font2);
        menuPanel.add(menuChoiceLabel, menuLayout);

        //textField
        menuLayout.gridy = 2;
        menuLayout.fill = GridBagConstraints.HORIZONTAL;
        JTextField textField = new JTextField();
        textField.setFont(font2);
        menuPanel.add(textField, menuLayout);

        textField.addActionListener(e ->{
            String input = textField.getText();
            if (input.matches("\\d{1,5}")) {
                int number = Integer.parseInt(input);
                if (number > 5) {
                    JOptionPane.showMessageDialog(this, "The number must be from 1 to 5");
                } else {
                    switch (number) {
                        case 1:
                            choiceOne();
                            break;
                        case 2:
                            System.out.println("HELLO MIGGA");
                            break;
                        case 3:
                            //Action
                            break;
                        case 4:
                            //Action
                            break;
                        case 5:
                            System.exit(0);
                            break;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "You entered a valid Integer", "Error", JOptionPane.ERROR_MESSAGE);
            }
            textField.setText("");
        });
    }


    private void choiceOne() {
        JFrame choiceOneFrame = new JFrame("Curriculum");
        choiceOneFrame.setSize(1000, 500);

        comboBoxPanel = new JPanel();
        comboBoxPanel.setBorder(new EmptyBorder(20,0,0,0));
        setComboBoxPanel();

        displayCurriculum = new JPanel();
        JScrollPane scrollPane = new JScrollPane(courseTable);
        scrollPane.setBorder(new EmptyBorder(20,20,20,20));

        //setDisplayCurriculum();

        fillTable();

        choiceOneFrame.add(comboBoxPanel, BorderLayout.NORTH);
        //choiceOneFrame.add(label);
        choiceOneFrame.add(scrollPane);
        choiceOneFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        choiceOneFrame.setVisible(true);
    }

    private void fillTable() {

        twoDropdown.addActionListener(e -> fillTable());

        CurriculumManagement curriculum = new CurriculumManagement();

        curriculum.reset();
        curriculum.fillCurriculum();

        ArrayList<Course> list = curriculum.getCurriculum();
        ArrayList<Course> filtered = new ArrayList<>();

        String selectedYear = (String) oneDropdown.getSelectedItem();
        String selectedSemester = (String) twoDropdown.getSelectedItem();

        byte yearToInt = 0;

        switch (Objects.requireNonNull(selectedYear)) {
            case "1st Year" -> yearToInt = 1;
            case "2nd Year" -> yearToInt = 2;
            case "3rd Year" -> yearToInt = 3;
            case "4th Year" -> yearToInt = 4;
        }

        for (Course course : list)
            if (course.getYear() == yearToInt && course.getTerm().equals(selectedSemester)) filtered.add(course);

        String[] column = new String[]{"Course No.", "Course Title", "Units"};
        String[][] data = new String[filtered.size()][3];

        for (int i = 0; i < filtered.size(); i++) {
            Course course = filtered.get(i);
            data[i][0] = course.getCourseNumber();
            data[i][1] = course.getDescriptiveTitle();
            data[i][2] = "" + course.getUnits();
        }

        courseTable.setModel(new DefaultTableModel(data, column));
        TableColumnModel columnModel = courseTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setPreferredWidth(700);
        columnModel.getColumn(2).setPreferredWidth(40);

    }

    private void setComboBoxPanel() {

        String[] option1 = {"1st Year","2nd Year","3rd Year","4th Year"};
        String[] option2 = {"First Semester", "Second Semester", "Short Term"};

        oneDropdown = new JComboBox<>(option1);
        oneDropdown.setPreferredSize(new Dimension(150,40));
        oneDropdown.setFont(font2);

        twoDropdown = new JComboBox<>(option2);
        twoDropdown.setPreferredSize(new Dimension(150,40));
        twoDropdown.setFont(font2);

        oneDropdown.addActionListener(e -> {
            String selected = (String) oneDropdown.getSelectedItem();
            if (selected.equals("4th Year")) {
                String[] newOption = {"First Semester", "Second Semester"};
                twoDropdown.setModel(new DefaultComboBoxModel<>(newOption));
                fillTable();

            } else if (selected.equals("1st Year") || selected.equals("2nd Year") || selected.equals("3rd Year")) {
                twoDropdown.setModel(new DefaultComboBoxModel<>(option2));
                fillTable();
            }
        });

        comboBoxPanel.add(oneDropdown);
        comboBoxPanel.add(twoDropdown);

    }



    //PROBLEM
    private void setDisplayCurriculum() {
        CurriculumManagement curriculum = new CurriculumManagement();
        curriculum.fillCurriculum();
        ArrayList<Course> courses = curriculum.getCurriculum();

        StringBuilder formattedText = new StringBuilder();

        formattedText.append("------------------------------------------------------------------------------------------------\n");
        formattedText.append(String.format("%-20s%-20s%-40s%-10s%-10s\n", "Year", "Term", "Course No. Descriptive title", "Units", "Grade"));
        formattedText.append("------------------------------------------------------------------------------------------------\n");

        //course details
        for (Course course : courses) {
            formattedText.append(String.format("%-20s%-20s%-40s%-10.1s%-10s\n",
                    course.getYear(),
                    course.getTerm(),
                    course.getCourseNumber() + " " + course.getDescriptiveTitle(),
                    course.getUnits(),
                    course.getGrade() == 0 ? "Not Yet Taken" : String.valueOf(course.getGrade())));
        }
        displayArea = new JTextArea(formattedText.toString());
        displayArea.setFont(font2);
        displayArea.setEditable(false);

        displayCurriculum.removeAll();

        displayCurriculum.revalidate();
        displayCurriculum.repaint();
    }



}

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class CurriculumGui extends JFrame {
    private JPanel menuPanel, comboBoxPanel;
    private JPanel displayCurriculum;
    private JTextArea displayArea;

    private ActionListener dropDownHandler1, dropDownHandler2;
    private JComboBox<String> oneDropdown, twoDropdown;


    private static final Font font1 = new Font("Arial", Font.BOLD, 20);
    private static final Font font2 = new Font("Times New Roman", Font.BOLD, 15);

    public static void main(String[] args) {
        new CurriculumGui().CurriculumGUI();
    }

    public void CurriculumGUI(){
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
        setComboBoxPanel();


        displayCurriculum = new JPanel();

        setDisplayCurriculum();

        choiceOneFrame.add(comboBoxPanel, BorderLayout.NORTH);
        choiceOneFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        choiceOneFrame.setVisible(true);
    }

    private void setComboBoxPanel() {

        String[] option1 = {"1st Year","2nd Year","3rd Year","4th Year"};
        oneDropdown = new JComboBox<>(option1);
        oneDropdown.setPreferredSize(new Dimension(150,40));
        oneDropdown.setFont(font2);

        String[] option2 = {"First Semester", "Second Semester", "Short Term"};
        twoDropdown = new JComboBox<>(option2);
        oneDropdown.setPreferredSize(new Dimension(150,40));
        oneDropdown.setFont(font2);

        comboBoxPanel = new JPanel();
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

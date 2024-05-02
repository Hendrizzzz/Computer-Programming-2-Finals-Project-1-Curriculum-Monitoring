import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CurriculumGUI {
    private JFrame frame;
    private JComboBox<String> yearDropdown, termDropdown;
    private JButton fillButton, saveButton;
    private JTable courseTable;
    private DefaultTableModel model;
    private CurriculumManager curriculumManager;

    public CurriculumGUI(CurriculumManager curriculumManager) {
        this.curriculumManager = curriculumManager;

        frame = new JFrame("Curriculum Monitoring");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Center the frame on the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int xPos = (screenSize.width - frame.getWidth()) / 2;
        int yPos = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(xPos, yPos);

        // Create year and term dropdowns
        yearDropdown = new JComboBox<>(new String[]{"1", "2", "3", "4"});
        termDropdown = new JComboBox<>(new String[]{"First Sem", "Second Sem", "Short Term"});

        // Create buttons for filling and saving curriculum
        fillButton = new JButton("Fill Curriculum");
        fillButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fillCurriculum();
            }
        });

        saveButton = new JButton("Save Curriculum");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveCurriculum();
            }
        });

        // Create the course table
        courseTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(courseTable);

        // Create the button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(fillButton);
        buttonPanel.add(saveButton);

        // Create the combo box panel
        JPanel comboBoxPanel = new JPanel();
        comboBoxPanel.setLayout(new GridLayout(1, 2));
        comboBoxPanel.add(yearDropdown);
        comboBoxPanel.add(termDropdown);

        // Add panels to the frame
        frame.add(comboBoxPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Show the frame
        frame.setVisible(true);
    }

    private void fillCurriculum() {
        String selectedYear = (String) yearDropdown.getSelectedItem();
        String selectedTerm = (String) termDropdown.getSelectedItem();
        curriculumManager.fillCurriculum(selectedYear, selectedTerm);
        displayCurriculum();
    }
    private void saveCurriculum() {
        curriculumManager.saveCurriculum();
    }

    public void displayCurriculum() 
        ArrayList<Course> curriculum = curriculumManager.getCurriculum();
        Object[][] data = new Object[curriculum.size()][6];

        for (int i = 0; i < curriculum.size(); i++) {
            Course course = curriculum.get(i);
            data[i][0] = course.getYear();
            data[i][1] = course.getTerm();
            data[i][2] = course.getCourseNumber();
            data[i][3] = course.getDescriptiveTitle();
            data[i][4] = course.getUnits();
            data[i][5] = course.getGrade() == 0 ? "Not Yet Taken" : course.getGrade();
        }

        String[] columnNames = {"Year", "Term", "Course Number", "Course Title", "Units", "Grade"};
        model = new DefaultTableModel(data, columnNames);
        courseTable.setModel(model);
    }
    public static void main(String[] args) {
        CurriculumManager curriculumManager = new CurriculumManagement();
        SwingUtilities.invokeLater(() -> new CurriculumGUI(curriculumManager));
    }

}

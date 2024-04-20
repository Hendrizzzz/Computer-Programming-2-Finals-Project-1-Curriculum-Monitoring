import java.io.*;
import java.util.ArrayList;

public class CurriculumManagement implements CurriculumManager{
    private static final String fileName = "Curriculum.txt";
    private static final ArrayList<Course> courses = new ArrayList<>();

    @Override
    public void fillCurriculum() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null){
                String[] courseText = line.split(",");
                Course course = readCourse(courseText);
                courses.add(course);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    private Course readCourse(String[] courseText) {
        byte year = Byte.parseByte(courseText[0]);
        String term = courseText[1];
        String courseNumber = courseText[3];
        byte units = Byte.parseByte(courseText[4]);

        if (courseText[3].equals("Not Yet Taken")){
            return new Course(year, term, courseText[2], courseNumber, units);
        } else {
            byte grade = Byte.parseByte(courseText[5]);
            return new Course(year, term, courseText[2], courseNumber, units, grade);
        }
    }



    @Override
    public ArrayList<Course> showCurriculum() {
        return courses;
    }



    @Override
    public void editGrade(ArrayList<Byte> grades, int year, String term) {
        int count = 0;
        for (Course course : courses){
            if (course.getYear() == year && course.getTerm().equals(term)){
                course.setGrade(grades.get(count++));
            }
        }
    }



    @Override
    public void editCourse(ArrayList<String[]> courseDetails, int year, String term) {
        int count = 0;
        for (Course course : courses){
            if (course.getYear() == year && course.getTerm().equals(term)){
                course.setCourseNumber(courseDetails.get(count++)[0]);
                course.setDescriptiveTitle(courseDetails.get(count++)[1]);
                course.setUnits(Byte.parseByte(courseDetails.get(count++)[2]));
            }
        }
    }



    @Override
    public void saveCurriculum() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))){
            for (Course course : courses){
                writer.write(course.getYear() + "," + course.getTerm() + "," + course.getCourseNumber() +
                                course.getDescriptiveTitle() + "," + course.getUnits() + "," + course.getGrade() + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

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
    public ArrayList<Course> getCurriculum() {
        return courses;
    }



    @Override
    public void editGrade(ArrayList<Byte> grades, int year, String term) throws ValueOutOfRangeException {
        int count = 0;
        for (Course course : courses){
            if (course.getYear() == year && course.getTerm().equals(term)){

                if (notValid(grades.get(count), (byte) 65, (byte) 99)){
                    throw new ValueOutOfRangeException();
                }
                course.setGrade(grades.get(count++));
            }
        }
    }

    private boolean notValid(Byte number, byte minimum, byte maximum) {
        return number > maximum || number < minimum;
    }


    @Override
    public void editCourse(ArrayList<String[]> courseDetails, int year, String term) throws ValueOutOfRangeException {
        int count = 0;
        for (Course course : courses){
            if (course.getYear() == year && course.getTerm().equals(term)){
                if (notValid(Byte.parseByte(courseDetails.get(count)[2]), (byte) 1, (byte) 3)){
                    throw new ValueOutOfRangeException();
                }
                course.setCourseNumber(courseDetails.get(count)[0]);
                course.setDescriptiveTitle(courseDetails.get(count)[1]);
                course.setUnits(Byte.parseByte(courseDetails.get(count++)[2]));
            }
        }
    }

    @Override
    public double calculateGPA() {
        int units = 0;
        double score = 0;
        for (Course course : courses){
            if (course.getGrade() == 0){
                continue;
            }
            score += course.getGrade();
            units += course.getUnits();
        }
        return score/units;
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

class ValueOutOfRangeException extends Exception{
    public ValueOutOfRangeException(){
        super();
    }
}

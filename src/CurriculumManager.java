import java.util.ArrayList;

public interface CurriculumManager {
    void fillCurriculum();
    void saveCurriculum();

    ArrayList<Course> showCurriculum();
    void editGrade(ArrayList<Byte> grades, int year, String term);
    void editCourse(ArrayList<String[]> courseDetails, int year, String term);
}

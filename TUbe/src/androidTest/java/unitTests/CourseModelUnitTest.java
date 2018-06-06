package unitTests;

import com.group.tube.Enums.Semester;
import com.group.tube.Models.Course;
import com.group.tube.Models.Episode;

import org.junit.Test;

import java.util.ArrayList;

import static com.group.tube.utils.TestDataGenerator.getRandomDate;
import static org.junit.Assert.assertEquals;

public class CourseModelUnitTest {
    @Test
    public void checkConstructorOfCourseModel() throws Exception {
        String id = "1";
        String courseTitle = "this course";

        Course course = new Course(id, courseTitle);

        assertEquals(id, course.getId(), id);
        assertEquals(courseTitle, course.getCourseTitle());
    }

    @Test
    public void checkGetterAndSetter() throws Exception {

        Course course = new Course("1", "course");

        Episode episode = new Episode("1", "course", "episode",
                "presenter url", "presentation url", getRandomDate(), "00:00:00");

        String id = "2";
        String courseTitle = "new course";

        course.setId(id);
        course.setCourseTitle(courseTitle);

        ArrayList<Episode> episodes = new ArrayList<Episode>();
        episodes.add(episode);

        assertEquals(id, course.getId());
        assertEquals(courseTitle, course.getCourseTitle());
        assertEquals("WS 00", course.getSemesterString());
        assertEquals(0, course.getSemesterYear());
        assertEquals(true, course.isWs());
    }

    @Test
    public void checkEnumSemester() {
        Semester semester = Semester.WS;
        assertEquals(Semester.WS, semester);

        semester = Semester.SS;
        assertEquals(Semester.SS, semester);
    }
}
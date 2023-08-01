package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebTestingFacultyTests {

    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(facultyController).isNotNull();
    }

    @Test
    void testPostFaculty() throws Exception {
        Faculty faculty1 = new Faculty();
        faculty1.setName("Griffindor");
        faculty1.setColor("red");

        Faculty faculty2 = new Faculty();
        faculty2.setName("Slytherin");
        faculty2.setColor("green");

        Assertions
                .assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/faculty", faculty1, String.class))
                .isNotNull();

        Assertions
                .assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/faculty", faculty2, String.class))
                .isNotNull();

    }

    @Test
    void testGetFaculties() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty", String.class))
                .isNotNull();
    }

    @Test
    void testGetFaculty() throws Exception {
        Faculty expected = new Faculty();
        expected.setId(1);
        expected.setName("Griffindor");
        expected.setColor("red");

        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty/1", Faculty.class))
                .isEqualTo(expected);
    }

    @Test
    void testPutFaculty() throws Exception {
        Faculty exp = new Faculty();
        exp.setId(2);
        exp.setName("Ravencrow");
        exp.setColor("blue");

        this.restTemplate.put("http://localhost:" + port + "/faculty", exp, String.class);

        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty/2", Faculty.class))
                .isEqualTo(exp);
    }

    @Test
    void testDeleteFaculty() throws Exception {
        this.restTemplate.delete("http://localhost:" + port + "/faculty/2", Student.class);

        Faculty expected = new Faculty();
        expected.setId(0);
        expected.setName(null);
        expected.setColor(null);

        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty/2", Faculty.class))
                .isEqualTo(expected);
    }
}

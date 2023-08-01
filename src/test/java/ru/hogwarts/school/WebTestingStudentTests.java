package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;

import static com.fasterxml.jackson.databind.type.LogicalType.Collection;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WebTestingStudentTests {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    void testPostStudent() throws Exception {
        Student student1 = new Student();
        student1.setName("Harry");
        student1.setAge(13);

        Student student2 = new Student();
        student2.setName("Ron");
        student2.setAge(14);

        Student student3 = new Student();
        student3.setName("John");
        student3.setAge(15);

        Assertions
                .assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student", student1, String.class))
                .isNotNull();

        Assertions
                .assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student", student2, String.class))
                .isNotNull();

        Assertions
                .assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student", student3, String.class))
                .isNotNull();
    }

    @Test
    void testGetStudents() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student", String.class))
                .isNotNull();
    }

    @Test
    void testGetStudent() throws Exception {
        Student expected = new Student();
        expected.setId(1);
        expected.setName("Harry");
        expected.setAge(13);

        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/1", Student.class))
                .isEqualTo(expected);
    }

    @Test
    void testPutStudents() throws Exception {
        Student exp = new Student();
        exp.setId(2);
        exp.setName("Hermione");
        exp.setAge(13);

        this.restTemplate.put("http://localhost:" + port + "/student", exp, String.class);

        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/2", Student.class))
                .isEqualTo(exp);
    }

    @Test
    void testDeleteStudents() throws Exception {
        this.restTemplate.delete("http://localhost:" + port + "/student/3", Student.class);

        Student expected = new Student();
        expected.setId(0);
        expected.setName(null);
        expected.setAge(0);

        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/3", Student.class))
                .isEqualTo(expected);
    }

}

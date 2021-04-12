package ge.tsu.spring.lecture4.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/students")
    public List<StudentView> getStudents() throws IOException {
        return studentService.getList();
    }

    @GetMapping("/students/{studentId}")
    public StudentView getStudent(@PathVariable String studentId) throws RecordNotFoundException, IOException {
        return studentService.getById(studentId);
    }

    @PostMapping("/students")
    public void addStudent(@RequestBody AddStudent addStudent) throws RecordAlreadyExistsException, IOException {
        studentService.add(addStudent);
    }

    @DeleteMapping("/students/{studentId}")
    public void deleteStudent(@PathVariable String studentId) throws RecordNotFoundException, IOException {
        studentService.delete(studentId);
    }
}

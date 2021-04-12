package ge.tsu.spring.lecture4.student;

import java.io.IOException;
import java.util.List;

public interface StudentService {
    void add(AddStudent addStudent) throws RecordAlreadyExistsException, IOException;
    List<StudentView> getList() throws IOException;
    StudentView getById(String id) throws RecordNotFoundException, IOException;
    void delete(String id) throws RecordNotFoundException, IOException;
}

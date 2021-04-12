package ge.tsu.spring.lecture4.student.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import ge.tsu.spring.lecture4.student.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Service
public class StudentServiceImpl implements StudentService {

    private static final String JSON_DATA = "/home/romiko/Desktop/Java/lecture4/src/main/java/ge/tsu/spring/lecture4/data/students.json";

    @Override
    public List<StudentView> getList() throws IOException {
        Reader reader = Files.newBufferedReader(Paths.get(JSON_DATA));

        Type listType = new TypeToken<ArrayList<StudentView>>(){}.getType();
        List<StudentView> studentViews = new Gson().fromJson(reader, listType);
        reader.close();

        return studentViews;
    }

    @Override
    public StudentView getById(String id) throws IOException, RecordNotFoundException {
        Reader reader = Files.newBufferedReader(Paths.get(JSON_DATA));

        Type listType = new TypeToken<ArrayList<StudentView>>(){}.getType();
        List<StudentView> studentViews = new Gson().fromJson(reader, listType);
        reader.close();

        for (StudentView studentView: studentViews){
            if (studentView.getId().equals(id)){
                return studentView;
            }
        }

        throw new RecordNotFoundException("Unable to find student with specified id");
    }

    @Override
    public void add(AddStudent addStudent) throws IOException, RecordAlreadyExistsException {
        Reader reader = Files.newBufferedReader(Paths.get(JSON_DATA));

        Type listType = new TypeToken<ArrayList<StudentView>>(){}.getType();
        List<StudentView> studentViews = new Gson().fromJson(reader, listType);
        reader.close();

        if (studentViews != null){
            Optional<StudentView> exists = studentViews
                    .stream()
                    .filter(it -> addStudent.getName().equals(it.getName()) && it.getSurname().equals(addStudent.getSurname()))
                    .findFirst();
            if (exists.isPresent()) {
                throw new RecordAlreadyExistsException(
                        String.format("Student with name: %s and surname: %s already exists", addStudent.getName(), addStudent.getSurname()));
            }
        }else studentViews = new ArrayList<StudentView>();

        studentViews.add(new StudentView(
                UUID.randomUUID().toString(),
                addStudent.getName(),
                addStudent.getSurname(),
                addStudent.getAge()
        ));

        Writer writer = Files.newBufferedWriter(Paths.get(JSON_DATA));
        new GsonBuilder().setPrettyPrinting().create().toJson(studentViews, writer);
        writer.close();
    }

    @Override
    public void delete(String id) throws IOException, RecordNotFoundException {
        Reader reader = Files.newBufferedReader(Paths.get(JSON_DATA));

        Type listType = new TypeToken<ArrayList<StudentView>>(){}.getType();
        List<StudentView> studentViews = new Gson().fromJson(reader, listType);
        reader.close();

        Iterator<StudentView> studentViewIterator = studentViews.iterator();

        while (studentViewIterator.hasNext()) {
            StudentView studentView = studentViewIterator.next();
            if (studentView.getId().equals(id)) {
                studentViewIterator.remove();

                Writer writer = Files.newBufferedWriter(Paths.get(JSON_DATA));
                new GsonBuilder().setPrettyPrinting().create().toJson(studentViews, writer);
                writer.close();

                return;
            }
        }
        throw new RecordNotFoundException("Unable to find student with specified id");
    }
}

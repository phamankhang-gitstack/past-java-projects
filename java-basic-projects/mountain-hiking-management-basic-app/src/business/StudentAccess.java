/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business;

/**
 *
 * @author trang
 */
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import models.Student;

public class StudentAccess {
    private static final String DATA_FILE_PATH = System.getProperty("user.dir") + "/registrations.dat";
    private static final String RECOVERY_FILE_PATH = System.getProperty("user.dir") + "/recovery_registrations.dat";
    private List<Student> studentList;

    public StudentAccess() {
        this.studentList = new ArrayList<>();
        if (hasRecoveryData()) {
            // load main data for now, and the program will check the recovery file later.
            readFromFile(DATA_FILE_PATH);
        } else readFromFile(DATA_FILE_PATH);
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public Student getStudentById(String id) { // get only one student object via stream().filter(s -> ...)
        return studentList.stream().filter(s -> s.getId().equalsIgnoreCase(id)).findFirst().orElse(null);
    }

    public void addStudent(Student student) {
        studentList.add(student);
    }
    
    public boolean deleteStudent(String id) {
        Student studentToDelete = getStudentById(id);
        if (studentToDelete != null) {
            studentList.remove(studentToDelete);
            return true;
        }
        return false;
    }
    
    private boolean saveToFile(String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(studentList);
            return true;
        } catch (IOException e) {
            System.err.println("Error saving data to " + filePath + ": " + e.getMessage());
            return false;
        }
    }

    private boolean readFromFile(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            Object obj = ois.readObject();
            if (obj instanceof List) this.studentList = (List<Student>) obj;
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("File not found. Starting with an empty list.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading data from " + filePath + ". Starting with an empty list. Error: " + e.getMessage());
        }
        return false;
    }
    
    public boolean saveToRecoveryFile() {
        return saveToFile(RECOVERY_FILE_PATH);
    }
    
    public boolean readFromRecoveryFile() {
        return readFromFile(RECOVERY_FILE_PATH);
    }

    public boolean hasRecoveryData() {
        File recoveryFile = new File(RECOVERY_FILE_PATH);
        return recoveryFile.exists() && recoveryFile.length() > 0;
    }
    
    public void deleteRecoveryFile() {
        new File(RECOVERY_FILE_PATH).delete();
    }
    
    public boolean saveToFile() {
        boolean success = saveToFile(DATA_FILE_PATH);
        if (success) deleteRecoveryFile();
        return success;
    }
}
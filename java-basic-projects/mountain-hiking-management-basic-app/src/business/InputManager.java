/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business;

/**
 *
 * @author trang
 */
import java.util.List;
import models.MountainCode;
import models.Student;
import models.StatisticRecord;
import tools.Inputter;
import tools.Validation;

public class InputManager {
    private DataController controller;
    private Inputter inputter;
    
    public InputManager(DataController controller, Inputter inputter) {
        this.controller = controller;
        this.inputter = inputter;
    }
    
    public void checkRecoveryData() {
        if (controller.hasRecoveryData()) {
            System.out.println("--------------------------------------------------------------------");
            System.out.println("!!! AUTO-RECOVERY DATA FOUND !!!");
            System.out.println("It appears the program crashed during the last session.");
            String choice = inputter.getConfirmation("Do you want to load the unsaved recovery data? (Y/N): ");
            if (choice.equals("Y")) {
                if (controller.loadRecoveryData()) {
                    System.out.println("Recovery data loaded successfully. Initiating new session with unsaved changes.");
                } else {
                    System.out.println("Failed to load recovery data. Initiating new session using primary file.");
                    controller.discardRecoveryData(); // clean up corrupted recovery file
                }
            } else {
                controller.discardRecoveryData();
                System.out.println("Recovery data discarded. Initiating brand new session.");
            }
            System.out.println("--------------------------------------------------------------------");
        }
    }
    
    public void newRegistration() {
        List<Student> currentStudents = controller.getStudentList();
        String id = inputter.getValidId(currentStudents);
        String name = inputter.getValidName();
        String phone = inputter.getValidPhone(currentStudents);
        String email = inputter.getValidEmail(currentStudents);
        controller.displayAvailableMountains();
        String mountainCode = inputter.getValidMountainCode(controller.getMountainMap());
        double tuitionFee = controller.calculateTuitionFee(phone);
        Student newStudent = new Student(id, name, phone, email, mountainCode, tuitionFee);
        controller.add(newStudent);
        System.out.println("Registration successful!");
    }
    
    public void updateRegistration() {
        String id = inputter.getString("Enter the Student ID to update: ");
        if (!Validation.checkId(id)) {
            System.out.println("Invalid student ID.");
            return;
        }
        Student student = controller.getStudentById(id);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        System.out.println("Current Information:");
        System.out.printf("| %-10s | %-20s | %-12s | %-30s | %-10s | %-10s |\n", "Student ID", "Name", "Phone", "Email", "Peak Code", "Fee");
        System.out.println("----------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-10s | %-20s | %-12s | %-30s | %-10s | %-10.0f |\n", 
            student.getId(), student.getName(), student.getPhone(), student.getEmail(), student.getMountainCode(), student.getTuitionFee());
        boolean updated = false; 
        String newName;
        boolean nameValid;
        do {
            newName = inputter.getString(String.format("Enter new Name (currently: %s, leave blank to skip): ", student.getName()));
            nameValid = newName.isEmpty() || Validation.checkName(newName);
            if (!nameValid) System.out.println("Invalid name format.");
        } while (!nameValid);
        if (!newName.isEmpty() && !newName.equals(student.getName())) {
            student.setName(newName);
            updated = true;
            System.out.println("Name updated.");
        }
        String newPhone;
        boolean phoneValid;
        do {
            newPhone = inputter.getString(String.format("Enter new Phone (currently: %s, leave blank to skip): ", student.getPhone()));
            phoneValid = newPhone.isEmpty() || (Validation.checkPhone(newPhone) && Validation.checkDuplicate(newPhone, controller.getStudentList(), "phone"));
            if (!phoneValid) {
                if (!newPhone.isEmpty()) System.out.println(Validation.checkPhone(newPhone) ? 
                        "This phone number is already registered." : "Invalid phone number format.");
            }
        } while (!phoneValid);
        if (!newPhone.isEmpty() && !newPhone.equals(student.getPhone())) {
            student.setPhone(newPhone);
            student.setTuitionFee(controller.calculateTuitionFee(newPhone)); 
            updated = true;
            System.out.println("Phone number updated.");
        }
        String newEmail;
        boolean emailValid;
        do {
            newEmail = inputter.getString(String.format("Enter new Email (currently: %s, leave blank to skip): ", student.getEmail()));
            emailValid = newEmail.isEmpty() || (Validation.checkEmail(newEmail) && Validation.checkDuplicate(newEmail, controller.getStudentList(), "email"));
            if (!emailValid) {
                 if (!newEmail.isEmpty()) System.out.println(Validation.checkEmail(newEmail) ? 
                        "This email is already registered." : "Invalid email format.");
            }
        } while (!emailValid);
        if (!newEmail.isEmpty() && !newEmail.equals(student.getEmail())) {
            student.setEmail(newEmail);
            updated = true;
            System.out.println("Email updated.");
        }
        controller.displayAvailableMountains();
        String newMountainCode;
        boolean mountainCodeValid;
        do {
            newMountainCode = inputter.getString(String.format("Enter new Mountain Code (currently: %s, leave blank to skip): ", student.getMountainCode()));
            mountainCodeValid = newMountainCode.isEmpty() || Validation.checkMountainCode(newMountainCode, controller.getMountainMap());
            if (!mountainCodeValid) System.out.println("Invalid mountain code.");
        } while (!mountainCodeValid);
        if (!newMountainCode.isEmpty()) {
            String newNormalizedCode = new MountainCode(newMountainCode).getCode();
            if (!newNormalizedCode.equals(student.getMountainCode())) {
                student.setMountainCode(newMountainCode);
                updated = true;
                System.out.println("Mountain Code updated.");
            }
        }
        if (updated) { // set unsaved flag only if updated
            controller.setUnsaved(true);
            System.out.println("The registration has been successfully updated.");
        } else System.out.println("No updates. Keeping registration information.");
    }
    
    public void displayRegistration() {
        List<Student> students = controller.getStudentList();
        if (students.isEmpty()) {
            System.out.println("No registrations to display.");
            return;
        }
        System.out.println("Registered Students:");
        System.out.printf("| %-10s | %-20s | %-12s | %-30s | %-10s | %-20s | %-10s |\n", "Student ID", "Name", "Phone", "Email", "Peak Code", "Peak Name", "Fee");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");
        for (Student s : students) {
            System.out.printf("| %-10s | %-20s | %-12s | %-30s | %-10s | %-20s | %-10.0f |\n", 
                    s.getId(), s.getName(), s.getPhone(), s.getEmail(), s.getMountainCode(), controller.getMountainName(s.getMountainCode()), s.getTuitionFee());
        }
    }

    public void deleteRegistration() {
        String id = inputter.getString("Enter the Student ID to delete: ");
        if (!Validation.checkId(id)) {
            System.out.println("Invalid student ID.");
            return;
        }
        Student studentToDelete = controller.getStudentById(id);
        if (studentToDelete == null) {
            System.out.println("This student has not registered yet.");
            return;
        }
        System.out.println("Student Details:");
        System.out.println("------------------------------------------");
        System.out.println("Student ID: " + studentToDelete.getId());
        System.out.println("Name: " + studentToDelete.getName());
        System.out.println("Phone: " + studentToDelete.getPhone());
        System.out.println("Email: " + studentToDelete.getEmail());
        System.out.println("Mountain: " + studentToDelete.getMountainCode());
        System.out.println("Fee: " + controller.formatFee(studentToDelete.getTuitionFee()));
        System.out.println("------------------------------------------");
        String confirm = inputter.getConfirmation("Are you sure you want to delete this registration? (Y/N): ");
        if (confirm.equalsIgnoreCase("Y")) {
            if (controller.delete(id)) {
                System.out.println("The registration has been successfully deleted.");
            } else System.out.println("Failed to delete registration.");
        } else System.out.println("Deletion cancelled.");
    }

    public void search() {
        String name = inputter.getString("Enter the name or a partial name to search: ");
        if (name.trim().isEmpty()) {
            System.out.println("Empty search input. Failed to initiate.");
            return;
        }
        List<Student> matchingStudents = controller.searchByName(name);
        if (matchingStudents.isEmpty()) {
            System.out.println("No one matches the search criteria!");
            return;
        }
        System.out.println("Matching Students:");
        System.out.printf("| %-10s | %-20s | %-12s | %-30s | %-10s | %-10s |\n", "Student ID", "Name", "Phone", "Email", "Peak Code", "Fee");
        System.out.println("----------------------------------------------------------------------------------------------------------------");
        for (Student s : matchingStudents) {
            System.out.printf("| %-10s | %-20s | %-12s | %-30s | %-10s | %-10.0f |\n", 
                    s.getId(), s.getName(), s.getPhone(), s.getEmail(), s.getMountainCode(), s.getTuitionFee());
        }
    }

    public void filterByCampus() {
        String campusCode = controller.getCampusCode(inputter.getString("Enter the campus code (SE, HE, DE, QE, CE): ").toUpperCase());
        if (campusCode.trim().isEmpty()) {
            System.out.println("Empty filter input. Failed to initiate.");
            return;
        }
        List<Student> filteredStudents = controller.filterByCampusCode(campusCode);
        if (filteredStudents.isEmpty()) {
            System.out.println("No students have registered under this campus.");
            return;
        }
        String campusName = controller.getCampusName(campusCode);
        System.out.printf("Registered Students Under %s (%s) Campus:\n", campusName, campusCode);
        System.out.printf("| %-10s | %-20s | %-12s | %-30s | %-10s | %-10s |\n", "Student ID", "Name", "Phone", "Email", "Peak Code", "Fee");
        System.out.println("----------------------------------------------------------------------------------------------------------------");
        for (Student s : filteredStudents) {
            System.out.printf("| %-10s | %-20s | %-12s | %-30s | %-10s | %-10.0f |\n", 
                    s.getId(), s.getName(), s.getPhone(), s.getEmail(), s.getMountainCode(), s.getTuitionFee());
        }
        double total = controller.getTotalTuitionCostForList(filteredStudents);
        System.out.println("----------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-94s | %-10.0f |\n", String.format("Total Cost of Registrations Under %s (%s) Campus:", campusName, campusCode), total);
    }
    
    public void filterByMountainCode() {
        String mountainCode = inputter.getValidMountainCode(controller.getMountainMap());
        List<Student> filteredStudents = controller.filterByPeakCode(mountainCode);
        if (filteredStudents.isEmpty()) {
            System.out.println("No students have registered under this campus.");
            return;
        }
        System.out.printf("Registered Students for %s:\n", mountainCode);
        System.out.printf("| %-10s | %-20s | %-12s | %-30s | %-10s | %-10s |\n", "Student ID", "Name", "Phone", "Email", "Peak Code", "Fee");
        System.out.println("----------------------------------------------------------------------------------------------------------------");
        for (Student s : filteredStudents) {
            System.out.printf("| %-10s | %-20s | %-12s | %-30s | %-10s | %-10.0f |\n", 
                    s.getId(), s.getName(), s.getPhone(), s.getEmail(), s.getMountainCode(), s.getTuitionFee());
        }
        double total = controller.getTotalTuitionCostForList(filteredStudents);
        System.out.println("----------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-94s | %-10.0f |\n", String.format("Total Cost of Registrations for %s:", mountainCode), total);
    }
    
    public void displayStatistics() {
        List<StatisticRecord> records = controller.getStatistics();
        if (records.isEmpty()) {
            System.out.println("No registrations to generate statistics.");
            return;
        }
        System.out.println("Statistics of Registration by Mountain Peak:");
        System.out.printf("| %-10s | %-22s | %-22s | %-12s |\n", 
            "Peak Code", "Peak Name", "Number of Participants", "Total Cost"); 
        System.out.println("--------------------------------------------------------------------------------");
        for (StatisticRecord r : records) {
            System.out.printf("| %-10s | %-22s | %-22d | %-12.0f |\n", 
                    r.getMountainCode(), r.getMountainName(), r.getParticipants(), r.getTotalCost());
        }
        double total = controller.getTotalTuitionCost();
        System.out.println("--------------------------------------------------------------------------------");
        System.out.printf("| %-60s | %-12.0f |\n", "Total Cost of All Registrations:", total);
    }
    
    public void displayMaxMinStatistics() {
        List<StatisticRecord> records = controller.getStatistics();
        if (records.isEmpty()) {
            System.out.println("No registrations to generate statistics.");
            return;
        }
        List<StatisticRecord> maxRecords = controller.getMaxParticipantsMountains(records);
        System.out.println("Mountain Peak(s) with Maximum Participants:");
        System.out.printf("| %-10s | %-22s | %-22s | %-12s |\n", 
            "Peak Code", "Peak Name", "Number of Participants", "Total Cost"); 
        System.out.println("--------------------------------------------------------------------------------");
        for (StatisticRecord r : maxRecords) {
            System.out.printf("| %-10s | %-22s | %-22d | %-12.0f |\n", 
                    r.getMountainCode(), r.getMountainName(), r.getParticipants(), r.getTotalCost());
        }
        List<StatisticRecord> minRecords = controller.getMinParticipantsMountains(records);
        System.out.println("\nMountain Peak(s) with Minimum Participants:");
        System.out.printf("| %-10s | %-22s | %-22s | %-12s |\n", 
            "Peak Code", "Peak Name", "Number of Participants", "Total Cost"); 
        System.out.println("--------------------------------------------------------------------------------");
        for (StatisticRecord r : minRecords) {
            System.out.printf("| %-10s | %-22s | %-22d | %-12.0f |\n", 
                    r.getMountainCode(), r.getMountainName(), r.getParticipants(), r.getTotalCost());
        }
    }
    
    public void saveData() {
        boolean saved = controller.saveChanges();
        if (saved) {
            System.out.println("Changes saved.");
        } else {
            System.out.println("Failed to save changes. Please check file permissions and try again.");
        }
    }
    
    public void save() {
        if (!controller.getUnsaved()) {
            System.out.println("Data is up to date. No saving needed.");
            return;
        }
        String confirm = inputter.getConfirmation("Do you want to save changes right now? (Y/N): ");
        if (confirm.equalsIgnoreCase("Y")) {
            saveData();
        } else {
            System.out.println("Saving cancelled. Resuming unsaved session.");
        }
    }
    
    public void exit() {
        if (controller.getUnsaved()) {
            String confirm = inputter.getConfirmation("Do you want to save changes before exiting? (Y/N): ");
            if (confirm.equalsIgnoreCase("Y")) {
                saveData();
            } else {
                System.out.println("Saving cancelled.");
                controller.discardRecoveryData();
            }
        }
        System.out.println("Exiting.");
        System.exit(0);
    }
}
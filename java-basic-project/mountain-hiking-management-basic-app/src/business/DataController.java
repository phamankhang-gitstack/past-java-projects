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
import java.util.Map;
import java.util.List;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.Locale;
import java.text.NumberFormat;
import models.Student;
import models.Mountain;
import models.MountainCode;
import models.StatisticRecord;
import tools.Validation;

public class DataController {
    private StudentAccess access;
    private Map<String, Mountain> mountainMap;
    private boolean unsaved;

    public DataController(StudentAccess access, Map<String, Mountain> mountainMap) {
        this.access = access;
        this.mountainMap = mountainMap;
        this.unsaved = false;
    }
    
    public Map<String, Mountain> getMountainMap() {
        return mountainMap;
    }
    
    public String getCampusName(String code) {
        switch (code) {
            case "CE": return "Can Tho";
            case "DE": return "Da Nang";
            case "HE": return "Ha Noi";
            case "SE": return "Ho Chi Minh";
            case "QE": return "Quy Nhon";
            default: return "Unknown";
        }
    }
    
    public String getCampusCode(String input) {
        if (input == null) return "";
        String code = input.trim().toUpperCase();
        if (code.length() == 2) return code;
        if (code.length() == 1) {
            char firstChar = code.charAt(0);
            if (firstChar == 'C' || firstChar == 'D' || firstChar == 'H' || firstChar == 'S' || firstChar == 'Q') {
                return firstChar + "E";
            }
        }
        return code;
    }
    
    public Student getStudentById(String id) {
        return access.getStudentById(id);
    }
    
    public List<Student> getStudentList() {
        return access.getStudentList();
    }
    
    public void add(Student student) {
        access.addStudent(student);
        unsaved = true;
        access.saveToRecoveryFile();
    }

    public void update(String id, String newName, String newPhone, String newEmail, String newMountainCode) {
        Student student = access.getStudentById(id);
        if (student == null) return;
        if (newName != null && Validation.checkName(newName)) {
            student.setName(newName);
        }
        if (newPhone != null && Validation.checkPhone(newPhone)) {
            student.setPhone(newPhone);
            student.setTuitionFee(calculateTuitionFee(newPhone));
        }
        if (newEmail != null && Validation.checkEmail(newEmail)) {
            student.setEmail(newEmail);
        }
        if (newMountainCode != null && Validation.checkMountainCode(newMountainCode, mountainMap)) {
            student.setMountainCode(newMountainCode);
        }
        unsaved = true;
        access.saveToRecoveryFile();
    }
    
    public boolean delete(String id) {
        boolean deleted = access.deleteStudent(id);
        if (deleted) {
            unsaved = true;
            access.saveToRecoveryFile();
        }
        return deleted;
    }

    public List<Student> searchByName(String name) { // get every possible student object via stream().filter(s -> ...)
        return access.getStudentList().stream().filter(s -> s.getName().toLowerCase().contains(name.toLowerCase())).collect(Collectors.toList());
    }

    public List<Student> filterByCampusCode(String campusCode) { // get every possible student object via stream().filter(s -> ...)
        return access.getStudentList().stream().filter(s -> s.getId().startsWith(campusCode)).collect(Collectors.toList());
    }
    
    public void sortByMountainCode() { // used to be included, now placeholder
        access.getStudentList().sort(Comparator.comparing(Student::getMountainCodeObj));
    }
    
    public List<Student> filterByPeakCode(String mountainCode) {
        String normalizedCode = new MountainCode(mountainCode).getCode();
        return access.getStudentList().stream().filter(s -> s.getMountainCode().equals(normalizedCode)).collect(Collectors.toList());
    }
    
    public void displayAvailableMountains() {
        System.out.println("-------------- Available Mountain Peaks --------------");
        List<Mountain> mountains = new ArrayList<>(mountainMap.values());
        mountains.sort(Comparator.comparing(Mountain::getCode));
        System.out.printf("| %-10s | %-22s | %-12s |\n", "Peak Code", "Mountain Name", "Province");
        System.out.println("-------------------------------------------------------");
        for (Mountain m : mountains) {
            System.out.printf("| %-10s | %-22s | %-12s |\n", m.getCode(), m.getName(), m.getProvince());
        }
        System.out.println("-------------------------------------------------------");
    }
    
    public List<StatisticRecord> getStatistics() {
        Statistics st = new Statistics(access.getStudentList(), mountainMap);
        return st.getStatisticsByLocation();
    }
    
    public double calculateTuitionFee(String phone) {
        double baseFee = 6000000;
        if (Validation.checkSponsoredPhone(phone)) return baseFee * (1 - 0.35);
        return baseFee;
    }
    
    public String formatFee(double value) {
        // US locale for comma separation
        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
        // set maximum fraction digits to 0 to prevent decimals
        formatter.setMaximumFractionDigits(0); 
        return formatter.format(value);
    }
    
    public double getTotalTuitionCost() {
        return Statistics.getTotalTuitionCost(access.getStudentList());
    }
    
    public double getTotalTuitionCostForList(List<Student> students) {
        return Statistics.getTotalTuitionCost(students);
    }
    
    public String getMountainName(String mountainCode) {
        if (mountainCode == null || !mountainMap.containsKey(mountainCode)) {
            return "Unknown";
        }
        return mountainMap.get(mountainCode).getName();
    }
    
    public List<StatisticRecord> getMaxCostMountains(List<StatisticRecord> records) { // placeholder
        if (records == null || records.isEmpty()) return Collections.emptyList();
        double maxCost = records.stream().mapToDouble(StatisticRecord::getTotalCost).max().orElse(0.0);
        return records.stream().filter(r -> r.getTotalCost() == maxCost).collect(Collectors.toList());
    }
    
    public List<StatisticRecord> getMinCostMountains(List<StatisticRecord> records) { // placeholder
        if (records == null || records.isEmpty()) return Collections.emptyList();
        double minCost = records.stream().mapToDouble(StatisticRecord::getTotalCost).min().orElse(0.0);
        return records.stream().filter(r -> r.getTotalCost() == minCost).collect(Collectors.toList());
    }
    
    public List<StatisticRecord> getMaxParticipantsMountains(List<StatisticRecord> records) {
        if (records == null || records.isEmpty()) return Collections.emptyList();
        int maxParticipants = records.stream().mapToInt(StatisticRecord::getParticipants).max().orElse(0);
        return records.stream().filter(r -> r.getParticipants() == maxParticipants).collect(Collectors.toList());
    }
    
    public List<StatisticRecord> getMinParticipantsMountains(List<StatisticRecord> records) {
        if (records == null || records.isEmpty()) return Collections.emptyList();
        int minParticipants = records.stream().mapToInt(StatisticRecord::getParticipants).min().orElse(0);
        return records.stream().filter(r -> r.getParticipants() == minParticipants).collect(Collectors.toList());
    }
    
    public boolean saveChanges() {
        if (access.saveToFile()) {
            unsaved = false;
            return true;
        }
        return false;
    }
    
    public boolean getUnsaved() {
        return unsaved;
    }

    public void setUnsaved(boolean unsaved) {
        this.unsaved = unsaved;
    }
    
    public boolean hasRecoveryData() {
        return access.hasRecoveryData();
    }
    
    public boolean loadRecoveryData() {
        boolean loaded = access.readFromRecoveryFile();
        if (loaded) this.unsaved = true;
        return loaded;
    }
    
    public void discardRecoveryData() {
        access.deleteRecoveryFile();
    }
}
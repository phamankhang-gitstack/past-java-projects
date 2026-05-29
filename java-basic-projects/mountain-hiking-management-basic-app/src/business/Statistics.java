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
import java.util.ArrayList;
import java.util.Map;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.Comparator;
import models.Student;
import models.Mountain;
import models.MountainCode;
import models.StatisticRecord;

public class Statistics {
    private final List<Student> studentList;
    private final Map<String, Mountain> mountainMap;

    public Statistics(List<Student> studentList, Map<String, Mountain> mountainMap) {
        this.studentList = studentList == null ? Collections.emptyList() : studentList;
        this.mountainMap = mountainMap == null ? Collections.emptyMap() : mountainMap;
    }

    public List<StatisticRecord> getStatisticsByLocation() {
        Map<String, List<Student>> registrationsByMountain = studentList.stream()
                .filter(s -> s.getMountainCode() != null).collect(Collectors.groupingBy(Student::getMountainCode));
        List<StatisticRecord> records = new ArrayList<>();
        for (Map.Entry<String, List<Student>> e : registrationsByMountain.entrySet()) {
            String code = e.getKey();
            List<Student> list = e.getValue();
            int participants = list.size();
            double totalCost = list.stream().mapToDouble(Student::getTuitionFee).sum();
            Mountain mountain = mountainMap.get(code);
            String mountainName = mountain == null ? "Unknown Mountain" : mountain.getName();
            records.add(new StatisticRecord(code, mountainName, participants, totalCost));
        }
        records.sort(Comparator.comparing(r -> new MountainCode(r.getMountainCode())));
        // records.sort(Comparator.comparing(StatisticRecord::getParticipants));
        return records;
    }
    
    public static double getTotalTuitionCost(List<Student> students) {
        return students.stream().mapToDouble(Student::getTuitionFee).sum();
    }
}
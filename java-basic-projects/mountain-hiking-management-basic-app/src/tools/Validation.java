/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tools;

/**
 *
 * @author trang
 */
import java.util.List;
import java.util.Map;
import models.Mountain;
import models.MountainCode;
import models.Student;

public interface Validation {
    public final String STUDENT_ID_VALID = "^[CcDdHhSsQq][Ee]\\d{6}$";
    public final String NAME_VALID = "^.{2,20}$";
    public final String PHONE_VALID = "^(0)\\d{9}$";
    public final String[] VIETTEL_PREFIX = { "086","096","097","098","032","033","034","035","036","037","038","039" };
    public final String[] VNPT_PREFIX = { "081","082","083","084","085","088","091","094" };
    public final String EMAIL_VALID = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    
    public static boolean checkDuplicate(String value, List<Student> existed, String field) {
        if (value == null || existed == null) return true;
        for (Student s : existed) {
            if (s == null) continue;
            String studentValue = null;
            boolean ignoreCase = false;
            switch (field.toLowerCase()) {
                case "id":
                    studentValue = s.getId();
                    ignoreCase = true;
                    break;
                case "phone":
                    studentValue = s.getPhone();
                    ignoreCase = false;
                    break;
                case "email":
                    studentValue = s.getEmail();
                    ignoreCase = true;
                    break;
                default:
                    return true; 
            }
            if (studentValue != null) {
                if (ignoreCase) {
                    if (studentValue.equalsIgnoreCase(value)) return false;
                } else {
                    if (studentValue.equals(value)) return false;
                }
            }
        }
        return true;
    }
    
    public static boolean checkId(String id) {
        if (id == null || !id.matches(STUDENT_ID_VALID)) return false;
        return true;
    }
    
    public static boolean checkName(String name) {
        if (name == null) return false;
        return name.matches(NAME_VALID);
    }
    
    public static boolean checkPhone(String phone) {
        if (phone == null) return false;
        return phone.matches(PHONE_VALID);
    }
    
    public static boolean checkSponsoredPhone(String phone) {
        if (!checkPhone(phone)) return false;
        String prefix = phone.substring(0, 3);
        for (String s : VIETTEL_PREFIX) if (s.equals(prefix)) return true;
        for (String s : VNPT_PREFIX) if (s.equals(prefix)) return true;
        return false;
    }
    
    public static boolean checkEmail(String email) {
        if (email == null) return false;
        return email.matches(EMAIL_VALID);
    }
    
    public static boolean checkMountainCode(String code, Map<String, Mountain> mountainMap) {
        if (mountainMap == null || code == null) return false;
        try {
            String normalized = new MountainCode(code).getCode();
            return mountainMap.containsKey(normalized);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment;

/**
 *
 * @author trang
 */
public abstract class FPTCoc {
    private String name;
    private int age;
    private String code;
    private int grade;
    private Ao ao;
    public FPTCoc(String name, int age, String code, int grade, Ao ao) {
        this.name = name;
        this.age = age;
        this.code = code;
        this.grade = grade;
        this.ao = ao;
    }
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public String getCode() {
        return code;
    }
    public int getGrade() {
        return grade;
    }
    public Ao getAo() {
        return ao;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public void setGrade(int grade) {
        this.grade = grade;
    }
    public void setAo(Ao ao) {
        this.ao = ao;
    }
    public boolean isValidCode() {
        if (this.code == null || this.code.length() != 7) {
            return false;
        }
        if (this.code.charAt(0) != 'S' || this.code.charAt(1) != 'E') {
            return false;
        }
        for (int i = 2; i < this.code.length(); i++) {
            if (!Character.isDigit(this.code.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    public abstract boolean isPassed();
    public abstract String getLetterGrade();
    @Override
    public String toString() {
        String type = (this instanceof FPTCocthuong) ? "Thuong" : "Sieu";
        return String.format("%s (%s) - Grade: %d (%s) - %s",
            this.name, type, this.grade, this.getLetterGrade(), this.isPassed() ? "PASSED" : "FAILED");
    }
}
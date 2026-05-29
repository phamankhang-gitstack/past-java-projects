/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment;

/**
 *
 * @author trang
 */
public class FPTCocthuong extends FPTCoc {
    public FPTCocthuong(String name, int age, String code, int grade, Ao ao) {
        super(name, age, code, grade, ao);
    }
    @Override
    public boolean isPassed() {
        return getGrade() >= 50;
    }
    @Override
    public String getLetterGrade() {
        int g = getGrade();
        if (g >= 90) return "A";
        if (g >= 80) return "B";
        if (g >= 70) return "C";
        if (g >= 60) return "D";
        if (g >= 50) return "E";
        return "F";
    }
}
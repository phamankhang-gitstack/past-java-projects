/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment;

/**
 *
 * @author trang
 */
public class FPTSieucoc extends FPTCoc {
    public FPTSieucoc(String name, int age, String code, int grade, Ao ao) {
        super(name, age, code, grade, ao);
    }
    @Override
    public boolean isPassed() {
        return getGrade() >= 70;
    }
    @Override
    public String getLetterGrade() {
        int g = getGrade();
        if (g >= 80) return "A";
        if (g >= 70) return "B";
        if (g >= 60) return "C";
        if (g >= 50) return "D";
        return "F";
    }
}
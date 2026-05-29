/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author trang
 */
public class MountainCode implements Comparable<MountainCode> {
    private final String code; // normalized, e.g. MT01, MT11...

    public MountainCode(String rawCode) { // initiate normalization and validate
        if (rawCode == null) throw new IllegalArgumentException("Mountain code cannot be null");
        String s = rawCode.trim().toUpperCase();
        if (!s.startsWith("MT")) s = "MT" + s;
        String numPart = s.substring(2).trim();
        if (numPart.isEmpty()) throw new IllegalArgumentException("Mountain code missing numeric part");
        int n;
        try {
            n = Integer.parseInt(numPart);
            if (n < 0) throw new IllegalArgumentException("Mountain code number must be positive");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid mountain code number: " + numPart);
        }
        this.code = String.format("MT%02d", n);
    }

    public String getCode() {
        return code;
    }

    public int getNumber() {
        return Integer.parseInt(code.substring(2));
    }

    @Override
    public int compareTo(MountainCode mc) {
        return Integer.compare(this.getNumber(), mc.getNumber());
    }

    @Override
    public String toString() {
        return code;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof MountainCode)) return false;
        MountainCode other = (MountainCode) obj;
        return this.code.equals(other.code);
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }
}
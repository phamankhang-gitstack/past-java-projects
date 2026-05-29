/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package assignment;

/**
 *
 * @author trang
 */
import java.util.*;
import java.io.*;
public class Assignment {
    private static HashMap<String, Ao> mapAo = new LinkedHashMap<>();
    private static ArrayList<FPTCoc> listCoc = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        boolean run = true;
        while (run) {
            System.out.println("\n1. Them ao moi");
            System.out.println("2. Cap nhat ao");
            System.out.println("3. Xoa ao");
            System.out.println("4. Hien thi danh sach ao");
            System.out.println("5. Luu ao vao file");
            System.out.println("6. Tai ao tu file");
            System.out.println("7. Them FPTCoc moi");
            System.out.println("8. Cap nhat FPTCoc");
            System.out.println("9. Xoa FPTCoc");
            System.out.println("10. Hien thi danh sach FPTCoc theo ao");
            System.out.println("11. Luu FPTCoc vao file");
            System.out.println("12. Tai FPTCoc tu file");
            System.out.println("13. Thoat chuong trinh");
            System.out.print("Nhap 1 so de chay: ");
            try {
                int choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1: themAo(); break;
                    case 2: cnAo(); break;
                    case 3: xoaAo(); break;
                    case 4: htAo(); break;
                    case 5: luuAo(); break;
                    case 6: taiAo(); break;
                    case 7: themFPTCoc(); break;
                    case 8: cnFPTCoc(); break;
                    case 9: xoaFPTCoc(); break;
                    case 10: htFPTCoc(); break;
                    case 11: luuFPTCoc(); break;
                    case 12: taiFPTCoc(); break;
                    case 13: run = false; break;
                    default: System.out.println("Lua chon khong hop le");
                }
            } catch (NumberFormatException e) {
                System.out.println("Lua chon khong hop le");
            }
        }
    }
    private static void themAo() {
        String code = null;
        while (true) {
            System.out.print("Nhap ma ao: ");
            code = sc.nextLine().toUpperCase();
            if (mapAo.containsKey(code)) {
                System.out.println("Da co ma ao nay roi");
            } else {
                break;
            }
        }
        System.out.print("Nhap ten ao: ");
        String name = sc.nextLine();
        mapAo.put(code, new Ao(code, name));
        System.out.printf("Them %s - %s\n", code, name);
    }
    private static void cnAo() {
        System.out.print("Nhap ma ao de cap nhat: ");
        String code = sc.nextLine().toUpperCase();
        if (!mapAo.containsKey(code)) {
            System.out.println("Khong tim thay ao voi ma nay");
            return;
        }
        System.out.print("Nhap ten Ao: ");
        String newName = sc.nextLine();
        mapAo.get(code).setName(newName);
        System.out.printf("Cap nhat %s - %s\n", code, newName);
    }
    private static void xoaAo() {
        System.out.print("Nhap ma ao de xoa: ");
        String code = sc.nextLine().toUpperCase();
        if (mapAo.containsKey(code)) {
            mapAo.remove(code);
            System.out.println("Xoa " + code);
        } else {
            System.out.println("Khong tim thay ao voi ma nay");
        }
    }
    private static void htAo() {
        if (mapAo.isEmpty()) {
            System.out.println("Hien tai chua co ao nao");
        } else {
            System.out.println("--- Danh sach Ao ---");
            for (String code : mapAo.keySet()) {
                System.out.println(mapAo.get(code));
            }
        }
    }
    private static void luuAo() {
        try (PrintWriter w = new PrintWriter(new FileWriter("aos.csv"))) {
            for (Ao ao : mapAo.values()) {
                w.printf("%s,%s\n", ao.getCode(), ao.getName());
            }
            System.out.println("Da ghi Aos vao Aos.csv");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    private static void taiAo() {
        try (Scanner fsc = new Scanner(new File("aos.csv"))) {
            mapAo.clear();
            while (fsc.hasNextLine()) {
                String l = fsc.nextLine();
                String[] cut = l.split(",", 2);
                if (cut.length == 2) {
                    mapAo.put(cut[0], new Ao(cut[0], cut[1]));
                }
            }
            System.out.println("Da doc " + mapAo.size() + " Aos from Aos.csv");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
    private static void themFPTCoc() {
        String type = "";
        while (true) {
            System.out.print("Nhap phan loai FPTCoc (T=Thuong, S=Sieu): ");
            type = sc.nextLine().trim().toUpperCase();
            if (type.equals("T") || type.equals("S")) {
                break;
            }
            System.out.println("Input khong hop le");
        }
        System.out.print("Nhap ten: ");
        String name = sc.nextLine();
        int age = 0;
        while (true) {
            System.out.print("Nhap tuoi: ");
            try {
                age = Integer.parseInt(sc.nextLine());
                if (age <= 0) {
                    System.out.println("Tuoi khong hop li");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Input khong hop le");
            }
        }
        System.out.print("Nhap ma FPTCoc: ");
        String code = sc.nextLine().toUpperCase();
        int grade = 0;
        while (true) {
            System.out.print("Nhap diem: ");
            try {
                grade = Integer.parseInt(sc.nextLine());
                if (grade < 0) {
                    System.out.println("Diem khong hop li");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Input khong hop le");
            }
        }
        System.out.print("Nhap ma ao coc de hoc: ");
        String codeAo = sc.nextLine().toUpperCase();
        Ao ao = mapAo.get(codeAo);
        if (ao == null) {
            System.out.println("Khong tim thay ao voi ma nay");
            return;
        }
        if (type.equals("T")) {
            listCoc.add(new FPTCocthuong(name, age, code, grade, ao));
        } else {
            listCoc.add(new FPTSieucoc(name, age, code, grade, ao));
        }
        System.out.printf("Them %s (%s) vao Ao %s\n", name, (type.equals("T")) ? "Thuong" : "Sieu", codeAo);
    }
    private static FPTCoc timFPTCoc(String code) {
        for (FPTCoc f : listCoc) {
            if (f.getCode().equalsIgnoreCase(code)) return f;
        }
        return null;
    }
    private static void cnFPTCoc() {
        System.out.print("Nhap ma FPTCoc de cap nhat: ");
        String code = sc.nextLine().toUpperCase();
        FPTCoc goiCoc = timFPTCoc(code);
        if (goiCoc == null) {
            System.out.println("Khong tim thay coc voi ma nay");
            return;
        }
        System.out.print("Nhap ten moi: ");
        String newName = sc.nextLine();
        goiCoc.setName(newName);
        while (true) {
            System.out.print("Nhap tuoi moi: ");
            String newAge = sc.nextLine();
            try {
                int newAgeEx = Integer.parseInt(newAge);
                if (newAgeEx <= 0) {
                    System.out.println("Tuoi khong hop li");
                } else {
                    goiCoc.setAge(newAgeEx);
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Input khong hop le");
            }
        }
        while (true) {
            System.out.print("Nhap diem moi: ");
            String newGrade = sc.nextLine();
            try {
                int newGradeEx = Integer.parseInt(newGrade);
                if (newGradeEx < 0) {
                    System.out.println("Diem khong hop li");
                } else {
                    goiCoc.setGrade(newGradeEx);
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Input khong hop le");
            }
        }
        System.out.print("Nhap ma ao moi: ");
        String newCodeAo = sc.nextLine().toUpperCase();
        if (!newCodeAo.isEmpty()) {
            Ao newAo = mapAo.get(newCodeAo);
            if (newAo == null) {
                System.out.println("Khong tim thay ao voi ma nay");
            } else {
                goiCoc.setAo(newAo);
            }
        }
        System.out.println("Cap nhat FPTCoc " + code);
    }
    private static void xoaFPTCoc() {
        System.out.print("Nhap ma FPTCoc de xoa: ");
        String code = sc.nextLine().toUpperCase();
        FPTCoc goiCocXoa = timFPTCoc(code);
        if (goiCocXoa != null) {
            listCoc.remove(goiCocXoa);
            System.out.println("Xoa FPTCoc " + code);
        } else {
            System.out.println("Khong tim thay coc voi ma nay");
        }
    }
    private static void htFPTCoc() {
        if (listCoc.isEmpty()) {
            System.out.println("Hien tai chua co coc nao");
        } else {
            for (Ao ao : mapAo.values()) {
                System.out.printf("\n--- FPTCocs cua Ao %s - %s ---\n", ao.getCode(), ao.getName());
                for (FPTCoc f : listCoc) {
                    if (f.getAo().equals(ao)) System.out.println(f.toString());
                }
            }
        }
    }
    private static void luuFPTCoc() {
        try (PrintWriter w = new PrintWriter(new FileWriter("FPTCocs.csv"))) {
            for (FPTCoc f : listCoc) {
                String type = (f instanceof FPTCocthuong) ? "T" : "S";
                w.printf("%s,%s,%d,%s,%d,%s\n",
                    type, f.getName(), f.getAge(), f.getCode(), f.getGrade(), f.getAo());
            }
            System.out.println("Da ghi FPTCocs vao FPTCocs.csv");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    private static void taiFPTCoc() {
        try (Scanner fsc = new Scanner(new File("FPTCocs.csv"))) {
            listCoc.clear();
            while (fsc.hasNextLine()) {
                String l = fsc.nextLine();
                String[] cut = l.split(",");
                if (cut.length == 6) {
                    String type = cut[0];
                    String name = cut[1];
                    int age = Integer.parseInt(cut[2]);
                    String code = cut[3];
                    int grade = Integer.parseInt(cut[4]);
                    String codeAo = cut[5];
                    Ao ao = mapAo.get(codeAo);
                    if (ao == null) {
                        System.out.println("Khong tim thay ao cho coc nen bo qua");
                        continue;
                    }
                    if (type.equals("T")) {
                        listCoc.add(new FPTCocthuong(name, age, code, grade, ao));
                    } else if (type.equals("S")) {
                        listCoc.add(new FPTSieucoc(name, age, code, grade, ao));
                    }
                }
            }
            System.out.println("Da doc " + listCoc.size() + " FPTCocs tu FPTCocs.csv");
        } catch (FileNotFoundException | NumberFormatException e) {
            System.out.println(e.getMessage());
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tools;

/**
 *
 * @author trang
 */
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import models.Mountain;
import models.MountainCode;

public class DataLoader {
    public static Map<String, Mountain> loadMountains(String filePath) {
        Map<String, Mountain> mountainMap = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {
            String line;
            // skip first line
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    if (line.toLowerCase().contains("code") && line.contains(",")) continue;
                }
                if (line.trim().isEmpty()) continue;
                // start to load data from line 2 downwards
                String[] data = line.split(",", 4);
                if (data.length >= 2) {
                    String code = data[0].trim();
                    String name = data.length > 1 ? data[1].trim() : "";
                    String province = data.length > 2 ? data[2].trim() : "";
                    String description = data.length > 3 ? data[3].trim() : "";
                    String mountainCode = new MountainCode(code).getCode();
                    mountainMap.put(mountainCode, new Mountain(mountainCode, name, province, description)); 
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading mountain data from " + filePath + ": " + e.getMessage());
        }
        return mountainMap;
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package run;

/**
 *
 * @author trang
 */
import java.util.Map;
import models.Mountain;
import tools.Inputter;
import tools.DataLoader;
import business.StudentAccess;
import business.DataController;
import business.InputManager;
import view.Menu;

public class Main {
    private static final String MOUNTAIN_FILE_PATH = System.getProperty("user.dir") + "/MountainList.csv";
    public static void main(String[] args) {
        Map<String, Mountain> mountainMap = DataLoader.loadMountains(MOUNTAIN_FILE_PATH);
        Inputter inputter = new Inputter();
        StudentAccess access = new StudentAccess();
        DataController controller = new DataController(access, mountainMap);
        InputManager manager = new InputManager(controller, inputter);
        manager.checkRecoveryData();
        Menu menu = new Menu(manager, inputter);
        menu.run();
    }
}
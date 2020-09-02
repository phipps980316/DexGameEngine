package RenderTest;

import RenderEngine.DisplayManager;
import org.lwjgl.opengl.Display;

public class MainGameLoop {
    public static void main(String[] args) {
        DisplayManager.createDisplay();
        while(!Display.isCloseRequested()){
            DisplayManager.updateDisplay();
        }
        DisplayManager.closeDisplay();
    }
}

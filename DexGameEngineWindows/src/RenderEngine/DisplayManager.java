package RenderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;

public class DisplayManager {

    private static final int width = 1280;
    private static final int height = 720;
    private static final int fpsCap = 120;

    public static void createDisplay(){

        ContextAttribs attribs = new ContextAttribs(3,2).withForwardCompatible(true).withProfileCore(true);

        try {
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.create(new PixelFormat(), attribs);
            Display.setTitle("DexEngine");
        } catch (LWJGLException e) {
            e.printStackTrace();
        }

        GL11.glViewport(0,0,width, height);
    }
    public static void updateDisplay(){
        Display.sync(fpsCap);
        Display.update();
    }
    public static void closeDisplay(){
        Display.destroy();
    }
}

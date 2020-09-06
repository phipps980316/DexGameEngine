package RenderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class DisplayManager {

    private static final int width = 1920;
    private static final int height = 1080;
    private static final int fpsCap = 120;

    private static long timeLastFrame;
    private static float delta;

    public static void createDisplay() {

        ContextAttribs attribs = new ContextAttribs(3, 2).withForwardCompatible(true).withProfileCore(true);

        try {
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.create(new PixelFormat(), attribs);
            Display.setTitle("DexEngine");
        } catch (LWJGLException e) {
            e.printStackTrace();
        }

        GL11.glViewport(0, 0, width, height);
        timeLastFrame = getCurrentTime();
    }

    public static void updateDisplay() {
        Display.sync(fpsCap);
        Display.update();
        long timeCurrentFrame = getCurrentTime();
        delta = (timeCurrentFrame - timeLastFrame)/1000f;
        timeLastFrame = timeCurrentFrame;
    }

    public static float getFrameTime(){
        return delta;
    }

    public static void closeDisplay() {
        Display.destroy();
    }

    private static long getCurrentTime() {
        return Sys.getTime() * 1000 / Sys.getTimerResolution();
    }
}
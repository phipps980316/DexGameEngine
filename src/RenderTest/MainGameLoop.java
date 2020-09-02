package RenderTest;

import RenderEngine.*;
import org.lwjgl.opengl.Display;

public class MainGameLoop {
    public static void main(String[] args) {
        DisplayManager.createDisplay();
        ModelLoader modelLoader = new ModelLoader();
        ModelRenderer modelRenderer = new ModelRenderer();
        StaticShader shader = new StaticShader();

        float[] vertices = {
                -0.5f, 0.5f, 0f,
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
                0.5f, 0.5f, 0f
        };

        int[] indices = {
                0,1,3,
                3,1,2
        };

        RawModel model = modelLoader.loadToVAO(vertices,indices);

        while(!Display.isCloseRequested()){
            modelRenderer.prepare();
            shader.start();
            modelRenderer.render(model);
            shader.stop();
            DisplayManager.updateDisplay();
        }

        shader.cleanUp();
        modelLoader.cleanUp();
        DisplayManager.closeDisplay();
    }
}

package RenderTest;

import RenderEngine.DisplayManager;
import RenderEngine.ModelLoader;
import RenderEngine.ModelRenderer;
import RenderEngine.RawModel;
import org.lwjgl.opengl.Display;

public class MainGameLoop {
    public static void main(String[] args) {
        DisplayManager.createDisplay();
        ModelLoader modelLoader = new ModelLoader();
        ModelRenderer modelRenderer = new ModelRenderer();

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
            modelRenderer.render(model);
            DisplayManager.updateDisplay();
        }

        modelLoader.cleanUp();
        DisplayManager.closeDisplay();
    }
}

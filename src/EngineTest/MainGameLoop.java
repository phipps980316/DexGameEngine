package EngineTest;

import Models.RawModel;
import Models.TexturedModel;
import RenderEngine.*;
import Shaders.StaticShader;
import Textures.ModelTexture;
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

        float[] textureCoords = {
                0,0,
                0,1,
                1,1,
                1,0
        };

        RawModel model = modelLoader.loadToVAO(vertices,textureCoords,indices);
        ModelTexture texture = new ModelTexture(modelLoader.loadTexture("tex1"));
        TexturedModel texturedModel = new TexturedModel(model, texture);

        while(!Display.isCloseRequested()){
            modelRenderer.prepare();
            shader.start();
            modelRenderer.render(texturedModel);
            shader.stop();
            DisplayManager.updateDisplay();
        }

        shader.cleanUp();
        modelLoader.cleanUp();
        DisplayManager.closeDisplay();
    }
}

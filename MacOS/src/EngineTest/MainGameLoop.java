package EngineTest;

import Entities.Camera;
import Entities.Entity;
import Models.RawModel;
import Models.TexturedModel;
import RenderEngine.*;
import Shaders.StaticShader;
import Textures.ModelTexture;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

public class MainGameLoop {
    public static void main(String[] args) {
        DisplayManager.createDisplay();
        ModelLoader modelLoader = new ModelLoader();
        StaticShader shader = new StaticShader();
        ModelRenderer modelRenderer = new ModelRenderer(shader);


        RawModel model = OBJLoader.loadObjModel("Cube", modelLoader);
        ModelTexture texture = new ModelTexture(modelLoader.loadTexture("CubeTex"));
        TexturedModel texturedModel = new TexturedModel(model, texture);
        Entity entity = new Entity(texturedModel, new Vector3f(0,0,0), new Vector3f(0,0,0), 1);
        Camera camera = new Camera();

        while(!Display.isCloseRequested()){
            entity.changeRotation(1,1,0);
            camera.move();
            modelRenderer.prepare();
            shader.start();
            shader.loadViewMatrix(camera);
            modelRenderer.render(entity,shader);
            shader.stop();
            DisplayManager.updateDisplay();
        }

        shader.cleanUp();
        modelLoader.cleanUp();
        DisplayManager.closeDisplay();
    }
}

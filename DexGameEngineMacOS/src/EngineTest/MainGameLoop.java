package EngineTest;

import Entities.Camera;
import Entities.Entity;
import Entities.Light;
import Models.RawModel;
import Models.TexturedModel;
import RenderEngine.*;
import Terrains.Terrain;
import Textures.ModelTexture;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

public class MainGameLoop {
    public static void main(String[] args) {
        DisplayManager.createDisplay();
        ModelLoader modelLoader = new ModelLoader();


        RawModel model = OBJLoader.loadObjModel("dragon", modelLoader);
        ModelTexture texture = new ModelTexture(modelLoader.loadTexture("cubeTex"));
        texture.setShineDamper(10);
        texture.setReflectivity(1);
        TexturedModel texturedModel = new TexturedModel(model, texture);
        Entity entity = new Entity(texturedModel, new Vector3f(0,0,-50), new Vector3f(0,0,0), 1);
        Light light = new Light(new Vector3f(20000,20000,2000), new Vector3f(1,1,1));
        Camera camera = new Camera();

        Terrain terrain = new Terrain(0,-1,modelLoader, new ModelTexture(modelLoader.loadTexture("grass")));
        Terrain terrain2 = new Terrain(-1,-1,modelLoader, new ModelTexture(modelLoader.loadTexture("grass")));

        RenderManager renderManager = new RenderManager();
        while(!Display.isCloseRequested()){
            entity.changeRotation(0,1,0);
            camera.move();
            renderManager.processTerrain(terrain);
            renderManager.processTerrain(terrain2);
            renderManager.processEntity(entity);
            renderManager.render(light, camera);
            DisplayManager.updateDisplay();
        }

        renderManager.cleanUp();
        modelLoader.cleanUp();
        DisplayManager.closeDisplay();
    }
}

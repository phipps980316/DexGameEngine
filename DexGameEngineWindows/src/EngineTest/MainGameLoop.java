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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainGameLoop {
    public static void main(String[] args) {
        DisplayManager.createDisplay();
        ModelLoader modelLoader = new ModelLoader();


        RawModel grassModel = OBJLoader.loadObjModel("grassModel", modelLoader);
        ModelTexture grassTexture = new ModelTexture(modelLoader.loadTexture("grassTexture"));
        grassTexture.setHasTransparency(true);
        grassTexture.setUseFakeLighting(true);
        TexturedModel grassTexturedModel = new TexturedModel(grassModel, grassTexture);

        RawModel fernModel = OBJLoader.loadObjModel("fern", modelLoader);
        ModelTexture fernTexture = new ModelTexture(modelLoader.loadTexture("fern"));
        fernTexture.setHasTransparency(true);
        TexturedModel fernTexturedModel = new TexturedModel(fernModel, fernTexture);

        List<Entity> entities = new ArrayList<>();
        Random random = new Random();
        for(int i = 0; i < 200; i++){
            entities.add(new Entity(grassTexturedModel, new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -600), new Vector3f(0,0,0), 3));
            entities.add(new Entity(fernTexturedModel, new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -600), new Vector3f(0,0,0), 3));
        }


        Light light = new Light(new Vector3f(20000,20000,2000), new Vector3f(1,1,1));
        Camera camera = new Camera();

        Terrain terrain = new Terrain(0,-1,modelLoader, new ModelTexture(modelLoader.loadTexture("grass")));
        Terrain terrain2 = new Terrain(-1,-1,modelLoader, new ModelTexture(modelLoader.loadTexture("grass")));

        RenderManager renderManager = new RenderManager();
        while(!Display.isCloseRequested()){
            camera.move();
            renderManager.processTerrain(terrain);
            renderManager.processTerrain(terrain2);
            for(Entity entity:entities){
                renderManager.processEntity(entity);
            }
            renderManager.render(light, camera);
            DisplayManager.updateDisplay();
        }

        renderManager.cleanUp();
        modelLoader.cleanUp();
        DisplayManager.closeDisplay();
    }
}

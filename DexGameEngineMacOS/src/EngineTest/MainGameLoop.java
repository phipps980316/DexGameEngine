package EngineTest;

import DataStructures.Vector3D;
import Entities.Camera;
import Entities.Entity;
import Entities.Light;
import IO.ModelFileLoader;
import Models.Model;
import Models.ModelData;
import RenderEngine.*;
import Models.ModelTexture;
import Terrains.Terrain;
import org.lwjgl.opengl.Display;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainGameLoop {
    public static void main(String[] args) {
        landscapeDemo();
    }

    public static void cubeDemo(){
        DisplayManager.createDisplay();
        ModelLoader modelLoader = new ModelLoader();

        ModelData modelData = ModelFileLoader.loadModel("cube");
        ModelTexture modelTexture = new ModelTexture(modelLoader.loadTexture("cube"), 1, 0, false, false);
        assert modelData != null;
        Model cube = modelLoader.loadToVAO(modelData, modelTexture);
        Entity entity = new Entity(cube, new Vector3D(0, 0, -25), new Vector3D(0,0,0), 1);

        Light light = new Light(new Vector3D(20000,20000,2000), new Vector3D(1,1,1));
        Camera camera = new Camera();

        RenderManager renderManager = new RenderManager();
        while(!Display.isCloseRequested()){
            entity.changeRotation(1,1,0);
            camera.move();
            renderManager.processEntity(entity);
            renderManager.render(light, camera);
            DisplayManager.updateDisplay();
        }

        renderManager.cleanUp();
        modelLoader.cleanUp();
        DisplayManager.closeDisplay();
    }

    public static void landscapeDemo(){
        DisplayManager.createDisplay();
        ModelLoader modelLoader = new ModelLoader();

        ModelData grassData = ModelFileLoader.loadModel("grassModel");
        ModelTexture grassTexture = new ModelTexture(modelLoader.loadTexture("grassTexture"),1,0, true, true);
        assert grassData != null;
        Model grass = modelLoader.loadToVAO(grassData, grassTexture);

        ModelData fernData = ModelFileLoader.loadModel("fern");
        ModelTexture fernTexture = new ModelTexture(modelLoader.loadTexture("fern"),1,0, true, false);
        assert fernData != null;
        Model fern = modelLoader.loadToVAO(fernData, fernTexture);

        List<Entity> entities = new ArrayList<>();
        Random random = new Random();
        for(int i = 0; i < 200; i++){
            entities.add(new Entity(grass, new Vector3D(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -600), new Vector3D(0,0,0), 3));
            entities.add(new Entity(fern, new Vector3D(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -600), new Vector3D(0,0,0), 3));
        }


        Light light = new Light(new Vector3D(20000,20000,2000), new Vector3D(1,1,1));
        Camera camera = new Camera();

        ModelTexture terrainTexture = new ModelTexture(modelLoader.loadTexture("grass"), 1, 0, false, false);
        Terrain terrain = new Terrain(0,-1,modelLoader, terrainTexture);
        Terrain terrain2 = new Terrain(-1,-1,modelLoader, terrainTexture);

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

package EngineTest;

import Entities.Camera;
import Entities.Entity;
import Entities.Light;
import Entities.Player;
import IO.ModelFileLoader;
import Models.ModelData;
import Models.RawModel;
import Models.TexturedModel;
import RenderEngine.*;
import Textures.ModelTexture;
import Terrains.Terrain;
import Textures.TerrainTexture;
import Textures.TerrainTexturePack;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainGameLoop {
    public static void main(String[] args) {
        landscapeDemo();
    }

    public static void landscapeDemo(){
        DisplayManager.createDisplay();
        ModelLoader modelLoader = new ModelLoader();

        ModelData grassData = ModelFileLoader.loadModel("grassModel");
        ModelTexture grassTexture = new ModelTexture(modelLoader.loadTexture("grassTexture"),1,0, true, true);
        assert grassData != null;
        TexturedModel grass = new TexturedModel(modelLoader.loadToVAO(grassData), grassTexture);

        ModelData fernData = ModelFileLoader.loadModel("fern");
        ModelTexture fernTexture = new ModelTexture(modelLoader.loadTexture("fern"),1,0, true, false);
        assert fernData != null;
        TexturedModel fern = new TexturedModel(modelLoader.loadToVAO(fernData), fernTexture);

        List<Entity> entities = new ArrayList<>();
        Random random = new Random();
        for(int i = 0; i < 200; i++){
            entities.add(new Entity(grass, new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -600), new Vector3f(0,0,0), 3));
            //entities.add(new Entity(fern, new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -600), new Vector3f(0,0,0), 6));
        }


        Light light = new Light(new Vector3f(20000,20000,2000), new Vector3f(1,1,1));

        TerrainTexture backgroundTexture = new TerrainTexture(modelLoader.loadTexture("grass"));
        TerrainTexture rTexture = new TerrainTexture(modelLoader.loadTexture("mud"));
        TerrainTexture gTexture = new TerrainTexture(modelLoader.loadTexture("grassFlowers"));
        TerrainTexture bTexture = new TerrainTexture(modelLoader.loadTexture("path"));
        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);

        TerrainTexture blendMap = new TerrainTexture(modelLoader.loadTexture("blendMap"));


        Terrain terrain = new Terrain(0,-1,modelLoader, texturePack, blendMap);
        Terrain terrain2 = new Terrain(-1,-1,modelLoader, texturePack, blendMap);

        ModelData playerData = ModelFileLoader.loadModel("player");
        ModelTexture playerTexture = new ModelTexture(modelLoader.loadTexture("playerTexture"),1,0, true, true);
        assert playerData != null;
        TexturedModel playerModel = new TexturedModel(modelLoader.loadToVAO(playerData), playerTexture);
        Player player = new Player(playerModel, new Vector3f(100, 0, -50), new Vector3f(0,0,0), 1);
        Camera camera = new Camera(player);

        RenderManager renderManager = new RenderManager();
        while(!Display.isCloseRequested()){
            camera.move();
            player.move();
            renderManager.processEntity(player);
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

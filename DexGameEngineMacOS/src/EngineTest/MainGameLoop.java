package EngineTest;

import Entities.Camera;
import Entities.Entity;
import Entities.Light;
import Entities.Player;
import GUI.GUIRenderer;
import GUI.GUITexture;
import IO.ModelFileLoader;
import Models.ModelData;
import Models.TexturedModel;
import RenderEngine.*;
import Textures.ModelTexture;
import Terrains.Terrain;
import Textures.TerrainTexture;
import Textures.TerrainTexturePack;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
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

        Light light = new Light(new Vector3f(0, 10000, -7000), new Vector3f(1,1,1));
        Light light2 = new Light(new Vector3f(-200,10,-200), new Vector3f(1,0,0));
        Light light3 = new Light(new Vector3f(200,10,200), new Vector3f(0,1,0));
        Light light4 = new Light(new Vector3f(200,1000,100), new Vector3f(0,0,1));

        List<Light> lights = new ArrayList<>();
        lights.add(light);
        lights.add(light2);
        lights.add(light3);
        lights.add(light4);

        TerrainTexture backgroundTexture = new TerrainTexture(modelLoader.loadTexture("grassy2"));
        TerrainTexture rTexture = new TerrainTexture(modelLoader.loadTexture("mud"));
        TerrainTexture gTexture = new TerrainTexture(modelLoader.loadTexture("grassFlowers"));
        TerrainTexture bTexture = new TerrainTexture(modelLoader.loadTexture("path"));
        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);

        TerrainTexture blendMap = new TerrainTexture(modelLoader.loadTexture("blendMap"));

        Terrain terrain = new Terrain(0,-1,modelLoader, texturePack, blendMap, "heightmap");

        ModelData fernData = ModelFileLoader.loadModel("fern");
        ModelTexture fernTexture = new ModelTexture(modelLoader.loadTexture("fern"),1,0, true, false);
        fernTexture.setNumberOfRows(2);
        assert fernData != null;
        TexturedModel fern = new TexturedModel(modelLoader.loadToVAO(fernData), fernTexture);

        List<Entity> entities = new ArrayList<>();
        Random random = new Random();
        for(int i = 0; i < 200; i++){
            float x = random.nextFloat() * 800;
            float z = random.nextFloat() * -800;
            float y = terrain.getTerrainHeightAtPlayer(x, z);
            entities.add(new Entity(fern, new Vector3f(x,y,z), new Vector3f(0,0,0), 2, random.nextInt(4)));
        }

        ModelData playerData = ModelFileLoader.loadModel("player");
        ModelTexture playerTexture = new ModelTexture(modelLoader.loadTexture("playerTexture"),1,0, true, true);
        assert playerData != null;
        TexturedModel playerModel = new TexturedModel(modelLoader.loadToVAO(playerData), playerTexture);
        Player player = new Player(playerModel, new Vector3f(100, 0, -50), new Vector3f(0,0,0), 1);
        Camera camera = new Camera(player);

        List<GUITexture> GUIs = new ArrayList<>();
        GUITexture guiTexture = new GUITexture(modelLoader.loadTexture("health"), new Vector2f(-0.75f, -0.75f), new Vector2f(0.25f, 0.25f));
        GUIs.add(guiTexture);

        GUIRenderer guiRenderer = new GUIRenderer(modelLoader);


        RenderManager renderManager = new RenderManager();
        while(!Display.isCloseRequested()){
            camera.move();
            player.move(terrain);
            renderManager.processEntity(player);
            renderManager.processTerrain(terrain);
            for(Entity entity:entities){
                renderManager.processEntity(entity);
            }
            renderManager.render(lights, camera);
            guiRenderer.render(GUIs);
            DisplayManager.updateDisplay();
        }

        guiRenderer.cleanUp();
        renderManager.cleanUp();
        modelLoader.cleanUp();
        DisplayManager.closeDisplay();
    }
}

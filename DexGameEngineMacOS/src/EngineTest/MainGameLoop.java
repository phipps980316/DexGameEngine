package EngineTest;

import Beta.Cube;
import Beta.Face;
import Beta.FaceOption;
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
import Toolbox.MousePicker;
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

        Light light = new Light(new Vector3f(10, 10, 10), new Vector3f(0.4f,0.4f,0.4f));
        List<Light> lights = new ArrayList<>();
        lights.add(light);

        /*TerrainTexture backgroundTexture = new TerrainTexture(modelLoader.loadTexture("grassy2"));
        TerrainTexture rTexture = new TerrainTexture(modelLoader.loadTexture("mud"));
        TerrainTexture gTexture = new TerrainTexture(modelLoader.loadTexture("grassFlowers"));
        TerrainTexture bTexture = new TerrainTexture(modelLoader.loadTexture("path"));
        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);

        TerrainTexture blendMap = new TerrainTexture(modelLoader.loadTexture("blendMap"));

        Terrain terrain = new Terrain(0,-1,modelLoader, texturePack, blendMap, "heightmap");*/

        Cube cubeModel = new Cube(new FaceOption[]{FaceOption.TOP, FaceOption.LEFT, FaceOption.RIGHT, FaceOption.BOTTOM, FaceOption.FRONT, FaceOption.BACK});
        ModelData cubeData = cubeModel.getRenderData();
        //ModelData cubeData = ModelFileLoader.loadModel("cube");
        ModelTexture cubeTexture = new ModelTexture(modelLoader.loadTexture("cube1"),1,1, true, false);
        assert cubeData != null;
        TexturedModel cube = new TexturedModel(modelLoader.loadToVAO(cubeData), cubeTexture);

        //ModelData cubeData2 = ModelFileLoader.loadModel("cubebig");
        //ModelTexture cubeTexture2 = new ModelTexture(modelLoader.loadTexture("cube1"),1,0, true, false);
        //assert cubeData2 != null;
        //TexturedModel cube2 = new TexturedModel(modelLoader.loadToVAO(cubeData2), cubeTexture);

        List<Entity> entities = new ArrayList<>();
        //entities.add(new Entity(lamp, new Vector3f(400, -4.7f, -400), new Vector3f(0,0,0), 1));
        /*Random random = new Random();
        for(int i = 0; i < 200; i++){
            float x = random.nextFloat() * 800;
            float z = random.nextFloat() * -800;
            float y = terrain.getTerrainHeightAtPlayer(x, z);
            entities.add(new Entity(cube, new Vector3f(x,y,z), new Vector3f(0,0,0), 2, random.nextInt(4)));
        }*/

        //for(int x = 0; x < 250; x++){
           // for(int z = 0; z < 250; z++){
            //    entities.add(new Entity(cube, new Vector3f(x*2,0,z*2), new Vector3f(0,0,0), 1));
           // }
       // }

        entities.add(new Entity(cube, new Vector3f(0,0,0), new Vector3f(0,0,0), 1));
        //entities.add(new Entity(cube2, new Vector3f(5,0,5), new Vector3f(0,0,0), 1));

        ModelData playerData = ModelFileLoader.loadModel("player");
        ModelTexture playerTexture = new ModelTexture(modelLoader.loadTexture("playerTexture"),1,0, true, true);
        assert playerData != null;
        TexturedModel playerModel = new TexturedModel(modelLoader.loadToVAO(playerData), playerTexture);
        Player player = new Player(playerModel, new Vector3f(0, 0, 0), new Vector3f(0,0,0), 1);
        Camera camera = new Camera(player);

        List<GUITexture> GUIs = new ArrayList<>();
        GUITexture guiTexture = new GUITexture(modelLoader.loadTexture("health"), new Vector2f(-0.75f, -0.75f), new Vector2f(0.25f, 0.25f));
        GUIs.add(guiTexture);

        GUIRenderer guiRenderer = new GUIRenderer(modelLoader);


        RenderManager renderManager = new RenderManager(modelLoader);

        MousePicker picker = new MousePicker(camera, renderManager.getProjectionMatrix());

        while(!Display.isCloseRequested()){
            camera.move();
            //player.move(terrain);
            picker.update();
            //renderManager.processEntity(player);
            //renderManager.processTerrain(terrain);
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

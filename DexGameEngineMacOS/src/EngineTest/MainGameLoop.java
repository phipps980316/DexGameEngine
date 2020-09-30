package EngineTest;

import Beta.Region;
import Beta.WorldSettings;
import Entities.Camera;
import Entities.Entity;
import Entities.Light;
import Entities.Player;
import Models.ModelData;
import Models.TexturedModel;
import RenderEngine.*;
import Textures.ModelTexture;
import Toolbox.MousePicker;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class MainGameLoop {
    public static void main(String[] args) {
        landscapeDemo();
    }

    public static void landscapeDemo(){
        DisplayManager.createDisplay();
        ModelLoader modelLoader = new ModelLoader();

        int chunkSize = WorldSettings.CHUNK_SIZE;
        int scale = WorldSettings.CUBE_SIZE;
        int regionSize = WorldSettings.REGION_SIZE;
        int center = ((regionSize * chunkSize)/2)*scale;

        Light light = new Light(new Vector3f(center, 10000, center), new Vector3f(0.4f,0.4f,0.4f));
        List<Light> lights = new ArrayList<>();
        lights.add(light);

        ModelTexture texture = new ModelTexture(modelLoader.loadTexture("cube"),1,1, false, false);

        Region region = new Region(modelLoader, texture);
        List<Entity> entities = region.getChunkEntities();

        //ModelData playerData = ModelFileLoader.loadModel("player");
        ModelTexture playerTexture = new ModelTexture(modelLoader.loadTexture("player"),1,1, false, false);
        //assert playerData != null;
        TexturedModel playerModel = new TexturedModel(modelLoader.loadModelToVAO(new ModelData(new float[]{}, new float[]{}, new float[]{}, new int[]{}, 0)), playerTexture);
        Player player = new Player(playerModel, new Vector3f(0,0,0), new Vector3f(0,0,0), 1);
        Camera camera = new Camera(player);

        RenderManager renderManager = new RenderManager();
        MousePicker mousePicker = new MousePicker(camera, renderManager.getProjectionMatrix());

        while(!Display.isCloseRequested()){
            camera.move();
            player.move(camera.getAngle(), region);
            mousePicker.update();
            System.out.println(mousePicker.getCurrentRay());

            for(Entity entity:entities){
                renderManager.processModel(entity);
            }
            renderManager.processModel(player);
            renderManager.render(lights, camera);
            DisplayManager.updateDisplay();
        }

        renderManager.cleanUp();
        modelLoader.cleanUp();
        DisplayManager.closeDisplay();
    }
}

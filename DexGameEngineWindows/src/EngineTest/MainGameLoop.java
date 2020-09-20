package EngineTest;

import Beta.Chunk;
import Beta.Cube;
import Beta.FaceOption;
import Beta.Region;
import Entities.Camera;
import Entities.Entity;
import Entities.Light;
import Entities.Player;
import Models.ModelData;
import Models.TexturedModel;
import RenderEngine.*;
import Textures.ModelTexture;
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

        int chunkSize = 16;
        int regionSize = 16;
        int scale = 10;
        int center = ((regionSize * chunkSize)/2)*scale;

        Light light = new Light(new Vector3f(center, 10000, center), new Vector3f(0.4f,0.4f,0.4f));
        List<Light> lights = new ArrayList<>();
        lights.add(light);

        ModelTexture texture = new ModelTexture(modelLoader.loadTexture("cube"),1,1, true, false);
        Region region = new Region(modelLoader, texture);
        List<Entity> entities = region.getChunkEntities();

        TexturedModel playerModel = new TexturedModel(modelLoader.loadToVAO(new ModelData(new float[]{}, new float[]{}, new float[]{}, 0)), texture);
        Player player = new Player(playerModel, new Vector3f(0, chunkSize*scale, 0), new Vector3f(0,0,0), 1);
        Camera camera = new Camera(player);

        RenderManager renderManager = new RenderManager(modelLoader);

        while(!Display.isCloseRequested()){
            camera.move();
            player.move(camera.getAngle(), region);
            for(Entity entity:entities){
                renderManager.processEntity(entity);
            }
            renderManager.processEntity(player);
            renderManager.render(lights, camera);
            DisplayManager.updateDisplay();
        }

        renderManager.cleanUp();
        modelLoader.cleanUp();
        DisplayManager.closeDisplay();
    }
}

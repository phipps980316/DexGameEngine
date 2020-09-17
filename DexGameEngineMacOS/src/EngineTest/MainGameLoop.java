package EngineTest;

import Beta.Chunk;
import Beta.Cube;
import Beta.FaceOption;
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

        Light light = new Light(new Vector3f(10, 10, 10), new Vector3f(0.4f,0.4f,0.4f));
        List<Light> lights = new ArrayList<>();
        lights.add(light);

        ArrayList<FaceOption> faceOptions = new ArrayList<>();
        faceOptions.add(FaceOption.TOP);
        faceOptions.add(FaceOption.LEFT);
        faceOptions.add(FaceOption.RIGHT);
        faceOptions.add(FaceOption.BOTTOM);
        faceOptions.add(FaceOption.FRONT);
        faceOptions.add(FaceOption.BACK);

        int chunkSize = 16;
        Chunk chunk = new Chunk(chunkSize);
        ModelData cubeData = chunk.getRenderData();
        ModelTexture cubeTexture = new ModelTexture(modelLoader.loadTexture("cube1"),1,1, true, false);
        assert cubeData != null;
        TexturedModel cube = new TexturedModel(modelLoader.loadToVAO(cubeData), cubeTexture);

        List<Entity> entities = new ArrayList<>();

        entities.add(new Entity(cube, new Vector3f(0,0,0), new Vector3f(0,0,0), 1));

        Player player = new Player(cube, new Vector3f(chunkSize/2, chunkSize, chunkSize/2), new Vector3f(0,0,0), 1);
        Camera camera = new Camera(player);

        RenderManager renderManager = new RenderManager(modelLoader);

        while(!Display.isCloseRequested()){
            camera.move();
            for(Entity entity:entities){
                renderManager.processEntity(entity);
            }
            renderManager.render(lights, camera);
            DisplayManager.updateDisplay();
        }

        renderManager.cleanUp();
        modelLoader.cleanUp();
        DisplayManager.closeDisplay();
    }
}

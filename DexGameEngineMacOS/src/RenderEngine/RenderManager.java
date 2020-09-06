package RenderEngine;

import DataStructures.Matrix4D;
import DataStructures.Vector3D;
import Entities.Camera;
import Entities.Entity;
import Entities.Light;
import Models.Model;
import Shaders.EntityShader;
import Shaders.TerrainShader;
import Terrains.Terrain;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RenderManager {
    private static final float FOV = 70;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1000f;

    private static final Vector3D skyColour = new Vector3D(0.3f,0.3f,0.3f);

    private Matrix4D projectionMatrix;

    private EntityShader entityShader = new EntityShader();
    private EntityRenderer entityRenderer;

    private TerrainShader terrainShader = new TerrainShader();
    private TerrainRenderer terrainRenderer;

    private Map<Model, List<Entity>> entities = new HashMap<>();
    private List<Terrain> terrains = new ArrayList<>();

    public RenderManager(){
        enableCulling();
        createProjectionMatrix();
        entityRenderer = new EntityRenderer(entityShader, projectionMatrix);
        terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
    }

    private void createProjectionMatrix(){
        float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        float yScale = (float) (1f / Math.tan(Math.toRadians(FOV/2f))) * aspectRatio;
        float xScale = yScale / aspectRatio;
        float frustumLength = FAR_PLANE - NEAR_PLANE;

        projectionMatrix = new Matrix4D();
        projectionMatrix.values[0][0] = xScale;
        projectionMatrix.values[1][1] = yScale;
        projectionMatrix.values[2][2] = -((FAR_PLANE + NEAR_PLANE) / frustumLength);
        projectionMatrix.values[2][3] = -1;
        projectionMatrix.values[3][2] = -((2*NEAR_PLANE*FAR_PLANE) / frustumLength);
        projectionMatrix.values[3][3] = 0;
    }

    public void prepare(){
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(skyColour.x, skyColour.y, skyColour.z, 1);
    }

    public void render(Light light, Camera camera){
        prepare();
        entityShader.start();
        entityShader.loadSkyColour(skyColour);
        entityShader.loadLight(light);
        entityShader.loadViewMatrix(camera);
        entityRenderer.render(entities);
        entityShader.stop();

        terrainShader.start();
        terrainShader.loadSkyColour(skyColour);
        terrainShader.loadLight(light);
        terrainShader.loadViewMatrix(camera);
        terrainRenderer.render(terrains);
        terrainShader.stop();
        terrains.clear();
        entities.clear();
    }

    public static void enableCulling(){
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
    }

    public static void disableCulling(){
        GL11.glDisable(GL11.GL_CULL_FACE);
    }

    public void processTerrain(Terrain terrain){
        terrains.add(terrain);
    }

    public void processEntity(Entity entity){
        Model entityModel = entity.getModel();
        List<Entity> batch = entities.get(entityModel);
        if(batch != null) {
            batch.add(entity);
        }
        else {
            List<Entity> newBatch = new ArrayList<>();
            newBatch.add(entity);
            entities.put(entityModel, newBatch);
        }
    }

    public void cleanUp(){
        entityShader.cleanUp();
        terrainShader.cleanUp();
    }
}

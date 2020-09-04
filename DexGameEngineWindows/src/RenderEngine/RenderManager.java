package RenderEngine;

import Entities.Camera;
import Entities.Entity;
import Entities.Light;
import Models.TexturedModel;
import Shaders.StaticShader;

import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RenderManager {
    private StaticShader shader = new StaticShader();
    private ModelRenderer renderer = new ModelRenderer(shader);

    private Map<TexturedModel, List<Entity>> entities = new HashMap<>();

    public void render(Light light, Camera camera){
        renderer.prepare();
        shader.start();
        shader.loadLight(light);
        shader.loadViewMatrix(camera);
        renderer.render(entities);
        shader.stop();
        entities.clear();
    }

    public void processEntity(Entity entity){
        TexturedModel entityModel = entity.getModel();
        List<Entity> batch = entities.get(entityModel);
        if(batch != null) {
            batch.add(entity);
        }
        else {
            List<Entity> newBatch = new ArrayList<Entity>();
            newBatch.add(entity);
            entities.put(entityModel, newBatch);
        }
    }

    public void cleanUp(){
        shader.cleanUp();
    }
}

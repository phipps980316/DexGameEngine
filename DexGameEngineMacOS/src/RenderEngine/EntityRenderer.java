package RenderEngine;

import Entities.Entity;
import Models.TexturedModel;
import Shaders.EntityShader;
import Textures.ModelTexture;
import Toolbox.MatrixMaths;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import java.util.List;
import java.util.Map;

public class EntityRenderer {

    private EntityShader shader;

    public EntityRenderer(EntityShader shader, Matrix4f projectionMatrix){
        this.shader = shader;
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }

    public void render(Map<TexturedModel, List<Entity>> entities){
        for(TexturedModel model:entities.keySet()){
            prepareTexturedModel(model);
            List<Entity> batch = entities.get(model);
            for(Entity entity:batch){
                prepareInstance(entity);
                GL11.glDrawElements(GL11.GL_TRIANGLES, model.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            }
            unbindTexturedModel();
        }
    }

    private void prepareTexturedModel(TexturedModel model){
        GL30.glBindVertexArray(model.getModel().getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        ModelTexture texture = model.getTexture();
        if(texture.hasTransparency()){
            RenderManager.disableCulling();
        }
        shader.loadFakeLightingVariable(texture.useFakeLighting());
        shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getTextureID());
    }

    private void unbindTexturedModel(){
        RenderManager.enableCulling();
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    private void prepareInstance(Entity entity){
        Matrix4f transformationMatrix = MatrixMaths.createTransformationMatrix(entity.getPosition(), entity.getRotation(), entity.getScale());
        shader.loadTransformationMatrix(transformationMatrix);
    }
}

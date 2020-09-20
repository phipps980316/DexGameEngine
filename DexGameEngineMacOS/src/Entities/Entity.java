package Entities;

import Models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;

public class Entity {
    private TexturedModel model;
    private Vector3f position;
    private Vector3f rotation;
    private float scale;

    private int textureIndex = 0;

    public Entity(TexturedModel model, Vector3f position, Vector3f rotation, float scale) {
        this.model = model;
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public void changePosition(float dx, float dy, float dz){
        this.position.x+=dx;
        this.position.y+=dy;
        this.position.z+=dz;
    }

    public void changeRotation(float dx, float dy, float dz){
        this.rotation.x+=dx;
        this.rotation.y+=dy;
        this.rotation.z+=dz;
    }

    public TexturedModel getModel() {
        return model;
    }

    public void setModel(TexturedModel model) {
        this.model = model;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public float getTextureXOffset(){
        int column = textureIndex % model.getTexture().getNumberOfRows();
        return (float)column/(float)model.getTexture().getNumberOfRows();
    }

    public float getTextureYOffset(){
        int row = textureIndex/model.getTexture().getNumberOfRows();
        return (float)row/(float)model.getTexture().getNumberOfRows();
    }
}

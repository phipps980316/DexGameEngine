package Entities;

import DataStructures.Vector3D;
import Models.Model;

public class Entity {
    private Model model;
    private Vector3D position;
    private Vector3D rotation;
    private float scale;

    public Entity(Model model, Vector3D position, Vector3D rotation, float scale) {
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

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Vector3D getPosition() {
        return position;
    }

    public void setPosition(Vector3D position) {
        this.position = position;
    }

    public Vector3D getRotation() {
        return rotation;
    }

    public void setRotation(Vector3D rotation) {
        this.rotation = rotation;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}

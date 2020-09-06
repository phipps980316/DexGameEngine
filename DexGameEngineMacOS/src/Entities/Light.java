package Entities;

import DataStructures.Vector3D;

public class Light {
    private Vector3D position;
    private Vector3D colour;

    public Light(Vector3D position, Vector3D colour) {
        this.position = position;
        this.colour = colour;
    }

    public Vector3D getPosition() {
        return position;
    }

    public void setPosition(Vector3D position) {
        this.position = position;
    }

    public Vector3D getColour() {
        return colour;
    }

    public void setColour(Vector3D colour) {
        this.colour = colour;
    }
}

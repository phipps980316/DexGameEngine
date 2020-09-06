package Entities;

import DataStructures.Vector3D;
import org.lwjgl.input.Keyboard;

public class Camera {
    private final Vector3D position = new Vector3D(0,5,0);
    private float pitch = 10;
    private float yaw;
    private float roll;

    public Camera(){

    }

    public void move(){
        if(Keyboard.isKeyDown(Keyboard.KEY_W)){
            position.z-=0.02f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_S)){
            position.z+=0.02f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            position.x+=0.02f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_A)){
            position.x-=0.02f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
            position.y+=0.02f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
            position.y-=0.02f;
        }
    }

    public Vector3D getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }
}

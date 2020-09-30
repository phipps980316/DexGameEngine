package Entities;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
    private float angle = 0;

    private final Vector3f position = new Vector3f(0,0,0);
    private float pitch = 20;
    private float yaw = 0;

    private final Player player;

    public Camera(Player player){
        this.player = player;
        Mouse.setGrabbed(true);
    }

    public void move(){
        calculatePitch();
        calculateAngle();
        updateCameraPosition();
        yaw = 180 - (player.getRotation().y);
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getAngle() {
        return angle;
    }

    private void calculatePitch(){
        float pitchChange = Mouse.getDY() * 0.1f;
        pitch -= pitchChange;

        if (pitch >= 90) {
            pitch = 90;
        }
        else if (pitch <= -90) {
            pitch = -90;
        }
    }

    private void calculateAngle() {
        float angleChange = Mouse.getDX() * 0.1f;
        angle -= angleChange;
    }

    private void updateCameraPosition(){
        this.position.x = player.getPosition().x;
        this.position.y = player.getPosition().y + 10;
        this.position.z = player.getPosition().z;
    }
}

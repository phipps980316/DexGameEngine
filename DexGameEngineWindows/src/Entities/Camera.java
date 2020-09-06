package Entities;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
    private float distanceFromPlayer = 50;
    private float angleAroundPlayer = 0;

    private final Vector3f position = new Vector3f(0,50,0);
    private float pitch = 20;
    private float yaw = 0;
    private float roll;

    private final Player player;

    public Camera(Player player){
        this.player = player;
    }

    public void move(){
        calculateZoom();
        calculatePitch();
        calculateAngleAroundPlayer();
        float horizontalDistance = calculateHorizontalDistance();
        float verticalDistance = calculateVerticalDistance();
        calculateCameraPosition(horizontalDistance, verticalDistance);
        yaw = 180 - (player.getRotation().y + angleAroundPlayer);
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

    public float getRoll() {
        return roll;
    }

    private float calculateHorizontalDistance(){
        return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
    }

    private float calculateVerticalDistance(){
        return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
    }

    private void calculateZoom(){
        float zoomLevel = Mouse.getDWheel() * 0.1f;
        distanceFromPlayer -= zoomLevel;
    }

    private void calculatePitch(){
        if(Mouse.isButtonDown(1)){
            float pitchChange = Mouse.getDY() * 0.1f;
            pitch -= pitchChange;
        }
    }

    private void calculateAngleAroundPlayer() {
        if(Mouse.isButtonDown(0)){
            float angleChange = Mouse.getDX() * 0.3f;
            angleAroundPlayer -= angleChange;
        }
    }

    private void calculateCameraPosition(float horizontal, float vertical){
        float theta = player.getRotation().y + angleAroundPlayer;
        float dx = (float) (horizontal * Math.sin(Math.toRadians(theta)));
        float dz = (float) (horizontal * Math.cos(Math.toRadians(theta)));
        position.x = player.getPosition().x - dx;
        position.y = player.getPosition().y + vertical + 10;
        position.z = player.getPosition().z - dz;
    }
}

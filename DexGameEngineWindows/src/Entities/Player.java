package Entities;

import Beta.Region;
import Models.TexturedModel;
import RenderEngine.DisplayManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Player extends Entity{
    private static final float RUN_SPEED = 20;
    private static final float GRAVITY = -50;
    private static final float JUMP_POWER = 30;

    private float currentXSpeed = 0;
    private float currentYSpeed = 0;
    private float currentZSpeed = 0;
    private boolean hasJumped = false;

    public Player(TexturedModel model, Vector3f position, Vector3f rotation, float scale) {
        super(model, position, rotation, scale);
    }

    public void move(float angle, Region region){
        checkKeyboardInputs();
        super.setRotation(new Vector3f(0, angle, 0));

        float yaw = 180 - (getRotation().y + angle);

        float frameTime = DisplayManager.getFrameTime();
        currentYSpeed += GRAVITY * frameTime;
        float XDistance = currentXSpeed * frameTime;
        float YDistance = currentYSpeed * frameTime;
        float ZDistance = currentZSpeed * frameTime;

        float dx = 0;
        float dy;
        float dz = 0;

        //move in X direction
        dx -= (float) (XDistance * Math.sin(Math.toRadians(yaw-90)));
        dz += (float) (XDistance * Math.cos(Math.toRadians(yaw-90)));

        //move in Z direction
        dx += (float) (ZDistance * Math.sin(Math.toRadians(yaw)));
        dz -= (float) (ZDistance * Math.cos(Math.toRadians(yaw)));

        //move in Y direction
        dy = YDistance;

        super.changePosition(dx, dy ,dz);
        checkCollisionsWithTerrain(region);
    }

    private void checkKeyboardInputs(){
        if(Keyboard.isKeyDown(Keyboard.KEY_W) && Keyboard.isKeyDown(Keyboard.KEY_S)){
            currentZSpeed = 0;
        } else if(Keyboard.isKeyDown(Keyboard.KEY_W)){
            currentZSpeed = RUN_SPEED;
        } else if(Keyboard.isKeyDown(Keyboard.KEY_S)){
            currentZSpeed = -RUN_SPEED;
        } else {
            currentZSpeed = 0;
        }

        if(Keyboard.isKeyDown(Keyboard.KEY_D) && Keyboard.isKeyDown(Keyboard.KEY_A)){
            currentXSpeed = 0;
        } else if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            currentXSpeed = RUN_SPEED;
        } else if(Keyboard.isKeyDown(Keyboard.KEY_A)){
            currentXSpeed = -RUN_SPEED;
        } else {
            currentXSpeed = 0;
        }

        if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            if(!hasJumped){
                currentYSpeed = JUMP_POWER;
                hasJumped = true;
            }
        }
    }

    private void checkCollisionsWithTerrain(Region region){
        int terrainHeight = region.heightAtPoint(getPosition().x, getPosition().z);
        if(super.getPosition().y<terrainHeight){
            currentYSpeed = 0;
            hasJumped = false;
            super.getPosition().y = terrainHeight;
        }

        int terrainWidth = region.getChunkSize() * region.getRegionSize() * region.getScale();
        if(super.getPosition().x>terrainWidth){
            super.getPosition().x = terrainWidth;
        } else if(super.getPosition().x<0){
            super.getPosition().x = 0;
        }

        if(super.getPosition().z>terrainWidth){
            super.getPosition().z = terrainWidth;
        } else if(super.getPosition().z<0){
            super.getPosition().z = 0;
        }
    }
}

package Entities;

import Models.TexturedModel;
import RenderEngine.DisplayManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Player extends Entity{
    private static final float RUN_SPEED = 20;
    private static final float TURN_SPEED = 160;
    private static final float GRAVITY = -50;
    private static final float JUMP_POWER = 30;

    private static final float TERRAIN_HEIGHT = 0;

    private float currentSpeed = 0;
    private float currentTurnSpeed = 0;
    private float upwardsSpeed = 0;
    private boolean isInAir = false;

    public Player(TexturedModel model, Vector3f position, Vector3f rotation, float scale) {
        super(model, position, rotation, scale);
    }

    public void move(){
        checkKeyboardInputs();
        super.changeRotation(0, currentTurnSpeed * DisplayManager.getFrameTime(), 0);

        float distance = currentSpeed * DisplayManager.getFrameTime();
        float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotation().y)));
        float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotation().y)));
        super.changePosition(dx, 0 ,dz);
        upwardsSpeed += GRAVITY * DisplayManager.getFrameTime();
        super.changePosition(0, upwardsSpeed * DisplayManager.getFrameTime(), 0);
        if(super.getPosition().y<TERRAIN_HEIGHT){
            upwardsSpeed = 0;
            isInAir = false;
            super.getPosition().y = TERRAIN_HEIGHT;
        }
    }

    private void jump(){
        if(!isInAir){
            upwardsSpeed = JUMP_POWER;
            isInAir = true;
        }
    }

    private void checkKeyboardInputs(){
        if(Keyboard.isKeyDown(Keyboard.KEY_W)){
            currentSpeed = RUN_SPEED;
        } else if(Keyboard.isKeyDown(Keyboard.KEY_S)){
            currentSpeed = -RUN_SPEED;
        } else {
            currentSpeed = 0;
        }

        if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            currentTurnSpeed = -TURN_SPEED;
        } else if(Keyboard.isKeyDown(Keyboard.KEY_A)){
            currentTurnSpeed = TURN_SPEED;
        } else {
            currentTurnSpeed = 0;
        }

        if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            jump();
        }
    }
}

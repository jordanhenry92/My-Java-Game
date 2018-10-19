package entities;

import models.TexturedModel;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import renderEngine.DisplayManager;
import terrains.Terrain;

public class Player extends Entity {

    private static final float RUN_SPEED = 60;
    private static final float TURN_SPEED = 160;
    private static float GRAVITY = -50;
    private static final float JUMP_POWER = 30;
    private static final float TERRAIN_HEIGHT = 0;

    private float currentSpeed = 0;
    private float currentTurnSpeed = 0;
    private float upwardsSpeed = 0;

    private boolean isInAir = false;

    public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
    }

    public void move(DisplayManager window, Terrain terrain) {
        checkInputs(window);
        super.increaseRotation(0, currentTurnSpeed * window.getFrameTimeSeconds(), 0);
        float distance = currentSpeed * window.getFrameTimeSeconds();
        float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
        float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
        super.increasePositiion(dx, 0, dz);
        upwardsSpeed += GRAVITY * window.getFrameTimeSeconds();
        super.increasePositiion(0, upwardsSpeed * window.getFrameTimeSeconds(), 0 );
        float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
        if (super.getPosition().y < terrainHeight) {
            upwardsSpeed = 0;
            isInAir = false;
            super.getPosition().y = terrainHeight;
        }
    }

    private void jump() {
        if (!isInAir) {
            this.upwardsSpeed = JUMP_POWER;
            isInAir = true;
        }
    }

    private void checkInputs(DisplayManager window) {
        if(window.isKeyDown(GLFW.GLFW_KEY_W)){
            this.currentSpeed = RUN_SPEED;
        } else if (window.isKeyDown(GLFW.GLFW_KEY_S)) {
            this.currentSpeed = -RUN_SPEED;
        } else {
            this.currentSpeed = 0;
        }

        if(window.isKeyDown(GLFW.GLFW_KEY_D)){
            this.currentTurnSpeed = -TURN_SPEED;
        } else if (window.isKeyDown(GLFW.GLFW_KEY_A)) {
            this.currentTurnSpeed = TURN_SPEED;
        } else {
            this.currentTurnSpeed = 0;
        }

        if(window.isKeyDown(GLFW.GLFW_KEY_SPACE)){
            jump();
        }

    }

}

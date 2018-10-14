package entities;


import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import renderEngine.DisplayManager;

public class Camera {

    private Vector3f position = new Vector3f(0,0,0);
    private float pitch;
    private float yaw;
    private float roll;

    public Camera(){}

    public void move(DisplayManager window){
        if(window.isKeyDown(GLFW.GLFW_KEY_W)){
            position.z-=0.02f;
        }
        if(window.isKeyDown(GLFW.GLFW_KEY_D)){
            position.x+=0.02f;
        }
        if(window.isKeyDown(GLFW.GLFW_KEY_A)){
            position.x-=0.02f;
        }
        if(window.isKeyDown(GLFW.GLFW_KEY_S)){
            position.z+=0.02f;
        }
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



}
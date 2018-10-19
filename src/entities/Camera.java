package entities;


import com.sun.glass.events.WheelEvent;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import renderEngine.DisplayManager;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class Camera {

    private float distanceFromPlayer = 50;
    private float angleAroundPlayer = 0;


    private Vector3f position = new Vector3f(0,20,0);
    private float pitch = 60;
    private float yaw;
    private float roll;

    private Player player;

    public Camera(Player player){
        this.player = player;
    }

    public void move(DisplayManager window){
        //calculateZoom();
        calculatePitch(window);
        calculateAngleAroundPlayer(window);
        float horizontalDistance = calculateHorizontalDistance();
        float verticalDistance = calculateVerticalDistance();
        calculateCameraPosition(horizontalDistance, verticalDistance);
        this.yaw = 180 - (player.getRotY() + angleAroundPlayer);
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

    private void calculateCameraPosition(float horizontalDistance, float verticalDistance) {
        float theta = player.getRotY() + angleAroundPlayer;
        float offsetX = (float) (horizontalDistance * Math.sin(Math.toRadians(theta)));
        float offsetZ = (float) (horizontalDistance * Math.cos(Math.toRadians(theta)));
        position.x = player.getPosition().x - offsetX;
        position.z = player.getPosition().z - offsetZ;
        position.y = player.getPosition().y + verticalDistance;
    }

    private float calculateHorizontalDistance() {
        return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
    }

    private float calculateVerticalDistance() {
        return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
    }

    private void calculateZoom() {
        float zoomLevel = MouseEvent.MOUSE_WHEEL * 0.1f;
        distanceFromPlayer -= zoomLevel;

    }

    private void calculatePitch(DisplayManager window) {
        if (window.isMouseDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
            float pitchChange = window.getDY(window) * 0.2f;
            pitch += pitchChange;
        }
        window.getDY(window);
    }

    private void calculateAngleAroundPlayer(DisplayManager window) {
        if (window.isMouseDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
            float angleChange = window.getDX(window) * 0.3f;
            angleAroundPlayer -= angleChange;
        }
        window.getDX(window);
    }
}

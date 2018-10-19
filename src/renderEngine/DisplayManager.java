package renderEngine;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import java.nio.DoubleBuffer;

public class DisplayManager {

    private int width, height;
    private String title;
    private long window;
    private double fps_cap, time, processedTime = 0;
    private boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
    private boolean[] mouseButtons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];


    public float oldMouseX = -1, oldMouseY = -1, newMouseX = 0, newMouseY = 0;

    private static long lastFrameTime;
    private static float delta;

    public DisplayManager(int width, int height, int fps, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
        fps_cap = fps;
    }

    public void createDisplay() {
        if (!GLFW.glfwInit()) {
            System.err.println("Error: Couldn't initialize GLFW");
            System.exit(-1);
        }

        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);

        window = GLFW.glfwCreateWindow(width, height, title, 0, 0);

        if (window == 0) {
            System.err.println("Error: Window could not be created.");
            System.exit(-1);
        }

        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();

        GLFWVidMode videomode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        GLFW.glfwSetWindowPos(window, (videomode.width() - width)/2, (videomode.height() - height)/2);

        GLFW.glfwShowWindow(window);
        lastFrameTime = getCurrentTime();

        time = getTime();
    }

    public boolean closed() {
        return GLFW.glfwWindowShouldClose(window);
    }

    public void update() {
        for (int i = 0; i < GLFW.GLFW_KEY_LAST; i++) {
            keys[i] = isKeyDown(i);
        }
        for (int i = 0; i < GLFW.GLFW_MOUSE_BUTTON_LAST; i++) {
            mouseButtons[i] = isMouseDown(i);
        }
        GLFW.glfwPollEvents();
        long currentFrameTime = getCurrentTime();
        delta = (currentFrameTime - lastFrameTime)/1000f;
        lastFrameTime = currentFrameTime;
    }

    public static float getFrameTimeSeconds() {
        return delta;
    }

    private static long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public void stop() {
        GLFW.glfwTerminate();
    }

    public void swapBuffers() {
        GLFW.glfwSwapBuffers(window);
    }

    public double getTime() {
        return (double) System.nanoTime() / (double) 1000000000;
    }

    // These functions lets us know if a key or mouse button is being pressed or not.

    public boolean isKeyDown(int keycode) {
        return GLFW.glfwGetKey(window, keycode) == 1;
    }

    public boolean isMouseDown(int mouseButton) {
        return GLFW.glfwGetMouseButton(window, mouseButton) == 1;
    }

    public boolean isKeyPressed(int keycode) {
        return isKeyDown(keycode) && !keys[keycode];
    }

    public boolean isKeyReleased(int keycode) {
        return !isKeyDown(keycode) && keys[keycode];
    }

    public boolean isMousePressed(int mouseButton) {
        return isMouseDown(mouseButton) && !mouseButtons[mouseButton];
    }

    public boolean isMouseReleased(int mouseButton) {
        return !isMouseDown(mouseButton) && mouseButtons[mouseButton];
    }

    // Gives us the coordinates

    public double getMouseX() {
        DoubleBuffer buffer = BufferUtils.createDoubleBuffer(1);
        GLFW.glfwGetCursorPos(window, buffer, null);
        return buffer.get(0);
    }

    public double getMouseY() {
        DoubleBuffer buffer = BufferUtils.createDoubleBuffer(1);
        GLFW.glfwGetCursorPos(window, null, buffer);
        return buffer.get(0);
    }

    public boolean isUpdating() {
        double nextTime = getTime();
        double passedTime = nextTime - time;
        processedTime += passedTime;
        time = nextTime;

        while (processedTime > 1.0/fps_cap) {
            processedTime -= 1.0/fps_cap;
            return true;
        }

        return false;
    }

    public float getDY(DisplayManager window) {
        newMouseY = (float) window.getMouseY();
        float dy = newMouseY - oldMouseY;
        oldMouseY = newMouseY;
        return dy;
    }

    public float getDX(DisplayManager window) {
        newMouseX = (float) window.getMouseX();
        float dx = newMouseX - oldMouseX;
        oldMouseX = newMouseX;
        return dx;

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getTitle() {
        return title;
    }

    public long getWindow() {
        return window;
    }

    public double getFPS() {
        return fps_cap;
    }



}

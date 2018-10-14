package engineTester;

import Shaders.StaticShader;
import entities.Camera;
import entities.Entity;
import entities.Light;
import models.TexturedModel;
import org.joml.Vector3f;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import models.RawModel;
import renderEngine.OBJLoader;
import renderEngine.Renderer;
import textures.ModelTexture;

import java.io.IOException;

public class MainGameLoop {

    public static final int WIDTH = 1280, HEIGHT = 720, FPS = 60;

    public static void main(String[] args) throws IOException {

        DisplayManager window = new DisplayManager(WIDTH, HEIGHT, FPS, "My Java Game");
        window.createDisplay();

        Loader loader = new Loader();
        StaticShader shader = new StaticShader();
        Renderer renderer = new Renderer(shader);

        RawModel model = OBJLoader.loadObjModel("dragon", loader);

        TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("res/brewster.png")));

        Entity entity = new Entity(staticModel, new Vector3f(0, 0, -25), 0, 0, 0, 1);
        Light light = new Light(new Vector3f(0, 0, -20), new Vector3f(1, 1, 1));

        Camera camera = new Camera();

        while (!window.closed()) {
            if (window.isUpdating()) {
                entity.increaseRotation(0, 1, 0);
                camera.move(window);
                renderer.prepare();
                shader.start();
                shader.loadLight(light);
                shader.loadViewMatrix(camera);
                renderer.render(entity, shader);
                shader.stop();
                window.update();
                window.swapBuffers();
            }
        }
        loader.cleanUp();
        shader.cleanUp();
        window.stop();
    }

}

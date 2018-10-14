package engineTester;

import Shaders.StaticShader;
import entities.Camera;
import entities.Entity;
import entities.Light;
import models.TexturedModel;
import org.joml.Vector3f;
import renderEngine.*;
import models.RawModel;
import textures.ModelTexture;

import java.io.IOException;

public class MainGameLoop {

    public static final int WIDTH = 1280, HEIGHT = 720, FPS = 60;

    public static void main(String[] args) throws IOException {

        DisplayManager window = new DisplayManager(WIDTH, HEIGHT, FPS, "My Java Game");
        window.createDisplay();

        Loader loader = new Loader();

        RawModel model = OBJLoader.loadObjModel("dragon", loader);

        TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("res/brewster.png")));
        ModelTexture texture = staticModel.getTexture();
        texture.setShineDamper(10);
        texture.setReflectivity(1);

        Entity entity = new Entity(staticModel, new Vector3f(0, -5, -15), 0, 0, 0, 1);
        Light light = new Light(new Vector3f(0, 0, -10), new Vector3f(1, 1, 1));

        Camera camera = new Camera();

        MasterRenderer renderer = new MasterRenderer();

        while (!window.closed()) {
            if (window.isUpdating()) {
                entity.increaseRotation(0, 0.5f, 0);
                camera.move(window);
                renderer.processEntity(entity);
                renderer.render(light, camera);
                window.update();
                window.swapBuffers();
            }
        }
        renderer.cleanUp();
        loader.cleanUp();
        window.stop();
    }

}

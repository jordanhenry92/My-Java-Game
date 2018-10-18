package engineTester;

import Shaders.StaticShader;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import models.TexturedModel;
import org.joml.Vector3f;
import renderEngine.*;
import models.RawModel;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainGameLoop {

    public static final int WIDTH = 1280, HEIGHT = 720, FPS = 60;

    public static void main(String[] args) throws IOException {

        DisplayManager window = new DisplayManager(WIDTH, HEIGHT, FPS, "My Java Game");
        window.createDisplay();
        Loader loader = new Loader();

        //*****************TERRAIN TEXTURE STUFF*********************

        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("res/grassy.png"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("res/dirt.png"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("res/pinkFlowers.png"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("res/path.png"));

        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("res/blendMap.png"));

        //***********************************************************

        //*****************MODEL LOADING STUFF***********************

        RawModel tree = OBJLoader.loadObjModel("lowPolyTree", loader);
        RawModel fern = OBJLoader.loadObjModel("fern", loader);
        RawModel grass = OBJLoader.loadObjModel("grassModel", loader);

        TexturedModel treeModel = new TexturedModel(tree, new ModelTexture(loader.loadTexture("res/lowPolyTree.png")));
        TexturedModel fernModel = new TexturedModel(fern, new ModelTexture(loader.loadTexture("res/fern.png")));
        TexturedModel grassModel = new TexturedModel(grass, new ModelTexture(loader.loadTexture("res/grassTexture.png")));

        fernModel.getTexture().setHasTransparency(true);
        grassModel.getTexture().setHasTransparency(true);

        ModelTexture texture = treeModel.getTexture();
        texture.setShineDamper(10);
        texture.setReflectivity(0.5f);

        Light light = new Light(new Vector3f(2000, 2000, 2000), new Vector3f(1, 1, 1));

        Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap);
        Terrain terrain2 = new Terrain(-1, -1, loader, texturePack, blendMap);


        List<Entity> entities = new ArrayList<Entity>();
        Random random = new Random();
        for (int i=0; i<100; i++) {
            entities.add(new Entity(treeModel, new Vector3f(random.nextFloat() *800 - 400, 0, random.nextFloat() * -600), 0, 0,0, 2.5f));
            entities.add(new Entity(fernModel, new Vector3f(random.nextFloat() *800 - 400, 0, random.nextFloat() * -600), 0, 0,0, 0.6f));
            entities.add(new Entity(grassModel, new Vector3f(random.nextFloat() *800 - 400, 0, random.nextFloat() * -600), 0, 0,0, 2f));
        }

        Camera camera = new Camera();

        MasterRenderer renderer = new MasterRenderer();

        RawModel bunnyModel = OBJLoader.loadObjModel("bunny", loader);
        TexturedModel stanfordBunny = new TexturedModel(bunnyModel, new ModelTexture(loader.loadTexture("res/white.png")));

        Player player = new Player(stanfordBunny, new Vector3f(0, 0, -50), 0, 0, 0, 1);

        while (!window.closed()) {
            if (window.isUpdating()) {
                camera.move(window);
                player.move(window);
                renderer.processEntity(player);

                renderer.processTerrain(terrain);
                renderer.processTerrain(terrain2);

                for(Entity entity:entities){
                    renderer.processEntity(entity);
                }
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

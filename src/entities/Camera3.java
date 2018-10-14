package entities;

import toolbox.Matrix4f;
import org.joml.Vector3f;

public class Camera3 {

    private Vector3f position, rotation;

    public Camera3() {
        position = new Vector3f(0, 0, 0);
        rotation = new Vector3f(0, 0, 0);
    }

    public Camera3(Vector3f position, Vector3f rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public Matrix4f getViewMatrix() {
        Matrix4f rotateX = new Matrix4f().rotateAround(rotation.x(), new Vector3f(1, 0, 0));
        Matrix4f rotateY = new Matrix4f().rotateAround(rotation.y(), new Vector3f(0, 1, 0));
        Matrix4f rotateZ = new Matrix4f().rotateAround(rotation.z(), new Vector3f(0, 0, 1));
        Matrix4f rotation = rotateX.mul(rotateY.mul(rotateZ));

        Vector3f negPosition = position.mul(-1);
        Matrix4f translation = new Matrix4f().translate(negPosition);

        return translation.mul(rotation);
    }

    public void addPosition(Vector3f value) {
        position = position.add(value);
    }

    public void addPosition(float x, float y, float z) {
        position = position.add(new Vector3f(x, y, x));
    }

    public void addRotation(Vector3f value) {
        rotation = rotation.add(value);
    }

    public void addRotation(float x, float y, float z) {
        rotation = rotation.add(new Vector3f(x, y, x));
    }

    public void setPosition(Vector3f value) {
        position = value;
    }

    public void setPosition(float x, float y, float z) {
        position = new Vector3f(x, y, z);
    }

    public void setRotation(Vector3f value) {
        rotation = value;
    }

    public void setdRotation(float x, float y, float z) {
        rotation = new Vector3f(x, y, z);
    }











}
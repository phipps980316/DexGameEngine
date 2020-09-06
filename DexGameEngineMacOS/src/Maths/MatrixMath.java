package Maths;

import DataStructures.Matrix4D;
import DataStructures.Vector3D;
import Entities.Camera;


public class MatrixMath {
    public static Matrix4D createTransformationMatrix(Vector3D translation, Vector3D rotation, float scale){
        Matrix4D matrix = new Matrix4D();
        matrix.translate(translation);
        matrix.rotate((float) Math.toRadians(rotation.x), new Vector3D(1,0,0));
        matrix.rotate((float) Math.toRadians(rotation.y), new Vector3D(0,1,0));
        matrix.rotate((float) Math.toRadians(rotation.z), new Vector3D(0,0,1));
        matrix.scale(new Vector3D(scale, scale, scale));
        return matrix;
    }

    public static Matrix4D createViewMatrix(Camera camera){
        Matrix4D viewMatrix = new Matrix4D();
        viewMatrix.rotate((float) Math.toRadians(camera.getPitch()), new Vector3D(1,0,0));
        viewMatrix.rotate((float) Math.toRadians(camera.getYaw()), new Vector3D(0,1,0));
        Vector3D cameraPosition = camera.getPosition();
        Vector3D negativeCameraPosition = new Vector3D(-cameraPosition.x, -cameraPosition.y, -cameraPosition.z);
        viewMatrix.translate(negativeCameraPosition);
        return viewMatrix;
    }
}

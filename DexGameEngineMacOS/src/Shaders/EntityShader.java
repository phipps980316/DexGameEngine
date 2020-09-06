package Shaders;

import DataStructures.Matrix4D;
import DataStructures.Vector3D;
import Entities.Camera;
import Entities.Light;
import Maths.MatrixMath;

public class EntityShader extends Shader {

    private static final String VERTEX_FILE = "src/shaders/entityVertexShader.txt";
    private static final String FRAGMENT_FILE = "src/shaders/entityFragmentShader.txt";

    private int transformationMatrixLocation;
    private int projectionMatrixLocation;
    private int viewMatrixLocation;
    private int lightPositionLocation;
    private int lightColourLocation;
    private int shineDamperLocation;
    private int reflectivityLocation;
    private int fakeLightingLocation;
    private int skyColourLocation;

    public EntityShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttributes(0, "position");
        super.bindAttributes(1, "textureCoords");
        super.bindAttributes(2, "normal");
    }

    @Override
    protected void getAllUniformVariableLocations() {
        transformationMatrixLocation = super.getUniformVariableLocation("transformationMatrix");
        projectionMatrixLocation = super.getUniformVariableLocation("projectionMatrix");
        viewMatrixLocation = super.getUniformVariableLocation("viewMatrix");
        lightPositionLocation = super.getUniformVariableLocation("lightPosition");
        lightColourLocation = super.getUniformVariableLocation("lightColour");
        shineDamperLocation = super.getUniformVariableLocation("shineDamper");
        reflectivityLocation = super.getUniformVariableLocation("reflectivity");
        fakeLightingLocation = super.getUniformVariableLocation("useFakeLighting");
        skyColourLocation = super.getUniformVariableLocation("skyColour");

    }

    public void loadTransformationMatrix(Matrix4D matrix) {
        super.loadMatrix(transformationMatrixLocation, matrix);
    }

    public void loadProjectionMatrix(Matrix4D matrix) {
        super.loadMatrix(projectionMatrixLocation, matrix);
    }

    public void loadViewMatrix(Camera camera) {
        Matrix4D viewMatrix = MatrixMath.createViewMatrix(camera);
        super.loadMatrix(viewMatrixLocation, viewMatrix);
    }

    public void loadLight(Light light){
        super.loadVector(lightPositionLocation, light.getPosition());
        super.loadVector(lightColourLocation, light.getColour());
    }

    public void loadShineVariables(float damper, float reflectivity){
        super.loadFloat(shineDamperLocation, damper);
        super.loadFloat(reflectivityLocation, reflectivity);
    }

    public void loadFakeLightingVariable(boolean useFakeLighting){
        super.loadBoolean(fakeLightingLocation, useFakeLighting);
    }

    public void loadSkyColour(Vector3D rgb){
        super.loadVector(skyColourLocation, rgb);
    }
}

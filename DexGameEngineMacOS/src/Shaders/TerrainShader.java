package Shaders;

import Entities.Camera;
import Entities.Light;
import Toolbox.Maths;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class TerrainShader extends Shader{
    private static final String VERTEX_FILE = "src/shaders/terrainVertexShader.txt";
    private static final String FRAGMENT_FILE = "src/shaders/terrainFragmentShader.txt";

    private int transformationMatrixLocation;
    private int projectionMatrixLocation;
    private int viewMatrixLocation;
    private int lightPositionLocation;
    private int lightColourLocation;
    private int shineDamperLocation;
    private int reflectivityLocation;
    private int skyColourLocation;

    public TerrainShader() {
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
        skyColourLocation = super.getUniformVariableLocation("skyColour");
    }

    public void loadTransformationMatrix(Matrix4f matrix) {
        super.loadMatrix(transformationMatrixLocation, matrix);
    }

    public void loadProjectionMatrix(Matrix4f matrix) {
        super.loadMatrix(projectionMatrixLocation, matrix);
    }

    public void loadViewMatrix(Camera camera) {
        Matrix4f viewMatrix = Maths.createViewMatrix(camera);
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

    public void loadSkyColour(Vector3f rgb){
        super.loadVector(skyColourLocation, rgb);
    }
}

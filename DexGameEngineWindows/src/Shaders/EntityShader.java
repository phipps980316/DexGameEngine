package Shaders;

import Entities.Camera;
import Entities.Light;
import Toolbox.Maths;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

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
    private int numberOfRowsLocation;
    private int offsetLocation;

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
        numberOfRowsLocation = super.getUniformVariableLocation("numberOfRows");
        offsetLocation = super.getUniformVariableLocation("offset");

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
        super.load3DVector(lightPositionLocation, light.getPosition());
        super.load3DVector(lightColourLocation, light.getColour());
    }

    public void loadShineVariables(float damper, float reflectivity){
        super.loadFloat(shineDamperLocation, damper);
        super.loadFloat(reflectivityLocation, reflectivity);
    }

    public void loadFakeLightingVariable(boolean useFakeLighting){
        super.loadBoolean(fakeLightingLocation, useFakeLighting);
    }

    public void loadSkyColour(Vector3f rgb){
        super.load3DVector(skyColourLocation, rgb);
    }

    public void loadNumberOfRows(int number){
        super.loadFloat(numberOfRowsLocation, number);
    }

    public void loadOffset(float x, float y){
        super.load2DVector(offsetLocation, new Vector2f(x, y));
    }


}

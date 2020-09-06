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
    private int backgroundTextureLocation;
    private int rTextureLocation;
    private int gTextureLocation;
    private int bTextureLocation;
    private int blendMapLocation;

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
        backgroundTextureLocation = super.getUniformVariableLocation("backgroundTexture");
        rTextureLocation = super.getUniformVariableLocation("rTexture");
        gTextureLocation = super.getUniformVariableLocation("gTexture");
        bTextureLocation = super.getUniformVariableLocation("bTexture");
        blendMapLocation = super.getUniformVariableLocation("blendMap");
    }

    public void connectTextures(){
        super.loadInt(backgroundTextureLocation, 0);
        super.loadInt(rTextureLocation, 1);
        super.loadInt(gTextureLocation, 2);
        super.loadInt(bTextureLocation, 3);
        super.loadInt(blendMapLocation, 4);
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

    public void loadSkyColour(Vector3f rgb){
        super.load3DVector(skyColourLocation, rgb);
    }
}

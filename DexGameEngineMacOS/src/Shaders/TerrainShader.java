package Shaders;

import Entities.Camera;
import Entities.Light;
import Toolbox.Maths;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.util.List;

public class TerrainShader extends Shader{

    private static final int MAX_LIGHTS = 4;

    private static final String VERTEX_FILE = "src/shaders/terrainVertexShader.txt";
    private static final String FRAGMENT_FILE = "src/shaders/terrainFragmentShader.txt";

    private int transformationMatrixLocation;
    private int projectionMatrixLocation;
    private int viewMatrixLocation;
    private int lightPositionLocation[];
    private int lightColourLocation[];
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
        shineDamperLocation = super.getUniformVariableLocation("shineDamper");
        reflectivityLocation = super.getUniformVariableLocation("reflectivity");
        skyColourLocation = super.getUniformVariableLocation("skyColour");
        backgroundTextureLocation = super.getUniformVariableLocation("backgroundTexture");
        rTextureLocation = super.getUniformVariableLocation("rTexture");
        gTextureLocation = super.getUniformVariableLocation("gTexture");
        bTextureLocation = super.getUniformVariableLocation("bTexture");
        blendMapLocation = super.getUniformVariableLocation("blendMap");

        lightPositionLocation = new int[MAX_LIGHTS];
        lightColourLocation = new int[MAX_LIGHTS];
        for(int i = 0; i < MAX_LIGHTS; i++){
            lightPositionLocation[i] = super.getUniformVariableLocation("lightPosition["+i+"]");
            lightColourLocation[i] = super.getUniformVariableLocation("lightColour["+i+"]");
        }
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

    public void loadLights(List<Light> lights){
        for(int i = 0; i < MAX_LIGHTS; i++){
            if(i<lights.size()){
                super.load3DVector(lightPositionLocation[i], lights.get(i).getPosition());
                super.load3DVector(lightColourLocation[i], lights.get(i).getColour());
            } else {
                super.load3DVector(lightPositionLocation[i], new Vector3f(0,0,0));
                super.load3DVector(lightColourLocation[i], new Vector3f(0,0,0));
            }
        }
    }

    public void loadShineVariables(float damper, float reflectivity){
        super.loadFloat(shineDamperLocation, damper);
        super.loadFloat(reflectivityLocation, reflectivity);
    }

    public void loadSkyColour(Vector3f rgb){
        super.load3DVector(skyColourLocation, rgb);
    }
}

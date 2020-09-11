package Shaders;

import RenderEngine.DisplayManager;
import org.lwjgl.util.vector.Matrix4f;

import Entities.Camera;

import Shaders.Shader;
import Toolbox.Maths;
import org.lwjgl.util.vector.Vector3f;

public class SkyboxShader extends Shader{

    private static final String VERTEX_FILE = "src/shaders/skyboxVertexShader.txt";
    private static final String FRAGMENT_FILE = "src/shaders/skyboxFragmentShader.txt";

    private static final float ROTATE_SPEED = 1.0f;

    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_fogColor;
    private int location_cubeMap;
    private int location_cubeMap2;
    private int location_blendFactor;


    private float rotation = 0;

    public SkyboxShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    public void loadProjectionMatrix(Matrix4f matrix){
        super.loadMatrix(location_projectionMatrix, matrix);
    }

    public void loadViewMatrix(Camera camera){
        Matrix4f matrix = Maths.createViewMatrix(camera);
        matrix.m30 = 0;
        matrix.m31 = 0;
        matrix.m32 = 0;
        rotation += ROTATE_SPEED * DisplayManager.getFrameTime();
        Matrix4f.rotate((float) Math.toRadians(rotation), new Vector3f(0,1,0), matrix, matrix);
        super.loadMatrix(location_viewMatrix, matrix);
    }

    @Override
    protected void getAllUniformVariableLocations() {
        location_projectionMatrix = super.getUniformVariableLocation("projectionMatrix");
        location_viewMatrix = super.getUniformVariableLocation("viewMatrix");
        location_fogColor = super.getUniformVariableLocation("fogColour");
        location_cubeMap = super.getUniformVariableLocation("cubeMap");
        location_cubeMap2 = super.getUniformVariableLocation("cubeMap2");
        location_blendFactor = super.getUniformVariableLocation("blendFactor");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttributes(0, "position");
    }

    public void loadFogColour(Vector3f rgb){
        super.load3DVector(location_fogColor, rgb);
    }

    public void connectTextureUnits(){
        super.loadInt(location_cubeMap, 0);
        super.loadInt(location_cubeMap2, 1);
    }

    public void loadBlendFactor(float blend){
        super.loadFloat(location_blendFactor, blend);
    }
}

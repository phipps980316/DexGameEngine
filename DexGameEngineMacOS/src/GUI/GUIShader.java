package GUI;

import org.lwjgl.util.vector.Matrix4f;

import Shaders.Shader;

public class GUIShader extends Shader{

    private static final String VERTEX_FILE = "src/GUI/guiVertexShader.txt";
    private static final String FRAGMENT_FILE = "src/GUI/guiFragmentShader.txt";

    private int location_transformationMatrix;

    public GUIShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    public void loadTransformation(Matrix4f matrix){
        super.loadMatrix(location_transformationMatrix, matrix);
    }

    @Override
    protected void getAllUniformVariableLocations() {
        location_transformationMatrix = super.getUniformVariableLocation("transformationMatrix");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttributes(0, "position");
    }




}

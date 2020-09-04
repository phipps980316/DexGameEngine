package RenderEngine;

import Models.RawModel;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class OBJLoader {
    public static RawModel loadObjModel(String filename, ModelLoader modelLoader){
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(new File("res/"+filename+".obj"));
        } catch (FileNotFoundException e) {
            System.err.println("Couldn't Load OBJ File!");
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(fileReader);
        String line;
        List<Vector3f> vertices = new ArrayList<>();
        List<Vector2f> textures = new ArrayList<>();
        List<Vector3f> normals = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();
        float[] verticesArray = null;
        float[] normalsArray = null;
        float[] textureArray = null;
        int[] indicesArray = null;
        try{
            while(true){
                line = reader.readLine();
                String[] currentLine = line.split(" ");
                if(line.startsWith("v ")){
                    Vector3f vertex = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
                    vertices.add(vertex);
                }else if(line.startsWith("vt ")){
                    Vector2f texture = new Vector2f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]));
                    textures.add(texture);
                }else if(line.startsWith("vn ")){
                    Vector3f normal = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
                    normals.add(normal);
                }else if(line.startsWith("f ")){
                    textureArray = new float[vertices.size()*2];
                    normalsArray = new float[vertices.size()*3];
                    break;
                }
            }

            while(line!=null){
                if(!line.startsWith("f ")){
                    line = reader.readLine();
                    continue;
                }
                String[] currentLine = line.split(" ");
                String[] vertex1 = currentLine[1].split("/");
                String[] vertex2 = currentLine[2].split("/");
                String[] vertex3 = currentLine[3].split("/");
                processVertex(vertex1, indices, textures, normals, textureArray, normalsArray);
                processVertex(vertex2, indices, textures, normals, textureArray, normalsArray);
                processVertex(vertex3, indices, textures, normals, textureArray, normalsArray);
                line= reader.readLine();
            }
            reader.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        verticesArray = new float[vertices.size()*3];
        indicesArray = new int[indices.size()];

        int vertexPointer = 0;
        for(Vector3f vertex:vertices){
            verticesArray[vertexPointer++] = vertex.x;
            verticesArray[vertexPointer++] = vertex.y;
            verticesArray[vertexPointer++] = vertex.z;
        }

        for(int i=0; i < indices.size(); i++){
            indicesArray[i] = indices.get(i);
        }
        return modelLoader.loadToVAO(verticesArray, textureArray, normalsArray, indicesArray);
    }

    private static void processVertex(String[] vertex, List<Integer> indices, List<Vector2f> textures, List<Vector3f> normals, float[] textureArray, float[] normalsArray) {
        int currentVertex = Integer.parseInt(vertex[0]) - 1;
        indices.add(currentVertex);
        Vector2f currentTexture = textures.get(Integer.parseInt(vertex[1])-1);
        textureArray[currentVertex*2] = currentTexture.x;
        textureArray[currentVertex*2+1] = 1 - currentTexture.y;
        Vector3f currentNormal = normals.get(Integer.parseInt(vertex[2])-1);
        normalsArray[currentVertex*3] = currentNormal.x;
        normalsArray[currentVertex*3+1] = currentNormal.y;
        normalsArray[currentVertex*3+2] = currentNormal.z;
    }
}

package Beta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cube {
    //private ArrayList<Face> faces;
    private HashMap<FaceOption, Face> faces;
    private int faceCount;

    private ArrayList<Point3D> allVertices;
    private ArrayList<Point2D> allTextures;
    private ArrayList<Point3D> allNormals;
    private Point3D worldPosition;



    public Cube(ArrayList<FaceOption> faceOptions, int posX, int posY, int posZ){
        faces = new HashMap<FaceOption, Face>();
        faceCount = 0;
        for (FaceOption faceOption : faceOptions) {
            faces.put(faceOption, new Face(faceOption));
            faceCount++;
        }
        allVertices = new ArrayList<>();
        allTextures = new ArrayList<>();
        allNormals = new ArrayList<>();
        worldPosition = new Point3D(posX, posY, posZ);
    }

    public void removeFace(FaceOption faceOption){
        faces.remove(faceOption);
        faceCount--;
    }

    public void constructCube(){
        for (Map.Entry<FaceOption, Face> entry : faces.entrySet()){
            Face face = entry.getValue();
            for(int j = 0; j < face.getVertices().size(); j++){
                Vertex vertex = face.getVertices().get(j);
                Point3D vertexLocalPosition = vertex.getPosition();
                Point3D vertexWorldPosition = new Point3D(vertexLocalPosition.x + worldPosition.x, vertexLocalPosition.y + worldPosition.y, vertexLocalPosition.z + worldPosition.z);
                allVertices.add(vertexWorldPosition);
                allTextures.add(vertex.getTexture());
                allNormals.add(vertex.getNormal());
            }
        }
    }

    public ArrayList<Point3D> getAllVertices() {
        return allVertices;
    }

    public ArrayList<Point2D> getAllTextures() {
        return allTextures;
    }

    public ArrayList<Point3D> getAllNormals() {
        return allNormals;
    }

    public int getFaceCount() {
        return faceCount;
    }

    /*public ModelData getRenderData() {
        ArrayList<Point3D> allVertices = new ArrayList<>();
        ArrayList<Point2D> allTextures = new ArrayList<>();
        ArrayList<Point3D> allNormals = new ArrayList<>();

        for (Map.Entry<FaceOption, Face> entry : faces.entrySet()){
            Face face = entry.getValue();
            for(int j = 0; j < face.getVertices().size(); j++){
                Vertex vertex = face.getVertices().get(j);
                allVertices.add(vertex.getPosition());
                allTextures.add(vertex.getTexture());
                allNormals.add(vertex.getNormal());
            }
        }

        for (int i = 0; i < allVertices.size(); i++) {
            Point3D vertex = allVertices.get(i);
            this.vertices[i * 3] = vertex.x;
            this.vertices[i * 3 + 1] = vertex.y;
            this.vertices[i * 3 + 2] = vertex.z;

            Point2D texture = allTextures.get(i);
            this.textures[i * 2] = texture.x;
            this.textures[i * 2 + 1] = texture.y;

            Point3D normal = allNormals.get(i);
            this.normals[i * 3] = normal.x;
            this.normals[i * 3 + 1] = normal.y;
            this.normals[i * 3 + 2] = normal.z;

        }

        int count = allVertices.size();
        return new ModelData(this.vertices, this.textures, this.normals, count);

    }*/

}

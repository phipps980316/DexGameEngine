package Beta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cube {
    private final HashMap<FaceOption, Face> faces;
    private int faceCount;

    private final Point3D chunkPosition;

    public Cube(ArrayList<FaceOption> faceOptions, int posX, int posY, int posZ){
        faces = new HashMap<>();
        faceCount = 0;
        for (FaceOption faceOption : faceOptions) {
            faces.put(faceOption, new Face(faceOption));
            faceCount++;
        }
        chunkPosition = new Point3D(posX, posY, posZ);
    }

    public void removeFace(FaceOption faceOption){
        faces.remove(faceOption);
        faceCount--;
    }

    public CubeData constructCube(){
        CubeData cubeData = new CubeData();
        for (Map.Entry<FaceOption, Face> entry : faces.entrySet()){
            Face face = entry.getValue();
            for(int j = 0; j < face.getVertices().size(); j++){
                Vertex vertex = face.getVertices().get(j);
                Point3D vertexLocalPosition = vertex.getPosition();
                Point3D vertexWorldPosition = new Point3D(vertexLocalPosition.x + chunkPosition.x, vertexLocalPosition.y + chunkPosition.y, vertexLocalPosition.z + chunkPosition.z);
                cubeData.addVertex(vertexWorldPosition);
                cubeData.addTexture(vertex.getTexture());
                cubeData.addNormal(vertex.getNormal());
            }
        }
        return cubeData;
    }

    public int getFaceCount() {
        return faceCount;
    }
}

/*package Beta;

        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.Map;

public class Cube {
    private final HashMap<FaceOption, Face> faces;
    private int faceCount;

    private final ArrayList<Point3D> allVertices;
    private final ArrayList<Point2D> allTextures;
    private final ArrayList<Point3D> allNormals;
    private final Point3D chunkPosition;

    public Cube(ArrayList<FaceOption> faceOptions, int posX, int posY, int posZ){
        faces = new HashMap<>();
        faceCount = 0;
        for (FaceOption faceOption : faceOptions) {
            faces.put(faceOption, new Face(faceOption));
            faceCount++;
        }
        allVertices = new ArrayList<>();
        allTextures = new ArrayList<>();
        allNormals = new ArrayList<>();
        chunkPosition = new Point3D(posX, posY, posZ);
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
                Point3D vertexWorldPosition = new Point3D(vertexLocalPosition.x + chunkPosition.x, vertexLocalPosition.y + chunkPosition.y, vertexLocalPosition.z + chunkPosition.z);
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
}*/


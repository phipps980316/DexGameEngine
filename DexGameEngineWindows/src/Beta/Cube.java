package Beta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cube {
    private final HashMap<FaceOption, Face> faces;
    private HitBox hitBox;
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
        int faceNumber = 0;
        for (Map.Entry<FaceOption, Face> entry : faces.entrySet()){
            Face face = entry.getValue();
            for(int j = 0; j < face.getVertices().size(); j++){
                Vertex vertex = face.getVertices().get(j);
                Point3D vertexLocalPosition = vertex.getPosition();
                Point3D vertexWorldPosition = new Point3D(vertexLocalPosition.x + chunkPosition.x, vertexLocalPosition.y + chunkPosition.y, vertexLocalPosition.z + chunkPosition.z);
                cubeData.addVertex(new Vertex(vertexWorldPosition, vertex.getTexture(), vertex.getNormal()));
            }
            cubeData.addIndices(face.getIndices(faceNumber));
            faceNumber++;
        }
        return cubeData;
    }

    public int getFaceCount() {
        return faceCount;
    }

    public HitBox getHitBox() {
        return hitBox;
    }
}
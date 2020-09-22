package Beta;

import java.util.ArrayList;

public class CubeData {
    private ArrayList<Point3D> allVertices;
    private ArrayList<Point2D> allTextures;
    private ArrayList<Point3D> allNormals;

    public CubeData(){
        allVertices = new ArrayList<>();
        allTextures = new ArrayList<>();
        allNormals = new ArrayList<>();
    }

    public void addVertex(Point3D vertex){
        this.allVertices.add(vertex);
    }

    public void addTexture(Point2D texture){
        this.allTextures.add(texture);
    }

    public void addNormal(Point3D normal){
        this.allNormals.add(normal);
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
}

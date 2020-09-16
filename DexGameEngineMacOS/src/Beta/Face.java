package Beta;

import java.util.ArrayList;

public class Face {
    private ArrayList<Point3D> vertices;
    private Point3D normal;

    private static final Point3D V1 = new Point3D(-1, 1, 1);
    private static final Point3D V2 = new Point3D(1, 1, 1);
    private static final Point3D V3 = new Point3D(1, 1, -1);
    private static final Point3D V4 = new Point3D(-1, 1, -1);
    private static final Point3D V5 = new Point3D(-1, -1, -1);
    private static final Point3D V6 = new Point3D(1, -1, -1);
    private static final Point3D V7 = new Point3D(1, -1, 1);
    private static final Point3D V8 = new Point3D(-1, -1, 1);

    public Face(FaceOption option){
        vertices = new ArrayList<>();
        switch (option) {
            case TOP -> {
                vertices.add(V1);
                vertices.add(V2);
                vertices.add(V3);
                vertices.add(V4);
                normal = new Point3D(0, 1, 0);
            }
            case BOTTOM -> {
                vertices.add(V5);
                vertices.add(V6);
                vertices.add(V7);
                vertices.add(V8);
                normal = new Point3D(0, -1, 0);
            }
            case FRONT -> {
                vertices.add(V4);
                vertices.add(V3);
                vertices.add(V6);
                vertices.add(V5);
                normal = new Point3D(0, 0, -1);
            }
            case BACK -> {
                vertices.add(V2);
                vertices.add(V1);
                vertices.add(V8);
                vertices.add(V7);
                normal = new Point3D(0, 0, 1);
            }
            case LEFT -> {
                vertices.add(V1);
                vertices.add(V4);
                vertices.add(V5);
                vertices.add(V8);
                normal = new Point3D(-1, 0, 0);
            }
            case RIGHT -> {
                vertices.add(V3);
                vertices.add(V2);
                vertices.add(V7);
                vertices.add(V6);
                normal = new Point3D(1, 0, 0);
            }
        }
    }

    public ArrayList<Point3D> getVertices() {
        return vertices;
    }

    public ArrayList<Point2D> getTextures() {
        ArrayList<Point2D> textures = new ArrayList<>();
        textures.add(new Point2D(0,1));
        textures.add(new Point2D(1,1));
        textures.add(new Point2D(1,0));
        textures.add(new Point2D(0,0));
        return textures;
    }

    public ArrayList<Point3D> getNormals() {
        ArrayList<Point3D> normals = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            normals.add(normal);
        }
        return normals;
    }

    public ArrayList<Integer> getIndices(int multiplier){
        ArrayList<Integer> indices = new ArrayList<>();
        indices.add((4 * multiplier));
        indices.add(1 + (4 * multiplier));
        indices.add(2 + (4 * multiplier));
        indices.add((4 * multiplier));
        indices.add(2 + (4 * multiplier));
        indices.add(3 + (4 * multiplier));
        return indices;
    }
}

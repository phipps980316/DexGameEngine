package Beta;

import java.util.ArrayList;

public class Face {
    private final ArrayList<Vertex> vertices;

    private static final Point3D V1 = new Point3D(0, 1, 1);
    private static final Point3D V2 = new Point3D(1, 1, 1);
    private static final Point3D V3 = new Point3D(1, 1, 0);
    private static final Point3D V4 = new Point3D(0, 1, 0);
    private static final Point3D V5 = new Point3D(0, 0, 0);
    private static final Point3D V6 = new Point3D(1, 0, 0);
    private static final Point3D V7 = new Point3D(1, 0, 1);
    private static final Point3D V8 = new Point3D(0, 0, 1);

    private static final Point2D texTopLeft = new Point2D(0,1);
    private static final Point2D texTopRight = new Point2D(1,1);
    private static final Point2D texBottomRight = new Point2D(1,0);
    private static final Point2D texBottomLeft = new Point2D(0,0);

    public Face(FaceOption option){
        vertices = new ArrayList<>();
        Point3D normal;
        switch (option) {
            case TOP -> {
                normal = new Point3D(0, 1, 0);
                vertices.add(new Vertex(V1, texTopLeft, normal));
                vertices.add(new Vertex(V2, texTopRight, normal));
                vertices.add(new Vertex(V3, texBottomRight, normal));
                vertices.add(new Vertex(V4, texBottomLeft, normal));
            }
            case BOTTOM -> {
                normal = new Point3D(0, -1, 0);
                vertices.add(new Vertex(V5, texTopLeft, normal));
                vertices.add(new Vertex(V6, texTopRight, normal));
                vertices.add(new Vertex(V7, texBottomRight, normal));
                vertices.add(new Vertex(V8, texBottomLeft, normal));
            }
            case FRONT -> {
                normal = new Point3D(0, 0, -1);
                vertices.add(new Vertex(V4, texTopLeft, normal));
                vertices.add(new Vertex(V3, texTopRight, normal));
                vertices.add(new Vertex(V6, texBottomRight, normal));
                vertices.add(new Vertex(V5, texBottomLeft, normal));
            }
            case BACK -> {
                normal = new Point3D(0, 0, 1);
                vertices.add(new Vertex(V2, texTopLeft, normal));
                vertices.add(new Vertex(V1, texTopRight, normal));
                vertices.add(new Vertex(V8, texBottomRight, normal));
                vertices.add(new Vertex(V7, texBottomLeft, normal));
            }
            case LEFT -> {
                normal = new Point3D(-1, 0, 0);
                vertices.add(new Vertex(V1, texTopLeft, normal));
                vertices.add(new Vertex(V4, texTopRight, normal));
                vertices.add(new Vertex(V5, texBottomRight, normal));
                vertices.add(new Vertex(V8, texBottomLeft, normal));
            }
            case RIGHT -> {
                normal = new Point3D(1, 0, 0);
                vertices.add(new Vertex(V3, texTopLeft, normal));
                vertices.add(new Vertex(V2, texTopRight, normal));
                vertices.add(new Vertex(V7, texBottomRight, normal));
                vertices.add(new Vertex(V6, texBottomLeft, normal));
            }
        }
    }

    public ArrayList<Vertex> getVertices() {
        return vertices;
    }

    public int[] getIndices(int faceNumber){
        return new int[]{(faceNumber * 4),1 + (faceNumber*4),2 + (faceNumber*4), (faceNumber * 4),2 + (faceNumber*4),3 + (faceNumber*4)};
    }
}

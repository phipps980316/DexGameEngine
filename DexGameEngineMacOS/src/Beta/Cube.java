package Beta;

import Models.ModelData;

import java.util.ArrayList;

public class Cube {
    private ArrayList<Face> faces;

    private float[] vertices;
    private float[] textures;
    private float[] normals;
    private int[] indices;

    public Cube(FaceOption[] faceOptions){
        faces = new ArrayList<>();
        for (FaceOption faceOption : faceOptions) {
            faces.add(new Face(faceOption));
        }
        vertices = new float[faceOptions.length*12];
        textures = new float[faceOptions.length*8];
        normals = new float[faceOptions.length*12];
        indices = new int[faceOptions.length*6];
    }

    public ModelData getRenderData() {
        ArrayList<Point3D> allVertices = new ArrayList<>();
        ArrayList<Point2D> allTextures = new ArrayList<>();
        ArrayList<Point3D> allNormals = new ArrayList<>();
        ArrayList<Integer> allIndices = new ArrayList<>();

        for (int i = 0; i < faces.size(); i++) {
            Face face = faces.get(i);
            allVertices.addAll(face.getVertices());
            allTextures.addAll(face.getTextures());
            allNormals.addAll(face.getNormals());
            allIndices.addAll(face.getIndices(i));
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

        for (int i = 0; i < allIndices.size(); i++){
            this.indices[i] = allIndices.get(i);
        }

        return new ModelData(this.vertices, this.textures, this.normals, this.indices, 0);

    }

}

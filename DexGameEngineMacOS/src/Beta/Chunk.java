package Beta;

import Models.ModelData;

import java.util.ArrayList;
import java.util.Arrays;

public class Chunk {
    private final Cube[][][] cubes;
    private final int[][] heights;
    private int faceCount;
    private final ArrayList<Point3D> visibleCubes;
    private final int size;
    private final int scale;
    private final int maxHeight;

    public Chunk(int size, int scale){
        this.maxHeight = size*size;
        cubes = new Cube[size][maxHeight][size];
        heights = new int[size][size];
        visibleCubes = new ArrayList<>();
        this.faceCount = 0;
        this.size = size;
        this.scale = scale;

        for(int[] row : heights){
            Arrays.fill(row, size - 1);
        }

        for(int x = 0; x < size; x++){
            for(int y = 0; y < size; y++){
                for(int z = 0; z < size; z++){
                    if(heights[x][z] == y){
                        ArrayList<FaceOption> faceOptions = calculateFaces(x,y,z);
                        cubes[x][y][z] = new Cube(faceOptions, x, y, z);
                        visibleCubes.add(new Point3D(x, y, z));
                    }
                }
            }
        }
    }

    private ArrayList<FaceOption> calculateFaces(int x, int y, int z){
        ArrayList<FaceOption> faceOptions = new ArrayList<>();
        if(x > 0){
            if(cubes[x-1][y][z] == null) {
                addFace(faceOptions, FaceOption.LEFT);
            }
            else {
                removeFace(cubes[x-1][y][z], FaceOption.RIGHT);
            }
        } else {
            addFace(faceOptions, FaceOption.LEFT);
        }

        if(x < size - 2){
            if(cubes[x + 1][y][z] == null) {
                addFace(faceOptions, FaceOption.RIGHT);
            }
            else {
                removeFace(cubes[x+1][y][z], FaceOption.LEFT);
            }
        } else {
            addFace(faceOptions, FaceOption.RIGHT);
        }

        if(y > 0){
            if(cubes[x][y - 1][z] == null) {
                addFace(faceOptions, FaceOption.BOTTOM);
            }
            else {
                removeFace(cubes[x][y-1][z], FaceOption.TOP);
            }
        } else {
            addFace(faceOptions, FaceOption.BOTTOM);
        }

        if(y < size - 2){
            if(cubes[x][y + 1][z] == null) {
                addFace(faceOptions, FaceOption.TOP);
            }
            else {
                removeFace(cubes[x][y+1][z], FaceOption.BOTTOM);
            }
        } else {
            addFace(faceOptions, FaceOption.TOP);
        }

        if(z > 0){
            if(cubes[x][y][z-1] == null) {
                addFace(faceOptions, FaceOption.FRONT);
            }
            else {
                removeFace(cubes[x][y][z-1], FaceOption.BACK);
            }
        } else {
            addFace(faceOptions, FaceOption.FRONT);
        }

        if(z < size - 2){
            if(cubes[x][y][z+1] == null) {
                addFace(faceOptions, FaceOption.BACK);
            }
            else {
                removeFace(cubes[x][y][z+1], FaceOption.FRONT);
            }
        } else {
            addFace(faceOptions, FaceOption.BACK);
        }
        return faceOptions;
    }

    private void addFace(ArrayList<FaceOption> faceOptions, FaceOption faceOption){
        faceOptions.add(faceOption);
        faceCount++;
    }

    private void removeFace(Cube cube, FaceOption faceOption){
        cube.removeFace(faceOption);
        faceCount--;

    }

    public void addBlock(int x, int y, int z){
        if(x > 0 && y > 0 && z > 0 && x < size && y < maxHeight && z < size){
            if(cubes[x][y][z] == null){
                ArrayList<FaceOption> faceOptions = calculateFaces(x,y,z);
                cubes[x][y][z] = new Cube(faceOptions, x, y, z);
                visibleCubes.add(new Point3D(x, y, z));
                heights[x][z]++;
            }
        }
    }

    public int getHeight(float x, float z){
        int cubeX = (int) ((x/scale) % size);
        int cubeZ = (int) ((z/scale) % size);
        return (heights[cubeX][cubeZ]+1)*scale;
    }

    public ModelData getRenderData() {
        float[] vertices = new float[faceCount*18];
        float[] textures = new float[faceCount*12];
        float[] normals = new float[faceCount*18];

        int vertexCount = 0;

        for (Point3D pos : visibleCubes) {
            Cube cube = cubes[(int) pos.x][(int) pos.y][(int) pos.z];
            if (cube.getFaceCount() > 0) {
                cube.constructCube();
                for (int j = 0; j < cube.getAllVertices().size(); j++) {
                    Point3D vertex = cube.getAllVertices().get(j);
                    vertices[vertexCount * 3] = vertex.x;
                    vertices[vertexCount * 3 + 1] = vertex.y;
                    vertices[vertexCount * 3 + 2] = vertex.z;

                    Point2D texture = cube.getAllTextures().get(j);
                    textures[vertexCount * 2] = texture.x;
                    textures[vertexCount * 2 + 1] = texture.y;

                    Point3D normal = cube.getAllNormals().get(j);
                    normals[vertexCount * 3] = normal.x;
                    normals[vertexCount * 3 + 1] = normal.y;
                    normals[vertexCount * 3 + 2] = normal.z;

                    vertexCount++;
                }
            }
        }
        return new ModelData(vertices, textures, normals, vertexCount);
    }
}

package Beta;

import Models.ModelData;

import java.util.ArrayList;

public class Chunk {
    private Cube[][][] cubes;
    private int size;
    private int faceCount;

    public Chunk(int size){
        cubes = new Cube[size][size][size*size];
        this.size = size;
        this.faceCount = 0;

        for(int x = 0; x < size; x++){
            for(int y = 0; y < size; y++){
                for(int z = 0; z < size*size; z++){
                    System.out.println("Processing X: " + x + ", Y: " + y + ", Z: " + z);
                    ArrayList<FaceOption> faceOptions = new ArrayList<>();
                    if(x > 0){
                        if(cubes[x - 1][y][z] == null) {
                            faceOptions.add(FaceOption.LEFT);
                            faceCount++;
                        }
                        else {
                            cubes[x-1][y][z].removeFace(FaceOption.RIGHT);
                            faceCount--;
                        }
                    } else {
                        faceOptions.add(FaceOption.LEFT);
                        faceCount++;
                    }

                    if(x < size - 2){
                        if(cubes[x + 1][y][z] == null) {
                            faceOptions.add(FaceOption.RIGHT);
                            faceCount++;
                        }
                        else {
                            cubes[x+1][y][z].removeFace(FaceOption.LEFT);
                            faceCount--;
                        }
                    } else {
                        faceOptions.add(FaceOption.RIGHT);
                        faceCount++;
                    }

                    if(y > 0){
                        if(cubes[x][y - 1][z] == null) {
                            faceOptions.add(FaceOption.BOTTOM);
                            faceCount++;
                        }
                        else {
                            cubes[x][y-1][z].removeFace(FaceOption.TOP);
                            faceCount--;
                        }
                    } else {
                        faceOptions.add(FaceOption.BOTTOM);
                        faceCount++;
                    }

                    if(y < size - 2){
                        if(cubes[x][y + 1][z] == null) {
                            faceOptions.add(FaceOption.TOP);
                            faceCount++;
                        }
                        else {
                            cubes[x][y + 1][z].removeFace(FaceOption.BOTTOM);
                            faceCount--;
                        }
                    } else {
                        faceOptions.add(FaceOption.TOP);
                        faceCount++;
                    }

                    if(z > 0){
                        if(cubes[x][y][z - 1] == null) {
                            faceOptions.add(FaceOption.FRONT);
                            faceCount++;
                        }
                        else {
                            cubes[x][y][z - 1].removeFace(FaceOption.BACK);
                            faceCount--;
                        }
                    } else {
                        faceOptions.add(FaceOption.FRONT);
                        faceCount++;
                    }

                    if(z < size - 2){
                        if(cubes[x][y][z + 1] == null) {
                            faceOptions.add(FaceOption.BACK);
                            faceCount++;
                        }
                        else {
                            cubes[x][y][z+1].removeFace(FaceOption.FRONT);
                            faceCount--;
                        }
                    } else {
                        faceOptions.add(FaceOption.BACK);
                        faceCount++;
                    }

                    cubes[x][y][z] = new Cube(faceOptions, x, y, z);
                }
            }
        }
    }

    public ModelData getRenderData() {
        float[] vertices = new float[faceCount*18];
        float[] textures = new float[faceCount*12];
        float[] normals = new float[faceCount*18];

        int vertexCount = 0;

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                for (int z = 0; z < size; z++) {
                    Cube cube = cubes[x][y][z];
                    cube.constructCube();
                    for(int i = 0; i < cube.getAllVertices().size(); i++){
                        Point3D vertex = cube.getAllVertices().get(i);
                        vertices[vertexCount * 3] = vertex.x;
                        vertices[vertexCount * 3 + 1] = vertex.y;
                        vertices[vertexCount * 3 + 2] = vertex.z;

                        Point2D texture = cube.getAllTextures().get(i);
                        textures[vertexCount * 2] = texture.x;
                        textures[vertexCount * 2 + 1] = texture.y;

                        Point3D normal = cube.getAllNormals().get(i);
                        normals[vertexCount * 3] = normal.x;
                        normals[vertexCount * 3 + 1] = normal.y;
                        normals[vertexCount * 3 + 2] = normal.z;

                        vertexCount++;
                    }
                }
            }
        }

        return new ModelData(vertices, textures, normals, vertexCount);
    }
}

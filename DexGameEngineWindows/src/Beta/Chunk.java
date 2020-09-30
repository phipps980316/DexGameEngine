package Beta;

import Models.ModelData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Chunk {
    private final HashMap<Point3D, Cube> cubes;
    boolean[][][] possibleCubes;
    private int faceCount;

    public Chunk(int[][] heights){
        cubes = new HashMap<>();
        possibleCubes = new boolean[WorldSettings.CHUNK_SIZE][WorldSettings.TERRAIN_TOTAL_HEIGHT][WorldSettings.CHUNK_SIZE];
        this.faceCount = 0;

        for(int x = 0; x < WorldSettings.CHUNK_SIZE; x++){
            for(int z = 0; z < WorldSettings.CHUNK_SIZE; z++){
                for(int y = 0; y <= heights[x][z]; y++){
                    possibleCubes[x][y][z] = true;
                }
            }
        }
    }

    public void placeCubes(int[][] heights, Chunk leftChunk, Chunk rightChunk, Chunk frontChunk, Chunk backChunk){
        for(int x = 0; x < WorldSettings.CHUNK_SIZE; x++){
            for(int z = 0; z < WorldSettings.CHUNK_SIZE; z++){
                for(int y = 0; y <= heights[x][z]; y++){
                    if(possibleCubes[x][y][z]){
                        Cube cube = makeCube(x, y, z, leftChunk, rightChunk, frontChunk, backChunk);
                        if(cube != null){
                            cubes.put(new Point3D(x, y, z), cube);
                            faceCount += cube.getFaceCount();
                        }
                    }
                }
            }
        }
    }

    private Cube makeCube(int x, int y, int z, Chunk leftChunk, Chunk rightChunk, Chunk frontChunk, Chunk backChunk){
        boolean visibleFromLeft;
        boolean visibleFromRight;
        boolean visibleFromTop;
        boolean visibleFromBottom;
        boolean visibleFromFront;
        boolean visibleFromBack;

        if (x > 0) {
            visibleFromLeft = !possibleCubes[x - 1][y][z];
        } else if(leftChunk != null) {
            visibleFromLeft = !leftChunk.getPossibleCubes()[WorldSettings.CHUNK_SIZE - 1][y][z];
        } else {
            visibleFromLeft = false;
        }

        if (x < WorldSettings.CHUNK_SIZE - 1) {
            visibleFromRight = !possibleCubes[x + 1][y][z];
        } else if(rightChunk != null) {
            visibleFromRight = !rightChunk.getPossibleCubes()[0][y][z];
        } else {
            visibleFromRight = false;
        }

        if (y > 0) {
            visibleFromBottom = !possibleCubes[x][y - 1][z];
        } else {
            visibleFromBottom = false;
        }

        if (y < WorldSettings.TERRAIN_TOTAL_HEIGHT - 1) {
            visibleFromTop = !possibleCubes[x][y + 1][z];
        } else {
            visibleFromTop = false;
        }

        if (z > 0) {
            visibleFromFront = !possibleCubes[x][y][z - 1];
        } else if(frontChunk != null) {
            visibleFromFront = !frontChunk.getPossibleCubes()[x][y][WorldSettings.CHUNK_SIZE - 1];
        } else {
            visibleFromFront = false;
        }

        if (z < WorldSettings.CHUNK_SIZE - 1) {
            visibleFromBack = !possibleCubes[x][y][z + 1];
        } else if(backChunk != null) {
            visibleFromBack = !backChunk.getPossibleCubes()[x][y][0];
        } else {
            visibleFromBack = false;
        }

        ArrayList<FaceOption> faceOptions = new ArrayList<>();
        if(visibleFromLeft) faceOptions.add(FaceOption.LEFT);
        if(visibleFromRight) faceOptions.add(FaceOption.RIGHT);
        if(visibleFromTop) faceOptions.add(FaceOption.TOP);
        if(visibleFromBottom) faceOptions.add(FaceOption.BOTTOM);
        if(visibleFromFront) faceOptions.add(FaceOption.FRONT);
        if(visibleFromBack) faceOptions.add(FaceOption.BACK);

        if(faceOptions.size() > 0){
            return new Cube(faceOptions, x, y, z);
        } else {
            return null;
        }
    }

    /*public void addBlock(int x, int y, int z){
        if(x > 0 && y > 0 && z > 0 && x < WorldSettings.CHUNK_SIZE && y < WorldSettings.MAX_HEIGHT && z < WorldSettings.CHUNK_SIZE){
            if(cubes[x][y][z] == null){
                ArrayList<FaceOption> faceOptions = calculateFaces(x,y,z);
                cubes[x][y][z] = new Cube(faceOptions, x, y, z);
                populatedCubes.add(new Point3D(x, y, z));
            }
        }
    }*/

    public ModelData getRenderData() {
        float[] vertices = new float[faceCount*12];
        float[] textures = new float[faceCount*8];
        float[] normals = new float[faceCount*12];
        int[] indices = new int[faceCount*6];

        int currentVertexCount = 0;
        int currentIndexCount = 0;
        int currentFaceCount = 0;
        int indexOffset = 0;

        for (Map.Entry<Point3D, Cube> cubeEntry : cubes.entrySet()) {
            Cube cube = cubeEntry.getValue();
            CubeData cubeData = cube.constructCube();
            for (int i = 0; i < cubeData.getAllVertices().size(); i++) {
                Vertex currentVertex = cubeData.getAllVertices().get(i);
                vertices[currentVertexCount * 3] = currentVertex.getPosition().x;
                vertices[currentVertexCount * 3 + 1] = currentVertex.getPosition().y;
                vertices[currentVertexCount * 3 + 2] = currentVertex.getPosition().z;

                textures[currentVertexCount * 2] = currentVertex.getTexture().x;
                textures[currentVertexCount * 2 + 1] = currentVertex.getTexture().y;

                normals[currentVertexCount * 3] = currentVertex.getNormal().x;
                normals[currentVertexCount * 3 + 1] = currentVertex.getNormal().y;
                normals[currentVertexCount * 3 + 2] = currentVertex.getNormal().z;

                currentVertexCount++;
            }
            for (int i = 0; i < cubeData.getAllIndices().size(); i++){
                indices[currentIndexCount] = cubeData.getAllIndices().get(i) + indexOffset;
                currentIndexCount++;
            }
            currentFaceCount += cube.getFaceCount();
            indexOffset = currentFaceCount * 4;
        }
        return new ModelData(vertices, textures, normals, indices, 0);
    }

    public boolean[][][] getPossibleCubes() {
        return possibleCubes;
    }
}
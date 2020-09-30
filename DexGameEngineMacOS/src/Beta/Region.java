package Beta;

import Entities.Entity;
import Models.TexturedModel;
import RenderEngine.ModelLoader;
import Textures.ModelTexture;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;

public class Region {
    private final Chunk[][] chunks;
    private final ArrayList<Entity> chunkEntities;
    private int[][] heights;

    public ArrayList<Entity> getChunkEntities() {
        return chunkEntities;
    }

    /*public Region(ModelLoader modelLoader, ModelTexture texture){
        TerrainGenerator terrainGenerator = new TerrainGenerator(256, WorldSettings.TERRAIN_HEIGHT, 100, 20, WorldSettings.TERRAIN_SMOOTHNESS);
        heights = terrainGenerator.generateHeights();
        chunks = new Chunk[WorldSettings.REGION_SIZE][WorldSettings.REGION_SIZE];
        chunkEntities = new ArrayList<>();

        for (int x = 0; x < WorldSettings.REGION_SIZE; x++){
            for (int z = 0; z < WorldSettings.REGION_SIZE; z++){
                chunks[x][z] = new Chunk(getChunkHeights(x, z));
            }
        }

        for (int x = 0; x < WorldSettings.REGION_SIZE; x++){
            for (int z = 0; z < WorldSettings.REGION_SIZE; z++){

                TexturedModel chunkModel = new TexturedModel(modelLoader.loadModelToVAO(chunks[x][z].getRenderData()), texture);
                chunkEntities.add(new Entity(chunkModel, new Vector3f(x*WorldSettings.CHUNK_WIDTH_PIXELS, 0, z* WorldSettings.CHUNK_WIDTH_PIXELS), new Vector3f(0,0,0), WorldSettings.CUBE_SIZE));
            }
        }
    }*/

    public Region(ModelLoader modelLoader, ModelTexture texture){
        TerrainGenerator terrainGenerator = new TerrainGenerator(256, WorldSettings.TERRAIN_HEIGHT, 100, 20, WorldSettings.TERRAIN_SMOOTHNESS);
        heights = terrainGenerator.generateHeights();
        chunks = new Chunk[WorldSettings.REGION_SIZE][WorldSettings.REGION_SIZE];
        chunkEntities = new ArrayList<>();

        for (int x = 0; x < WorldSettings.REGION_SIZE; x++){
            for (int z = 0; z < WorldSettings.REGION_SIZE; z++){
                chunks[x][z] = new Chunk(getChunkHeights(x, z));
            }
        }

        for (int x = 0; x < WorldSettings.REGION_SIZE; x++){
            for (int z = 0; z < WorldSettings.REGION_SIZE; z++){
                Chunk left = null;
                Chunk right = null;
                Chunk front = null;
                Chunk back = null;

                if(x > 0) left = chunks[x - 1][z];
                if(x < WorldSettings.REGION_SIZE - 1) right = chunks[x + 1][z];
                if(z > 0) front = chunks[x][z - 1];
                if(z < WorldSettings.REGION_SIZE - 1) back = chunks[x][z + 1];

                chunks[x][z].placeCubes(getChunkHeights(x, z), left, right, front, back);
                TexturedModel chunkModel = new TexturedModel(modelLoader.loadModelToVAO(chunks[x][z].getRenderData()), texture);
                chunkEntities.add(new Entity(chunkModel, new Vector3f(x*WorldSettings.CHUNK_WIDTH_PIXELS, 0, z* WorldSettings.CHUNK_WIDTH_PIXELS), new Vector3f(0,0,0), WorldSettings.CUBE_SIZE));
            }
        }
    }

    public int heightAtPoint(float x, float z){
        int posX = (int) Math.floor(x/WorldSettings.CUBE_SIZE);
        int posZ = (int) Math.floor(z/WorldSettings.CUBE_SIZE);
        if((posX >= 0 && posX < WorldSettings.REGION_WIDTH_CUBES) && (posZ >= 0 && posZ < WorldSettings.REGION_WIDTH_CUBES)){
            return heights[posX][posZ]*WorldSettings.CUBE_SIZE;
        }
        return WorldSettings.CHUNK_SIZE*WorldSettings.CUBE_SIZE; //default out of bound height
    }

    public int getRegionSize() {
        return WorldSettings.REGION_WIDTH_PIXELS;
    }

    private int[][] getChunkHeights(int x, int z){
        int[][] chunkHeights = new int[WorldSettings.CHUNK_SIZE][WorldSettings.CHUNK_SIZE];
        for(int i = 0; i < WorldSettings.CHUNK_SIZE; i++){
            for(int j = 0; j < WorldSettings.CHUNK_SIZE; j++){
                chunkHeights[i][j] = this.heights[i + (x*WorldSettings.CHUNK_SIZE)][j + (z*WorldSettings.CHUNK_SIZE)];
            }
        }
        return chunkHeights;
    }
}

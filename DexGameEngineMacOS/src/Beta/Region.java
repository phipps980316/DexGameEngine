package Beta;

import Entities.Entity;
import Models.TexturedModel;
import RenderEngine.ModelLoader;
import Textures.ModelTexture;
import org.lwjgl.Sys;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;

public class Region {
    private final Chunk[][] chunks;
    private final int chunkSize;
    private final int regionSize;
    private final int scale;
    private final ArrayList<Entity> chunkEntities;

    public ArrayList<Entity> getChunkEntities() {
        return chunkEntities;
    }

    public Region(ModelLoader modelLoader, ModelTexture texture){
        this.chunkSize = WorldSettings.CHUNK_SIZE;
        int chunkWidth = WorldSettings.CHUNK_WIDTH;
        this.regionSize = WorldSettings.REGION_SIZE;
        this.scale = WorldSettings.CUBE_SIZE;
        chunks = new Chunk[regionSize][regionSize];
        chunkEntities = new ArrayList<>();

        int chunkNumber = 0;
        for (int x = 0; x < regionSize; x++){
            for (int z = 0; z < regionSize; z++){
                chunks[x][z] = new Chunk();
                chunks[x][z].addBlock(15, 16, 2);
                TexturedModel chunkModel = new TexturedModel(modelLoader.loadCubeToVAO(chunks[x][z].getRenderData()), texture);
                chunkEntities.add(new Entity(chunkModel, new Vector3f(x*chunkWidth, 0, z* chunkWidth), new Vector3f(0,0,0), scale));
                chunkNumber++;
                System.out.println(chunkNumber);
            }
        }
    }

    public int heightAtPoint(float x, float z){
        int posX = (int) Math.floor(x/(regionSize*scale));
        int posZ = (int) Math.floor(z/(regionSize*scale));
        if((posX >= 0 && posX < regionSize) && (posZ >= 0 && posZ < regionSize)){
            return chunks[posX][posZ].getHeight(x, z);
        }
        return chunkSize*scale; //default out of bound height
    }

    public int getRegionSize() {
        return WorldSettings.REGION_WIDTH;
    }
}

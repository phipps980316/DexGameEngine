package Beta;

import Entities.Entity;
import Models.TexturedModel;
import RenderEngine.ModelLoader;
import Textures.ModelTexture;
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

    public int heightAtPoint(float x, float z){
        int posX = (int) Math.floor(x/(regionSize*scale));
        int posZ = (int) Math.floor(z/(regionSize*scale));
        if((posX >= 0 && posX < regionSize) && (posZ >= 0 && posZ < regionSize)){
            return chunks[posX][posZ].getHeight(x, z);
        }
        return chunkSize*scale; //default out of bound height
    }

    public Region(int chunkSize, int regionSize, int scale, ModelLoader modelLoader, ModelTexture texture){
        this.chunkSize = chunkSize;
        this.regionSize = regionSize;
        this.scale = scale;
        chunks = new Chunk[regionSize][regionSize];
        chunkEntities = new ArrayList<>();

        for (int x = 0; x < regionSize; x++){
            for (int z = 0; z < regionSize; z++){
                chunks[x][z] = new Chunk(chunkSize, scale);
                TexturedModel chunkModel = new TexturedModel(modelLoader.loadToVAO(chunks[x][z].getRenderData()), texture);
                chunkEntities.add(new Entity(chunkModel, new Vector3f(x*chunkSize*scale, 0, z*chunkSize*scale), new Vector3f(0,0,0), scale));
            }
        }
    }

    public int getChunkSize() {
        return chunkSize;
    }

    public int getRegionSize() {
        return regionSize;
    }

    public int getScale() {
        return scale;
    }
}

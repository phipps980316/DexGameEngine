package Beta;

public class WorldSettings {
    public final static int CUBE_SIZE = 10;
    public final static int CHUNK_SIZE = 16;
    public final static int REGION_SIZE = 64;

    public final static int TERRAIN_SMOOTHNESS = 15; //Must be odd
    public final static int TERRAIN_HEIGHT = 128;
    public final static int TERRAIN_DEPTH = 256 - TERRAIN_HEIGHT;
    public final static int TERRAIN_TOTAL_HEIGHT = 256;

    public final static int CHUNK_WIDTH_PIXELS = CUBE_SIZE * CHUNK_SIZE;
    public final static int REGION_WIDTH_PIXELS = REGION_SIZE * CHUNK_WIDTH_PIXELS;

    public final static int REGION_WIDTH_CUBES = REGION_SIZE * CHUNK_SIZE;
}

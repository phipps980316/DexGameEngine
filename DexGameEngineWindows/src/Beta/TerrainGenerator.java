package Beta;

import java.util.Arrays;
import java.util.Random;

public class TerrainGenerator {
    private float[][] noise;
    private final Random random;
    private final int seed;
    private final int size;

    public TerrainGenerator(int size, int levels, int runs, int maxPatchSize, int sigma){
        this.noise = new float[size][size];
        this.random = new Random();
        this.seed = random.nextInt(1000000000);
        this.size = size;

        for(float[] array : noise){
            Arrays.fill(array, 1.0f);
        }

        this.random.setSeed(this.seed);

        float colorChange = 1f/levels;

        for(int i = 0; i < levels; i++){
            for(int j = 0; j < runs; j++){
                int patchSize = this.random.nextInt(maxPatchSize);
                int centerX = this.random.nextInt(size);
                int centerY = this.random.nextInt(size);

                for(int x = centerX - patchSize; x < centerX + patchSize; x++){
                    for(int y = centerY - patchSize; y < centerY + patchSize; y++){
                        if(x > 0 && x < size && y > 0 && y < size) this.noise[x][y] -= colorChange;
                    }
                }
            }
        }
        blur(3*sigma, sigma);
    }

    private void blur(int kernelSize, int sigma){
        int center = kernelSize/2;
        float[][] blurKernel = generateBlurKernel(kernelSize, sigma, center);
        float[][] processedImage = new float[size][size];

        for(int x = 0; x < size; x++){
            for(int y = 0; y < size; y++){
                for(int posX = 0; posX < kernelSize; posX++){
                    for(int posY = 0; posY < kernelSize; posY++){
                        int offsetX = posX - (kernelSize/2);
                        int offsetY = posY - (kernelSize/2);
                        if(((x + offsetX) > 0)&&((x + offsetX) < size)&&((y + offsetY) > 0)&&((y + offsetY) < size)){
                            processedImage[x][y] += noise[x+offsetX][y+offsetY] * blurKernel[posX][posY];
                        }
                    }
                }
            }
        }
        noise = processedImage;
    }

    private float[][] generateBlurKernel(int kernelSize, float sigma, int center){
        float[][] coefficients = new float[kernelSize][kernelSize];
        float coefficientSum = 0.0f;

        if(kernelSize % 2 == 1 && sigma > 0){
            for(int x = 0; x < kernelSize; x++){
                for(int y = 0; y < kernelSize; y++){
                    int distFromCenterX = Math.abs(x - center);
                    int distFromCenterY = Math.abs(y - center);
                    float value = (float) ((1/(2*Math.PI*Math.pow(sigma, 2)))*Math.exp(-((Math.pow(distFromCenterX, 2) + Math.pow(distFromCenterY, 2))/(2*Math.pow(sigma, 2)))));
                    coefficients[x][y] = value;
                    coefficientSum += value;
                }
            }
        }

        for(int x = 0; x < kernelSize; x++){
            for(int y = 0; y < kernelSize; y++){
                coefficients[x][y] = coefficients[x][y] * (1/coefficientSum);
            }
        }

        return coefficients;
    }

    public int[][] generateHeights() {
        int[][] heights = new int[WorldSettings.REGION_WIDTH_CUBES][WorldSettings.REGION_WIDTH_CUBES];
        for(int i = 0; i < WorldSettings.REGION_WIDTH_CUBES; i++){
            for(int j = 0; j < WorldSettings.REGION_WIDTH_CUBES; j++){
                heights[i][j] = (int) Math.ceil(noise[i * size/WorldSettings.REGION_WIDTH_CUBES][j * size/WorldSettings.REGION_WIDTH_CUBES] * WorldSettings.TERRAIN_HEIGHT);
                heights[i][j] += WorldSettings.TERRAIN_DEPTH;
            }
        }
        return heights;
    }
}

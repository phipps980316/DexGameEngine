package Beta;

import java.util.ArrayList;

public class HitBox {
    float minX;
    float maxX;
    float minY;
    float maxY;
    float minZ;
    float maxZ;

    public HitBox(ArrayList<Vertex> allVertices){
        minX = Integer.MAX_VALUE;
        maxX = Integer.MIN_VALUE;
        minY = Integer.MAX_VALUE;
        maxY = Integer.MIN_VALUE;
        minZ = Integer.MAX_VALUE;
        maxZ = Integer.MIN_VALUE;

        for(Vertex vertex : allVertices){
            float x = vertex.getPosition().x;
            if (x < minX) minX = x;
            if (x > maxX) maxX = x;

            float y = vertex.getPosition().y;
            if (y < minY) minY = y;
            if (y > maxY) maxY = y;

            float z = vertex.getPosition().z;
            if (z < minZ) minZ = z;
            if (z > maxZ) maxZ = z;
        }

        minX *= WorldSettings.CUBE_SIZE;
        maxX *= WorldSettings.CUBE_SIZE;
        minY *= WorldSettings.CUBE_SIZE;
        maxY *= WorldSettings.CUBE_SIZE;
        minZ *= WorldSettings.CUBE_SIZE;
        maxZ *= WorldSettings.CUBE_SIZE;
    }
}

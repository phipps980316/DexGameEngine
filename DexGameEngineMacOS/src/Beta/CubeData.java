package Beta;

import java.util.ArrayList;

public class CubeData {
    private final ArrayList<Vertex> allVertices;
    private final ArrayList<Integer> allIndices;

    public CubeData(){
        allVertices = new ArrayList<>();
        allIndices = new ArrayList<>();
    }

    public void addVertex(Vertex vertex){
        this.allVertices.add(vertex);
    }

    public void addIndices(int[] indices) {
        for(int index : indices){
            allIndices.add(index);
        }
    }

    public ArrayList<Vertex> getAllVertices() {
        return allVertices;
    }

    public ArrayList<Integer> getAllIndices() {
        return allIndices;
    }
}
